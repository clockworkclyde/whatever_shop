package com.github.clockworkclyde.data.models.dto

import android.graphics.Bitmap
import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.model.user.UserPhoto

data class UserPhotoDto(
   val fileName: String,
   val bmp: Bitmap
): IConvertableTo<UserPhoto> {

   override fun convertTo(): UserPhoto {
      return UserPhoto(
         fileName, bmp
      )
   }
}