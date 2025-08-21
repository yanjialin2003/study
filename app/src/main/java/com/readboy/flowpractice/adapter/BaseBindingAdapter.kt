package com.readboy.flowpractice.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingAdapter: RecyclerView.Adapter<BaseBindingAdapter.BindingViewHolder>() {
    class BindingViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
}