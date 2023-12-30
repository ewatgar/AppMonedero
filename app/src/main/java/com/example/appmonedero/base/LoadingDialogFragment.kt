package com.example.appmonedero.base

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
        // Utilizar la instancia de LayoutInfater para inflar el diseño XML que contiene un
        // componente ProgressBar
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.fragment_loading_dialog, null)

        // Crea un cuadro de diálogo con el diseño inflado
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setTitle("Cargando...")
        builder.setCancelable(false)
        //
        // Devuelve el cuadro de diálogo creado
        return builder.create()
    }
}