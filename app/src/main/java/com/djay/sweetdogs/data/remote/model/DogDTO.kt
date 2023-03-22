package com.djay.sweetdogs.data.remote.model

import android.os.Parcelable
import com.djay.sweetdogs.domain.model.Dog
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogDTO(
    val bred_for: String,
    val breed_group: String,
    val country_code: String,
    val description: String,
    val height: Height,
    val history: String,
    val id: Int,
    val image: Image,
    val life_span: String,
    val name: String,
    val origin: String,
    val reference_image_id: String,
    val temperament: String,
    val weight: Weight
) : Parcelable {

}

@Parcelize
data class Height(
    val imperial: String,
    val metric: String
): Parcelable

@Parcelize
data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
): Parcelable

@Parcelize
data class Weight(
    val imperial: String,
    val metric: String
): Parcelable

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