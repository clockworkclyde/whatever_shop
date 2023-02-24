package com.github.clockworkclyde.eshop.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
open class AppModule {

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext