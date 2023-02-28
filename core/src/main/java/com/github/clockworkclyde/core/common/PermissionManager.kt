package com.github.clockworkclyde.core.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context) : IPermissionManager {

   object Permissions {
      const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
      const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
      const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
      const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
   }

   override fun checkPermissionGranted(permission: String): Boolean {
      return ContextCompat.checkSelfPermission(
         context,
         Manifest.permission.READ_EXTERNAL_STORAGE
      ) == KEY_GRANTED
   }

   override fun checkMultipleGranted(vararg permissions: String): Map<String, Boolean> {
      return permissions.associateWith { checkPermissionGranted(it) }
   }

   companion object {
      const val KEY_GRANTED = PackageManager.PERMISSION_GRANTED
      const val KEY_DENIED = PackageManager.PERMISSION_DENIED
   }
}