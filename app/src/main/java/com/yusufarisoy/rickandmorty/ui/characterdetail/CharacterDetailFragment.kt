package com.yusufarisoy.rickandmorty.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.extensions.hide
import com.yusufarisoy.core.extensions.show
import com.yusufarisoy.core.views.base.BaseFragment
import com.yusufarisoy.core.views.custom.AppBarStateChangeListener
import com.yusufarisoy.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.yusufarisoy.rickandmorty.ui.characterdetail.epoxy.CharacterDetailEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment() {

    private val viewModel: CharacterDetailViewModel by viewModels()
    private lateinit var binding: FragmentCharacterDetailBinding
    private lateinit var epoxyController: CharacterDetailEpoxyController
    private lateinit var appBarStateChangeListener: AppBarStateChangeListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        val id = requireArguments().getInt("id")
        viewModel.init(id)
        observeState()
        initEpoxyController()
        withProgress(viewModel, ::onProgress)
        withError(viewModel, ::onError)
        setOnClickListeners()
        setStateListeners()
    }

    private fun observeState() = withUiState(viewModel, isDistinctUntilChange = false) { state ->
        epoxyController.setData(state)
        state.character?.let {
            initCharacter(it)
        }
    }

    private fun initCharacter(character: Character) {
        Glide.with(requireContext()).load(character.image).into(binding.imageView)
        binding.toolbar.title = character.name
    }

    private fun initEpoxyController() {
        val locationOnClick: LocationOnClick = object : LocationOnClick {
            override fun onClick(url: String) {
                //TODO
            }
        }
        epoxyController = CharacterDetailEpoxyController(locationOnClick)
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun setOnClickListeners() {
        binding.buttonFavorite.setOnClickListener {
            viewModel.addCharacterToFavorites()
        }
        binding.fabFavorite.setOnClickListener {
            showDialog(title = "Add to Favorites",
                message = "Are you sure?",
                positiveButtonText ="Yes",
                positiveButtonOnClick = {
                    viewModel.addCharacterToFavorites()
                },
                negativeButtonText = "Cancel")
        }
    }

    private fun setStateListeners() {
        appBarStateChangeListener = object : AppBarStateChangeListener() {
            override fun onStateChanged(state: State) {
                if (state == State.IDLE) {
                    binding.buttonFavorite.hide()
                } else if (state == State.COLLAPSED) {
                    binding.buttonFavorite.show()
                }
            }
        }
        binding.appBar.addOnOffsetChangedListener(appBarStateChangeListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.appBar.removeOnOffsetChangedListener(appBarStateChangeListener)
    }
}