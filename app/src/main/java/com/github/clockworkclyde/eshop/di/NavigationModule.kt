package com.github.clockworkclyde.eshop.di

import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

   @Singleton
   @Binds
   fun bindNavigator(impl: Navigator): INavigator

}