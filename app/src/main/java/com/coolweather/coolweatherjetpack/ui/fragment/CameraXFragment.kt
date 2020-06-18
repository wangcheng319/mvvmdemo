package com.coolweather.coolweatherjetpack.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.*
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.coolweather.coolweatherjetpack.R
import com.dajiabao.common.util.BitmapUtil
import com.dajiabao.common.util.DisplayUtil
import com.dajiabao.common.util.ScreenUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_camera_x.*
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val REQUEST_CODE_PERMISSIONS = 10

// This is an array of all the permission specified in the manifest.
private val REQUIRED_PERMISSIONS =
    arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO)

class CameraXFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewFinder: TextureView
    private lateinit var btn: Button
    private lateinit var video:Button
    private lateinit var switch:Button

    private lateinit var preview:Preview
    private lateinit var previewConfig:PreviewConfig
    private lateinit var imageCaptureConfig:ImageCaptureConfig
    private lateinit var imageCapture:ImageCapture
    private lateinit var videoCapture:VideoCapture


    private val executor = Executors.newSingleThreadExecutor()
    private var lensFacing = CameraX.LensFacing.BACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_camera_x, container, false)
        viewFinder = view.findViewById(R.id.textureView)
        btn = view.findViewById(R.id.btn_photo)
        video = view.findViewById(R.id.btn_video)
        switch = view.findViewById(R.id.btn_switch)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }


    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        // Create configuration object for the viewfinder use case
        //CameraX会自动确定要使用的最佳分辨率，无须setTargetResolution
        previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(viewFinder.width, viewFinder.height))
        }.build()


        // Build the viewfinder use case
        preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
            }.build()

        // Build the image capture use case and attach button click listener

        /**
         * 各个存储目录对应情况
         *
         * requireActivity().externalMediaDirs.first() ======> /storage/emulated/0/Android/media/com.coolweather.coolweatherjetpack.dev/1587619745621.jpg
         *
         *  requireActivity().getExternalFilesDirs(null).first() =====> /storage/emulated/0/Android/data/com.coolweather.coolweatherjetpack.dev/files/1587620186095.jpg
         *
         *  requireActivity().getExternalFilesDirs("mphoto").first() =====> /storage/emulated/0/Android/data/com.coolweather.coolweatherjetpack.dev/files/mphoto/1587620282100.jpg
         *
         *  <<以上为私有目录，app删除后会删除对应的文件夹，这些文件夹的读写都不需要权限>>
         *
         *
         */
        imageCapture = ImageCapture(imageCaptureConfig)


        val  videoCaptureConfig = VideoCaptureConfig.Builder().apply {  }.build()
        videoCapture = VideoCapture(videoCaptureConfig)


        btn.setOnClickListener {
            val file = File(
                requireActivity().externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg"
            )

            if(file.isDirectory && !file.exists()){
                file.mkdir()
            }

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        viewFinder.post {
                            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        val msg = "Photo capture succeeded: ${file.absolutePath}"
                        Log.d("CameraXApp", msg)
//                        var bitmap = BitmapFactory.decodeFile(file.absolutePath)
//                        saveSignImage(file.name,bitmap)
                        viewFinder.post {
                            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        }
                        cropBitmap(file)
                    }
                })
        }

        video.setOnClickListener {
            val file = File(
                requireActivity().externalMediaDirs.first(),
                "${System.currentTimeMillis()}.mp4"
            )

            if(file.isDirectory && !file.exists()){
                file.mkdir()
            }

            videoCapture.startRecording(file,executor,object :VideoCapture.OnVideoSavedListener{
                override fun onVideoSaved(file: File) {
                    val msg = "Video capture succeeded: ${file.absolutePath}"
                    Log.d("CameraXApp", msg)
//                        var bitmap = BitmapFactory.decodeFile(file.absolutePath)
//                        saveSignImage(file.name,bitmap)
                    viewFinder.post {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(
                    videoCaptureError: VideoCapture.VideoCaptureError,
                    message: String,
                    cause: Throwable?
                ) {
                    val msg = "Video capture failed: $message"
                    Log.e("CameraXApp", msg, cause)
                    viewFinder.post {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }

            })

            object :CountDownTimer(5*1000,1000){
                override fun onFinish() {
                    videoCapture.stopRecording()
                    video.text = "录像"
                }

                override fun onTick(millisUntilFinished: Long) {
                    video.text = millisUntilFinished.div(1000).toString()
                    Log.e("CameraXApp", "millisUntilFinished:$millisUntilFinished")
                }

            }.start()
        }

        switch.setOnClickListener {
            lensFacing = if (CameraX.LensFacing.FRONT == lensFacing) {
                CameraX.LensFacing.BACK
            } else {
                CameraX.LensFacing.FRONT
            }
            CameraX.getCameraWithLensFacing(lensFacing)
            bindCameraUseCases()
        }


        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview,imageCapture, videoCapture)
    }

    /**
     * 裁剪
     */
    private fun cropBitmap(file: File) {
        var bitmap = BitmapFactory.decodeFile(file.absolutePath)


        var bitmap1 = BitmapUtil.cropBitmap(BitmapUtil.rotateBitmapByDegree(bitmap,BitmapUtil.getBitmapDegree(file.absolutePath)))


        val file = File(requireActivity().externalMediaDirs.first(), "crop.jpg")
        var fileOutputStream = FileOutputStream(file)
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()

    }

    fun dip2px(dipValue: Float): Int {
        return (dipValue * requireContext().resources.displayMetrics.density + 0.5f).toInt()
    }



    private fun startPoll() {
        Observable.interval(0, 5, TimeUnit.SECONDS)
            .subscribe(object : Observer<Long?> {

                override fun onError(e: Throwable) {

                }

                override fun onNext(t: Long) {

                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }

    private fun bindCameraUseCases() {
        CameraX.unbindAll()
        CameraX.bindToLifecycle(this, preview,imageCapture, videoCapture)
    }


    /**
     * 将文件保存到公共文件夹中
     */
    private fun saveSignImage( fileName:String,  bitmap:Bitmap) {
        try {
            //设置保存参数到ContentValues中
            var contentValues = ContentValues()
            //设置文件名
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            //兼容Android Q和以下版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
                //RELATIVE_PATH是相对路径不是绝对路径
                //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/test");
                //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
            } else {
                contentValues.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path);
            }
            //设置文件类型
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG")
            //执行insert操作，向系统文件夹中添加文件
            //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
            var uri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            if (uri != null) {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                var outputStream = requireContext().contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    outputStream.flush()
                    outputStream.close()
                }
            }
        } catch (e:Exception) {
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CameraX.unbindAll()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraXFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

