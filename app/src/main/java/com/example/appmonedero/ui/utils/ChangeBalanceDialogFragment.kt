package com.example.appmonedero.ui.utils

import android.R
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ChangeBalanceDialogFragment(
    private val onDepositClickListener: () -> Unit,
    private val onWithdrawClickListener: () -> Unit
): DialogFragment() {

    private val title: String = "Title"
    private val message: String = "Message"
    private val positiveButton: String = "positive"
    private val negativeButton: String = "negative"


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton){onDepositClickListener}
        builder.setNegativeButton(negativeButton){onWithdrawClickListener}

        return builder.create()
    }

    companion object {
        fun newInstance(title: String, message: String): BaseFragmentDialog {
            val fragment = BaseFragmentDialog()
            val args = Bundle()
            args.putString(Companion.title, title)
            args.putString(Companion.message, message)
            fragment.arguments = args
            return fragment
        }
    }




}