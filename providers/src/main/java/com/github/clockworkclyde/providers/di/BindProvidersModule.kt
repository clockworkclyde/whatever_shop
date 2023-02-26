package com.github.clockworkclyde.providers.di

import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.data.sources.IProductCardRemoteDataSource
import com.github.clockworkclyde.data.sources.IUserLocalDataSource
import com.github.clockworkclyde.providers.local.UserLocalDataSource
import com.github.clockworkclyde.providers.remote.ProductCardRemoteDataSource
import com.github.clockworkclyde.providers.resources.AndroidResourcesProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindProvidersModule {

   @Binds
   fun bindResourcesProvider(impl: AndroidResourcesProvider): IResourcesProvider

   @Binds
   fun bindUserLocalSource(impl: UserLocalDataSource): IUserLocalDataSource

   @Binds
   fun bindProductRemoteSource(impl: ProductCardRemoteDataSource): IProductCardRemoteDataSource
}