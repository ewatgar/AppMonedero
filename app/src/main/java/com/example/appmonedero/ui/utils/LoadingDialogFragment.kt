package com.example.appmonedero.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appmonedero.R

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.fragment_loading_dialog, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setTitle(getString(R.string.loading_dialog_title))
        builder.setCancelable(false)
        return builder.create()
    }
}