package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemMovieSearchBinding
import com.shubham.moviesdb.databinding.ItemSearchBinding
import com.shubham.moviesdb.response.Movie

class SearchMovieAdapter (val callback: SearchMovieAdapterCallback): RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder>() {


     private var moviesList: List<Movie>?=null

    fun setData(movies: List<Movie>){
        moviesList=movies
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieSearchBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        moviesList?.get(position)?.let { holder.bind(it,callback) }

    }

    override fun getItemCount(): Int {

        return moviesList!!.size
    }

    class MovieViewHolder(private val binding: ItemMovieSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, callback: SearchMovieAdapterCallback) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .centerCrop()
                .into(binding.moviePoster)

            binding.movieRating.text= movie.voteAverage.toString()
            binding.movieTitle.text=movie.title
            binding.movieOverview.text=movie.overview
            binding.root.setOnClickListener {
                movie.id?.let { it1 -> callback.onMovieClicked(it1) }
            }
        }

    }
}

interface SearchMovieAdapterCallback{
    fun onMovieClicked(id : Int)
}

