package com.shubham.moviesdb.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
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
import com.shubham.moviesdb.local.database.MovieEntity
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

    private var favorite = false
    private  var movie: Movie?=null
    private var id: Int?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = requireArguments().getInt(Constants.MOVIE_ID_KEY, 0)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        viewModel.getMovieById(id!!)
        viewModel.getCastCredits(id!!)
        viewModel.getSimilarMovies(id!!)
        initObservers()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.d("details @@", "initObservers: $favorite ")
        if (favorite) {
            menu.findItem(R.id.favorite).setIcon(R.drawable.ic_bookmark_filled)
        } else {
            menu.findItem(R.id.favorite).setIcon(R.drawable.ic_bookmark_unfilled)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            if (favorite) {
                deleteFavorite(item)
            } else {
                addFavorite(item)

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initObservers() {
        viewModel.onMoviesDetailsResponse.observe(viewLifecycleOwner, { movie ->
            this.movie= movie.body()!!
            setMovieDetails(movie)
            showTrailers(
                movie.body()?.videos
            )
        })

        viewModel.onCastCreditsResponse.observe(viewLifecycleOwner) {
            setCastRv(it.body()?.cast)
        }

        viewModel.onSimilarMoviesResponse.observe(viewLifecycleOwner) {
            setSimilarRv(it.body()?.results)
        }

        id?.let { viewModel.isMovieExist(it) }

        id?.let {
            viewModel.onIsMovieExist.observe(viewLifecycleOwner){
                if(it){
                    favorite=true
                    invalidateOptionsMenu(requireActivity())
                }
            }
        }

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
        binding.movieDetailsDuration.text = response?.body()?.runtime.toString()+" min"
        binding.movieDetailsReleaseDate.text = response?.body()?.releaseDate ?: ""
        binding.ratingsText.text = this.movie?.voteAverage.toString()
        if (response != null) {
            binding.movieDetailsGenres.text= getGenres(response.body()?.genres as List<Movie.Genre>)
        }

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + (response?.body()?.posterPath ?: ""))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.movieDetailsPoster )
    }

    override fun onMovieClicked(movieId: Int) {
        val bundle = Bundle()
        movieId.let { bundle.putInt(Constants.MOVIE_ID_KEY, it) }
        findNavController().navigate(R.id.action_movieDetailsFragment_self, bundle)
    }

    override fun onCastClicked(castId: Int) {
        val bundle = Bundle()
        bundle.putInt(CAST_ID_KEY,castId)
        findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment, bundle)
    }


    private fun deleteFavorite(item: MenuItem) {
        val movieEntity=MovieEntity(movie?.id!!,movie?.title!!,movie?.overview!!,movie?.posterPath!!,
            movie!!.releaseDate!!,0f)
        viewModel.deleteFavMovie(movieEntity)
        favorite = false
        item.setIcon(R.drawable.ic_bookmark_unfilled)

    }

    private fun addFavorite(item: MenuItem) {
        val movieEntity=MovieEntity(movie?.id!!,movie?.title!!,movie?.overview!!,movie?.posterPath!!,
            movie!!.releaseDate!!,0f)
        viewModel.addFavoriteMovie(movieEntity)
        favorite = true
        item.setIcon(R.drawable.ic_bookmark_filled)
    }

    fun getGenres(genres: List<Movie.Genre>): String {
        val currentGenres = mutableListOf<String>()
        for (genre in genres) {
            genre.name?.let { currentGenres.add(it) }
        }
        return TextUtils.join(", ", currentGenres)
    }

}