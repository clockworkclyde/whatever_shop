package com.github.clockworkclyde.eshop.ui.auth

import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

): BaseFlowViewModel() {

   val firstNameError = MutableSharedFlow<String>()
   val lastNameError = MutableSharedFlow<String>()
   val emailError = MutableSharedFlow<String>()

   fun onCreateAccountClicked() {
      // save token and navigate to page1
   }

   fun onAppleSignInClicked() {
      // empty
   }

   fun onGoogleSignInClicked() {
      // empty
   }

   fun onLoginClicked() {
      // navigate to Login Screen
   }

   private val _firstName = MutableStateFlow("")
   private val _lastName = MutableStateFlow("")
   private val _email = MutableStateFlow("")

   val firstName get() = _firstName.asStateFlow()
   val lastName get() = _lastName.asStateFlow()
   val email get() = _email.asStateFlow()

   fun setFirstName(text: String) {
      _firstName.value = text
   }

   fun setLastName(text: String) {
      _lastName.value = text
   }

   fun setEmail(text: String) {
      _email.value = text
   }
}