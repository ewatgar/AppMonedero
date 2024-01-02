/**
 * AccountsListFragment
 * Tiene la función de enseñar la lista de cuentas del cliente que ha iniciado sesión.
 * Al presionar una de las cuentas se puede depositar o retirar dinero de ella.
 * También se puede crear una nueva al pulsar el botón de la esquina inferior derecha.
 */

package com.example.appmonedero.ui.accountslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmonedero.R
import com.example.appmonedero.adapter.AccountsListAdapter
import com.example.appmonedero.base.ChangeBalanceDialogFragment
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.databinding.FragmentAccountsListBinding
import com.example.appmonedero.ui.accountslist.usecase.AccountsListState
import com.example.appmonedero.ui.accountslist.usecase.AccountsListViewModel

class AccountsListFragment : Fragment(), MenuProvider {

    private val TAG = "AccountsList"

    private var _binding: FragmentAccountsListBinding? = null
    private val binding get() = _binding!!

    private val args: AccountsListFragmentArgs by navArgs()
    private val viewmodel: AccountsListViewModel by viewModels()

    private lateinit var accountsListAdapter: AccountsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountsListBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerArgs: Customer = args.customer
        viewmodel.customer.value = customerArgs

        binding.bAddAccount.setOnClickListener { accountCreation(customerArgs) }
        initRecycler(customerArgs)
        initToolbar()

        viewmodel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is AccountsListState.Loading -> showProgressBar(it.active)
                AccountsListState.NoDataError -> showNoDataError()
                is AccountsListState.Success -> onSuccess(it.accountsList)
            }
        }
    }

    private fun accountCreation(customer: Customer) {
        val action =
            AccountsListFragmentDirections.actionAccountsListFragmentToAddAccountFragment(customer)
        findNavController().navigate(action)
    }

    private fun onSuccess(dataset: ArrayList<Account>) {
        hideNoDataError()
        accountsListAdapter.update(dataset)
    }

    private fun showProgressBar(active: Boolean) {
        if (active)
            findNavController().navigate(R.id.action_accountsListFragment_to_loadingDialogFragment)
        else
            findNavController().popBackStack()
    }

    private fun hideNoDataError() {
        with(binding) {
            imgEmptyList.visibility = View.GONE
            tvEmptyTitle.visibility = View.GONE
            tvEmptyText.visibility = View.GONE
            rvAccountsList.visibility = View.VISIBLE
        }
    }

    private fun showNoDataError() {
        with(binding) {
            imgEmptyList.visibility = View.VISIBLE
            tvEmptyTitle.visibility = View.VISIBLE
            tvEmptyText.visibility = View.VISIBLE
            rvAccountsList.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        viewmodel.getAccountsList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler(customer: Customer) {
        accountsListAdapter = AccountsListAdapter { openChangeBalanceDialog(customer, it) }
        val accountsListLayoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(
            binding.rvAccountsList.context,
            accountsListLayoutManager.orientation
        )
        with(binding.rvAccountsList) {
            layoutManager = accountsListLayoutManager
            adapter = accountsListAdapter
            setHasFixedSize(true)
            addItemDecoration(divider)
        }
    }

    private fun openChangeBalanceDialog(customer: Customer, account: Account) {
        ChangeBalanceDialogFragment.newInstance(
            { depositMoney(customer, account) },
            { withdrawMoney(customer, account) }
        ).show(parentFragmentManager, TAG)
    }

    private fun depositMoney(customer: Customer, account: Account) {
        val action =
            AccountsListFragmentDirections.actionAccountsListFragmentToDepositMoneyFragment(customer,account)
        findNavController().navigate(action)
    }

    private fun withdrawMoney(customer: Customer, account: Account) {
        val action =
            AccountsListFragmentDirections.actionAccountsListFragmentToWithdrawMoneyFragment(customer,account)
        findNavController().navigate(action)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title =
            "Cuentas de ${viewmodel.customer.value!!.username}"
        binding.toolbar.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_accounts_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_sort -> {
                accountsListAdapter.sortByBalance() //sort orden PERSONALIZADO
                return true
            }

            R.id.menu_refresh -> {
                viewmodel.getAccountsList()
                return true
            }

            else -> false
        }
    }

}