package com.rhm.capstone.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhm.capstone.core.ui.GameAdapter
import com.rhm.capstone.di.FavoriteModuleDependencies
import com.rhm.capstone.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupView()
        setupObserver()
    }

    private fun setupAdapter() {
        gameAdapter = GameAdapter(
            onClickItem = { selectedGame ->
                val toDetailFragment = FavoriteFragmentDirections.actionNavFavoriteToNavDetail(selectedGame.name)
                toDetailFragment.id = selectedGame.id
                findNavController().navigate(toDetailFragment)
            }
        )
    }

    private fun setupView() {
        with(binding.rvGame) {
            layoutManager = if (context?.resources?.configuration?.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            setHasFixedSize(true)
            adapter = gameAdapter
        }
    }

    private fun setupObserver() {
        favoriteViewModel.favoriteGames.observe(viewLifecycleOwner) { favoriteGames ->
            gameAdapter.submitList(favoriteGames)
            if (favoriteGames.isNotEmpty()) {
                binding.emptyPlaceholder.visibility = View.GONE
            } else {
                binding.emptyPlaceholder.text = getString(R.string.empty_data)
                binding.emptyPlaceholder.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
        _binding = null
    }
}