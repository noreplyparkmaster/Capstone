package com.rhm.capstone.home

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhm.capstone.R
import com.rhm.capstone.core.data.Resource
import com.rhm.capstone.core.ui.GameAdapter
import com.rhm.capstone.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupView()
        setupObserver()
    }

    private fun setupAdapter() {
        gameAdapter = GameAdapter()
        gameAdapter.onItemClick = { selectedGame ->
            val toDetailFragment = HomeFragmentDirections.actionNavHomeToNavDetail(selectedGame.name)
            toDetailFragment.id = selectedGame.id
            findNavController().navigate(toDetailFragment)
        }
    }

    private fun setupView() {
        with(binding.rvGame) {
            layoutManager = if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            setHasFixedSize(true)
            adapter = gameAdapter
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                homeViewModel.setSearchQuery(query)
                return true
            }
        })
    }

    private fun setupObserver() {
        homeViewModel.games.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorPlaceholder.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorPlaceholder.visibility = View.VISIBLE
                    binding.errorPlaceholder.text = resource.message
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    gameAdapter.setData(resource.data)
                    resource.data?.let { games ->
                        if (games.isEmpty()) {
                            binding.errorPlaceholder.visibility = View.VISIBLE
                            binding.errorPlaceholder.text = getString(R.string.empty_data)
                        } else {
                            binding.errorPlaceholder.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}