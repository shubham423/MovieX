package com.shubham.moviesdb.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shubham.moviesdb.R
import com.shubham.moviesdb.adapters.CastAdapter
import com.shubham.moviesdb.adapters.CastAdapterCallback
import com.shubham.moviesdb.adapters.SimilarAdapterCallback
import com.shubham.moviesdb.adapters.SimilarMoviesAdapter
import com.shubham.moviesdb.databinding.FragmentMovieDetailsBinding
import com.shubham.moviesdb.response.Cast
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.SimilarMovie
import com.shubham.moviesdb.response.VideosResponse
import com.shubham.moviesdb.utils.Constants
import com.shubham.moviesdb.utils.Constants.CAST_ID_KEY
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), SimilarAdapterCallback,CastAdapterCallback {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var castAdapter: CastAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getInt(Constants.MOVIE_ID_KEY, 0)
        Log.d("MoviesDetails", "id is $id")

        viewModel.getMovieById(id)
        viewModel.getCastCredits(id)
        viewModel.getSimilarMovies(id)
        initObservers()
    }

    private fun initObservers() {
        viewModel.onMoviesDetailsResponse.observe(viewLifecycleOwner, { movie ->
            setMovieDetails(movie)
            showTrailers(
                movie.body()?.videos
            )

            viewModel.onCastCreditsResponse.observe(viewLifecycleOwner) {
                setCastRv(it.body()?.cast)
            }

            viewModel.onSimilarMoviesResponse.observe(viewLifecycleOwner) {
                setSimilarRv(it.body()?.results)
            }
        })

    }

    private fun setSimilarRv(results: List<SimilarMovie?>?) {
        similarMoviesAdapter = SimilarMoviesAdapter(this)
        if (!results.isNullOrEmpty()){
            similarMoviesAdapter.setData(results as List<SimilarMovie>)
            binding.similarRv.adapter = similarMoviesAdapter
        }
        else{
            Toast.makeText(requireContext(), "No similar movies found", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setCastRv(cast: List<Cast?>?) {
        Log.d("details", "cast list is $cast")
        castAdapter = CastAdapter(this)
        if (cast != null) {
            castAdapter.setData(cast as List<Cast>)
        }
        binding.castRv.adapter = castAdapter
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
                        .load(String.format(Constants.YOUTUBE_THUMBNAIL_URL, key))
                        .apply(RequestOptions.placeholderOf(R.color.black_transp).centerCrop())
                        .into(thumbnailTrailer)
                    thumbnailTrailer.requestLayout()
                    thumbnailTrailer.setOnClickListener {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(String.format(Constants.YOUTUBE_VIDEO_URL, key))
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
        binding.movieDetailsReleaseDate.text = response?.body()?.releaseDate ?: ""

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + (response?.body()?.posterPath ?: ""))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.movieDetailsPoster)
    }

    override fun onMovieClicked(movieId: Int) {
        val bundle = Bundle()
        movieId.let { bundle.putInt(Constants.MOVIE_ID_KEY, it) }
        findNavController().navigate(R.id.action_movieDetailsFragment_self, bundle)
    }

    override fun onCastClicked(castId: Int) {
        Log.d("Movuedetails", "cast id is $id")
        val bundle = Bundle()
        bundle.putInt(CAST_ID_KEY,castId)
        findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment, bundle)
    }


}