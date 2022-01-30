package com.emad.movies.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.emad.movies.R
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.databinding.FragmentMoviesBinding
import com.emad.movies.domain.listeners.OnMovieSelected
import com.emad.movies.presentation.adapters.MovieAdapter
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MoviesFragment : Fragment(), OnMovieSelected {
    private lateinit var mBinding: FragmentMoviesBinding
    val moviesViewModel: MovieViewModel by viewModels()
    val adapter= MovieAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       mBinding= FragmentMoviesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.moviesRecyclerView.adapter= adapter
        loadPopularMovies()
    }

    private fun loadPopularMovies(){
        lifecycleScope.launchWhenStarted {
            moviesViewModel.moviesFlow.collectLatest {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }
    companion object{
        private const val TAG = "MoviesFragment"
    }

    override fun movieSelected(movie: PopularMovies.Result) {

    }
}