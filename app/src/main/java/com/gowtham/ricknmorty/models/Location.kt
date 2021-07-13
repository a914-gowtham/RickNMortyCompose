package com.gowtham.ricknmorty.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: String?,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: ArrayList<Character>?,
    val created: String?
)