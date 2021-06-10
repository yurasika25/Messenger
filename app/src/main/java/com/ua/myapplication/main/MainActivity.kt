package com.ua.myapplication.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ua.myapplication.R
import com.ua.myapplication.`interface`.OnPlusMinusCallback
import com.ua.myapplication.fragment.PageFragment

const val KEY_PAGE = "page"

class MainActivity : FragmentActivity(), OnPlusMinusCallback {

    private lateinit var pager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager = findViewById<View>(R.id.pager) as ViewPager
        pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
        initFirstPage(1)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.hasExtra(KEY_PAGE)) {
            initFirstPage(intent.getIntExtra(KEY_PAGE, 1))
        }
    }

    private inner class MyFragmentPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int) = PageFragment.newInstance(position + 1)

        override fun getCount() = page

        override fun getItemPosition(`object`: Any) = POSITION_NONE
    }

    override fun onPlus() {
        page += 1
        pagerAdapter.notifyDataSetChanged()
        pager.setCurrentItem(page, true)
    }

    override fun onMinus() {
        if (page == 1) return
        page -= 1
        pagerAdapter.notifyDataSetChanged()
        pager.setCurrentItem(page - 1, true)
    }

    private fun initFirstPage(page: Int) {
        this.page = page
        pagerAdapter.notifyDataSetChanged()
        pager.setCurrentItem(page - 1, true)
    }
}

