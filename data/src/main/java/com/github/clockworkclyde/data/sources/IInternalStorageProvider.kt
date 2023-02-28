package com.github.clockworkclyde.data.sources

import android.graphics.Bitmap
import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.data.models.dto.UserPhotoDto
import com.github.clockworkclyde.core.dto.Result

interface IInternalStorageProvider {
   suspend fun loadPhotoFromInternalStorage(fileName: String): Result<UserPhotoDto>
   suspend fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Result<UserPhotoDto>
   suspend fun deletePhotoFromInternalStorage(filename: String): AnyResult
}