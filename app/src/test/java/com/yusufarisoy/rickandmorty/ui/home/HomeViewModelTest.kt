package com.yusufarisoy.rickandmorty.ui.home

import com.yusufarisoy.core.data.entity.Info
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.entity.character.CharacterListResponse
import com.yusufarisoy.core.data.entity.character.Location
import com.yusufarisoy.core.data.entity.character.Origin
import com.yusufarisoy.core.data.entity.location.LocationListResponse
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.repository.CharacterRepository
import com.yusufarisoy.core.data.repository.LocationRepository
import com.yusufarisoy.rickandmorty.utils.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    private lateinit var characterRepository: CharacterRepository

    @RelaxedMockK
    private lateinit var locationRepository: LocationRepository

    @InjectMockKs
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(
            characterRepository,
            locationRepository
        )
    }

    @Test
    fun `Locations fetching`() = coroutineTestRule.runBlockingTest {

        val location = com.yusufarisoy.core.data.entity.location.Location(
            id = 0,
            name = "",
            type = "",
            dimension = "",
            residents = listOf(),
            url = "",
            created = ""
        )

        coEvery {
            characterRepository.getCharacters(any(), any(), any(), any())
        } returns flow {
            emit(NetworkResponse(
                NetworkResponse.Status.SUCCESS,
                CharacterListResponse(
                    info = Info(count = 0, pages = 0, next = "", prev = ""),
                    characters = listOf()
                ),
                null))
        }

        coEvery {
            locationRepository.getLocations(any(), any(), any(), any())
        } returns flow {
            emit(NetworkResponse(
                NetworkResponse.Status.SUCCESS,
                LocationListResponse(
                    info = Info(count = 0, pages = 0, next = "", prev = ""),
                    locations = listOf(location)
                ),
                null
            ))
        }

        homeViewModel.fetchState()

        assertThat(
            homeViewModel.currentUiState.locations?.get(0)
        ).isEqualTo(location)
    }

    @Test
    fun `Characters fetching`() = coroutineTestRule.runBlockingTest {

        val character = Character(
            id = 0,
            name = "",
            status = "",
            species = "",
            type = "",
            gender = "",
            origin = Origin(name = "", url = ""),
            location = Location(name = "", url = ""),
            image = "",
            episode = listOf(),
            url = "",
            created = ""
        )

        coEvery {
            characterRepository.getCharacters(any(), any(), any(), any())
        } returns flow {
            emit(NetworkResponse(
                NetworkResponse.Status.SUCCESS,
                CharacterListResponse(
                    info = Info(count = 0, pages = 0, next = "", prev = ""),
                    characters = listOf(character)
                ),
                null))
        }

        coEvery {
            locationRepository.getLocations(any(), any(), any(), any())
        } returns flow {
            emit(NetworkResponse(
                NetworkResponse.Status.SUCCESS,
                LocationListResponse(
                    info = Info(count = 0, pages = 0, next = "", prev = ""),
                    locations = listOf()
                ),
                null
            ))
        }

        homeViewModel.fetchState()

        assertThat(
            homeViewModel.currentUiState.characters?.get(0)
        ).isEqualTo(character)
    }
}