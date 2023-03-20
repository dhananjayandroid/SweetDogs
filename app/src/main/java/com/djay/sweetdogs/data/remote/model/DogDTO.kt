package com.djay.sweetdogs.data.remote.model

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
) {
    override fun hashCode(): Int {
        var result = id.hashCode()
        if(name.isEmpty()){
            result = 31 * result + name.hashCode()
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DogDTO

        if (bred_for != other.bred_for) return false
        if (breed_group != other.breed_group) return false
        if (country_code != other.country_code) return false
        if (description != other.description) return false
        if (height != other.height) return false
        if (history != other.history) return false
        if (id != other.id) return false
        if (image != other.image) return false
        if (life_span != other.life_span) return false
        if (name != other.name) return false
        if (origin != other.origin) return false
        if (reference_image_id != other.reference_image_id) return false
        if (temperament != other.temperament) return false
        if (weight != other.weight) return false

        return true
    }
}

data class Height(
    val imperial: String,
    val metric: String
)

data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

data class Weight(
    val imperial: String,
    val metric: String
)