package com.gowtham.ricknmorty.models

/*
fun GetCharactersQuery.Characters.toDomain(): List<Character> {

    return results?.map {
        val character=it?.fragments!!.characterDetail
        character.toDomain()
    }
        ?: emptyList()
}

fun CharacterDetail.toDomain(): Character{
    return   Character(id = id,
        gender = gender,
        origin = origin,
        location = location,
        episodes = episode,
        image = image,
        name = name,
        species = species,
        status = status,
        type = type)
}

fun GetLocationsQuery.Locations.toDomain(): List<Location> {

    return results?.map {
        val location=it?.fragments!!.locationDetail
        location.toDomain()
    }
        ?: emptyList()
}

fun LocationDetail.toDomain(): Location{
    return Location(id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents)
}

fun GetEpisodesQuery.Episodes.toDomain(): List<Episode> {

    return results?.map {
        val episode=it?.fragments!!.episodeDetail
        episode.toDomain()
    }
        ?: emptyList()
}

fun EpisodeDetail.toDomain(): Episode{
    return Episode(id = id,
        name = name,
         airTime = air_date,
    episode = episode,
    characters = characters?.map { it } ?: emptyList<Character>())
}*/
