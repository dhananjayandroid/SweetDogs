package com.djay.sweetdogs.data.mapper

import com.djay.sweetdogs.data.model.DogResponse
import com.djay.sweetdogs.domain.mapper.Mapper
import com.djay.sweetdogs.domain.model.Dog

class DogMapper : Mapper<DogResponse, Dog> {

    override fun mapFromEntity(entity: DogResponse): Dog {
        return Dog(
            entity.id,
            entity.name,
            entity.breed_group,
            entity.life_span,
            entity.origin,
            entity.image.url
        )
    }

    override fun mapToEntity(data: Dog): DogResponse {
        return null!! // todo skipping for now
    }

    fun mapFromEntityList(entities: List<DogResponse>): List<Dog> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntityList(dogs: List<Dog>): List<DogResponse> {
        return dogs.map { mapToEntity(it) }
    }
}