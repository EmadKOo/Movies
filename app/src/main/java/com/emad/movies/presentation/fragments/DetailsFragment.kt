package com.emad.movies.presentation.fragments

import android.opengl.Visibility
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
import com.emad.movies.BuildConfig
import com.emad.movies.R
import com.emad.movies.data.model.MovieDetails
import com.emad.movies.databinding.FragmentDetailsBinding
import com.emad.movies.presentation.adapters.ReviewsAdapter
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    lateinit var mBinding: FragmentDetailsBinding
    val movieViewModel: MovieViewModel by viewModels()
    val args: DetailsFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: ReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovieDetails(args.movieID)
        loadMovieReviews(args.movieID)
        requestNewToken()
    }

    private fun loadMovieDetails(movieID: Int) {
        lifecycleScope.launchWhenStarted {
            movieViewModel.getMovieDetails(movieID)
            movieViewModel.movieDetailsStateFlow.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        Log.d(TAG, "loadMovieDetails: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "loadMovieDetails: Loading ")
                    }
                    is Resource.Success -> {
                        bindingDetails(it.data!!)
                    }
                }
            }
        }
    }

    private fun loadMovieReviews(movieID: Int) {
        lifecycleScope.launchWhenStarted {
            movieViewModel.getMovieReviews(movieID)
            movieViewModel.getMovieReviewsStateFlow.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        Log.d(TAG, "loadMovieReviews: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "loadMovieReviews: Loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "loadMovieReviews: SUCCESS " + it.data)
                        if (it.data?.results?.size!!>0) {
                            mBinding.reviewsRecyclerView.adapter = adapter
                            mBinding.reviewsRecyclerView.visibility= View.VISIBLE
                            adapter.submitList(it.data!!.results)
                        }else{
                            Log.d(TAG, "loadMovieReviews: ELSES ")
                            mBinding.reviewsTV.setText(getString(R.string.noReviews))
                        }
                    }
                }
            }
        }
    }

    private fun requestNewToken(){
        lifecycleScope.launchWhenStarted {
           val job= movieViewModel.requestNewToken()
            movieViewModel.requestTokenStateFlow.collectLatest {
                when(it){
                    is Resource.Error -> {
                        Log.d(TAG, "requestNewToken: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "requestNewToken: Loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "requestNewToken: SUCCESS " + it.data)
                    }
                }
            }

        }
    }
    private fun bindingDetails(movieDetails: MovieDetails) {
        Picasso.get().load("${BuildConfig.IMAGE_BASE}${movieDetails.poster_path}")
            .into(mBinding.movieImageView)
        mBinding.movieDescription.setText(movieDetails.overview)
        mBinding.popularityVotes.setText(movieDetails.popularity.toString())
        mBinding.movieVotes.setText(movieDetails.vote_average.toString())
        mBinding.movieReleaseDate.setText(movieDetails.release_date)
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }
}