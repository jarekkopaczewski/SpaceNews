package com.example.spacenews

import android.content.Context
import android.content.Intent
import android.icu.number.IntegerWidth
import android.media.Image
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class CustomImage(context: Context?, id :Int) : ConstraintLayout(context!!)
{
    private var number: Int
    private var marked: Boolean = false
    private var star: ImageView

    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.image_news, this, true)
        val photo: ImageView = view.findViewById(R.id.photo)
        val title: TextView = view.findViewById(R.id.titleView)
        val site: TextView = view.findViewById(R.id.siteText)
        star = view.findViewById(R.id.markStar)
        number = id

        val queue = Volley.newRequestQueue(context)

        val request = StringRequest(
            Request.Method.GET, "https://api.spaceflightnewsapi.net/v3/articles/$id",
            {
                val message = Gson().fromJson(it, Message::class.java)
                Picasso.get().load(message.imageUrl).into(photo);
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
                LikedArticles.removeFromLiked(number)
                false
            } else {
                star.setImageResource(R.drawable.star_check)
                LikedArticles.addToLiked(number)
                true
            }
        }
    }

    fun getNumber(): Int
    {
        return number
    }

    fun setStar()
    {
        marked = true
        star.setImageResource(R.drawable.star_check)
    }
}