package com.djay.sweetdogs.utils

import com.djay.sweetdogs.data.mapper.DogMapper
import com.djay.sweetdogs.data.model.DogResponse
import com.djay.sweetdogs.data.model.Height
import com.djay.sweetdogs.data.model.Image
import com.djay.sweetdogs.data.model.Weight

object FakeDataProvider {

    val fakeDogResponse1 = DogResponse(
        bred_for = "Companionship",
        breed_group = "Toy",
        country_code = "US",
        description = "The Pug is a small, stocky, short-haired dog with a wrinkled face and curled tail.",
        height = Height(imperial = "10 - 13", metric = "25 - 33"),
        history = "The Pug is one of the oldest breeds of dogs and has been around for over 2,000 years.",
        id = 1,
        image = Image(
            height = 500,
            id = "abc123",
            url = "https://example.com/pug.jpg",
            width = 500
        ),
        life_span = "12 - 15 years",
        name = "Pug",
        origin = "China",
        reference_image_id = "xyz789",
        temperament = "Charming, Mischievous, Loving",
        weight = Weight(imperial = "14 - 18", metric = "6 - 8")
    )

    val fakeDogResponse2 = DogResponse(
        bred_for = "Hunting",
        breed_group = "Hound",
        country_code = "GB",
        description = "The Beagle is a small hound that is famous for its excellent sense of smell and ability to track prey.",
        height = Height(imperial = "13 - 15", metric = "33 - 38"),
        history = "The Beagle has been around since ancient times and was originally bred for hunting rabbits and hares.",
        id = 2,
        image = Image(
            height = 600,
            id = "def456",
            url = "https://example.com/beagle.jpg",
            width = 600
        ),
        life_span = "12 - 15 years",
        name = "Beagle",
        origin = "England",
        reference_image_id = "uvw987",
        temperament = "Friendly, Curious, Merry",
        weight = Weight(imperial = "20 - 25", metric = "9 - 11")
    )

    val fakeDogsResponseList = listOf(
        fakeDogResponse1,
        fakeDogResponse2
    )

    val fakeDogsList = DogMapper().mapFromEntityList(
        listOf(
            fakeDogResponse1,
            fakeDogResponse2
        )
    )
}