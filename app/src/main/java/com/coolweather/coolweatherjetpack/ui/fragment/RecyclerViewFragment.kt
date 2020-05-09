package com.coolweather.coolweatherjetpack.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.adapter.TestAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RecyclerViewFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mRecyclerView:RecyclerView

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
        var view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        mRecyclerView = view.findViewById(R.id.rv)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        var lists = arrayOf("1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3")
        Log.e("+++",""+lists.size)
        var adapter = TestAdapter(lists.toMutableList())
        mRecyclerView.adapter = adapter

        var header = Button(requireActivity())
        header.text = "header"
        adapter.setHeaderView(header)

        var footer = Button(requireActivity())
        footer.text = "footer"
        adapter.setFooterView(footer)

        adapter.setOnItemClickListener { _, _, position -> Log.e("+++","click$position") }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecyclerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
