package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemMovieSearchBinding
import com.shubham.moviesdb.local.database.MovieEntity

class BookmarkMovieAdapter (): RecyclerView.Adapter<BookmarkMovieAdapter.MovieViewHolder>() {


     private var moviesList: List<MovieEntity>?=null

    fun setData(movies: List<MovieEntity>){
        moviesList=movies
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieSearchBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        moviesList?.get(position)?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int {
        return if (moviesList!=null){
            moviesList!!.size
        } else{
            0
        }
    }

    class MovieViewHolder(private val binding: ItemMovieSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .centerCrop()
                .into(binding.moviePoster)

            binding.movieTitle.text=movie.title
            binding.movieOverview.text=movie.overview
        }

    }
}

