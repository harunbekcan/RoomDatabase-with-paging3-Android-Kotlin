package com.harunbekcan.roomdatabasewithpaging3project.ui.fragment

import android.os.Bundle
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.base.BaseFragment
import com.harunbekcan.roomdatabasewithpaging3project.databinding.FragmentFavoritesBinding

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_favorites

    override fun prepareView(savedInstanceState: Bundle?) {}
}