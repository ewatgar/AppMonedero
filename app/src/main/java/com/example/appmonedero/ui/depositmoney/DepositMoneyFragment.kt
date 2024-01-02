/**
 * DepositMoneyFragment
 * Tiene la función de depositar dinero en la cuenta elegida.
 * Hay que tener en cuenta que no se puede añadir más del doble del límite de fraude.
 */

package com.example.appmonedero.ui.depositmoney

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
import com.example.appmonedero.databinding.FragmentDepositMoneyBinding
import com.example.appmonedero.ui.ErrorTextWatcher
import com.example.appmonedero.ui.depositmoney.usecase.DepositMoneyState
import com.example.appmonedero.ui.depositmoney.usecase.DepositMoneyViewModel

class DepositMoneyFragment : Fragment() {

    private var _binding: FragmentDepositMoneyBinding? = null
    private val binding get() = _binding!!
    private val args: DepositMoneyFragmentArgs by navArgs()
    private val viewmodel: DepositMoneyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDepositMoneyBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerArgs : Customer = args.customer
        val accountArgs : Account = args.account
        viewmodel.customer.value = customerArgs
        viewmodel.account.value = accountArgs

        initTextWatcher()
        binding.bDeposit.setOnClickListener { viewmodel.verifyDeposit() }

        viewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                DepositMoneyState.MoneyEmptyError -> setMoneyEmptyError()
                DepositMoneyState.MoneyFormatError -> setMoneyFormatError()
                DepositMoneyState.MoneyOverLimit -> setMoneyOverLimit()
                DepositMoneyState.MoneyDoubleOverLimit -> setMoneyDoubleOverLimit()
                DepositMoneyState.Success -> onSuccess()
            }
        }
    }

    private fun onSuccess() {
        viewmodel.depositMoney()
        Toast.makeText(requireContext(), getString(R.string.deposit_toast_success,viewmodel.money.value!!.toInt()), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun setMoneyEmptyError() {
        with(binding){
            tilMoney.error = getString(R.string.error_til_money_empty)
            tilMoney.requestFocus()
        }
    }

    private fun setMoneyFormatError() {
        with(binding){
            tilMoney.error = getString(R.string.error_til_money_format)
            tilMoney.requestFocus()
        }
    }

    private fun setMoneyOverLimit() {
        viewmodel.depositMoney()
        viewmodel.controlTransaction(false)
        Toast.makeText(requireContext(), getString(R.string.error_deposit_til_money_over_limit,FraudControlRepository.FRAUD_LIMIT), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun setMoneyDoubleOverLimit() {
        viewmodel.controlTransaction(true)
        Toast.makeText(requireContext(), getString(R.string.error_deposit_til_money_double_over_limit,FraudControlRepository.FRAUD_LIMIT), Toast.LENGTH_SHORT).show()
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