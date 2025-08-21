package com.readboy.flowpractice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.readboy.flowpractice.R
import com.readboy.flowpractice.databinding.ItemUserBinding
import com.readboy.flowpractice.db.entity.User

class UserAdapter(private val context: Context) : BaseBindingAdapter() {

    private var data = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false)
        return BindingViewHolder(ItemUserBinding.bind(v))
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = data[position]
        val binding = holder.binding as ItemUserBinding
        binding.tvInformation.text = "${item.uid} - ${item.name} - ${item.age}"
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<User>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}