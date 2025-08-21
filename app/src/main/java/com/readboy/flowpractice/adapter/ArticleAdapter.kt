package com.readboy.flowpractice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.readboy.flowpractice.R
import com.readboy.flowpractice.databinding.ItemArticleBinding
import com.readboy.flowpractice.model.Article

class ArticleAdapter(private val context: Context) : BaseBindingAdapter() {

    private var data = ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return BindingViewHolder(ItemArticleBinding.bind(v))
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = data[position]
        val binding = holder.binding as ItemArticleBinding
        binding.tvInformation.text = "${item.id} - ${item.text}"
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Article>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}