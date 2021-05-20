package com.shubham.moviesdb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.shubham.moviesdb.adapters.MoviesAdapter
import com.shubham.moviesdb.databinding.FragmentMoviesBinding
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var popularAdapter: MoviesAdapter
    private lateinit var binding: FragmentMoviesBinding

    private lateinit var topRatedAdapter: MoviesAdapter
    private lateinit var nowPlayingAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
        viewModel.getNowPlayingMovies()

        initObservers()
    }

    private fun initObservers() {

        viewModel.onPopularMoviesResponse.observe(viewLifecycleOwner,{
            Log.d("ferwerew","$it")
            popularAdapter= it.body()?.let { it1 -> MoviesAdapter(it1.results) }!!
            binding.rvPopular.adapter=popularAdapter
        })

        viewModel.onTopRatedMoviesResponse.observe(viewLifecycleOwner,{
            Log.d("ferwerew###","$it")
            topRatedAdapter= it.body()?.let { it1 -> MoviesAdapter(it1.results) }!!
            binding.rvTopRated.adapter=topRatedAdapter
        })

        viewModel.onNowPlayingMoviesResponse.observe(viewLifecycleOwner,{
            Log.d("ferwerasaSassew","$it")
            nowPlayingAdapter= it.body()?.let { it1 -> MoviesAdapter(it1.results) }!!
            binding.rvNowPlaying.adapter=nowPlayingAdapter
        })

    }

}