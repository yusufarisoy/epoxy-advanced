package com.yusufarisoy.core.data.entity.location

import com.google.gson.annotations.SerializedName
import com.yusufarisoy.core.data.entity.Info

data class LocationListResponse(
    @SerializedName("info") val info: Info,

    @SerializedName("results") val locations: List<Location>
)