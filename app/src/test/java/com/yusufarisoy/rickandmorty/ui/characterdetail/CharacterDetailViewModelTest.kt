package com.yusufarisoy.rickandmorty.ui.characterdetail

import com.yusufarisoy.common.StateError
import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.entity.character.Location
import com.yusufarisoy.core.data.entity.character.Origin
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.repository.CharacterRepository
import com.yusufarisoy.rickandmorty.utils.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.NullPointerException

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    private lateinit var characterRepository: CharacterRepository

    @InjectMockKs
    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        characterDetailViewModel = CharacterDetailViewModel(
            characterRepository
        )
    }

    @Test
    fun `When fetch state is called state is getting set correctly`() = coroutineTestRule.runBlockingTest {

        val id = 1
        val character = Character(
            id = id,
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
            characterRepository.getCharacterById(id)
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.SUCCESS,
            data = character,
            error = null
        ))}

        characterDetailViewModel.init(id)

        assertThat(
            characterDetailViewModel.currentUiState.character
        ).isEqualTo(character)
    }

    fun `When error response returned from service error state is getting set correctly`() = coroutineTestRule.runBlockingTest {

        val id = -1
        val error = StateError(NullPointerException(), "Error message")
        coEvery {
            characterRepository.getCharacterById(id)
        } returns flow { emit(NetworkResponse(
            status = NetworkResponse.Status.ERROR,
            data = null,
            error = error
        ))}

        characterDetailViewModel.init(id)

        assertThat(
            characterDetailViewModel.stateFlow.value.error
        ).isEqualTo(error)
    }
}