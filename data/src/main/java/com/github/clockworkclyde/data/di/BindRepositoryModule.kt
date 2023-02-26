package com.github.clockworkclyde.data.di

import com.github.clockworkclyde.data.UserRepository
import com.github.clockworkclyde.domain.repository.IProductCardRepository
import com.github.clockworkclyde.domain.repository.IUserRepository
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

}