package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemMovieCardBinding
import com.shubham.moviesdb.databinding.ItemSearchBinding
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.MovieResponse

class SearchMovieAdapter (): RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder>() {


     private var moviesList: List<Movie>?=null

    fun setData(movies: List<Movie>){
        moviesList=movies
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemSearchBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        moviesList?.get(position)?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int {

        return moviesList!!.size
    }

    class MovieViewHolder(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .centerCrop()
                .into(binding.searchImage)

            binding.root.setOnClickListener {
               //todo
            }
        }

    }
}

