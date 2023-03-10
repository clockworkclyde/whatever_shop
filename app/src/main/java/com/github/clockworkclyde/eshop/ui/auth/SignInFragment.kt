package com.github.clockworkclyde.eshop.ui.auth

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.applyIfError
import com.github.clockworkclyde.core.utils.onTextChanged
import com.github.clockworkclyde.eshop.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, AuthViewModel>() {

   override fun inflateView() = FragmentSignInBinding.inflate(layoutInflater)

   override val viewModel: AuthViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      lifecycle.addObserver(viewModel)
   }

   override fun initViews() {
      setUpOnInputTextChanged()
      observeInputFieldsValidated()
   }

   override fun handleResultError() {
      viewModel.resultFlow.collectWhileStarted {
         it.applyIfError { error -> toast(error) }
      }
   }

   override fun initBinding(binding: FragmentSignInBinding) {
      binding.viewModel = viewModel
      binding.lifecycleOwner = viewLifecycleOwner
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