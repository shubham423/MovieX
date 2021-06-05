package com.shubham.moviesdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemSimilarBinding
import com.shubham.moviesdb.response.SimilarMovie
import com.shubham.moviesdb.utils.Constants.TMDB_IMAGE_BASE_URL

class SimilarMoviesAdapter(private val callback: SimilarAdapterCallback): RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    private var moviesList: List<SimilarMovie>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSimilarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        moviesList?.get(position)?.let { holder.bind(it,callback) }
    }

    override fun getItemCount(): Int {
        if (moviesList!=null){
            return moviesList!!.size
        }else{
            return 0
        }

    }

    fun setData(moviesList: List<SimilarMovie>){
        this.moviesList=moviesList
    }


    class ViewHolder(private val binding: ItemSimilarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(similar: SimilarMovie, callback: SimilarAdapterCallback) {
                binding.titleSimilar.text = similar.title
                binding.ratingSimilar.text = similar.voteAverage.toString()
            Glide.with(itemView)
                .load(TMDB_IMAGE_BASE_URL + similar.posterPath)
                .centerCrop()
                .into(binding.posterSimilar)

            binding.posterSimilar.setOnClickListener {
                similar.id?.let { it1 -> callback.onMovieClicked(it1) }
            }
        }

    }
}

interface SimilarAdapterCallback{
    fun onMovieClicked(movieId: Int)
}
