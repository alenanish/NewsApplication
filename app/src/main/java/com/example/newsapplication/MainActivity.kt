package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchImageButton)
        recyclerView = findViewById(R.id.newsRecyclerView)
        textView = findViewById(R.id.h2)
        recyclerView.layoutManager = LinearLayoutManager(this)


        searchButton.setOnClickListener {
            val keyword = searchEditText.text.toString()
            if (keyword.isNotEmpty()) {
                searchNews(keyword)
            }
        }
    }

    private fun searchNews(keyword: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            val response = NewsApiClient.apiService.getNews(keyword, "pub_35213da6be6bb866e0a83c74be0c437b91bd0").body()

            if (response?.status == "success") {
                if (response.totalResults > 0) {
                    textView.text = "Результаты поиска:"

                    val newsList = response.results.take(20)
                    val adapter = NewsAdapter(newsList)
                    recyclerView.adapter = adapter
                } else {
                    textView.text = "К сожалению, по вашему запросу ничего не найдено."
                }


            } else {
                textView.text = "Что-то пошло не так..."
            }

            if (textView.visibility == View.INVISIBLE) {
                textView.visibility = View.VISIBLE
            }
        }
    }
}