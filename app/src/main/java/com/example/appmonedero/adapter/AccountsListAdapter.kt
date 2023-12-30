package com.example.appmonedero.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.databinding.RecyclerAccountsListBinding

class AccountsListAdapter : RecyclerView.Adapter<AccountsListAdapter.AccountsListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AccountsListAdapter.AccountsListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: AccountsListAdapter.AccountsListViewHolder, position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class AccountsListViewHolder(private val binding: RecyclerAccountsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account, context: Context) {
            with(binding) {
                tvPlaceholder

            }
        }

    }
}
