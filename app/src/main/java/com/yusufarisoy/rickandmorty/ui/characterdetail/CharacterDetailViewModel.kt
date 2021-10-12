package com.yusufarisoy.rickandmorty.ui.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.repository.CharacterRepository
import com.yusufarisoy.core.utils.StatefulViewModel
import com.yusufarisoy.core.utils.UiState
import com.yusufarisoy.rickandmorty.ui.characterdetail.CharacterDetailViewModel.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    args: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : StatefulViewModel<CharacterDetailState>(CharacterDetailState()) {

    private val id: Int = args.get("id")!!

    init {
        fetchState()
    }

    private fun fetchState() {
        viewModelScope.launch {
            setProgress(progress = true)
            val state = fetchCharacterDetailState(id)
            setState {
                state
            }
            setProgress(progress = false)
        }
    }

    private suspend fun fetchCharacterDetailState(id: Int): CharacterDetailState = supervisorScope {
        var character: Character? = null
        withContext(Dispatchers.IO) {
            characterRepository.getCharacterById(id).collect {
                if (it.status == NetworkResponse.Status.SUCCESS) {
                    character = it.data
                } else {
                    setError(it.error)
                }
            }
        }
        CharacterDetailState(
            character = character
        )
    }

    fun addCharacterToFavorites() {
        //TODO: Room DB
    }

    data class CharacterDetailState(
        val character: Character? = null
    ) : UiState
}