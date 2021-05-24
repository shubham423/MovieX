package com.shubham.moviesdb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shubham.moviesdb.databinding.FragmentMovieDetailsBinding
import com.shubham.moviesdb.utils.Constants.MOVIE_ID_KEY
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getInt(MOVIE_ID_KEY,0)
        Log.d("MoviesDetails","id is $id")

        viewModel.getMovieById(id)
        initObservers()
    }

    private fun initObservers() {
        viewModel.onMoviesDetailsResponse.observe(viewLifecycleOwner,{

            binding.movieDetailsTitle.text = it.body()?.title ?: ""
            binding.movieDetailsOverview.text = it.body()?.overview ?: ""
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500"+ (it.body()?.posterPath ?: ""))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.movieDetailsPoster)
        })
    }


}