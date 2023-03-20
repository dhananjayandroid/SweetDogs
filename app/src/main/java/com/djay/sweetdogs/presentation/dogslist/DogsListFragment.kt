package com.djay.sweetdogs.presentation.dogslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.djay.sweetdogs.R
import com.djay.sweetdogs.databinding.FragmentDogsListBinding
import com.djay.sweetdogs.domain.model.Dog
import com.djay.sweetdogs.presentation.dogslist.adapter.DogListAdapter
import com.djay.sweetdogs.presentation.dogslist.adapter.PagingLoadStateAdapter
import com.djay.sweetdogs.presentation.state.UIState
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
    lateinit var dogsListAdapter: DogListAdapter
    private lateinit var binding: FragmentDogsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dogs_list, container, false
        )
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
                    displayData(it)
                }
        }
    }

    private suspend fun displayData(uiState: UIState<PagingData<Dog>>) {
        when (uiState) {
            is UIState.Loading -> Log.i("displayData", "Loading")
            is UIState.SuccessWithData ->   dogsListAdapter.submitData(uiState.data)
            is UIState.Error -> Log.e("displayData", "Error")
            is UIState.SuccessWithNoData -> Log.i("displayData", "SuccessWithNoData")
        }
    }

    private fun initRecyclerView() = binding.apply {
        recyclerViewDogs.apply {
            adapter = dogsListAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter { dogsListAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun onDogSelected(dog: Dog) {
        viewModel.onDogSelected(dog)
    }
}
