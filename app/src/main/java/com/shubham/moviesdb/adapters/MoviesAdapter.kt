package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemMovieCardBinding
import com.shubham.moviesdb.response.MovieResponse
import com.shubham.moviesdb.utils.Constants.TMDB_BASE_URL

class MoviesAdapter (private val moviesList: List<MovieResponse.Result>): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {

        return moviesList.size
    }

    class MovieViewHolder(private val binding: ItemMovieCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieResponse.Result) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + data.posterPath)
                .centerCrop()
                .into(binding.moviePoster)
            binding.movieTitle.text=data.title
        }

    }
}