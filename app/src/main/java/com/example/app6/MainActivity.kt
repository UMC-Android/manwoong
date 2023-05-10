package com.example.app6

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.app6.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.menuFrameLayout.id, MainHomeFragment())
            .commitAllowingStateLoss()

        binding.menuBottomNavigation.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.menuFrameLayout.id, MainHomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_plant -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.menuFrameLayout.id, MainPlantFragment())
                            .commitAllowingStateLoss()

                    }
                    R.id.menu_camera -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.menuFrameLayout.id, MainCameraFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_home
        }
    }
}
