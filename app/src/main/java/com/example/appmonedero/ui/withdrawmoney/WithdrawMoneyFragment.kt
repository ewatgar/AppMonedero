package com.example.appmonedero.ui.withdrawmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appmonedero.R
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.data.repository.FraudControlRepository
import com.example.appmonedero.databinding.FragmentWithdrawMoneyBinding
import com.example.appmonedero.ui.ErrorTextWatcher
import com.example.appmonedero.ui.withdrawmoney.usecase.WithdrawMoneyState
import com.example.appmonedero.ui.withdrawmoney.usecase.WithdrawMoneyViewModel

class WithdrawMoneyFragment : Fragment() {

    private var _binding: FragmentWithdrawMoneyBinding? = null
    private val binding get() = _binding!!
    private val args: WithdrawMoneyFragmentArgs by navArgs()
    private val viewmodel: WithdrawMoneyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWithdrawMoneyBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerArgs: Customer = args.customer
        val accountArgs: Account = args.account
        viewmodel.customer.value = customerArgs
        viewmodel.account.value = accountArgs

        initTextWatcher()
        binding.bDeposit.setOnClickListener { viewmodel.verifyWithdraw() }

        viewmodel.getState().observe(viewLifecycleOwner) {
            when (it) {
                WithdrawMoneyState.MoneyEmptyError -> setMoneyEmptyError()
                WithdrawMoneyState.MoneyFormatError -> setMoneyFormatError()
                WithdrawMoneyState.NotEnoughBalanceError -> setNotEnoughBalanceError()
                WithdrawMoneyState.MoneyOverLimit -> setMoneyOverLimit()
                WithdrawMoneyState.MoneyDoubleOverLimit -> setMoneyDoubleOverLimit()
                WithdrawMoneyState.Success -> onSuccess()
            }
        }
    }

    private fun onSuccess() {
        viewmodel.withdrawMoney()
        Toast.makeText(
            requireContext(),
            getString(R.string.deposit_toast_success, viewmodel.money.value!!.toInt()),
            Toast.LENGTH_SHORT
        ).show()
        findNavController().popBackStack()
    }

    private fun setMoneyEmptyError() {
        with(binding) {
            tilMoney.error = getString(R.string.error_til_money_empty)
            tilMoney.requestFocus()
        }
    }

    private fun setMoneyFormatError() {
        with(binding) {
            tilMoney.error = getString(R.string.error_til_money_format)
            tilMoney.requestFocus()
        }
    }

    private fun setNotEnoughBalanceError() {
        with(binding) {
            tilMoney.error = getString(R.string.error_withdraw_til_money_not_enough_balance)
            tilMoney.requestFocus()
        }
    }

    private fun setMoneyOverLimit() {
        viewmodel.withdrawMoney()
        viewmodel.controlTransaction(false)
        Toast.makeText(
            requireContext(), getString(
                R.string.error_withdraw_til_money_over_limit, FraudControlRepository.FRAUD_LIMIT
            ), Toast.LENGTH_SHORT
        ).show()
        findNavController().popBackStack()
    }

    private fun setMoneyDoubleOverLimit() {
        viewmodel.controlTransaction(true)
        Toast.makeText(
            requireContext(), getString(
                R.string.error_withdraw_til_money_double_over_limit,
                FraudControlRepository.FRAUD_LIMIT
            ), Toast.LENGTH_SHORT
        ).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextWatcher() {
        with(binding) {
            val twMoney = ErrorTextWatcher(tilMoney) //textWat
            edMoney.addTextChangedListener(twMoney)
        }
    }
}