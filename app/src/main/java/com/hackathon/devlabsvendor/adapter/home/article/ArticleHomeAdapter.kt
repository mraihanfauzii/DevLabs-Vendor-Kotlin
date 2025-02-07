package com.hackathon.devlabsvendor.adapter.home.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hackathon.devlabsvendor.databinding.ItemArticleCardBinding
import com.hackathon.devlabsvendor.model.Article

class ArticleHomeAdapter: RecyclerView.Adapter<ArticleHomeAdapter.ArticleListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val article = ArrayList<Article>()
    inner class ArticleListViewHolder(private val binding: ItemArticleCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun getArticle(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.photoUrl)
                    .centerCrop()
                    .into(imgArticleThumbnail)
                tvArticleTitle.text = article.title

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleListViewHolder {
        val data = ItemArticleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleListViewHolder(data)
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        holder.getArticle(article[position])
    }

    override  fun getItemCount(): Int = 5

    fun getArticles(listArticle: ArrayList<Article>) {
        article.clear()
        article.addAll(listArticle)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: Article)
    }
}