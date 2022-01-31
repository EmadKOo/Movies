package com.emad.movies.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.emad.movies.BuildConfig
import com.emad.movies.R
import com.emad.movies.data.model.RequestRate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favourite_dialog.view.*

@AndroidEntryPoint
class FavouriteDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater= activity?.layoutInflater
        val dialog= layoutInflater!!.inflate(R.layout.favourite_dialog, null)

        dialog.cancel.setOnClickListener {
            dismiss()
        }
        dialog.delete.setOnClickListener {

        }
        val alertDialog= AlertDialog.Builder(activity)
        alertDialog.setView(dialog)
        return alertDialog.create()
    }
}