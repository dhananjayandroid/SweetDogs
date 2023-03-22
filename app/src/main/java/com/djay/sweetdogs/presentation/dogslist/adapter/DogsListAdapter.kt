package com.djay.sweetdogs.presentation.dogslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djay.sweetdogs.R
import com.djay.sweetdogs.databinding.ItemDogsListBinding
import com.djay.sweetdogs.domain.model.Dog
import javax.inject.Inject

class DogsListAdapter @Inject constructor() :
    ListAdapter<Dog, DogsListAdapter.DogViewHolder>(ItemDiffCallback) {

    var onItemClickListener: ((Dog) -> Unit)? = null

    fun setItemClickListener(listener: (Dog) -> Unit) {
        onItemClickListener = listener
    }

    inner class DogViewHolder(private val binding: ItemDogsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Dog) {
            val context = binding.root.context
            binding.run {
                tvName.text = item.name
                tvBreed.text = context.getString(R.string.breed, item.breedGroup.orEmpty())
                tvLife.text = context.getString(R.string.lifespan, item.lifeSpan.orEmpty())
                tvOrigin.text = context.getString(R.string.origin, item.origin.orEmpty())
                Glide.with(context).load(item.image).into(ivDog)
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick -> itemClick(item) }
                }
            }
        }
    }

    private companion object ItemDiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
                    && oldItem.breedGroup == newItem.breedGroup
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemDogsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dog?.let { holder.bind(getItem(position)) }
    }
}