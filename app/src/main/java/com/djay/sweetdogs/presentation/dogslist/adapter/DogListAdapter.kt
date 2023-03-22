package com.djay.sweetdogs.presentation.dogslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djay.sweetdogs.R
import com.djay.sweetdogs.databinding.ItemDogsListBinding
import com.djay.sweetdogs.domain.model.Dog
import javax.inject.Inject

class DogListAdapter @Inject constructor() :
    PagingDataAdapter<Dog, DogListAdapter.DogViewHolder>(COMPARATOR) {

    var onItemClickListener: ((Dog) -> Unit)? = null

    fun setItemClickListener(listener: (Dog) -> Unit) {
        onItemClickListener = listener
    }

    object COMPARATOR : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
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
        dog?.let { holder.bind(dog) }
    }

    inner class DogViewHolder(private val binding: ItemDogsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Dog) {
            val context = binding.root.context
            binding.tvName.text = item.name
            binding.tvBreed.text = context.getString(R.string.breed, item.breedGroup.orEmpty())
            binding.tvLife.text = context.getString(R.string.lifespan, item.lifeSpan.orEmpty())
            binding.tvOrigin.text = context.getString(R.string.origin, item.origin.orEmpty())
            Glide.with(context).load(item.image).into(binding.ivDog)
            binding.root.setOnClickListener {
                onItemClickListener?.let { itemClick ->
                    itemClick(item)
                }
            }
        }
    }
}