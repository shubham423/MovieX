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
import com.shubham.moviesdb.adapters.TvShowsAdapter
import com.shubham.moviesdb.adapters.TvShowsAdapterCallback
import com.shubham.moviesdb.databinding.FragmentTvShowsBinding
import com.shubham.moviesdb.response.TvShow
import com.shubham.moviesdb.utils.Constants
import com.shubham.moviesdb.viewmodels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment(),TvShowsAdapterCallback {

    private val viewModel: TvShowsViewModel by viewModels()
    private lateinit var binding: FragmentTvShowsBinding

    private lateinit var popularAdapter: TvShowsAdapter
    private lateinit var topRatedAdapter: TvShowsAdapter
    private lateinit var nowPlayingAdapter: TvShowsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentTvShowsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularTvShows()
        viewModel.getTopRatedTvShows()
        viewModel.getNowPlayingTvShows()
        initObservers()
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.homeSearchButton.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment_to_searchFragment)
        }
    }

    private fun initObservers() {

        viewModel.onPopularTvShowsResponse.observe(viewLifecycleOwner,{
            Log.d("ferwerew", "$it")
            popularAdapter= it.body()?.let { it1 ->TvShowsAdapter(it1.results as List<TvShow>, this) }!!
            binding.rvPopular.adapter=popularAdapter
        })

        viewModel.onTopRatedTvShowsResponse.observe(viewLifecycleOwner,{
            Log.d("ferwerew###", "$it")
            topRatedAdapter= it.body()?.let { it1 -> TvShowsAdapter(it1.results as List<TvShow>, this) }!!
            binding.rvTopRated.adapter=topRatedAdapter
        })

//        viewModel.onNowPlayingTvShowsResponse.observe(viewLifecycleOwner,{
//            Log.d("ferwerasaSassew", "$it")
//            nowPlayingAdapter= it.body()?.let { it1 -> TvShowsAdapter(it1.results as List<TvShow>, this) }!!
//            binding.rvNowPlaying.adapter=nowPlayingAdapter
//        })

    }

    override fun onMovieClicked(tvShow: TvShow) {
        val bundle = Bundle()
        tvShow.id?.let { bundle.putInt(Constants.MOVIE_ID_KEY, it) }
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

}