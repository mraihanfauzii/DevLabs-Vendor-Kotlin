package com.hackathon.devlabsvendor.ui.main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackathon.devlabsvendor.adapter.home.article.AllArticleAdapter
import com.hackathon.devlabsvendor.databinding.ActivityArticleBinding
import com.hackathon.devlabsvendor.model.Article
import com.hackathon.devlabsvendor.ui.main.profile.favorite.FavoriteActivity
import com.hackathon.devlabsvendor.utils.ArticleDataDummy

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleAdapter: AllArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleAdapter = AllArticleAdapter()
        articleAdapter.getArticles(ArticleDataDummy.listArticle)
        articleRecyclerView()
        onArticleClick()

        binding.ivFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            intent.putExtra("navigate_to", "FavoriteArticleFragment")
            startActivity(intent)
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filter(text: String) {
        val filteredList = ArticleDataDummy.listArticle.filter { it.title.contains(text, ignoreCase = true) }
        articleAdapter.getArticles(filteredList)
    }

    private fun articleRecyclerView() {
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(this@ArticleActivity, LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }
    }

    private fun onArticleClick() {
        articleAdapter.setOnItemClickCallback(object: AllArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                Intent(this@ArticleActivity, DetailArticleActivity::class.java).also {
                    it.putExtra(DetailArticleActivity.PHOTO_URL, article.photoUrl)
                    it.putExtra(DetailArticleActivity.TITLE, article.title)
                    it.putExtra(DetailArticleActivity.DESCRIPTION, article.description)
                    it.putExtra(DetailArticleActivity.ID, article.id.toString())
                    startActivity(it)
                }
            }
        })
    }
}