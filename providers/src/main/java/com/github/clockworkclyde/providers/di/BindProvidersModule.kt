package com.github.clockworkclyde.providers.di

import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.data.sources.*
import com.github.clockworkclyde.providers.local.UserLocalDataSource
import com.github.clockworkclyde.providers.local.pref.PreferenceProvider
import com.github.clockworkclyde.providers.local.storage.InternalStorageProvider
import com.github.clockworkclyde.providers.remote.ProductCardRemoteDataSource
import com.github.clockworkclyde.providers.remote.ProductDetailsRemoteDataSource
import com.github.clockworkclyde.providers.remote.SearchSuggestionRemoteDataSource
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

   @Binds
   fun bindPreferenceProvider(impl: PreferenceProvider): IPreferenceProvider

   @Binds
   fun bindInternalStorageProvider(impl: InternalStorageProvider): IInternalStorageProvider

   @Binds
   fun bindSuggestionRemoteDataSource(impl: SearchSuggestionRemoteDataSource): ISearchSuggestionRemoteDataSource

   @Binds
   fun bindProductDetailsRemoteDataSource(impl: ProductDetailsRemoteDataSource): IProductDetailsRemoteDataSource
}