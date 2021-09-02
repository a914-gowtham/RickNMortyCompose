package com.gowtham.ricknmorty.domain

import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(val repository: MainRepository) {
    fun getCharacters() = repository.getCharacters()
    suspend fun getCharacter(
        characterId: String
    ) = repository.getCharacter(characterId)
}
