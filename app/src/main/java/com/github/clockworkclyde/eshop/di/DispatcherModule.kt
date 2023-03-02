package com.github.clockworkclyde.eshop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

   @IoDispatcher
   @Provides
   fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

   @DefaultDispatcher
   @Provides
   fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher