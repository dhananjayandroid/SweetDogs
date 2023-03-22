package com.djay.sweetdogs.presentation.dogdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.djay.sweetdogs.R
import com.djay.sweetdogs.databinding.FragmentDogDetailBinding
import com.djay.sweetdogs.domain.model.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailFragment : Fragment() {
    private lateinit var binding: FragmentDogDetailBinding
    private val arguments: DogDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDogDetailBinding.inflate(inflater).apply { binding = this }.rootView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = displayData(arguments.dog)

    private fun displayData(dog: Dog) {
        binding.run {
            tvDogDetailsName.text = dog.name
            tvDogDetailsBreed.text = getString(R.string.breed, dog.breedGroup.orEmpty())
            tvDogDetailsLifeSpan.text = getString(R.string.lifespan, dog.lifeSpan.orEmpty())
            tvOrigin.text = getString(R.string.origin, dog.origin.orEmpty())
            Glide.with(this@DogDetailFragment).load(dog.image).into(ivDogDetails)
        }
    }
}
