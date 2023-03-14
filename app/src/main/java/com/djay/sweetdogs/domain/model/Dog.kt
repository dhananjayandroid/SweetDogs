package com.djay.sweetdogs.domain.model

import java.io.Serializable

data class Dog(
    val id: Int,
    val name: String,
    val breed_group: String?,
    val life_span: String?,
    val origin: String? = "",
    val image: String?,
) : Serializable {

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

        other as Dog
        if (breed_group != other.breed_group) return false
        if (id != other.id) return false
        if (image != other.image) return false
        if (life_span != other.life_span) return false
        if (name != other.name) return false
        if (origin != other.origin) return false

        return true
    }
}