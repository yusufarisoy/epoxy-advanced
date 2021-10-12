package com.yusufarisoy.core.data.remote.character

import com.yusufarisoy.core.data.entity.character.Character
import com.yusufarisoy.core.data.entity.character.CharacterListResponse
import com.yusufarisoy.core.data.remote.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET(Constants.GET_CHARACTERS)
    suspend fun getCharacters(
        @Query("name") characterName: String?,
        @Query("status") characterStatus: String?,
        @Query("gender") characterGender: String?,
        @Query("page") page: Int?
    ): Response<CharacterListResponse>

    @GET(Constants.GET_CHARACTER_BY_ID)
    suspend fun getCharacterById(@Path("id") id: Int): Response<Character>
}