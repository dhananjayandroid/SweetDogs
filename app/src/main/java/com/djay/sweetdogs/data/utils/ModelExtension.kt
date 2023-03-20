package com.djay.sweetdogs.data.utils

import com.djay.sweetdogs.data.remote.model.DogDTO
import com.djay.sweetdogs.domain.model.Dog

fun DogDTO.toDog(): Dog {
    return Dog(
        this.id,
        this.name,
        this.breed_group,
        this.life_span,
        this.origin,
        this.image.url
    )
}

fun List<DogDTO>.toDogList(): List<Dog> {
    return this.map { it.toDog() }
}