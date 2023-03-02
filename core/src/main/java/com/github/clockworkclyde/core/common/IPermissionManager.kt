package com.github.clockworkclyde.core.common

interface IPermissionManager {
   fun checkPermissionGranted(permission: String): Boolean
   fun checkMultipleGranted(vararg permissions: String): Map<String, Boolean>
}