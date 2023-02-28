package com.github.clockworkclyde.domain.repository

interface IPreferenceRepository {
   fun getCurrentUserEmail(): String?
   fun saveCurrentUserEmail(email: String)
}