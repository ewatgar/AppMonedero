package com.example.appmonedero.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.databinding.RecyclerAccountsListBinding

class AccountsListAdapter(
    private val onChangeBalanceDialogClickListener: (account: Account) -> Unit
) : RecyclerView.Adapter<AccountsListAdapter.AccountsListViewHolder>() {

    private var dataset: ArrayList<Account> = arrayListOf()
    fun update(newDataset: ArrayList<Account>) {
        dataset = newDataset
        notifyDataSetChanged()
    }

    fun sortByBalance(){
        dataset.sortBy { it.balance }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountsListAdapter.AccountsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerAccountsListBinding.inflate(layoutInflater, parent, false)
        return AccountsListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AccountsListAdapter.AccountsListViewHolder, position: Int
    ) {
        val account = dataset[position]
        holder.bind(account)
        holder.itemView.setOnClickListener {onChangeBalanceDialogClickListener(account)}
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class AccountsListViewHolder(private val binding: RecyclerAccountsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {
            with(binding) {
                tvPaisDc.text = account.countryCode.name + account.dc
                tvEntidad.text = account.entidad.toString()
                tvOficina.text = account.oficina.toString()
                tvDc.text = account.dc.toString()
                tvAccountNum.text = account.accountNum.toString()
                tvBalanceNumber.text = account.balance.toString()
            }
        }

    }
}
