package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("url") var url: String= "",
    @SerializedName("size") var size: Size
): Serializable
