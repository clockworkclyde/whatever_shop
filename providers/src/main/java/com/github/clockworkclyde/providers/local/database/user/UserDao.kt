package com.github.clockworkclyde.providers.local.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.clockworkclyde.data.models.local.UserEntity

@Dao
interface UserDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun storeUser(user: UserEntity)

   @Query("SELECT * FROM UserEntity WHERE email = :email")
   suspend fun findUserByEmail(email: String): UserEntity?

   @Query("SELECT * FROM UserEntity WHERE firstName = :firstName")
   suspend fun findUserByName(firstName: String): UserEntity?

   @Query("DELETE FROM UserEntity WHERE email = :email")
   suspend fun removeUserByEmail(email: String)
}