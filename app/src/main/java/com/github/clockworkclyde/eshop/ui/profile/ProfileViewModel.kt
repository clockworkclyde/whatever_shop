package com.github.clockworkclyde.eshop.ui.profile

import android.graphics.Bitmap
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.common.*
import com.github.clockworkclyde.core.dto.UEvent
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.presentation.viewmodels.INavigationViewModel
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.domain.model.user.User
import com.github.clockworkclyde.domain.usecases.profile.GetUserDataUseCase
import com.github.clockworkclyde.domain.usecases.profile.LogOutUserUseCase
import com.github.clockworkclyde.eshop.navigation.directions.profile.ProfileMenuDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.domain.model.user.UserPhoto
import com.github.clockworkclyde.domain.usecases.profile.SaveNewUserPhotoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   private val logOutUser: LogOutUserUseCase,
   private val getUserData: GetUserDataUseCase,
   private val saveUserPhoto: SaveNewUserPhotoUseCase,
   private val navigator: INavigator
) : BaseFlowViewModel(), INavigationViewModel<ProfileMenuDirections>, DefaultLifecycleObserver {

   override val directions = ProfileMenuDirections()

   val menuItems: List<ProfileMenuItem> by lazy { ProfileMenuItem.getItems() }

   val profile: FlowResult<User> by lazy {
      onEventFlow<UEvent.Fetch, Result<User>> {
         emit(emptyResult())
         emit(loadingResult())
         emit(getUserData())
      }
   }

   /* Profile data */
   @OptIn(ExperimentalCoroutinesApi::class)
   override val resultFlow: FlowResult<User> by lazy {
      photo.flatMapLatest {
         profile.flatMapFlowIfSuccess { user ->
            when (it) {
               is Result.Success -> user.copy(pic = it.data)
               else -> user
            }.toSuccessResult()
         }
      }
   }

   fun onMenuItemClicked(index: Int, item: ProfileMenuItem) {
      when (item) {
         is ProfileMenuItem.Balance -> Unit
         is ProfileMenuItem.Help -> Unit
         is ProfileMenuItem.History -> Unit
         is ProfileMenuItem.Logout -> onLogOut()
         is ProfileMenuItem.PaymentMethod -> Unit
         is ProfileMenuItem.RestorePurchase -> Unit
         is ProfileMenuItem.TradeStore -> Unit
      }
   }

   override val supportFlow: MutableSharedFlow<ErrorThrowableResult> by lazy {
      MutableSharedFlow()
   }

   val photo: MutableStateFlow<Result<UserPhoto>> by lazy {
      MutableStateFlow(emptyResult())
   }

   fun onNewPhotoSelected(bitmap: Bitmap) {
      viewModelScope.launch {
         photo.tryEmit(loadingResult())
         photo.tryEmit(
            saveUserPhoto(
               UserPhoto(
                  fileName = UserPhoto.KEY_PROFILE_PIC,
                  bitmap = bitmap
               )
            )
         )
      }
   }

   private fun onLogOut() {
      viewModelScope.launch {
         logOutUser.invoke()
            .applyIfError { supportFlow.emit(it) }
            .applyIfSuccess { processNavEvent(directions.onLogOut(), navigator) }
      }
   }

   private fun fetchUserProfileData() {
      processEvent(UEvent.Fetch)
   }

   override fun onStart(owner: LifecycleOwner) {
      super.onStart(owner)
      fetchUserProfileData()
   }
}