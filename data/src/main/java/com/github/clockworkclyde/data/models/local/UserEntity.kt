package com.github.clockworkclyde.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.model.user.User

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
) : IConvertableTo<User> {

   override fun convertTo(): User {
      return User(
         id = id,
         email = email,
         firstName = firstName,
         lastName = lastName
      )
   }

   companion object {
      fun User.toDbEntity(): UserEntity = object : IConvertableTo<UserEntity> {
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