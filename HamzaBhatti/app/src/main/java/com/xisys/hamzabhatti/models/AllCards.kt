package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllCards(
    @SerializedName("card_type") var card_type: String= "",
    @SerializedName("card") var card : Card,
):Serializable
