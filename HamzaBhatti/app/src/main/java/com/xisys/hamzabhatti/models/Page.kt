package com.xisys.hamzabhatti.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Page(
    @SerializedName("cards") var cards : ArrayList<AllCards> = ArrayList()
):Serializable
