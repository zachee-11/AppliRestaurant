package fr.isen.zachee.androiderestaurant.NetWork

import com.google.gson.annotations.SerializedName

data class MenuData(@SerializedName("data")val data: List<Category>)
