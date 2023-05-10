package com.example.app6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.app6.databinding.FragmentMainCameraBinding
import me.relex.circleindicator.CircleIndicator3

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainCameraFragment : Fragment() {

    private var _binding: FragmentMainCameraBinding? = null
    private val binding get() = _binding!!

    private val numPage = 5

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainCameraBinding.inflate(inflater, container, false)
        val view = binding.root

        val mPager = binding.viewpager
        val mIndicator = binding.indicator

        val pagerAdapter = MyAdapter(requireActivity(), numPage)
        mPager.adapter = pagerAdapter

        mIndicator.setViewPager(mPager)
        mIndicator.createIndicators(numPage, 0)

        mPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        mPager.currentItem = 1000
        mPager.offscreenPageLimit = 4

        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    mPager.currentItem = position
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mIndicator.animatePageSelected(position % numPage)
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainCameraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
