package com.shubham.moviesdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shubham.moviesdb.adapters.BookmarkMovieAdapter
import com.shubham.moviesdb.databinding.FragmentBookMarksBinding
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookMarksFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var binding : FragmentBookMarksBinding
    private lateinit var bookmarkMovieAdapter: BookmarkMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentBookMarksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMovies()
        viewModel.onAllMoviesResponse.observe(viewLifecycleOwner){
            Log.d("Bookmark","$it")
            bookmarkMovieAdapter= BookmarkMovieAdapter()
            bookmarkMovieAdapter.setData(it)
            binding.bookmarkRv.adapter=bookmarkMovieAdapter
        }
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}