package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Attributes(
    @SerializedName("text_color") var text_color: String= "",
    @SerializedName("font")  var font: Font
): Serializable
