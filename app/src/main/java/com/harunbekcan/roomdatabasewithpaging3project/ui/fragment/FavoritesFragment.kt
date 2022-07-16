package com.harunbekcan.roomdatabasewithpaging3project.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.LogUtils
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.base.BaseFragment
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.databinding.FragmentFavoritesBinding
import com.harunbekcan.roomdatabasewithpaging3project.ui.adapter.FavoritesAdapter
import com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel.FavoritesViewModel
import com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(),
    FavoritesAdapter.RemoveFromFavoritesAdapterListener {

    private val viewModel: FavoritesViewModel by viewModels()


    private lateinit var favoritesAdapter: FavoritesAdapter


    override fun getLayoutId(): Int = R.layout.fragment_favorites

    override fun prepareView(savedInstanceState: Bundle?) {
        initAdapter()
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModel.favoritesObserve.observe(viewLifecycleOwner) {
            viewModel.adapterList.clear()
            it?.let {
                viewModel.adapterList.addAll(it)
                favoritesAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initAdapter() {
        favoritesAdapter = FavoritesAdapter(viewModel.adapterList)
        binding.favoritesRecyclerView.adapter = favoritesAdapter
        favoritesAdapter.setListener(this)
    }

    override fun removeFromFavoritesPagingAdapterItemClicked(data: PopularTvDatabaseModel?, view: View) {
        val isFavorite = if (data?.isFavorite == 0) 1 else 0
        data?.let {
            viewModel.removePopularTvList(data.popularTvId,isFavorite)
        }
    }
}