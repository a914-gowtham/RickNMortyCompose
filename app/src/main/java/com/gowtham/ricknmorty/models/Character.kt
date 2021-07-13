package com.gowtham.ricknmorty.models

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val gender: String?,
    val id: String?,
    val origin: Location?,
    val location: Location?,
    val episodes: ArrayList<Episode>?,
    val image: String?,
    val name: String?,
    val species: String?,
    val status: String?,
    val type: String?
)