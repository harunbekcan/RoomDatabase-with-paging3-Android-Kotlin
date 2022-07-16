package com.harunbekcan.roomdatabasewithpaging3project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.databinding.ItemPopularTvLayoutBinding
import com.harunbekcan.roomdatabasewithpaging3project.utils.Constant
import com.harunbekcan.roomdatabasewithpaging3project.utils.loadImage
import javax.inject.Inject

class FavoritesAdapter @Inject constructor(private val favoriteList: ArrayList<PopularTvDatabaseModel>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var listener: RemoveFromFavoritesAdapterListener? = null

    fun setListener(listener: RemoveFromFavoritesAdapterListener) {
        this.listener = listener
    }

    interface RemoveFromFavoritesAdapterListener {
        fun removeFromFavoritesPagingAdapterItemClicked(data: PopularTvDatabaseModel?, view: View)
    }

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPopularTvLayoutBinding.bind(itemView)
        fun bind(popularTvDatabaseModel: PopularTvDatabaseModel) {
            with(binding) {
                popularTvNameTextView.text = popularTvDatabaseModel.name
                popularTvLanguageTextView.text = popularTvDatabaseModel.original_language
                val imageUrl = Constant.IMAGE_URL_START + popularTvDatabaseModel.poster_path
                popularTvImageView.loadImage(imageUrl)

                if (popularTvDatabaseModel.isFavorite == Constant.IS_FAVORITE_TRUE){
                    favoriteStatusButtonImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_favorite_selected))
                } else {
                    favoriteStatusButtonImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_favorite_unselected))
                }

                favoriteStatusButtonImageView.setOnClickListener {
                    listener?.removeFromFavoritesPagingAdapterItemClicked(popularTvDatabaseModel,it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_tv_layout, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}