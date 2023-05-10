package com.example.app6

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainVPAdapter(fragmentActivity: MainPlantFragment) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
        return  when (position) {
            0 -> OneFragment()
            1 -> TwoFragment()
            2 -> ThreeFragment()
            else -> OneFragment()

        }
    }
}