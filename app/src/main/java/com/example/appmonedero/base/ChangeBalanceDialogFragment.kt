package com.example.appmonedero.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.appmonedero.R

class ChangeBalanceDialogFragment(
    private val onDepositClickListener: () -> Unit,
    private val onWithdrawClickListener: () -> Unit
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title: String = getString(R.string.change_balance_dialog_title)
        val message: String = getString(R.string.change_balance_dialog_message)
        val deposit: String = getString(R.string.change_balance_dialog_deposit)
        val withdraw: String = getString(R.string.change_balance_dialog_withdraw)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(deposit){_,_ -> onDepositClickListener.invoke()}
        builder.setNegativeButton(withdraw){_,_ -> onWithdrawClickListener.invoke()}
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