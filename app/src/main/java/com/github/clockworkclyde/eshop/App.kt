package com.github.clockworkclyde.eshop

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

   override fun onCreate() {
      super.onCreate()
      Timber.plant(Timber.DebugTree())
      Kotpref.init(this)
   }

}