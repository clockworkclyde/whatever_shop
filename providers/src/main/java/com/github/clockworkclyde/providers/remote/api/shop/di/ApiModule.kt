package com.github.clockworkclyde.providers.remote.api.shop.di

import com.github.clockworkclyde.providers.remote.api.shop.EShopApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
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