package com.github.clockworkclyde.data.sources

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourcesProvider {

   fun getString(@StringRes id: Int): String
   fun getStringArray(@ArrayRes arrayId: Int): Array<String>
   fun getString(@StringRes id: Int, vararg formatArgs: Any): String
}