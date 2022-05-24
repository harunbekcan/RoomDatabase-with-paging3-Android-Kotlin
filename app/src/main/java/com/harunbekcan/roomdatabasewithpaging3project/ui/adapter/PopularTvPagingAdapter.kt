package com.harunbekcan.roomdatabasewithpaging3project.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.harunbekcan.roomdatabasewithpaging3project.data.response.PopularTvItem
import com.harunbekcan.roomdatabasewithpaging3project.databinding.ItemPopularTvLayoutBinding
import com.harunbekcan.roomdatabasewithpaging3project.utils.loadImage
import javax.inject.Inject

class PopularTvPagingAdapter
@Inject
constructor() : PagingDataAdapter<PopularTvItem, PopularTvPagingAdapter.PopularTvViewHolder>(DiffUtils) {

    class PopularTvViewHolder(private val binding: ItemPopularTvLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(popularTvItem: PopularTvItem){
            binding.apply {
                popularTvNameTextView.text = popularTvItem.name
                popularTvLanguageTextView.text = popularTvItem.original_language
                val imageUrl = "https://image.tmdb.org/t/p/w500" + popularTvItem.poster_path
                popularTvImageView.loadImage(imageUrl)
            }
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<PopularTvItem>(){
        override fun areItemsTheSame(oldItem: PopularTvItem, newItem: PopularTvItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularTvItem, newItem: PopularTvItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: PopularTvViewHolder, position: Int) {
        val popularTvItem = getItem(position)
        if(popularTvItem != null){
            holder.bind(popularTvItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvViewHolder {
        return PopularTvViewHolder(ItemPopularTvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}