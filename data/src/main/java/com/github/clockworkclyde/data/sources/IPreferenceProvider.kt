package com.github.clockworkclyde.data.sources

import com.github.clockworkclyde.core.common.FlowList

interface IPreferenceProvider {
   fun getCurrentUserEmail(): String?
   fun saveCurrentUserEmail(email: String)
   fun saveAsFavorite(name: String): Boolean
   fun removeFavorite(name: String): Boolean
   fun getFavorites(): FlowList<String>
}