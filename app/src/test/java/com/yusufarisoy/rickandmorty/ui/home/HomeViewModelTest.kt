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
import com.yusufarisoy.core.extensions.addElements
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
    fun `When locations fetched state is getting set correctly`() = coroutineTestRule.runBlockingTest {

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
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.SUCCESS,
            data = CharacterListResponse(
                info = Info(count = 0, pages = 0, next = "", prev = ""),
                characters = listOf()
            ), error = null
        ))}

        coEvery {
            locationRepository.getLocations(any(), any(), any(), any())
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.SUCCESS,
            data = LocationListResponse(
                info = Info(count = 0, pages = 0, next = "", prev = ""),
                locations = listOf(location)
            ), error = null
        ))}

        homeViewModel.fetchState()

        assertThat(
            homeViewModel.currentUiState.locations
        ).isEqualTo(listOf(location))
    }

    @Test
    fun `When characters fetched state is getting set correctly`() = coroutineTestRule.runBlockingTest {

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
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.SUCCESS,
            data = CharacterListResponse(
                info = Info(count = 0, pages = 0, next = "", prev = ""),
                characters = listOf(character)
            ), error = null
        ))}

        coEvery {
            locationRepository.getLocations(any(), any(), any(), any())
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.SUCCESS,
            data = LocationListResponse(
                info = Info(count = 0, pages = 0, next = "", prev = ""),
                locations = listOf()
            ), error = null
        ))}

        homeViewModel.fetchState()

        assertThat(
            homeViewModel.currentUiState.characters
        ).isEqualTo(listOf(character))
    }

    @Test
    fun `When search text is set state is getting set correctly`() = coroutineTestRule.runBlockingTest {

        val searchText = "Rick Sanchez"
        homeViewModel.setSearchText(searchText)

        assertThat(
            homeViewModel.currentUiState.searchText
        ).isEqualTo(searchText)
    }

    @Test
    fun `When search text is set characters in state is getting fetched correctly`() = coroutineTestRule.runBlockingTest {

        val character = Character(
            id = 1,
            name = "Rick Sanchez",
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
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.SUCCESS,
            data = CharacterListResponse(
                info = Info(count = 0, pages = 0, next = "", prev = ""),
                characters = listOf(character)
            ), error = null
        ))}

        val searchText = "Rick Sanchez"
        homeViewModel.setSearchText(searchText)

        assertThat(
            homeViewModel.currentUiState.characters
        ).isEqualTo(listOf(character))
    }

    @Test
    fun `List extension add elements is working correctly`() = coroutineTestRule.runBlockingTest {

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

        val characterList: List<Character> = listOf(character, character)
        val updatedList = characterList.addElements(listOf(character, character))

        assertThat(
            updatedList
        ).isEqualTo(listOf(character, character, character, character))
    }
}