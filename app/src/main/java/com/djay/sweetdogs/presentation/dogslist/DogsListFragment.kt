package com.djay.sweetdogs.presentation.dogslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.djay.sweetdogs.databinding.FragmentDogsListBinding
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.presentation.dogslist.adapter.DogsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class DogsListFragment : Fragment() {
    private val viewModel: DogsListViewModel by viewModels()

    @Inject
    lateinit var dogsListAdapter: DogsListAdapter
    private lateinit var binding: FragmentDogsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogsListBinding.inflate(inflater)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        collectDogsList()
        onItemClicked()

        dogsListAdapter.setItemClickListener(::onDogSelected)
    }

    private fun onItemClicked() {
        viewModel.selectedDog.onEach {
            findNavController().navigate(
                DogsListFragmentDirections.actionDogListFragmentToDogDetailFragment(it)
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectDogsList() {
        lifecycleScope.launch {
            viewModel.dogsList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    dogsListAdapter.submitList(it)
                }
        }
    }

    private fun initRecyclerView() = binding.recyclerViewDogs.apply {
        adapter = dogsListAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
    }

    private fun onDogSelected(dog: Dog) {
        viewModel.onDogSelected(dog)
    }
}
