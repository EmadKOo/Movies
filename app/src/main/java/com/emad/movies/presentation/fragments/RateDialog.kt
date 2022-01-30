package com.emad.movies.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.emad.movies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.rate_dialog.view.*

@AndroidEntryPoint
class RateDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater= activity?.layoutInflater
        val dialog= layoutInflater!!.inflate(R.layout.rate_dialog, null)

        dialog.cancel.setOnClickListener {
            dismiss()
        }
        dialog.submit.setOnClickListener {
            Log.d(TAG, "onCreateDialog: "+ dialog.movieRatebar.rating)
        }
        val alertDialog= AlertDialog.Builder(activity)
        alertDialog.setView(dialog)
        return alertDialog.create()
    }
    companion object{
        private const val TAG = "RateDialog"
    }
}