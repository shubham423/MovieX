package com.shubham.moviesdb.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.shubham.moviesdb.R
import com.shubham.moviesdb.adapters.SearchMovieAdapter
import com.shubham.moviesdb.databinding.FragmentSearchBinding
import com.shubham.moviesdb.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private var query = ""
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        binding.buttonBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                getSearchResult()
            }

        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchResult()
                true
            }
            false
        }

    }

    private fun initObservers() {
        viewModel.onSearchMoviesResponse.observe(viewLifecycleOwner){
            if (it!=null){
                searchMovieAdapter= SearchMovieAdapter()
                it.body()?.let { it1 -> searchMovieAdapter.setData(it1.movies) }
                binding.searchRecyclerView.adapter=searchMovieAdapter
            }
        }
    }

    fun getSearchResult() {
        if (!binding.searchEditText.text.isNullOrEmpty())
            viewModel.searchMovie(binding.searchEditText.text.toString())
    }
}

