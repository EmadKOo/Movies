package com.emad.movies.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.emad.movies.BuildConfig
import com.emad.movies.R
import com.emad.movies.data.model.RequestRate
import com.emad.movies.presentation.viewmodel.MovieViewModel
import com.emad.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.rate_dialog.view.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RateDialog: DialogFragment() {
    val movieViewModel: MovieViewModel by viewModels()
    val args: RateDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater= activity?.layoutInflater
        val dialog= layoutInflater!!.inflate(R.layout.rate_dialog, null)

        dialog.cancel.setOnClickListener {
            dismiss()
        }
        dialog.submit.setOnClickListener {
            if (dialog.movieRatebar.rating>0)
                requestMovieRate(args.movieID, BuildConfig.SESSION_ID, RequestRate(dialog.movieRatebar.rating))
            else
                Toast.makeText(requireContext(), getString(R.string.pleaseSelectValue), Toast.LENGTH_LONG).show()
        }
        val alertDialog= AlertDialog.Builder(activity)
        alertDialog.setView(dialog)
        return alertDialog.create()
    }

    private fun requestMovieRate(movie_id: Int, session_id: String, requestRate: RequestRate){
        lifecycleScope.launchWhenStarted {
            movieViewModel.addingMovieRate(movie_id, session_id, requestRate)
            movieViewModel.requestRateStateFlow.collectLatest {
                when(it){
                    is Resource.Error -> {
                        Log.d(TAG, "requestMovieRate: ERROR " + it.data)
                        dismiss()
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "requestMovieRate: Loading")
                    }
                    is Resource.Success -> {
                        dismiss()
                        Log.d(TAG, "requestMovieRate: Success " + it.data)
                    }
                }
            }
        }
    }


    companion object{
        private const val TAG = "RateDialog"
    }
}