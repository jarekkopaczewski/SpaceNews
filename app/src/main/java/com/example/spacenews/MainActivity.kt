package com.example.spacenews

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity()
{
    private var homeFragment = HomeFragment()
    private var newsFragment = NewsFragment()
    private var likeFragment = LikeFragment()
    private var profileFragment = ProfileFragment()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#1d2327")
        }

        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        val layout: RelativeLayout = findViewById(R.id.frame_layout)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            lateinit var selectedFragment: Fragment
            supportActionBar?.hide()
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = homeFragment
                R.id.nav_news -> {
                    newsFragment.reset()
                    selectedFragment = newsFragment}
                R.id.nav_like -> selectedFragment = likeFragment
                R.id.nav_profile -> selectedFragment = profileFragment
            }
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            transaction.replace(R.id.frame_layout, selectedFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            true
        }

    }
}