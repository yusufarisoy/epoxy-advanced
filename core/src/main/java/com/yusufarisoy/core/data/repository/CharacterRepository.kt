package com.yusufarisoy.core.data.repository

import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.entity.character.CharacterListResponse
import com.yusufarisoy.core.data.remote.BaseDataSource
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.remote.character.CharacterService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterRepository @Inject constructor(private val characterService: CharacterService) : BaseDataSource() {

    suspend fun getCharacters(
        characterName: String? = null,
        characterStatus: String? = null,
        characterGender: String? = null,
        page: Int? = null
    ): Flow<NetworkResponse<CharacterListResponse>> = flow {
        emit(sendRequest { characterService.getCharacters(characterName, characterStatus, characterGender, page) })
    }.flowOn(Dispatchers.IO)

    suspend fun getCharacterById(id: Int): Flow<NetworkResponse<Character>> = flow {
        emit(sendRequest { characterService.getCharacterById(id) })
    }.flowOn(Dispatchers.IO)
}