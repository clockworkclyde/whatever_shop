package com.github.clockworkclyde.providers.local.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.emptyResult
import com.github.clockworkclyde.core.utils.errorResult
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.data.models.dto.UserPhotoDto
import com.github.clockworkclyde.data.sources.IInternalStorageProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class InternalStorageProvider @Inject constructor(
   @ApplicationContext private val context: Context // @ActivityContext
): IInternalStorageProvider {

   override suspend fun loadPhotoFromInternalStorage(fileName: String): Result<UserPhotoDto> {
      return withContext(Dispatchers.IO) {
         val files = context.filesDir.listFiles()
         files?.find { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.nameWithoutExtension == fileName }?.let {
            val bytes = it.readBytes()
            val bmp: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            UserPhotoDto(it.name, bmp).toSuccessResult()
         } ?: emptyResult()
      }
   }

   override suspend fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Result<UserPhotoDto> {
      return try {
         context.openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
            if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
               throw IOException("Couldn't save photo")
            }
         }
         loadPhotoFromInternalStorage(filename)
      } catch (e: Exception) {
         e.printStackTrace()
         errorResult(message = e.message, exception = e)
      }
   }

   override suspend fun deletePhotoFromInternalStorage(filename: String): Result<Boolean> {
      return try {
         context.deleteFile(filename).toSuccessResult()
      } catch (e: java.lang.Exception) {
         e.printStackTrace()
         errorResult(message = e.message, exception = e)
      }
   }

}