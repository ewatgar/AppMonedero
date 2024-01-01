package com.example.appmonedero.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appmonedero.R
import com.example.appmonedero.data.model.Customer
import com.example.appmonedero.databinding.FragmentLoginBinding
import com.example.appmonedero.ui.ErrorTextWatcher
import com.example.appmonedero.ui.login.usecase.LoginState
import com.example.appmonedero.ui.login.usecase.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextWatcher()

        binding.bLogin.setOnClickListener { viewmodel.validateCredentials() }
        viewmodel.getState().observe(viewLifecycleOwner) {
            when (it) {
                LoginState.UsernameEmptyError -> setUsernameEmptyError()
                LoginState.PinEmptyError -> setPinEmptyError()
                LoginState.PinFormatError -> setPinFormatError()
                LoginState.AuthenticationError -> setAuthenticationError()
                is LoginState.Loading -> showProgressBar(it.active)
                is LoginState.Success -> onSuccess(it.customer)
            }
        }
    }

    private fun onSuccess(customer: Customer) {
        val action = LoginFragmentDirections.actionLoginFragmentToAccountsListFragment(customer)
        findNavController().navigate(action)
    }

    private fun showProgressBar(active: Boolean) {
        if (active)
            findNavController().navigate(R.id.action_loginFragment_to_loadingDialogFragment)
        else
            findNavController().popBackStack()
    }

    private fun setUsernameEmptyError() {
        with(binding) {
            tilUsername.error = getString(R.string.error_til_username_empty)
            tilUsername.requestFocus()
        }
    }

    private fun setPinEmptyError() {
        with(binding) {
            tilPin.error = getString(R.string.error_til_pin_empty)
            tilPin.requestFocus()
        }
    }

    private fun setPinFormatError() {
        with(binding) {
            tilPin.error = getString(R.string.error_til_pin_format)
            tilPin.requestFocus()
        }
    }

    private fun setAuthenticationError() {
        //Snackbar.make(binding.root, getString(R.string.error_toast_auth), Snackbar.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), getString(R.string.error_toast_auth), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextWatcher() {
        with(binding) {
            val twUsername = ErrorTextWatcher(tilUsername) //textWatcher
            val twPin = ErrorTextWatcher(tilPin)
            edUsername.addTextChangedListener(twUsername)
            edPin.addTextChangedListener(twPin)
        }
    }
}