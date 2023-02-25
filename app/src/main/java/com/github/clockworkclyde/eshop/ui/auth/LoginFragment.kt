package com.github.clockworkclyde.eshop.ui.auth

import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.presentation.utils.onTextChanged
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.eshop.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding, AuthViewModel>() {

   override val viewModel: AuthViewModel by viewModels()

   override fun inflateView() = FragmentLoginBinding.inflate(layoutInflater)

   override fun initBinding(binding: FragmentLoginBinding) {
      binding.viewModel = viewModel
      binding.lifecycleOwner = this
   }

   override fun initViews() {
      setUpOnInputTextChanged()
      observeInputFieldsValidated()
   }

   private fun setUpOnInputTextChanged() {
      binding.firstNameTextInput.onTextChanged { viewModel.setFirstName(it) }
      binding.passwordTextInput.onTextChanged { viewModel.setPassword(it) }
   }

   private fun observeInputFieldsValidated() {
      viewModel.firstNameError.collectWhileStarted { binding.firstNameTextInput.error = it }
      viewModel.passwordError.collectWhileStarted { binding.passwordTextInput.error = it }
   }

}