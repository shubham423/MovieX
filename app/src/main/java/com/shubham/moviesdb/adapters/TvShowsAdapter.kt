package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemMovieCardBinding
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.TvShow
import dagger.hilt.android.AndroidEntryPoint


class TvShowsAdapter (private val tvShowsList: List<TvShow>, private val callback: TvShowsAdapterCallback): RecyclerView.Adapter<TvShowsAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(tvShowsList[position],callback)

    }

    override fun getItemCount(): Int {

        return tvShowsList.size
    }

    class MovieViewHolder(private val binding: ItemMovieCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow, callback: TvShowsAdapterCallback) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" +tvShow.posterPath)
                .centerCrop()
                .into(binding.moviePoster)
            binding.movieTitle.text=tvShow.name
            binding.root.setOnClickListener {
                callback.onMovieClicked(tvShow)
            }
        }
    }
}

interface TvShowsAdapterCallback{
    fun onMovieClicked(tvShow: TvShow)
}