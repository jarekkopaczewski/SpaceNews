package com.example.spacenews

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class NewsFragment : Fragment()
{
    private lateinit var layout: LinearLayout
    private lateinit var scroll: SwipeRefreshLayout
    private var currentId = 1
    private var list:MutableList<CustomImage> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view:View = inflater.inflate(R.layout.fragment_news, container, false)
        layout = view.findViewById(R.id.linearNew)
        scroll = view.findViewById(R.id.scrollNew)

        addArticles()

        layout.setOnClickListener{
            addArticles()
        }

        scroll.setOnRefreshListener {
            addArticles()
            scroll.isRefreshing = false
        }
        return view
    }

    private fun addArticles()
    {
        currentId = LikedArticles.getCount()
        for( i in 0..5 )
        {
            val custom = CustomImage(context,currentId)
            if(LikedArticles.getLikedArticles().contains(currentId)) custom.setStar()
            layout.addView(custom)
            list.add(custom)
            currentId++
            LikedArticles.setCount(currentId)
        }

        for(p in list)
        {
            p.setOnClickListener {
                animate(p)
                val articleFragment = ArticleFragment()
                articleFragment.setID(p.getNumber())
                activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace((view!!.parent as ViewGroup).id, articleFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        println("size = ${layout.size}")
    }

    private fun animate(v: View)
    {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        v.startAnimation(zoomIn)
        v.startAnimation(zoomOut)
    }

    fun reset()
    {
        currentId = 1;
    }
}