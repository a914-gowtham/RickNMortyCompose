package com.gowtham.ricknmorty.models

data class Episode(
    val id: String,
    val name: String,
    val airTime: String,
    val episode: String,
    val characters: ArrayList<Character>?,
    val created: String
)
