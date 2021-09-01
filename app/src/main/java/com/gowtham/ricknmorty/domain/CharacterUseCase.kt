package com.gowtham.ricknmorty.domain

import android.content.Context
import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(val repository: MainRepository) {
    fun getCharacters(context: Context) = repository.getCharacters(context)
    suspend fun getCharacter(
        context: Context,
        characterId: String
    ) = repository.getCharacter(context, characterId)
}