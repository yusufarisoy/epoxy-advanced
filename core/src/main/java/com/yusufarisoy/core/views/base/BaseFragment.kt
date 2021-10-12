package com.yusufarisoy.core.views.base

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.yusufarisoy.common.StateError
import com.yusufarisoy.core.extensions.withError
import com.yusufarisoy.core.extensions.withProgress
import com.yusufarisoy.core.extensions.withUiState
import com.yusufarisoy.core.utils.StatefulViewModel

abstract class BaseFragment : Fragment() {

    fun <V : StatefulViewModel<S>, S> withUiState(
        viewModel: V,
        isDistinctUntilChange: Boolean,
        onStateChanged: suspend (S) -> Unit
    ) = withUiState(viewModel, viewLifecycleOwner, isDistinctUntilChange, onStateChanged)

    fun <V : StatefulViewModel<*>> Fragment.withProgress(
        viewModel: V,
        onProgressEvent: suspend (Boolean) -> Unit
    ) = withProgress(viewModel, viewLifecycleOwner, onProgressEvent)

    fun <V : StatefulViewModel<*>> Fragment.withError(
        viewModel: V,
        onErrorEvent: suspend (StateError) -> Unit
    ) = withError(viewModel, viewLifecycleOwner, onErrorEvent)

    protected fun onProgress(isLoading: Boolean) {
        if (isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showProgress() {
        val activity = requireActivity() as BaseActivity
        activity.showProgress()
    }

    private fun hideProgress() {
        val activity = requireActivity() as BaseActivity
        activity.hideProgress()
    }

    protected fun onError(error: StateError) {
        showDialog(title = "An error occurred", error.message?: "", "Ok", { }, "")
        Log.e("ErrorLog", "BaseFragment - Exception: ${error.exception} - Message: ${error.message}")
    }

    fun showDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        positiveButtonOnClick: () -> Unit,
        negativeButtonText: String
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(title)
            setMessage(message)

            setPositiveButton(positiveButtonText) { dialog, _ ->
                positiveButtonOnClick()
                dialog.dismiss()
            }
            setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }
}