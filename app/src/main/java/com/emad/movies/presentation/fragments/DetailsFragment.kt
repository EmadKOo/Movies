package com.emad.movies.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.emad.movies.R
import com.emad.movies.databinding.FragmentDetailsBinding
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    lateinit var mBinding: FragmentDetailsBinding
    val movieViewModel: MovieViewModel by viewModels()
    val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding= FragmentDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovieDetails(args.movieID)
    }

    private fun loadMovieDetails(movieID: Int){
        lifecycleScope.launchWhenStarted {
            movieViewModel.getMovieDetails(movieID)
            movieViewModel.movieDetailsStateFlow.collectLatest {
                when(it){
                    is Resource.Error -> {
                        Log.d(TAG, "loadMovieDetails: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "loadMovieDetails: Loading ")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "loadMovieDetails: Success " + it.data?.adult)
                    }
                }
            }
        }
    }

    companion object{
        private const val TAG = "DetailsFragment"
    }
}