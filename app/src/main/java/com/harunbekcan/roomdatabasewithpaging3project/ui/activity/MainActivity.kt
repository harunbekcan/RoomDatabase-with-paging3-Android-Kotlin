package com.harunbekcan.roomdatabasewithpaging3project.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.harunbekcan.roomdatabasewithpaging3project.R
import com.harunbekcan.roomdatabasewithpaging3project.base.BaseActivity
import com.harunbekcan.roomdatabasewithpaging3project.databinding.ActivityMainBinding
import com.harunbekcan.roomdatabasewithpaging3project.utils.setGone
import com.harunbekcan.roomdatabasewithpaging3project.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),NavController.OnDestinationChangedListener{

    override fun getLayoutId(): Int = R.layout.activity_main

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.itemIconTintList = null
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favoritesFragment
            )
        )
        navHostFragment.navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {
                binding.bottomNavigation.setVisible()
            }

            R.id.favoritesFragment -> {
                binding.bottomNavigation.setVisible()
            }
            else -> {
                binding.bottomNavigation.setGone()
            }
        }
    }
}