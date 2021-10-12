package com.yusufarisoy.core.data.entity.character

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Origin(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
) : Parcelable