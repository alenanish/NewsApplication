package com.example.newsapplication


import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("news")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    class Article(val title: String, val description: String?, val image_url: String?, val content: String,
                  val creator: List<String>?, val pubDate: String, val link: String)

    data class NewsResponse(
        val status: String,
        val totalResults: Int,
        @SerializedName("results") val results: List<Article>
    )
}


