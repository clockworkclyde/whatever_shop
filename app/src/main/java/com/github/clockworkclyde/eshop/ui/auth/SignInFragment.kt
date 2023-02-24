package com.github.clockworkclyde.eshop.ui.auth

import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.presentation.utils.onTextChanged
import com.github.clockworkclyde.eshop.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, AuthViewModel>() {

   override fun inflateView() = FragmentSignInBinding.inflate(layoutInflater)

   override val viewModel: AuthViewModel by viewModels()

   override fun initViews() {
      setUpOnInputTextChanged()
      observeInputFieldsValidated()
   }

   override fun initBinding(binding: FragmentSignInBinding) {
      binding.viewModel = viewModel
      binding.lifecycleOwner = this
   }

   private fun setUpOnInputTextChanged() {
      binding.firstNameTextInput.onTextChanged { viewModel.setFirstName(it) }
      binding.lastNameTextInput.onTextChanged { viewModel.setLastName(it) }
      binding.emailTextInput.onTextChanged { viewModel.setEmail(it) }
   }

   private fun observeInputFieldsValidated() {
      viewModel.firstNameError.collectWhileStarted { binding.firstNameTextInput.error = it }
      viewModel.lastNameError.collectWhileStarted { binding.lastNameTextInput.error = it }
      viewModel.emailError.collectWhileStarted { binding.emailTextInput.error = it }
   }
}