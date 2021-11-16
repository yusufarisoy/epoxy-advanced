package com.yusufarisoy.rickandmorty.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yusufarisoy.core.utils.SimpleTextWatcher
import com.yusufarisoy.core.views.base.BaseFragment
import com.yusufarisoy.rickandmorty.databinding.FragmentRegisterBinding
import com.yusufarisoy.rickandmorty.ui.register.epoxy.RegisterEpoxyController
import com.yusufarisoy.rickandmorty.ui.register.epoxy.RegisterEpoxyController.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var epoxyController: RegisterEpoxyController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        observeState()
        observeViewModel()
        initEpoxyController()
        withProgress(viewModel, ::onProgress)
        withError(viewModel, ::onError)
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigateBack.collect {
                if (it) {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun observeState() = withUiState(viewModel, isDistinctUntilChange = false) { state ->
        epoxyController.setData(state)
    }

    private fun initEpoxyController() {
        epoxyController = RegisterEpoxyController(registerTextWatchers(), ::registerOnClick)
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun registerTextWatchers() = object : RegisterTextWatchers {
        override val emailTextWatcher = object : SimpleTextWatcher {
            override fun onTextChanged(text: String?) {
                viewModel.setEmailText(text)
            }
        }

        override val nameTextWatcher = object : SimpleTextWatcher {
            override fun onTextChanged(text: String?) {
                viewModel.setNameText(text)
            }
        }

        override val surnameTextWatcher = object : SimpleTextWatcher {
            override fun onTextChanged(text: String?) {
                viewModel.setSurnameText(text)
            }
        }

        override val passwordTextWatcher = object : SimpleTextWatcher {
            override fun onTextChanged(text: String?) {
                viewModel.setPasswordText(text)
            }
        }

        override val passwordRepeatTextWatcher = object : SimpleTextWatcher {
            override fun onTextChanged(text: String?) {
                viewModel.setPasswordRepeatText(text)
            }
        }
    }

    private fun registerOnClick() {
        viewModel.registerControl()
    }
}