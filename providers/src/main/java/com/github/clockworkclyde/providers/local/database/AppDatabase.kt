package com.github.clockworkclyde.providers.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.clockworkclyde.providers.local.database.user.UserDao

@Database(
   entities = [],
   version = 1,
   exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
   abstract fun userDao(): UserDao
}