package com.example.movierecommender.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movierecommender.databinding.FragmentMovieDetailsBinding
import com.example.movierecommender.ui.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lấy movieId từ arguments và gọi API
        val movieId = args.movieId
        viewModel.getMovieDetails(movieId)

        // Quan sát dữ liệu từ ViewModel
        viewModel.movieDetails.observe(viewLifecycleOwner) { details ->
            details?.let { detail ->
                binding.titleText.text = detail.title
                binding.overviewText.text = detail.overview
                Glide.with(this).load(detail.poster_path).into(binding.posterImage)

                binding.rateButton.setOnClickListener { showRatingDialog(detail.movieId) }
                viewModel.getRecommendations(1, detail.title) // Giả lập userId = 1
            }
        }

        viewModel.recommendations.observe(viewLifecycleOwner) { recs ->
            Toast.makeText(requireContext(), "Recommendations: $recs", Toast.LENGTH_LONG).show()
        }

        viewModel.ratingStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showRatingDialog(movieId: Int) {
        val ratingInput = TextInputEditText(requireContext()).apply { hint = "Enter rating (1-5)" }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Rate Movie")
            .setView(ratingInput)
            .setPositiveButton("Submit") { _, _ ->
                val rating = ratingInput.text.toString().toFloatOrNull()
                if (rating != null && rating in 1.0..5.0) {
                    viewModel.rateMovie(1, movieId, rating) // Giả lập userId = 1
                } else {
                    Toast.makeText(requireContext(), "Invalid rating", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}