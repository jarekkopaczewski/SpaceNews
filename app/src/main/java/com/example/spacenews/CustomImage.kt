package com.example.spacenews

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso

@SuppressLint("ViewConstructor")
class CustomImage(context: Context?, id :Int) : ConstraintLayout(context!!)
{
    private var number: Int
    private var marked: Boolean = false
    private var star: ImageView
    private var queue: RequestQueue
    private var photo: ImageView
    private var title: TextView
    private var site: TextView

    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.image_news, this, true)
        photo = view.findViewById(R.id.photo)
        title = view.findViewById(R.id.titleView)
        site = view.findViewById(R.id.siteText)
        star = view.findViewById(R.id.markStar)
        queue = Volley.newRequestQueue(context)
        number = id

        val request = StringRequest(
            Request.Method.GET, "https://api.spaceflightnewsapi.net/v3/articles/$id",
            {
                val message = Gson().fromJson(it, Message::class.java)
                Picasso.get().load(message.imageUrl).into(photo);
                title.text = message.title
                site.text = message.newsSite
            })
        {
            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
        }
        queue.add(request)

        setListeners()
    }

    private fun setListeners()
    {
        star.setOnClickListener{
            marked = if(marked)
            {
                star.setImageResource(R.drawable.star_minus_outline)
                LikedArticles.removeFromLiked(number)
                false
            }
            else
            {
                star.setImageResource(R.drawable.star_check)
                LikedArticles.addToLiked(number)
                true
            }
        }
    }

    fun getNumber(): Int = this.number

    fun setStar()
    {
        marked = true
        star.setImageResource(R.drawable.star_check)
    }
}