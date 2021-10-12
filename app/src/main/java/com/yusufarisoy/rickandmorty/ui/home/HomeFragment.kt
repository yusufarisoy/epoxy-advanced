package com.yusufarisoy.rickandmorty.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yusufarisoy.core.utils.SimpleTextWatcher
import com.yusufarisoy.core.views.base.BaseFragment
import com.yusufarisoy.rickandmorty.databinding.FragmentHomeBinding
import com.yusufarisoy.rickandmorty.ui.characterdetail.CharacterDetailFragment
import com.yusufarisoy.rickandmorty.ui.home.epoxy.HomeEpoxyController
import com.yusufarisoy.rickandmorty.ui.login.LoginFragment
import com.yusufarisoy.core.utils.PagingScrollListener
import com.yusufarisoy.rickandmorty.util.hideAndShow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var epoxyController: HomeEpoxyController
    private lateinit var scrollListener: PagingScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        observeState()
        initEpoxyController()
        initScrollListener()
        withProgress(viewModel, ::onProgress)
        withError(viewModel, ::onError)
        setOnClickListeners()
    }

    private fun observeState() = withUiState(viewModel, isDistinctUntilChange = false) { state ->
        epoxyController.setData(state)
        scrollListener.setLoading(false)
    }

    private fun initEpoxyController() {
        epoxyController = HomeEpoxyController(searchTextWatcher(), characterOnClickListener())
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun initScrollListener() {
        scrollListener = pagingScrollListener()
        binding.epoxyRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun searchTextWatcher() = object : SimpleTextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.setSearchText(p0.toString())
        }
    }

    private fun characterOnClickListener(): CharacterOnClick = object : CharacterOnClick {
        override fun onClick(id: Int) {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val detailFragment = CharacterDetailFragment()
            detailFragment.arguments = bundle
            hideAndShow(detailFragment)
        }
    }

    private fun pagingScrollListener() =
        object : PagingScrollListener() {
            override val hasNextPage: Boolean
                get() = viewModel.getHasNextPage()

            override fun loadMoreItems() {
                viewModel.fetchCharacters()
            }
        }

    private fun setOnClickListeners() {
        binding.fabLogin.setOnClickListener {
            hideAndShow(LoginFragment())
        }
    }

    override fun onStop() {
        super.onStop()
        binding.epoxyRecyclerView.removeOnScrollListener(scrollListener)
    }
}