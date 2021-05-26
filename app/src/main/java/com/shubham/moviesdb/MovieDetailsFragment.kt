package com.shubham.moviesdb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shubham.moviesdb.databinding.FragmentMovieDetailsBinding
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.VideosResponse
import com.shubham.moviesdb.utils.Constants.MOVIE_ID_KEY
import com.shubham.moviesdb.utils.Constants.YOUTUBE_THUMBNAIL_URL
import com.shubham.moviesdb.utils.Constants.YOUTUBE_VIDEO_URL
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import java.util.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getInt(MOVIE_ID_KEY, 0)
        Log.d("MoviesDetails", "id is $id")

        viewModel.getMovieById(id)
        initObservers()
    }

    private fun initObservers() {
        viewModel.onMoviesDetailsResponse.observe(viewLifecycleOwner, {
            setMovieDetails(it)
            showTrailers(
                it.body()?.videos
            )
        })

    }

    private fun showTrailers(videosResponse: VideosResponse?) {

        binding.movieTrailers.removeAllViews()
        if (videosResponse != null) {
            if (videosResponse.videos.isEmpty()) {
                binding.trailersLabel.visibility = View.GONE
                binding.movieTrailers.visibility = View.GONE
            } else {
                for ((key, name) in videosResponse.videos) {
                    val parent = layoutInflater.inflate(
                        R.layout.item_trailer,
                        binding.movieTrailers,
                        false
                    )
                    val thumbnailTrailer =
                        parent.findViewById<ImageView>(R.id.thumbnail_trailer)
                    val movieTrailerTitle =
                        parent.findViewById<TextView>(R.id.trailerTitle)
                    movieTrailerTitle.text = name
                    Glide.with(this)
                        .load(String.format(YOUTUBE_THUMBNAIL_URL, key))
                        .apply(RequestOptions.placeholderOf(R.color.violet).centerCrop())
                        .into(thumbnailTrailer)
                    thumbnailTrailer.requestLayout()
                    thumbnailTrailer.setOnClickListener {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(String.format(YOUTUBE_VIDEO_URL, key))
                            )
                        )
                    }
                    binding.movieTrailers.addView(parent)
                }
            }
        } else {
            binding.trailersLabel.visibility = View.GONE
            binding.movieTrailers.visibility = View.GONE
        }
    }


    private fun setMovieDetails(response: Response<Movie>?) {
        binding.movieDetailsTitle.text = response?.body()?.title ?: ""
        binding.movieDetailsOverview.text = response?.body()?.overview ?: ""
        binding.movieDetailsDuration.text = response?.body()?.runtime.toString()
        binding.movieDetailsGenres.text = response?.body()?.genres?.get(0).toString() ?: ""
        binding.movieDetailsReleaseDate.text = response?.body()?.releaseDate ?: ""

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + (response?.body()?.posterPath ?: ""))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.movieDetailsPoster)
    }


}