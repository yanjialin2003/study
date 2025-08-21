package com.readboy.flowpractice.net.api

import com.readboy.flowpractice.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    /**
     * 查询文章列表
     */
    @GET("article")
    suspend fun searchArticles(
        @Query("key") key: String
    ): List<Article>
}