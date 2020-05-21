package com.coolweather.coolweatherjetpack.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.data.model.account.TestDto
import com.coolweather.coolweatherjetpack.util.LogUtils
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.fragment_mmkv_test.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MmkvTestFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_mmkv_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button12.setOnClickListener { saveData() }
        button13.setOnClickListener { getData() }
    }

    private fun getData() {
        var item = MMKV.defaultMMKV().decodeParcelable("testDto",TestDto::class.java)
        LogUtils.d(""+item.name)
    }

    private fun saveData() {
        var testDto = TestDto("测试1",3)
        MMKV.defaultMMKV().encode("testDto",testDto)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MmkvTestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
