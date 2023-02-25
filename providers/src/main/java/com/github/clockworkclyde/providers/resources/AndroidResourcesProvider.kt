package com.github.clockworkclyde.providers.resources

import android.content.Context
import com.github.clockworkclyde.data.sources.ResourcesProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidResourcesProvider @Inject constructor(
   @ApplicationContext private val context: Context
): ResourcesProvider {

   override fun getString(id: Int): String = context.getString(id)

   override fun getStringArray(
      arrayId: Int
   ): Array<String> = context.resources.getStringArray(arrayId)

   override fun getString(
      id: Int,
      vararg formatArgs: Any
   ): String = context.getString(id, *formatArgs)

}