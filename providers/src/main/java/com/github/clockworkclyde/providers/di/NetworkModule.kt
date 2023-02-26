package com.github.clockworkclyde.providers.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

   open fun getBaseUrl() = "https://run.mocky.io/v3/"

   @Provides
   @BaseUrl
   fun provideBaseUrl() = getBaseUrl()

   @Provides
   @Singleton
   fun provideRetrofitBuilder(@BaseUrl baseUrl: String): Retrofit.Builder =
      Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
         .addCallAdapterFactory(CoroutineCallAdapterFactory())

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
   AnnotationTarget.FIELD,
   AnnotationTarget.VALUE_PARAMETER,
   AnnotationTarget.FUNCTION,
   AnnotationTarget.PROPERTY_GETTER,
   AnnotationTarget.PROPERTY_SETTER
)
annotation class BaseUrl