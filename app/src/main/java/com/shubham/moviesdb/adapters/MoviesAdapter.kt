package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemMovieCardBinding
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.MovieResponse

class MoviesAdapter (private val moviesList: List<Movie>, private val callback: MoviesAdapterCallback): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position],callback)

    }

    override fun getItemCount(): Int {

        return moviesList.size
    }

    class MovieViewHolder(private val binding: ItemMovieCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, callback: MoviesAdapterCallback) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .centerCrop()
                .into(binding.moviePoster)
            binding.movieTitle.text=movie.title

            binding.root.setOnClickListener {
                callback.onMovieClicked(movie)
            }
        }

    }
}

interface MoviesAdapterCallback{
    fun onMovieClicked(movie: Movie)
}