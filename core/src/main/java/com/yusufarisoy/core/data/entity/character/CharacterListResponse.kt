package com.yusufarisoy.core.data.entity.character

import com.google.gson.annotations.SerializedName
import com.yusufarisoy.core.data.entity.Info

data class CharacterListResponse(
    @SerializedName("info")
    val info: Info,

    @SerializedName("results")
    val characters: List<Character>
)