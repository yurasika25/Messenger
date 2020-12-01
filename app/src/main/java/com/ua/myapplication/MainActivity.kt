package com.ua.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class MainActivity : FragmentActivity(), OnPlusMinusCallback {

    private lateinit var pager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter

    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager = findViewById<View>(R.id.pager) as ViewPager
        pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter

        onPlus()
    }

    private inner class MyFragmentPagerAdapter(fm: FragmentManager) :
            FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int) = fragments[position]

        override fun getCount() = fragments.size

        override fun getItemPosition(`object`: Any) = POSITION_NONE
    }

    override fun onPlus() {
        val page = fragments.size + 1
        fragments += PageFragment.newInstance(page)
        pagerAdapter.notifyDataSetChanged()
        pager.setCurrentItem(page, true)
    }

    override fun onMinus() {
        fragments.removeLast()
        pagerAdapter.notifyDataSetChanged()
        pager.setCurrentItem(fragments.size - 1, true)
    }
}

