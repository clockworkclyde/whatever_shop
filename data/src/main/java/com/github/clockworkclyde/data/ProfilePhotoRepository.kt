package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.emptyResult
import com.github.clockworkclyde.core.utils.flatMapIfSuccess
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.data.sources.IInternalStorageProvider
import com.github.clockworkclyde.domain.model.user.UserPhoto
import com.github.clockworkclyde.domain.repository.IProfilePhotoRepository
import javax.inject.Inject

class ProfilePhotoRepository @Inject constructor(
   private val resources: IResourcesProvider,
   private val storageProvider: IInternalStorageProvider
) : IProfilePhotoRepository {

   override suspend fun saveNewPhoto(photo: UserPhoto): Result<UserPhoto> {
      val fileName = photo.fileName
      val bitmap = photo.bitmap
      return if (fileName != null && bitmap != null) {
         storageProvider.savePhotoToInternalStorage(fileName, bitmap)
            .flatMapIfSuccess { it.convertTo().toSuccessResult() }
      } else {
         emptyResult()
      }
   }

   override suspend fun getCurrentPhoto(): Result<UserPhoto> {
      return storageProvider.loadPhotoFromInternalStorage(UserPhoto.KEY_PROFILE_PIC)
         .flatMapIfSuccess { it.convertTo().toSuccessResult() }
   }

   override suspend fun removeCurrentPhoto(): AnyResult {
      return storageProvider.deletePhotoFromInternalStorage(UserPhoto.KEY_PROFILE_PIC)
   }

}