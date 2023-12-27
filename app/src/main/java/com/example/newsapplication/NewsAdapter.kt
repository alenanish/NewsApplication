package com.example.newsapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class NewsAdapter(private val newsList: List<NewsApiService.Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.newsTitleTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.news_card_view, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]
        holder.title.text = item.title
        holder.description.text = item.description
        Picasso.get().load(item.image_url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: News)
    }

}


