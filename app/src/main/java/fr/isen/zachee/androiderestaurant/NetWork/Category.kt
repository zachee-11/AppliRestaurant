package fr.isen.zachee.androiderestaurant.NetWork

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name_fr") val name : String,
    @SerializedName("items") val items : List<Dish>
)
