package com.example.movierecommender.ui.component.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierecommender.data.model.MovieResult
import com.example.movierecommender.databinding.ItemMovieBinding

class MovieAdapter(
    private val onClick: (MovieResult) -> Unit,
    private val onRate: (Int) -> Unit
) : ListAdapter<MovieResult, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieResult) {
            binding.movieTitle.text = movie.title
            binding.root.setOnClickListener { onClick(movie) }
            binding.rateButton.setOnClickListener { onRate(movie.id) }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem == newItem
        }
    }

    fun updateMovies(newMovies: List<MovieResult>) {
        submitList(newMovies)
    }
}
