package com.github.clockworkclyde.providers.local.pref

import com.github.clockworkclyde.data.sources.IPreferenceProvider
import com.github.clockworkclyde.providers.local.pref.user.CurrentUserPref
import javax.inject.Inject

class PreferenceProvider @Inject constructor(): IPreferenceProvider {

   override fun getCurrentUserEmail(): String? = CurrentUserPref.email.takeIf { it.isNotEmpty() }

   override fun saveCurrentUserEmail(email: String) {
      CurrentUserPref.email = email
   }
}