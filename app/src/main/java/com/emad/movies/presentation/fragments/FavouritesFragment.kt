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
import com.emad.movies.R
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.databinding.FragmentFavouritesBinding
import com.emad.movies.domain.listeners.OnFavouriteSelected
import com.emad.movies.presentation.adapters.FavouritesAdapter
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : Fragment(), OnFavouriteSelected {
    val viewModel: MovieViewModel by viewModels()
    lateinit var mBinding: FragmentFavouritesBinding
    @Inject
    lateinit var favouritesAdapter: FavouritesAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouritesAdapter.submitFavListener(this)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding=  FragmentFavouritesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllFavourites()
        handleViews()
    }

    private fun handleViews(){
        mBinding.favToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getAllFavourites(){
        lifecycleScope.launchWhenStarted {
            viewModel.getAllFavourites()
            viewModel.getAllFavouritesStateFlow.collectLatest {
                when(it){
                    is Resource.Error -> {
                        Log.d(TAG, "getAllFavourites: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "getAllFavourites: Loading")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "getAllFavourites: SUCCESS " + it.data)
                        Log.d(TAG, "getAllFavourites: SUCCESS Length " + it.data!!.size)
                        mBinding.moviesRecyclerView.adapter= favouritesAdapter
                        favouritesAdapter.submitList(it.data)
                    }
                }
            }
        }
    }
    
    override fun onFavSelected(favouriteEntity: FavouriteEntity) {
        findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(favouriteEntity.movieID))
    }
    
    companion object{
        private const val TAG = "FavouritesFragment"
    }
}