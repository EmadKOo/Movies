package com.emad.movies.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.emad.movies.BuildConfig
import com.emad.movies.R
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.model.RequestRate
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favourite_dialog.view.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavouriteDialog: DialogFragment() {
    val viewModel: MovieViewModel by viewModels()
    val args: FavouriteDialogArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater= activity?.layoutInflater
        val dialog= layoutInflater!!.inflate(R.layout.favourite_dialog, null)

        dialog.cancel.setOnClickListener {
            dismiss()
        }
        dialog.add.setOnClickListener {
            addFavourite(args.movieID, args.movieName, args.movieImage)
        }
        val alertDialog= AlertDialog.Builder(activity)
        alertDialog.setView(dialog)
        return alertDialog.create()
    }

    private fun addFavourite(movieID: Int, movieName: String, movieImage: String){
        lifecycleScope.launchWhenStarted {
            val favouriteEntity= FavouriteEntity(movieID, movieName, movieImage)
            viewModel.addFavouriteMovie(favouriteEntity)
            viewModel.addFavouriteStateFlow.collectLatest {
                when(it){
                    is Resource.Error -> {
                        Log.d(TAG, "addFavourite: ERROR " + it.data)
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "addFavourite: Loading ")
                    }
                    is Resource.Success -> {
                        dismiss()
                        Log.d(TAG, "addFavourite: SUCCESS " + it.data)
                    }
                }
            }
        }
    }
    companion object{
        private const val TAG = "FavouriteDialog"
    }
}