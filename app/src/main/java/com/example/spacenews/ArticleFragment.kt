package com.example.spacenews

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ArticleFragment : Fragment()
{
    private var idParam: Int = 0
    private lateinit var message: Message
    private lateinit var mMainImage: ImageView
    private lateinit var summaryText: TextView
    private lateinit var titleText: TextView
    private lateinit var dateText: TextView
    private lateinit var editDateText: TextView
    private lateinit var backButton: ImageView
    private lateinit var shareButton: ImageView
    private lateinit var commentButton: ImageView
    private lateinit var queue: RequestQueue
    private lateinit var zoomIn: Animation
    private lateinit var zoomOut: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view:View =  inflater.inflate(R.layout.fragment_article, container, false)
        val bounce = AnimationUtils.loadAnimation(context, R.anim.bounce)
        zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        zoomOut = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        mMainImage = view.findViewById(R.id.articleImage)
        summaryText = view.findViewById(R.id.summary)
        titleText = view.findViewById(R.id.titleArticle)
        dateText = view.findViewById(R.id.dateText)
        editDateText = view.findViewById(R.id.editDateText)
        backButton = view.findViewById(R.id.backButton)
        shareButton = view.findViewById(R.id.shareButton)
        commentButton = view.findViewById(R.id.commentButton)
        queue = Volley.newRequestQueue(context)

        commentButton.startAnimation(bounce)
        shareButton.startAnimation(bounce)
        backButton.startAnimation(bounce)

        // get article data
        makeUrlRequest()
        setListeners()

        return view
    }

    private fun makeUrlRequest()
    {
        val request = StringRequest(Request.Method.GET, "https://api.spaceflightnewsapi.net/v3/articles/$idParam",
            {
                message = Gson().fromJson(it, Message::class.java)
                Picasso.get().load(message.imageUrl).into(mMainImage);
                summaryText.text = message.summary
                titleText.text = message.title
                dateText.append(message.publishedAt.substring(0,10))
                editDateText.append(message.updatedAt.substring(0,10))
            })
        {
            Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();
        }
        queue.add(request)
    }

    private fun setListeners()
    {
        mMainImage.setOnClickListener {
            switchToWebCard(message.url)
            backButton.startAnimation(zoomIn)
            backButton.startAnimation(zoomOut)
        }

        backButton.setOnClickListener{
            LikedArticles.setCount(0)
            activity?.onBackPressed()
        }

        shareButton.setOnClickListener {
            shareButton.startAnimation(zoomIn)
            shareButton.startAnimation(zoomOut)
            context?.copyToClipboard(message.url)
            Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT ).show()
        }

        commentButton.setOnClickListener {
            commentButton.startAnimation(zoomIn)
            commentButton.startAnimation(zoomOut)
        }
    }

    // set id of article
    fun setID(id:Int)
    {
        this.idParam = id
    }

    // open current url in new web crt
    private fun switchToWebCard(address: String)
    {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(address)
        startActivity(intent)
    }

    // copy url of current site to phone clipboard
    private fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(this,ClipboardManager::class.java)
        clipboard?.setPrimaryClip(ClipData.newPlainText("",text))
    }
}