package com.shubham.moviesdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shubham.moviesdb.R
import com.shubham.moviesdb.adapters.MoviesAdapter
import com.shubham.moviesdb.adapters.MoviesAdapterCallback
import com.shubham.moviesdb.databinding.FragmentMoviesBinding
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.utils.Constants
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() , MoviesAdapterCallback {

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
        initClickListeners()

    }

    private fun initClickListeners() {
        binding.homeSearchButton.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment_to_searchFragment)
        }
    }

    private fun initObservers() {

        viewModel.onPopularMoviesResponse.observe(viewLifecycleOwner,{
            binding.popularShimmerContainer.visibility=View.GONE
            Log.d("ferwerew", "$it")
            popularAdapter= it.body()?.let { it1 -> MoviesAdapter(it1.movies, this) }!!
            binding.rvPopular.adapter=popularAdapter
        })

        viewModel.onTopRatedMoviesResponse.observe(viewLifecycleOwner,{
            binding.topRatedShimmerContainer.visibility=View.GONE
            Log.d("ferwerew###", "$it")
            topRatedAdapter= it.body()?.let { it1 -> MoviesAdapter(it1.movies, this) }!!
            binding.rvTopRated.adapter=topRatedAdapter
        })

        viewModel.onNowPlayingMoviesResponse.observe(viewLifecycleOwner,{
            binding.nowPlayingShimmerContainer.visibility=View.GONE
            Log.d("ferwerasaSassew", "$it")
            nowPlayingAdapter= it.body()?.let { it1 -> MoviesAdapter(it1.movies, this) }!!
            binding.rvNowPlaying.adapter=nowPlayingAdapter
        })

    }

    override fun onMovieClicked(movie: Movie) {
        val bundle = Bundle()
        movie.id?.let { bundle.putInt(Constants.MOVIE_ID_KEY, it) }
       findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

}