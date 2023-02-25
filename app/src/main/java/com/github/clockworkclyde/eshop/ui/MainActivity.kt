package com.github.clockworkclyde.eshop.ui

import android.os.Bundle
import com.github.clockworkclyde.core.navigation.INavigationEventHandler
import com.github.clockworkclyde.core.navigation.Navigator
import com.github.clockworkclyde.core.presentation.activities.NavHostActivity
import com.github.clockworkclyde.eshop.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : NavHostActivity() {

   @Inject override lateinit var navigator: Navigator

   override val hostFragmentId: Int = R.id.mainHostFragment

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
   }

}