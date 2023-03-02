package com.github.clockworkclyde.eshop.di

import com.github.clockworkclyde.core.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

   @Singleton
   @Provides
   fun provideNavigator(scope: CoroutineScope) = Navigator(scope)

}