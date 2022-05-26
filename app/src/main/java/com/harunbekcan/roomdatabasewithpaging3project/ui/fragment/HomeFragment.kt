package com.harunbekcan.roomdatabasewithpaging3project.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.blankj.utilcode.util.LogUtils
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.base.BaseFragment
import com.harunbekcan.roomdatabasewithpaging3project.databinding.FragmentHomeBinding
import com.harunbekcan.roomdatabasewithpaging3project.ui.adapter.PopularTvPagingAdapter
import com.harunbekcan.roomdatabasewithpaging3project.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var popularTvPagingAdapter: PopularTvPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        configureStateListener()
        pagingObserve()
    }

    private fun initAdapter(){
        binding.popularTvRecyclerView.adapter = popularTvPagingAdapter
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun pagingObserve() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllPopularTv().collectLatest { response->
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

}