package com.github.clockworkclyde.providers

import com.github.clockworkclyde.data.sources.ResourcesProvider
import com.github.clockworkclyde.providers.resources.AndroidResourcesProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindProvidersModule {

   @Binds
   fun bindResourcesProvider(impl: AndroidResourcesProvider): ResourcesProvider
}