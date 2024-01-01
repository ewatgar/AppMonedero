package com.example.appmonedero.ui.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer

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
        builder.setPositiveButton(positiveButton){_,_ -> onDepositClickListener.invoke()}
        builder.setNegativeButton(negativeButton){_,_ -> onWithdrawClickListener.invoke()}
        return builder.create()
    }

    companion object {
        fun newInstance(
            onDepositClickListener: () -> Unit,
            onWithdrawClickListener: () -> Unit
        ): ChangeBalanceDialogFragment {
            return ChangeBalanceDialogFragment(onDepositClickListener,onWithdrawClickListener)
        }
    }
}