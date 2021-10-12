package com.yusufarisoy.rickandmorty.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yusufarisoy.core.utils.SimpleTextWatcher
import com.yusufarisoy.core.views.base.BaseFragment
import com.yusufarisoy.rickandmorty.databinding.FragmentLoginBinding
import com.yusufarisoy.rickandmorty.ui.login.epoxy.LoginEpoxyController
import com.yusufarisoy.rickandmorty.ui.login.epoxy.LoginEpoxyController.*
import com.yusufarisoy.rickandmorty.ui.register.RegisterFragment
import com.yusufarisoy.rickandmorty.util.hideAndShow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var epoxyController: LoginEpoxyController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        observeState()
        initEpoxyController()
        withProgress(viewModel, ::onProgress)
        withError(viewModel, ::onError)
    }

    private fun observeState() = withUiState(viewModel, isDistinctUntilChange = false) { state ->
        epoxyController.setData(state)
    }

    private fun initEpoxyController() {
        epoxyController = LoginEpoxyController(loginTextWatchers(), loginCallBacks())
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun loginTextWatchers() = object : LoginTextWatchers {
        override val emailTextWatcher = object : SimpleTextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setEmailText(p0.toString())
            }
        }
        override fun onPasswordChanged(password: String) {
            viewModel.setPasswordText(password)
        }
    }

    private fun loginCallBacks() = object : LoginCallbacks {
        override fun loginOnClick() {
            viewModel.loginControl()
        }

        override fun registerOnClick() {
            hideAndShow(RegisterFragment())
        }
    }
}