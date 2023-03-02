package com.github.clockworkclyde.domain.repository

interface IPreferenceRepository {
   fun getCurrentUserEmail(): String?
   fun saveCurrentUserEmail(email: String)
   fun saveFavorite(name: String): Boolean
   fun getFavorites(): List<String>
}