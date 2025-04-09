package com.example.movierecommender.ui.component.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierecommender.databinding.FragmentSearchBinding
import com.example.movierecommender.ui.MainViewModel
import com.example.movierecommender.ui.component.search.adapter.MovieAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Thiết lập RecyclerView
        adapter = MovieAdapter(
            onClick = { movie ->
                val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(movie.id)
                findNavController().navigate(action)
            },
            onRate = { movieId -> viewModel.rateMovie(1, movieId, 4.0f) } // Giả lập userId = 1, rating = 4.0
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Thiết lập SearchView
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchMovies(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        // Quan sát dữ liệu từ ViewModel
        viewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            adapter.updateMovies(movies)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}