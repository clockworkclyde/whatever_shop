package com.github.clockworkclyde.data.sources

interface IPreferenceProvider {
   fun getCurrentUserEmail(): String?
   fun saveCurrentUserEmail(email: String)
   fun saveAsFavorite(name: String): Boolean
   fun getFavorites(): List<String>
}