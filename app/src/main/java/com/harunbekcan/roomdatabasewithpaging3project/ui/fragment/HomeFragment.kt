package com.harunbekcan.roomdatabasewithpaging3project.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
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

}