package com.example.appmonedero.ui.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer

class ChangeBalanceDialogFragment(
    private val onDepositClickListener: (Customer, Account) -> Unit,
    private val onWithdrawClickListener: (Customer, Account) -> Unit
): DialogFragment() {

    private val title: String = "Title"
    private val message: String = "Message"
    private val positiveButton: String = "positive"
    private val negativeButton: String = "negative"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customer = requireArguments().getSerializable(customer) as Customer
        val account = requireArguments().getSerializable(account) as Account
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton){_,_ -> onDepositClickListener.invoke(customer, account)}
        builder.setNegativeButton(negativeButton){_,_ -> onWithdrawClickListener.invoke(customer, account)}
        return builder.create()
    }

    companion object {
        const val customer = "customer"
        const val account = "account"

        fun newInstance(
            customer: Customer,
            account: Account,
            onDepositClickListener: (Customer, Account) -> Unit,
            onWithdrawClickListener: (Customer, Account) -> Unit
        ): ChangeBalanceDialogFragment {
            val fragment = ChangeBalanceDialogFragment(onDepositClickListener,onWithdrawClickListener)
            val args = Bundle()
            args.putSerializable(Companion.customer, customer)
            args.putSerializable(Companion.account, account)
            fragment.arguments = args
            return fragment
        }
    }
}