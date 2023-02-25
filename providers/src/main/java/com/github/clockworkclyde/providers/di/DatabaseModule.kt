package com.github.clockworkclyde.providers.di

import android.app.Application
import androidx.room.Room
import com.github.clockworkclyde.providers.local.database.AppDatabase
import com.github.clockworkclyde.providers.local.database.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

   @Provides
   @Singleton
   fun provideDatabase(app: Application): AppDatabase =
      Room.databaseBuilder(app, AppDatabase::class.java, "base_delivery_database")
         .fallbackToDestructiveMigration()
         .build()

   @Provides
   @Reusable
   fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
}