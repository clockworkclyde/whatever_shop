package com.github.clockworkclyde.eshop.ui.auth

import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.FlowAnyResult
import com.github.clockworkclyde.core.utils.orFalse
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.domain.usecases.validate.ValidateEmailUseCase
import com.github.clockworkclyde.domain.usecases.validate.ValidateFieldIsNotEmptyUseCase
import com.github.clockworkclyde.eshop.di.IoDispatcher
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.presentation.viewmodels.INavigationViewModel
import com.github.clockworkclyde.core.utils.applyIfError
import com.github.clockworkclyde.core.utils.applyIfSuccess
import com.github.clockworkclyde.core.utils.toEmptySuccess
import com.github.clockworkclyde.domain.usecases.auth.LoginAttempt
import com.github.clockworkclyde.domain.usecases.auth.LoginAttemptUseCase
import com.github.clockworkclyde.domain.usecases.auth.SignInAttempt
import com.github.clockworkclyde.domain.usecases.auth.SignInAttemptUseCase
import com.github.clockworkclyde.eshop.navigation.directions.auth.AuthDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val validateEmail: ValidateEmailUseCase,
   private val validateIsNotEmpty: ValidateFieldIsNotEmptyUseCase,
   private val signInAttempt: SignInAttemptUseCase,
   private val loginAttempt: LoginAttemptUseCase,
   private val navigator: INavigator,
   @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseFlowViewModel(), INavigationViewModel<AuthDirections> {

   override val directions = AuthDirections()

   val firstNameError = MutableSharedFlow<String>()
   val lastNameError = MutableSharedFlow<String>()
   val emailError = MutableSharedFlow<String>()
   val passwordError = MutableSharedFlow<String>()

   override val resultFlow: MutableSharedFlow<AnyResult> by lazy {
      MutableSharedFlow(
         replay = sharedFlowOptions.replay,
         extraBufferCapacity = sharedFlowOptions.bufferCapacity,
         onBufferOverflow = sharedFlowOptions.bufferOverflow
      )
   }

   /* Sign in validation */
   override val supportFlow: Flow<SignInAttempt> by lazy {
      val firstName = firstName.value
      val lastName = lastName.value
      val email = email.value
      flow {
         val firstNameHasError =
            validateIsNotEmpty(firstName)?.also { firstNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val lastNameHasError =
            validateIsNotEmpty(lastName)?.also { lastNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val emailHasError =
            validateEmail(email)?.also { emailError.emit(it) }?.isNotEmpty().orFalse()

         if (!(firstNameHasError && lastNameHasError && emailHasError)) {
            emit(SignInAttempt(firstName = firstName, lastName = lastName, email = email))
         }
      }
   }

   @OptIn(FlowPreview::class)
   private val signInFlow: FlowAnyResult by lazy {
      supportFlow
         //.debounce(200L)
         .flatMapConcat {
         flow {
            signInAttempt.invoke(it)
               .applyIfError { resultFlow.emit(it) }
               .applyIfSuccess { emit(toEmptySuccess()) }
         }
      }
   }

   fun onCreateAccountClicked() {
      viewModelScope.launch(ioDispatcher) {
         signInFlow.collect {
            if (it.isSuccess()) processNavEvent(directions.signInToShopCategoriesCleared(), navigator)
         }
      }
   }

   /* Login validation */
   private val secondSupportFlow: Flow<LoginAttempt> by lazy {
      val firstName = firstName.value
      val pass = password.value
      flow {
         val firstNameHasError =
            validateIsNotEmpty(firstName)?.also { firstNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val passwordHasError =
            validateIsNotEmpty(pass)?.also { passwordError.emit(it) }?.isNotEmpty()
               .orFalse()

         if (!(firstNameHasError && passwordHasError)) {
            emit(LoginAttempt(firstName, pass))
         }
      }
   }

   private val loginFlow: FlowAnyResult by lazy {
      secondSupportFlow.flatMapConcat {
         flow {
            loginAttempt(it)
               .applyIfError { resultFlow.emit(it) }
               .applyIfSuccess { emit(toEmptySuccess()) }
         }
      }
   }

   fun onLoginAttemptClicked() {
      viewModelScope.launch(ioDispatcher) {
         loginFlow.collect {
            if (it.isSuccess()) processNavEvent(directions.loginToShopCategoriesCleared(), navigator)
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
      processNavEvent(directions.showLogin(), navigator)
   }

   private val _firstName = MutableStateFlow("")
   private val _lastName = MutableStateFlow("")
   private val _email = MutableStateFlow("")
   private val _password = MutableStateFlow("")

   val firstName get() = _firstName.asStateFlow()
   val lastName get() = _lastName.asStateFlow()
   val email get() = _email.asStateFlow()
   val password get() = _password.asStateFlow()

   fun setFirstName(text: String) {
      _firstName.value = text
   }

   fun setLastName(text: String) {
      _lastName.value = text
   }

   fun setEmail(text: String) {
      _email.value = text
   }

   fun setPassword(text: String) {
      _password.value = text
   }
}