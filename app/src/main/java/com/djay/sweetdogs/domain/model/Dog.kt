package com.djay.sweetdogs.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Int,
    val name: String,
    val breedGroup: String?,
    val lifeSpan: String?,
    val origin: String? = "",
    val image: String?,
) : Parcelable