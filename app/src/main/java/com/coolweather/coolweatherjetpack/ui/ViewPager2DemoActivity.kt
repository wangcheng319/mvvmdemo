package com.coolweather.coolweatherjetpack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.ui.fragment.TestFragment1
import com.coolweather.coolweatherjetpack.ui.fragment.TestFragment2
import com.coolweather.coolweatherjetpack.ui.fragment.TestFragment3


class ViewPager2DemoActivity : AppCompatActivity() {

    private var mPager: ViewPager2? = null

    private var fragment1:TestFragment1? = null
    private var fragment2:TestFragment2? = null
    private var fragment3:TestFragment3? = null

    var fragments:MutableList<Fragment> = mutableListOf()

    private var pagerAdapter: FragmentStateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_demo)

        fragment1 = TestFragment1.newInstance("","")
        fragment2 = TestFragment2.newInstance("","")
        fragment3 = TestFragment3.newInstance("","")

        fragments.add(fragment1!!)
        fragments.add(fragment2!!)
        fragments.add(fragment3!!)


        mPager = findViewById(R.id.pager)
        pagerAdapter = ScreenSlidePagerAdapter(this,fragments)
        mPager?.orientation = ViewPager2.ORIENTATION_VERTICAL
        mPager?.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (mPager!!.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager!!.currentItem = mPager!!.currentItem - 1
        }
    }


     class ScreenSlidePagerAdapter(
        fa: FragmentActivity?,
        private val fragments: MutableList<Fragment>
    ) : FragmentStateAdapter(fa!!) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}
