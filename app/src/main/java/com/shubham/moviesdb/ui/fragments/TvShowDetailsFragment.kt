package com.shubham.moviesdb.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.shubham.moviesdb.R
import com.shubham.moviesdb.adapters.CastAdapter
import com.shubham.moviesdb.adapters.CastAdapterCallback
import com.shubham.moviesdb.databinding.FragmentTvShowDetailsBinding
import com.shubham.moviesdb.response.Cast
import com.shubham.moviesdb.response.CreditsResponse
import com.shubham.moviesdb.response.TvShow
import com.shubham.moviesdb.response.VideosResponse
import com.shubham.moviesdb.utils.Constants
import com.shubham.moviesdb.viewmodels.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

@AndroidEntryPoint
class TvShowDetailsFragment : Fragment(),CastAdapterCallback {

    private lateinit var binding: FragmentTvShowDetailsBinding

    private val viewModel: TvShowsViewModel by activityViewModels()

    private lateinit var castAdapter: CastAdapter
    var id : Int?=null
    private var show: TvShow?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentTvShowDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = requireArguments().getInt(Constants.MOVIE_ID_KEY, 0)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        viewModel.getShowById(id!!)

        initObservers()
    }

    private fun initObservers() {
        viewModel.onTvShowDetailsResponse.observe(viewLifecycleOwner, { show ->
            this.show= show.body()!!
            setShowDetails(show)
            setCastList(show.body()!!.credits?.cast)
            showTrailers(show.body()!!.videos)
        })
    }

    private fun setCastList(cast: List<Cast>?) {
        castAdapter= CastAdapter(this)
        if (cast != null) {
            castAdapter.setData(cast)
        }
        binding.castRv.adapter=castAdapter
    }

    private fun setShowDetails(show: Response<TvShow>?) {
        binding.movieDetailsTitle.text = show?.body()?.name ?: ""
        binding.movieDetailsOverview.text = show?.body()?.overview ?: ""
        binding.movieDetailsReleaseDate.text = show?.body()?.firstAirDate ?: ""

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + (show?.body()?.posterPath ?: ""))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.movieDetailsPoster)
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

    override fun onCastClicked(castId: Int) {
        TODO("Not yet implemented")
    }

}