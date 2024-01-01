package com.example.appmonedero.ui.addaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appmonedero.R
import com.example.appmonedero.data.model.Account
import com.example.appmonedero.data.model.CountryCode
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.databinding.FragmentAddAccountBinding
import com.example.appmonedero.ui.ErrorTextWatcher
import com.example.appmonedero.ui.accountslist.AccountsListFragmentArgs
import com.example.appmonedero.ui.addaccount.usecase.AddAccountState
import com.example.appmonedero.ui.addaccount.usecase.AddAccountViewModel

class AddAccountFragment : Fragment() {

    private var _binding: FragmentAddAccountBinding? = null
    private val binding get() = _binding!!
    private val args: AddAccountFragmentArgs by navArgs()
    private val viewmodel: AddAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAccountBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customerArgs: Customer = args.customer
        viewmodel.customer.value = customerArgs

        initSpinnerCountryCode()
        initTextWatcher()
        binding.bAddAccount.setOnClickListener { viewmodel.verifyNewAccount() }

        viewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                AddAccountState.EntidadEmptyError -> setEntidadEmptyError()
                AddAccountState.OficinaEmptyError -> setOficinaEmptyError()
                AddAccountState.DcEmptyError -> setDcEmptyError()
                AddAccountState.AccountNumEmptyError ->setAccountNumEmptyError()
                AddAccountState.EntidadFormatError -> setEntidadFormatError()
                AddAccountState.OficinaFormatError -> setOficinaFormatError()
                AddAccountState.DcFormatError -> setDcFormatError()
                AddAccountState.AccountNumFormatError -> setAccountNumFormatError()
                AddAccountState.AccountExistsError -> setAccountExistsError()
                AddAccountState.Success -> onSuccess()
            }
        }
    }

    private fun onSuccess() {
        viewmodel.createNewAccount()
        Toast.makeText(requireContext(), "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun setAccountExistsError() {
        Toast.makeText(requireContext(), "La cuenta ya existe", Toast.LENGTH_SHORT).show()
    }
    private fun setEntidadEmptyError() {
        with(binding){
            tilEntidad.error = getString(R.string.error_til_entidad_empty)
            tilEntidad.requestFocus()
        }
    }

    private fun setOficinaEmptyError() {
        with(binding){
            tilOficina.error = getString(R.string.error_til_oficina_empty)
            tilOficina.requestFocus()
        }
    }

    private fun setDcEmptyError() {
        with(binding){
            tilDc.error = getString(R.string.error_til_dc_empty)
            tilDc.requestFocus()
        }
    }

    private fun setAccountNumEmptyError() {
        with(binding){
            tilAccountNum.error = getString(R.string.error_til_account_num_empty)
            tilAccountNum.requestFocus()
        }
    }

    private fun setEntidadFormatError() {
        with(binding){
            tilEntidad.error = getString(R.string.error_til_entidad_format)
            tilEntidad.requestFocus()
        }
    }

    private fun setOficinaFormatError() {
        with(binding){
            tilOficina.error = getString(R.string.error_til_oficina_format)
            tilOficina.requestFocus()
        }
    }

    private fun setDcFormatError() {
        with(binding){
            tilDc.error = getString(R.string.error_til_dc_format)
            tilDc.requestFocus()
        }
    }

    private fun setAccountNumFormatError() {
        with(binding){
            tilAccountNum.error = getString(R.string.error_til_account_num_format)
            tilAccountNum.requestFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSpinnerCountryCode() {
        val spinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,CountryCode.entries)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCountryCode.adapter = spinnerAdapter
        viewmodel.countryCode.value = binding.spCountryCode.selectedItem as CountryCode
    }

    private fun initTextWatcher() {
        with(binding) {
            val twEntidad = ErrorTextWatcher(tilEntidad) //textWatcher
            val twOficina = ErrorTextWatcher(tilOficina)
            val twDc = ErrorTextWatcher(tilDc)
            val twAccountNum = ErrorTextWatcher(tilAccountNum)

            edEntidad.addTextChangedListener(twEntidad)
            edOficina.addTextChangedListener(twOficina)
            edDc.addTextChangedListener(twDc)
            edAccountNum.addTextChangedListener(twAccountNum)
        }
    }
}