package com.gowtham.ricknmorty.domain

import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class EpisodeUseCase @Inject constructor(val repository: MainRepository) {
    fun getEpisodes() = repository.getEpisodes()
    suspend fun getEpisode(
        episodeId: String
    ) = repository.getEpisode(episodeId)
}
