package com.example.spacenews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView

class LikeFragment : Fragment()
{
    private lateinit var layout: LinearLayout
    private lateinit var scroll: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_like, container, false)
        layout = view.findViewById(R.id.linearNews)
        scroll = view.findViewById(R.id.likedScroll)
        println("jest")
        addArticles()
        return view;
    }

    private fun addArticles()
    {
        var custom : CustomImage
        val arr = LikedArticles.getLikedArticles()
        for( id in arr )
        {
            println(id)
            custom = CustomImage(context,id)
            custom.setStar()
            layout.addView(custom)

            custom.setOnClickListener {
                val articleFragment = ArticleFragment()
                articleFragment.setID(custom.getNumber())
                activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace((view!!.parent as ViewGroup).id, articleFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}