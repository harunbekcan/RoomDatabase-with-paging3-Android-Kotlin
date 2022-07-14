package com.harunbekcan.roomdatabasewithpaging3project.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.base.BaseFragment
import com.harunbekcan.roomdatabasewithpaging3project.data.local.entity.PopularTvDatabaseModel
import com.harunbekcan.roomdatabasewithpaging3project.databinding.FragmentHomeBinding
import com.harunbekcan.roomdatabasewithpaging3project.ui.adapter.PopularTvPagingAdapter
import com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    PopularTvPagingAdapter.AddToFavoritesAdapterListener {

    override fun getLayoutId(): Int = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var popularTvPagingAdapter: PopularTvPagingAdapter

    override fun prepareView(savedInstanceState: Bundle?) {
        initAdapter()
        configureStateListener()
        pagingObserve()
    }

    private fun initAdapter() {
        binding.popularTvRecyclerView.adapter = popularTvPagingAdapter
        popularTvPagingAdapter.setListener(this)
    }

    private fun pagingObserve() {
        lifecycleScope.launch {
            viewModel.getAllPopularTv().collectLatest { response ->
                popularTvPagingAdapter.submitData(response)
            }
        }
    }

    private fun configureStateListener() {
        popularTvPagingAdapter.addLoadStateListener { loadState ->

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(activity, errorState.error.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun addToFavoritesPagingAdapterItemClicked(data: PopularTvDatabaseModel?, view: View) {
        val isFavorite = if (data?.isFavorite == 0) 1 else 0
        data?.let {
            viewModel.changeStatus(it.popularTvId, isFavorite)
        }
    }
}