package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Card(
    @SerializedName("value") var value: String= "",
    @SerializedName("attributes") var attributes : Attributes,
    @SerializedName("title") var title: Description,
    @SerializedName("description") var description: Description,
    @SerializedName("image") var image: Image
):Serializable
