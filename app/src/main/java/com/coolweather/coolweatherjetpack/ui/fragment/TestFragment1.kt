package com.coolweather.coolweatherjetpack.ui.fragment

import android.Manifest
import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.util.LogUtil
import com.coolweather.coolweatherjetpack.util.UniqueIDUtils
import com.coolweather.coolweatherjetpack.util.Utils
import kotlinx.android.synthetic.main.fragment_test1.*
import org.jetbrains.anko.support.v4.toast


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TestFragment1 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button4.setOnClickListener { getAndroidId() }
        button5.setOnClickListener { getSerialNumber() }
        button6.setOnClickListener { getMac() }
        button7.setOnClickListener { getPhoneNum() }
    }

    private fun getPhoneNum() {

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE),100)
            return
        }else{
            val tm = requireActivity().getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            LogUtil.i("getPhoneNum1:"+tm.line1Number)
            LogUtil.i("getPhoneNum2"+tm.groupIdLevel1)
            toast("getPhoneNum1:"+tm.line1Number)
        }
    }

    private fun getMac() {
        LogUtil.i("设备唯一码："+UniqueIDUtils.getUniqueID(requireActivity()))
    }

    private fun getSerialNumber() {
        LogUtil.i("getSerialNumber")
    }

    private fun getAndroidId() {
        LogUtil.i("getAndroidId")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getPhoneNum()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TestFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
