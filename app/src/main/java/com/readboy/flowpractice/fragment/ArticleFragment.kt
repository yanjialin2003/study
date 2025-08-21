package com.readboy.flowpractice.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.readboy.flowpractice.adapter.ArticleAdapter
import com.readboy.flowpractice.databinding.FragmentArticleBinding
import com.readboy.flowpractice.viewmodel.ArticleViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {
    private val mBinding: FragmentArticleBinding by lazy {
        FragmentArticleBinding.inflate(layoutInflater)
    }
    private val mArticleViewModel by viewModels<ArticleViewModel>()
    private lateinit var mArticleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mBinding.etSearch.textWatcherFlow().collect {
                    Log.d("TAG", "keyword: $it")
                    mArticleViewModel.searchArticles(it)
                }
            }
        }
        mArticleAdapter = ArticleAdapter(requireContext())
        mBinding.rvArticle.adapter = mArticleAdapter
        mBinding.rvArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mArticleViewModel.articles.observe(viewLifecycleOwner) { articles ->
            mArticleAdapter.setData(articles)
        }
    }

    // 获取关键字拓展函数
    private fun TextView.textWatcherFlow(): Flow<String> = callbackFlow {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                this@callbackFlow.trySend(s.toString()).isSuccess
            }
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }
}