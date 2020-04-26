package com.coolweather.coolweatherjetpack.ui.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.TransformActivity
import kotlinx.android.synthetic.main.fragment_transform.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 页面要开启共享元素动画  需要设置AppTheme  <item name="android:windowActivityTransitions">true</item>
 */
class TransformFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_photo.setOnClickListener { toTransformActivity() }
    }

    private fun toTransformActivity() {
        val intent = Intent(requireActivity(), TransformActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), imageView2, "test_name")
        startActivity(intent, options.toBundle())
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransformFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
