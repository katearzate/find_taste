package com.example.findtaste.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Commerce (
    @SerializedName("id")
    val id: Int,
    @SerializedName("commerce")
    val commerce: String,
    @SerializedName("address")
    val address: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("latitude")
    val lat: Double,
    @SerializedName("longitude")
    val lng: Double,
    @SerializedName("category")
    val category: String,
    @SerializedName("telephone")
    val telephone: String?,
    @SerializedName("photo")
    val photo: String?
): Serializable