package com.djay.sweetdogs.presentation.dogslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
        if (dog != null) {
            holder.bind(dog)
        }
    }

    inner class DogViewHolder(private val binding: ItemDogsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Dog) {
            binding.dog = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.let { itemClick ->
                    itemClick(item)
                }
            }
        }
    }
}