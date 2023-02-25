package com.github.clockworkclyde.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.dto.UserDto

@Entity
data class UserEntity(
   @JvmField
   @PrimaryKey(autoGenerate = true)
   val id: Int? = null,
   @JvmField
   val email: String,
   @JvmField
   val firstName: String? = null,
   @JvmField
   val lastName: String? = null
) : IConvertableTo<UserDto> {

   override fun convertTo(): UserDto {
      return UserDto(
         id = id,
         email = email,
         firstName = firstName,
         lastName = lastName
      )
   }

   companion object {
      fun UserDto.fromDomain(): UserEntity = object : IConvertableTo<UserEntity> {
         override fun convertTo(): UserEntity {
            return UserEntity(
               id = id,
               firstName = firstName,
               lastName = lastName,
               email = email
            )
         }
      }.convertTo()
   }
}