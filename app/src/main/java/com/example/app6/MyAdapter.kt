package com.example.app6

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.app6.*


class MyAdapter(fa: FragmentActivity?, var mCount: Int) : FragmentStateAdapter(fa!!) {
    override fun createFragment(position: Int): Fragment {
        val index = getRealPosition(position)
        return if (index == 0) OneCamFragment()
        else if (index == 1) TwoCamFragment()
        else if (index == 2) ThreeCamFragment()
        else if (index == 3) FourCamFragment()
        else return FiveCamFragment()

    }

    override fun getItemCount(): Int {
        return 2000
    }

    fun getRealPosition(position: Int): Int {
        return position % mCount
    }
}