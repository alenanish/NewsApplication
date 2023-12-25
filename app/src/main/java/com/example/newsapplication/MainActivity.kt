package com.example.newsapplication


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val apiKey = "pub_35213da6be6bb866e0a83c74be0c437b91bd0"
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var searchButton : ImageButton
    private lateinit var searchInput : EditText
    private lateinit var textView : TextView

    private var newsList = mutableListOf<News>()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            newsRecyclerView = findViewById(R.id.newsRecyclerView)
            newsRecyclerView.layoutManager = LinearLayoutManager(this)
            newsAdapter = NewsAdapter(newsList)
            newsRecyclerView.adapter = newsAdapter


            searchButton = findViewById(R.id.searchImageButton)
            searchInput = findViewById(R.id.searchEditText)
            textView = findViewById(R.id.h2)

            searchButton.setOnClickListener {


                searchNews()
                if (newsList.size > 0) {

                    textView.text = "Результаты поиска:"
                    if  (textView.visibility == View.INVISIBLE) {
                        textView.visibility = View.VISIBLE
                    }

                } else {
                    textView.text = "К сожалению, по Вашему запросу ничего не найдено."
                    if  (textView.visibility == View.INVISIBLE) {
                        textView.visibility = View.VISIBLE
                    }
                    newsList = mutableListOf()

                }

            }
        }

        private fun searchNews() {
            newsList.clear()
            val query = searchInput.text.toString()
            val list = mutableListOf<News>()

            if (query.isEmpty()) {
                Log.v("INFO", "Empty search was performed")
            }
            else {




                CoroutineScope(Dispatchers.IO).launch {
                    Log.v("INFO", "Search is performing: $query")
                    val response = ApiClient().newsApiService?.getNews(query, apiKey)

                    if (response?.isSuccessful == true) {
                        val newsResponse = response.body()
                        newsResponse?.results?.forEach { article ->
                            val news = News(article.title, article.description, article.image_url,
                                article.content, article.creator, article.pubDate, article.link)
                            newsList.add(news)
                            newsAdapter.notifyItemInserted(newsList.size - 1)
                        }

                    } else {

                        Toast.makeText(
                            this@MainActivity,
                            "Error: ${response?.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                newsAdapter = NewsAdapter(list)
            }

        }
    }