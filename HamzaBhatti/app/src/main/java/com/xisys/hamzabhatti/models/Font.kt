package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Font(
    @SerializedName("size")  var size: Int
):Serializable
