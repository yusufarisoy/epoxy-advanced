package com.yusufarisoy.rickandmorty.ui.characterdetail

import androidx.lifecycle.viewModelScope
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.repository.CharacterRepository
import com.yusufarisoy.core.utils.StatefulViewModel
import com.yusufarisoy.core.utils.UiState
import com.yusufarisoy.rickandmorty.ui.characterdetail.CharacterDetailViewModel.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : StatefulViewModel<CharacterDetailState>(CharacterDetailState()) {

    private var _id: Int? = null

    fun init(id: Int) {
        if (_id == null) {
            _id = id
            fetchState(id)
        }
    }

    private fun fetchState(id: Int) {
        viewModelScope.launch {
            setProgress(progress = true)
            fetchCharacterDetailState(id)
        }
    }

    private suspend fun fetchCharacterDetailState(id: Int) = viewModelScope.launch {
        characterRepository.getCharacterById(id).collect {
            if (it.status == NetworkResponse.Status.SUCCESS) {
                setState {
                    copy(character = it.data)
                }
            } else {
                setError(it.error)
            }
            setProgress(progress = false)
        }
    }

    fun addCharacterToFavorites() {
        //TODO: Room DB
    }

    data class CharacterDetailState(
        val character: Character? = null
    ) : UiState
}