package com.hackathon.devlabsvendor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hackathon.devlabsvendor.room.FavoriteDatabase
import com.hackathon.devlabsvendor.room.FavoriteRepository
import com.hackathon.devlabsvendor.model.Article
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository
    val allArticleFavorites: LiveData<List<Article>>

    init {
        val database = FavoriteDatabase.getDatabase(application)
        repository = FavoriteRepository(database)
        allArticleFavorites = repository.allArticleFavorites
    }

    fun insertArticleFavorite(article: Article) = viewModelScope.launch {
        repository.insertArticleFavorite(article)
    }

    fun deleteArticleFavorite(article: Article) = viewModelScope.launch {
        repository.deleteArticleFavorite(article)
    }
}