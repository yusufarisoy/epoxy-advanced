package com.yusufarisoy.rickandmorty.ui.home

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.entity.location.Location
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.repository.CharacterRepository
import com.yusufarisoy.core.data.repository.LocationRepository
import com.yusufarisoy.core.extensions.addElements
import com.yusufarisoy.core.utils.StatefulViewModel
import com.yusufarisoy.core.utils.UiState
import com.yusufarisoy.rickandmorty.ui.home.HomeViewModel.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository
) : StatefulViewModel<HomeState>(HomeState()) {

    private var page: Int = 1
    private var hasNextPage = false
    private val locationsChannel = Channel<List<Location>?>()
    private val charactersChannel = Channel<List<Character>?>()
    private lateinit var searchFlow: Flow<String?>

    fun getHasNextPage(): Boolean = hasNextPage

    fun setSearchText(searchText: String?) {
        setState {
            copy(searchText = searchText)
        }
        searchFlow = flowOf(searchText)
        observeSearchQuery()
    }

    init {
        fetchState()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchState() {
        viewModelScope.launch {
            fetchData()
            setProgress(progress = true)
            val state = fetchHomeState()
            setState {
                state
            }
            setProgress(progress = false)
        }
    }

    private suspend fun fetchData() = supervisorScope {
        fetchLocations()
        fetchCharacters(fetchType = FetchType.INIT)
    }

    private suspend fun fetchHomeState(
    ): HomeState = HomeState(
        locations = locationsChannel.receive(),
        characters = charactersChannel.receive()
    )

    private fun fetchLocations() = viewModelScope.launch {
        locationRepository.getLocations().collect {
            if (it.status == NetworkResponse.Status.SUCCESS) {
                locationsChannel.send(it.data?.locations)
            } else {
                setError(it.error)
            }
        }
    }

    fun fetchCharacters(
        fetchType: FetchType = FetchType.LOAD_MORE,
        characterStatus: String? = null,
        characterGender: String? = null
    ) = viewModelScope.launch { characterRepository
        .getCharacters(currentUiState.searchText, characterStatus, characterGender, page)
        .collect {
            if (it.status == NetworkResponse.Status.SUCCESS) {
                handleCharactersResponse(fetchType, it.data?.characters)
                checkNextPage(it.data?.info?.next)
            } else {
                setError(it.error)
            }
        }
    }

    private suspend fun handleCharactersResponse(fetchType: FetchType, characters: List<Character>?) {
        when (fetchType) {
            FetchType.INIT -> charactersChannel.send(characters)
            FetchType.LOAD_MORE -> {
                val characterList = currentUiState.characters?.addElements(characters)
                setState {
                    copy(characters = characterList)
                }
            }
            FetchType.SEARCH -> setState {
                copy(characters = characters)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchFlow
            .debounce(DEBOUNCE_MS)
            .distinctUntilChanged()
            .onEach {
                onQueryChanged()
            }
            .launchIn(viewModelScope)
    }

    private fun onQueryChanged() {
        page = 1
        fetchCharacters(fetchType = FetchType.SEARCH)
    }

    private fun checkNextPage(url: String?) {
        hasNextPage = url != null
        if (hasNextPage) {
            page = Uri.parse(url).getQueryParameter("page")!!.toInt()
        }
    }

    companion object {
        const val DEBOUNCE_MS = 400L
    }

    enum class FetchType {
        INIT, LOAD_MORE, SEARCH
    }

    data class HomeState(
        val searchText: String? = null,
        val locations: List<Location>? = null,
        val characters: List<Character>? = null
    ) : UiState
}