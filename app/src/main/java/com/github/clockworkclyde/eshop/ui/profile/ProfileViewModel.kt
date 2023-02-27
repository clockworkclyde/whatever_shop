package com.github.clockworkclyde.eshop.ui.profile

import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.presentation.viewmodels.INavigationViewModel
import com.github.clockworkclyde.eshop.navigation.directions.profile.ProfileMenuDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

): BaseFlowViewModel(), INavigationViewModel<ProfileMenuDirections> {

   override val directions = ProfileMenuDirections()

   val menuItems: List<ProfileMenuItem> by lazy { ProfileMenuItem.getItems() }

   fun onMenuItemClicked(index: Int, item: ProfileMenuItem) {
      when (item) {
         is ProfileMenuItem.Balance -> TODO()
         is ProfileMenuItem.Help -> TODO()
         is ProfileMenuItem.History -> TODO()
         is ProfileMenuItem.Logout -> onLogOut()
         is ProfileMenuItem.PaymentMethod -> TODO()
         is ProfileMenuItem.RestorePurchase -> TODO()
         is ProfileMenuItem.TradeStore -> TODO()
      }
   }

   private fun onLogOut() {
      // todo: remove user from db, settings and go to login
      viewModelScope.launch {

      }
   }
}