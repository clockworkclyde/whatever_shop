package com.github.clockworkclyde.eshop.ui.auth

import androidx.lifecycle.*
import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.FlowAnyResult
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.dto.UEvent
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.domain.usecases.validate.ValidateEmailUseCase
import com.github.clockworkclyde.domain.usecases.validate.ValidateFieldIsNotEmptyUseCase
import com.github.clockworkclyde.eshop.di.IoDispatcher
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.presentation.viewmodels.INavigationViewModel
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.domain.usecases.auth.*
import com.github.clockworkclyde.eshop.navigation.directions.auth.AuthDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val validateEmail: ValidateEmailUseCase,
   private val validateIsNotEmpty: ValidateFieldIsNotEmptyUseCase,
   private val signInAttempt: SignInAttemptUseCase,
   private val loginAttempt: LoginAttemptUseCase,
   private val navigator: INavigator,
   private val checkUserLoggedIn: CheckUserLoggedInUseCase,
   @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseFlowViewModel(), INavigationViewModel<AuthDirections>, DefaultLifecycleObserver {

   override val directions = AuthDirections()

   val firstNameError = MutableSharedFlow<String>()
   val lastNameError = MutableSharedFlow<String>()
   val emailError = MutableSharedFlow<String>()
   val passwordError = MutableSharedFlow<String>()

   private val userLoggedInFlow: Flow<AnyResult> by lazy {
      asyncFlow {
         emit(loadingResult())
         delay(300L)
         emitAll(checkUserLoggedIn())
      }
   }

   @OptIn(FlowPreview::class)
   val progressShouldBeVisible: StateFlow<Boolean> by lazy {
      userLoggedInFlow
         .flatMapConcat {
            flow {
               when (it) {
                  is Result.Loading -> emit(true)
                  else -> emit(false)
               }
            }
         }.stateIn(viewModelScope, SharingStarted.Lazily, true)
   }

   override fun onStart(owner: LifecycleOwner) {
      super.onStart(owner)
      viewModelScope.launch {
         userLoggedInFlow.collect {
            it.applyIfSuccess {
               processNavEvent(directions.toShopCategoriesCleared(), navigator)
            }
         }
      }
   }

   override val resultFlow: MutableSharedFlow<AnyResult> by lazy {
      MutableSharedFlow(
         replay = sharedFlowOptions.replay,
         extraBufferCapacity = sharedFlowOptions.bufferCapacity,
         onBufferOverflow = sharedFlowOptions.bufferOverflow
      )
   }

   /* Sign in validation */
   override val supportFlow: Flow<SignInAttempt> by lazy {
      onEventFlow<UEvent.Validate, SignInAttempt> {
         val firstName = firstName.value
         val lastName = lastName.value
         val email = email.value
         val firstNameHasError =
            validateIsNotEmpty(firstName)?.also { firstNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val lastNameHasError =
            validateIsNotEmpty(lastName)?.also { lastNameError.emit(it) }?.isNotEmpty()
               .orFalse()
         val emailHasError =
            validateEmail(email)?.also { emailError.emit(it) }?.isNotEmpty().orFalse()

         if (!(firstNameHasError || lastNameHasError || emailHasError)) {
            emit(SignInAttempt(firstName = firstName, lastName = lastName, email = email))
         }
      }
   }

   @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
   private val signInFlow: FlowAnyResult by lazy {
      supportFlow
         .debounce(HANDLE_EVENT_TIMEOUT)
         .flatMapLatest {
            asyncFlow {
               signInAttempt.invoke(it)
                  .applyIfError { resultFlow.emit(it) }
                  .applyIfSuccess { emit(toEmptySuccess()) }
            }
         }
   }

   private var signJob: Job? = null
   fun onCreateAccountClicked() {
      signJob?.run { if (isActive) cancel() }
      signJob = viewModelScope.launch(ioDispatcher) {
         processEvent(UEvent.Validate)
         signInFlow.collect {
            if (it.isSuccess()) processNavEvent(
               directions.toShopCategoriesCleared(),
               navigator
            )
         }
      }
   }

   /* Login validation */
   private val secondSupportFlow: Flow<LoginAttempt> by lazy {
      onEventFlow<UEvent.Validate, LoginAttempt> {
         val email = email.value
         val pass = password.value
         val emailHasError =
            validateEmail(email)?.also { emailError.emit(it) }?.isNotEmpty()
               .orFalse()
         val passwordHasError =
            validateIsNotEmpty(pass)?.also { passwordError.emit(it) }?.isNotEmpty()
               .orFalse()

         if (!(emailHasError || passwordHasError)) {
            emit(LoginAttempt(email, pass))
         }
      }
   }

   @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
   private val loginFlow: FlowAnyResult by lazy {
      secondSupportFlow
         .debounce(HANDLE_EVENT_TIMEOUT)
         .flatMapLatest {
            asyncFlow {
               loginAttempt(it)
                  .applyIfError { resultFlow.emit(it) }
                  .applyIfSuccess { emit(toEmptySuccess()) }
            }
         }
   }

   private var loginJob: Job? = null
   fun onLoginAttemptClicked() {
      loginJob?.run { if (isActive) cancel() }
      loginJob = viewModelScope.launch(ioDispatcher) {
         processEvent(UEvent.Validate)
         loginFlow.collect {
            if (it.isSuccess()) processNavEvent(
               directions.toShopCategoriesCleared(),
               navigator
            )
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

   companion object {
      const val HANDLE_EVENT_TIMEOUT = 200L
   }
}