package com.shubham.moviesdb.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.shubham.moviesdb.R
import com.shubham.moviesdb.databinding.FragmentActorDetailsBinding
import com.shubham.moviesdb.utils.Constants
import com.shubham.moviesdb.utils.Constants.TMDB_IMAGE_BASE_URL
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : Fragment() {
    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var binding: FragmentActorDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentActorDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getInt(Constants.CAST_ID_KEY, 0)
        Log.d("Actordetails", "cast id is $id")

        viewModel.getActorDetails(id)
        initObservers()
    }

    private fun initObservers() {
        viewModel.onCastDetailsResponse.observe(viewLifecycleOwner){
            binding.textActorName.text = it.body()?.name
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original"+ it.body()?.profilePath)

            binding.textBio.text = it.body()?.biography

            binding.textPopularity.text = it.body()?.popularity.toString()
            binding.textCharacter.text = it.body()?.knownForDepartment
            binding.textBirthday.text = it.body()?.birthday
        }
    }

}