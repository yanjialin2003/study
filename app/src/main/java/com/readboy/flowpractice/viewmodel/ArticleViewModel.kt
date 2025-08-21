package com.readboy.flowpractice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readboy.flowpractice.model.Article
import com.readboy.flowpractice.net.AppRetrofit
import com.readboy.flowpractice.net.api.ArticleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.printStackTrace

class ArticleViewModel : ViewModel() {

    val articles = MutableLiveData<List<Article>>()

    fun searchArticles(key: String){
        viewModelScope.launch {
            flow {
                val list = AppRetrofit.createRequest(ArticleApi::class.java).searchArticles(key)
                emit(list)
            }.flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                }
                .collect { list ->
                    articles.value = list
                }
        }
    }

}