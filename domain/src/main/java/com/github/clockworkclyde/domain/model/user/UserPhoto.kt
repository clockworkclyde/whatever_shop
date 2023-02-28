package com.github.clockworkclyde.domain.model.user

import android.graphics.Bitmap
import com.github.clockworkclyde.core.presentation.adapters.ListItem

data class UserPhoto(
   val fileName: String? = "",
   val bitmap: Bitmap?
): ListItem {
   override val itemId: Long = fileName.hashCode().toLong()

   companion object {
      const val KEY_PROFILE_PIC = "profile_pic"
   }
}