package com.github.clockworkclyde.eshop.ui.auth

import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.presentation.utils.orFalse
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.domain.validate.ValidateEmailUseCase
import com.github.clockworkclyde.domain.validate.ValidateFieldIsNotEmptyUseCase
import com.github.clockworkclyde.eshop.di.IoDispatcher
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.eshop.navigation.directions.auth.SignInDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val validateEmail: ValidateEmailUseCase,
   private val validateIsNotEmpty: ValidateFieldIsNotEmptyUseCase,
   private val navigator: INavigator,
   @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseFlowViewModel() {

   override val destinations = SignInDirections()

   val firstNameError = MutableSharedFlow<String>()
   val lastNameError = MutableSharedFlow<String>()
   val emailError = MutableSharedFlow<String>()

   override val supportFlow: Flow<Boolean> by lazy {
      flow {
         val firstNameHasError =
            validateIsNotEmpty(firstName.value)?.also { firstNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val lastNameHasError =
            validateIsNotEmpty(lastName.value)?.also { lastNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val emailHasError =
            validateEmail(email.value)?.also { emailError.emit(it) }?.isNotEmpty().orFalse()
         emit(firstNameHasError && lastNameHasError && emailHasError)
      }
   }

   fun onCreateAccountClicked() {
      viewModelScope.launch(ioDispatcher) {
         supportFlow.collect { ok ->
            if (ok) processNavEvent(destinations.showLogin(), navigator)
         }
      }
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