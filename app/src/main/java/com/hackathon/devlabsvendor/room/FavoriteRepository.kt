package com.hackathon.devlabsvendor.room

import androidx.lifecycle.LiveData
import com.hackathon.devlabsvendor.model.Article
import com.hackathon.devlabsvendor.room.FavoriteDatabase

class FavoriteRepository(private val database: FavoriteDatabase) {
    val allArticleFavorites: LiveData<List<Article>> = database.articleFavoriteDao().getAllArticles()

    suspend fun insertArticleFavorite(article: Article) {
        database.articleFavoriteDao().insert(article)
    }

    suspend fun deleteArticleFavorite(article: Article) {
        database.articleFavoriteDao().delete(article)
    }
}