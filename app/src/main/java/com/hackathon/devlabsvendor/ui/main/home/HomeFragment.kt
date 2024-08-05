package com.hackathon.devlabsvendor.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackathon.devlabsvendor.adapter.home.VideoAdapter
import com.hackathon.devlabsvendor.adapter.home.article.ArticleHomeAdapter
import com.hackathon.devlabsvendor.databinding.FragmentHomeBinding
import com.hackathon.devlabsvendor.model.Article
import com.hackathon.devlabsvendor.ui.authentication.AuthenticationManager
import com.hackathon.devlabsvendor.ui.main.message.LastMessageActivity
import com.hackathon.devlabsvendor.utils.ArticleDataDummy
import com.hackathon.devlabsvendor.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var authManager: AuthenticationManager
    private lateinit var articleAdapter: ArticleHomeAdapter
    private lateinit var homeViewModel: HomeViewModel
    private val videoIds = listOf(
        "nwz1awsSTjI",
        "89cYlGvpAds",
        "c8IOB50ve7w",
        "qbn_dAVsrXM",
        "wSLFN_p_SZI"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authManager = AuthenticationManager(requireContext())
        val getToken = authManager.getAccess(AuthenticationManager.TOKEN).toString()
        val token = "Bearer $getToken"

        articleAdapter = ArticleHomeAdapter()
        articleAdapter.getArticles(ArticleDataDummy.listArticle)
        articleAdapter.setOnItemClickCallback(object: ArticleHomeAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                Intent(context, DetailArticleActivity::class.java).also {
                    it.putExtra(DetailArticleActivity.PHOTO_URL, article.photoUrl)
                    it.putExtra(DetailArticleActivity.TITLE, article.title)
                    it.putExtra(DetailArticleActivity.DESCRIPTION, article.description)
                    it.putExtra(DetailArticleActivity.ID, article.id.toString())
                    startActivity(it)
                }
            }
        })

        homeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[HomeViewModel::class.java]
        homeViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }
        homeViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        binding.tvArtikel.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java)
            startActivity(intent)
        }

        binding.rvTema.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = VideoAdapter(videoIds, viewLifecycleOwner.lifecycle)
        }

        binding.btnChat.setOnClickListener {
            val intent = Intent(context, LastMessageActivity::class.java)
            startActivity(intent)
        }

        binding.rvArtikel.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = articleAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}