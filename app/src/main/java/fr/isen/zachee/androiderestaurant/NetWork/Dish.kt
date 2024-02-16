package fr.isen.zachee.androiderestaurant.NetWork

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val name : String,
    @SerializedName("images") val images : List<String>,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("prices") val prices: List<Price>
):Serializable
