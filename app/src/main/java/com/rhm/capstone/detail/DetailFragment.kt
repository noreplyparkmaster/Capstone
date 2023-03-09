package com.rhm.capstone.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rhm.capstone.R
import com.rhm.capstone.core.data.Resource
import com.rhm.capstone.core.domain.model.Game
import com.rhm.capstone.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = DetailFragmentArgs.fromBundle(arguments as Bundle).id
        setupObserver(gameId)
    }

    private fun setupObserver(gameId: Int) {
        detailViewModel.getDetailGame(gameId).observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_LONG).show()
                    resource.data?.let { game ->
                        showDetail(game)
                    }
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { game ->
                        showDetail(game)
                    }
                }
            }
        }
    }

    private fun showDetail(game: Game) {
        with(binding) {
            fabFavorite.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(game.imageUrl)
                .into(ivCoverImage)
            tvDescription.text = game.description

            var statusFavorite = game.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailViewModel.setFavoriteGame(game, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_24))
        } else {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border_24))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}