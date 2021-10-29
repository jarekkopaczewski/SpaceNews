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
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.parseColor("#1d2327")
        }

        // replace fragment when click on item in bottom navigation bar
        bottomNav.setOnNavigationItemSelectedListener { item ->
            lateinit var selectedFragment: Fragment
            var backStackName = ""
            when(item.itemId)
            {
                R.id.nav_home -> {
                    LikedArticles.setHomeCount(1)
                    selectedFragment = homeFragment
                    backStackName = "home"}
                R.id.nav_news -> {
                    LikedArticles.setCount(1)
                    selectedFragment = newsFragment
                    backStackName = "news"}
                R.id.nav_like -> {
                    selectedFragment = likeFragment
                    backStackName = "like"
                }
                R.id.nav_profile -> {
                    selectedFragment = profileFragment
                    backStackName = "profile"
                }
            }
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            transaction.replace(R.id.frame_layout, selectedFragment)
            transaction.addToBackStack(backStackName)
            transaction.commit()
            true
        }
    }
}