package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.FlowList

interface IPreferenceRepository {
   fun getCurrentUserEmail(): String?
   fun saveCurrentUserEmail(email: String)
   fun saveFavorite(name: String): Boolean
   fun removeFavorite(name: String): Boolean
   fun getFavorites(): FlowList<String>
}