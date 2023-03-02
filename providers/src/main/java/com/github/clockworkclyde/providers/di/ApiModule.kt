package com.github.clockworkclyde.providers.di

import com.github.clockworkclyde.providers.remote.api.EShopApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

   @Provides
   fun provideOkHttpClient(): OkHttpClient {
      return OkHttpClient.Builder()
         .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
         }).build()
   }

   @Provides
   fun provideShopApi(
      retrofitBuilder: Retrofit.Builder,
      okhttpClient: OkHttpClient
   ): EShopApi {
      return retrofitBuilder
         .client(okhttpClient)
         .build()
         .create(EShopApi::class.java)
   }

}