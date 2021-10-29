package com.example.spacenews

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class HomeFragment : Fragment()
{
    private lateinit var mMainImage: ImageView
    private lateinit var title: TextView
    private lateinit var site: TextView
    private lateinit var star: ImageView
    private lateinit var recycler: LinearLayout
    private lateinit var refreshHome: SwipeRefreshLayout
    private lateinit var homeScroll: NestedScrollView
    private lateinit var spaceship: ImageView
    private lateinit var menuButton: ImageView
    private lateinit var spaceName: ImageView
    private var marked: Boolean = false
    private var mainArtId: Int = 5
    private var currentId = 1
    private var list:MutableList<CustomRecyclerItem> = mutableListOf()
    private lateinit var queue: RequestQueue

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view:View =  inflater.inflate(R.layout.fragment_home, container, false)
        spaceName = view.findViewById(R.id.spaceNameImage)
        menuButton = view.findViewById(R.id.menuButton)
        mMainImage = view.findViewById(R.id.mMainImage)
        title = view.findViewById(R.id.titleHome)
        site = view.findViewById(R.id.siteHome)
        star = view.findViewById(R.id.homeStar)
        recycler = view.findViewById(R.id.recycler)
        refreshHome = view.findViewById(R.id.refreshHome)
        homeScroll = view.findViewById(R.id.homeScroll)
        spaceship = view.findViewById(R.id.spaceship)
        queue = Volley.newRequestQueue(context)

        if(LikedArticles.getLikedArticles().contains(mainArtId))
            star.setImageResource(R.drawable.star_check)

        makeUrlRequest()
        addArticles()
        setListeners()

        val left: Animation = AnimationUtils.loadAnimation(context, R.anim.left_in)
        spaceship.startAnimation(left)
        spaceName.startAnimation(left)

        return view
    }

    private fun makeUrlRequest()
    {
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
    }

    private fun setListeners()
    {

        refreshHome.setOnRefreshListener {
            addArticles()
            refreshHome.isRefreshing = false
        }

        menuButton.setOnClickListener {
            animate(menuButton)
            Toast.makeText(context, "Not available yet", Toast.LENGTH_LONG).show()
        }

        spaceship.setOnClickListener {
            val out: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
            spaceship.startAnimation(out)
        }

        spaceName.setOnClickListener {
            val bounce: Animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
            spaceship.startAnimation(bounce)
            spaceName.startAnimation(bounce)
        }

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
    }

    private fun addArticles()
    {
        currentId = LikedArticles.getHomeCount()
        for( i in 0..5 )
        {
            val custom = CustomRecyclerItem(context,currentId)
            if(LikedArticles.getLikedArticles().contains(currentId)) custom.setStar()
            recycler.addView(custom)
            list.add(custom)
            currentId++
            LikedArticles.setHomeCount(currentId)
        }

        for(p in list)
        {
            p.setOnClickListener {
                animate(p)
                LikedArticles.setHomeCount(0)
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