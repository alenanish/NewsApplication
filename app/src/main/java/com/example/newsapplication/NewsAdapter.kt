package com.example.newsapplication


import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class NewsAdapter(private val mCtx : Context, private val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var onClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(mCtx)
            .inflate(R.layout.news_card_view, parent, false)
        return NewsViewHolder(itemView)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener : DialogInterface.OnClickListener {
        fun onClick(position: Int, model: News)
    }
    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.textViewTitle.text = newsItem.title
        holder.textViewDescription.text = newsItem.description
        Picasso.get().load(newsItem.image_url).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, newsItem)
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView
        var textViewDescription: TextView
        var imageView: ImageView
        init {
            textViewTitle = itemView.findViewById(R.id.newsTitleTextView)
            textViewDescription = itemView.findViewById(R.id.descriptionTextView)
            imageView = itemView.findViewById(R.id.imageView)

        }
    }
}


