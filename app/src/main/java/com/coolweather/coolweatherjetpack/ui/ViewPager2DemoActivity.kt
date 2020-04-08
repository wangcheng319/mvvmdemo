package com.coolweather.coolweatherjetpack.ui

import android.os.Bundle
import android.util.SparseArray
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
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private val NUM_PAGES = 5

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private var mPager: ViewPager2? = null

    private var fragment1:TestFragment1? = null
    private var fragment2:TestFragment2? = null
    private var fragment3:TestFragment3? = null

    var fragments:MutableList<Fragment> = mutableListOf()

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private var pagerAdapter: FragmentStateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_demo)
        // Instantiate a ViewPager and a PagerAdapter.
        fragment1 = TestFragment1.newInstance("","")
        fragment2 = TestFragment2.newInstance("","")
        fragment3 = TestFragment3.newInstance("","")

        fragments.add(fragment1!!)
        fragments.add(fragment2!!)
        fragments.add(fragment3!!)


        mPager = findViewById(R.id.pager)
        pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager?.orientation = ViewPager2.ORIENTATION_VERTICAL
        mPager?.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (mPager!!.currentItem == 0) { // If the user is currently looking at the first step, allow the system to handle the
// Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else { // Otherwise, select the previous step.
            mPager!!.currentItem = mPager!!.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
     class ScreenSlidePagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            when(position){
                0 -> TestFragment1.newInstance("","")
                1 -> TestFragment2.newInstance("","")
                2 -> TestFragment3.newInstance("","")
            }
            return TestFragment1.newInstance("","")
        }
    }
}
