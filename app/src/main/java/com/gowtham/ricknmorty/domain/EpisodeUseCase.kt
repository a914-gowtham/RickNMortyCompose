package com.gowtham.ricknmorty.domain

import android.content.Context
import com.gowtham.ricknmorty.data.MainRepository
import javax.inject.Inject

class EpisodeUseCase @Inject constructor(val repository: MainRepository) {
    fun getEpisodes(context: Context) = repository.getEpisodes(context)
    suspend fun getEpisode(
        context: Context,
        episodeId: String
    ) = repository.getEpisode(context, episodeId)
}