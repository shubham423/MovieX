package com.shubham.moviesdb.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.shubham.moviesdb.R
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.utils.Constants.TMDB_IMAGE_BASE_URL
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(
    private val movies: List<Movie>
) : SliderViewAdapter<SliderAdapter.BackdropSlideVH>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup): BackdropSlideVH {
        return BackdropSlideVH(
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_item, null)
        )
    }

    override fun onBindViewHolder(viewHolder: BackdropSlideVH, position: Int) {
        viewHolder.bind(movies[position])
    }

    override fun getCount(): Int = movies.size

    class BackdropSlideVH(private val itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load(TMDB_IMAGE_BASE_URL + movie.backdropPath)
                .centerCrop()
                .into(itemView.findViewById(R.id.movieDetailsBackdrop))
        }
    }
}