package com.emad.movies.presentation.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.emad.movies.BuildConfig
import com.emad.movies.R
import com.emad.movies.data.enums.FavouriteStatus
import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.model.MovieDetails
import com.emad.movies.data.model.RequestRate
import com.emad.movies.databinding.FragmentDetailsBinding
import com.emad.movies.presentation.adapters.ReviewsAdapter
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import java.net.URL

import java.io.InputStream

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    lateinit var mBinding: FragmentDetailsBinding
    val movieViewModel: MovieViewModel by viewModels()
    val args: DetailsFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: ReviewsAdapter
    lateinit var movieDetail: DetailsEntity
    var isFav: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("setFavourite") { requestKey, bundle ->
            val action = bundle.getBoolean("action")
            isFav= action
            if (action)
                mBinding.favIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            else
                mBinding.favIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        handleViews()
        loadMovieDetails(args.movieID)
        loadMovieReviews(args.movieID)
    }

    override fun onResume() {
        super.onResume()
        checkIfFav(args.movieID)
    }
    private fun handleToolbar(){
        mBinding.backButtonDetails.setOnClickListener {
            findNavController().popBackStack()
        }

        mBinding.rateImageView.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToRateDialog(args.movieID))
        }
        mBinding.refreshIcon.setOnClickListener {
            loadMovieDetails(args.movieID)
            loadMovieReviews(args.movieID)
        }
    }

    private fun handleViews(){
        mBinding.favIcon.setOnClickListener {
            if (isFav)
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToFavouriteDialog(args.movieID, movieDetail?.movieName, "${BuildConfig.IMAGE_BASE}${movieDetail.movieImagePath}", FavouriteStatus.UN_FAVOURITE.ordinal))
            else
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToFavouriteDialog(args.movieID, movieDetail?.movieName, "${BuildConfig.IMAGE_BASE}${movieDetail.movieImagePath}", FavouriteStatus.FAVOURITE.ordinal))
        }
    }
    private fun loadMovieDetails(movieID: Int) {
        lifecycleScope.launchWhenStarted {
            movieViewModel.getMovieDetails(movieID)
            movieViewModel.movieDetailsStateFlow.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        Log.d(TAG, "loadMovieDetails: ERROR " + it.data)
                        mBinding.detailsProgressBar.visibility= GONE
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "loadMovieDetails: Loading ")
                        mBinding.detailsProgressBar.visibility= VISIBLE
                    }
                    is Resource.Success -> {
                        mBinding.detailsProgressBar.visibility= GONE
                        movieDetail= it.data!!
                        bindingDetails(it.data)
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
                        if (it.data?.size !!>0) {
                            mBinding.reviewsRecyclerView.adapter = adapter
                            mBinding.reviewsRecyclerView.visibility= View.VISIBLE
                            adapter.submitList(it.data)
                        }else{
                            Log.d(TAG, "loadMovieReviews: ELSES ")
                            mBinding.reviewsTV.setText(getString(R.string.noReviews))
                        }
                    }
                }
            }
        }
    }

    private fun checkIfFav(movieID: Int){
        lifecycleScope.launchWhenStarted {
            movieViewModel.checkIfFav(movieID)
            movieViewModel.checkIfMovieIsFavStateFlow.collectLatest {
                when(it){
                    is Resource.Error -> {
                        Log.d(TAG, "checkIfFav: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "checkIfFav: Loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "checkIfFav:Success "+ it.data)
                        if (it.data ==1){
                            isFav= true
                            mBinding.favIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
                        }else{
                            isFav= false
                            mBinding.favIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
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
                        activateToken("https://www.themoviedb.org/authenticate/${it.data?.request_token}")
                    }
                }
            }

        }
    }

    fun activateToken(url: String?): InputStream? {
        var myFileURL: URL? = null
        var inputStream: InputStream? = null
        try {
            myFileURL = URL(url)
            inputStream = myFileURL.openStream()
        } catch (e: Exception) {
            println(e.message)
        }
        return inputStream
    }

    private fun bindingDetails(movieDetails: DetailsEntity) {
        Glide.with(requireContext())
            .asBitmap()
            .load("${BuildConfig.IMAGE_BASE}${movieDetails.movieImagePath}")
            .centerCrop()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    mBinding.movieImageView.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    mBinding.movieImageView.setImageDrawable(placeholder)
                }
            })
        mBinding.movieDescription.setText(movieDetails.movieDescription)
        mBinding.popularityVotes.setText(movieDetails.moviePopularity.toString())
        mBinding.movieVotes.setText(movieDetails.movieVotes.toString())
        mBinding.movieReleaseDate.setText(movieDetails.movieReleaseDate)
        mBinding.movieNameTV.setText(movieDetails.movieName)
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }

}