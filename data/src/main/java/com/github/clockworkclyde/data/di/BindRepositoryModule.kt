package com.github.clockworkclyde.data.di

import com.github.clockworkclyde.data.*
import com.github.clockworkclyde.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindRepositoryModule {

   @Binds
   @Singleton
   fun bindUserRepository(impl: UserRepository): IUserRepository

   @Binds
   @Singleton
   fun bindProductCardRepository(impl: ProductCardRepository): IProductCardRepository

   @Binds
   @Singleton
   fun bindPreferenceRepository(impl: PreferenceRepository): IPreferenceRepository

   @Binds
   @Singleton
   fun bindProfilePhotoRepository(impl: ProfilePhotoRepository): IProfilePhotoRepository

   @Binds
   @Singleton
   fun bindSearchSuggestionRepository(impl: SearchSuggestionRepository): ISearchSuggestionRepository

   @Binds
   @Singleton
   fun bindDetailsRepository(impl: ProductDetailsRepository): IProductDetailsRepository

}