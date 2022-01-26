package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Description(
    @SerializedName("value") var value: String= "",
    @SerializedName("attributes") var attributes : Attributes
): Serializable
