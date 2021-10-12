package com.yusufarisoy.rickandmorty.ui

import android.os.Bundle
import com.yusufarisoy.core.extensions.hide
import com.yusufarisoy.core.extensions.show
import com.yusufarisoy.core.views.base.BaseActivity
import com.yusufarisoy.rickandmorty.R
import com.yusufarisoy.rickandmorty.databinding.ActivityMainBinding
import com.yusufarisoy.rickandmorty.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, HomeFragment()).commit()
    }

    override fun showProgress() {
        binding.progressBar.show()
    }

    override fun hideProgress() {
        binding.progressBar.hide()
    }
}