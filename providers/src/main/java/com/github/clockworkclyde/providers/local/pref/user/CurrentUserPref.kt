package com.github.clockworkclyde.providers.local.pref.user

import com.chibatching.kotpref.KotprefModel

object CurrentUserPref: KotprefModel() {

   var email by stringPref()
   var photoFileDir by stringPref()

}