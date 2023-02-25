package com.github.clockworkclyde.eshop.ui.auth

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.applyIfError
import com.github.clockworkclyde.core.utils.onTextChanged
import com.github.clockworkclyde.core.utils.toast
import com.github.clockworkclyde.eshop.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>() {

   override val viewModel: AuthViewModel by viewModels()

   override fun inflateView() = FragmentLoginBinding.inflate(layoutInflater)

   private var passwordShouldBeVisible = DEFAULT_PASSWORD_VISIBILITY

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      savedInstanceState?.getBoolean(KEY_PASSWORD_VISIBILITY)?.let { passwordShouldBeVisible = it }
   }

   override fun onSaveInstanceState(outState: Bundle) {
      super.onSaveInstanceState(outState)
      outState.putBoolean(KEY_PASSWORD_VISIBILITY, passwordShouldBeVisible)
   }

   override fun initBinding(binding: FragmentLoginBinding) {
      binding.viewModel = viewModel
      binding.lifecycleOwner = this
   }

   override fun initViews() {
      setUpOnInputTextChanged()
      setUpTogglePasswordClick()
      observeInputFieldsValidated()
   }

   override fun handleResultError() {
      viewModel.resultFlow.collectWhileStarted {
         it.applyIfError { error -> toast(error) }
      }
   }

   private fun setUpTogglePasswordClick() {
      with(binding) {
         updatePasswordVisibility(passwordShouldBeVisible)
         imgBtnChangePasswordVisibility.setOnClickListener { view ->
            view?.let {
               updatePasswordVisibility(passwordShouldBeVisible)
               passwordShouldBeVisible = !passwordShouldBeVisible
            }
         }
      }
   }

   private fun updatePasswordVisibility(visible: Boolean) {
      binding.passwordTextInput.apply {
         if (transformationMethod == null == visible) return
         transformationMethod =
            if (!visible) PasswordTransformationMethod()
            else null
      }
   }

   private fun setUpOnInputTextChanged() {
      binding.firstNameTextInput.onTextChanged { viewModel.setFirstName(it) }
      binding.passwordTextInput.onTextChanged { viewModel.setPassword(it) }
   }

   private fun observeInputFieldsValidated() {
      viewModel.firstNameError.collectWhileStarted { binding.firstNameTextInput.error = it }
      viewModel.passwordError.collectWhileStarted { binding.passwordTextInput.error = it }
   }

   companion object {
      private const val KEY_PASSWORD_VISIBILITY = "password_visibility"
      private const val DEFAULT_PASSWORD_VISIBILITY = false
   }

}