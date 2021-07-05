package com.gowtham.ricknmorty.models

import GetCharactersQuery
fun GetCharactersQuery.Characters.toDomain(): List<Character> {
    return results?.map { result ->
        Character(
            image = result?.fragments?.characterDetail?.image,
            name = result?.fragments?.characterDetail?.name
        )
    } ?: emptyList()
}
