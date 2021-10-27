package com.example.spacenews

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL


class HomeFragment : Fragment()
{
    private lateinit var mMainImage: ImageView
    private lateinit var title: TextView
    private lateinit var site: TextView
    private lateinit var star: ImageView
    private var marked: Boolean = false
    private var mainArtId: Int = 5

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view:View =  inflater.inflate(R.layout.fragment_home, container, false)
        mMainImage = view.findViewById(R.id.mMainImage)
        title = view.findViewById(R.id.titleHome)
        site = view.findViewById(R.id.siteHome)
        star = view.findViewById(R.id.homeStar)
        val queue = Volley.newRequestQueue(context)

        val request = StringRequest(
            Request.Method.GET, "https://api.spaceflightnewsapi.net/v3/articles/$mainArtId",
            {
                val message = Gson().fromJson(it, Message::class.java)
                Picasso.get().load(message.imageUrl).into(mMainImage)
                title.text = message.title
                site.text = message.newsSite
            })
        {
            Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();
        }
        queue.add(request)

        star.setOnClickListener{
            marked = if(marked) {
                star.setImageResource(R.drawable.star_minus_outline)
                LikedArticles.removeFromLiked(mainArtId)
                false
            } else {
                star.setImageResource(R.drawable.star_check)
                LikedArticles.addToLiked(mainArtId)
                true
            }
        }

        mMainImage.setOnClickListener {
            animate(mMainImage)
            val articleFragment = ArticleFragment()
            articleFragment.setID(mainArtId)
            activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace((view!!.parent as ViewGroup).id, articleFragment)
                .addToBackStack(null)
                .commit()
        }

        if(LikedArticles.getLikedArticles().contains(mainArtId))
        {
            star.setImageResource(R.drawable.star_check)
        }

        return view
    }

    private fun animate(v: View)
    {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        v.startAnimation(zoomIn)
        v.startAnimation(zoomOut)
    }
}