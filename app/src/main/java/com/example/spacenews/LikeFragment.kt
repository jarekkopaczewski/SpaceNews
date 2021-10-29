package com.example.spacenews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ScrollView

class LikeFragment : Fragment()
{
    private lateinit var layout: LinearLayout
    private lateinit var scroll: ScrollView
    private var list:MutableList<CustomImage> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_like, container, false)
        layout = view.findViewById(R.id.linearNews)
        scroll = view.findViewById(R.id.likedScroll)
        addArticles()
        return view;
    }

    private fun addArticles()
    {
        val arr = LikedArticles.getLikedArticles()
        for( id in arr )
        {
            val custom = CustomImage(context,id)
            if(LikedArticles.getLikedArticles().contains(id)) custom.setStar()
            layout.addView(custom)
            list.add(custom)
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

    }

    private fun animate(v: View)
    {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        v.startAnimation(zoomIn)
        v.startAnimation(zoomOut)
    }
}