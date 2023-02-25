package com.github.clockworkclyde.eshop.ui

import com.github.clockworkclyde.core.navigation.Navigator
import com.github.clockworkclyde.core.presentation.activities.NavHostActivity
import com.github.clockworkclyde.eshop.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : NavHostActivity(R.layout.activity_main) {

   @Inject override lateinit var navigationHandler: Navigator

   override val hostFragmentId: Int = R.id.mainHostFragment

}