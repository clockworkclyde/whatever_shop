package com.github.clockworkclyde.eshop.ui

import androidx.fragment.app.Fragment
import com.github.clockworkclyde.core.navigation.Navigator
import com.github.clockworkclyde.core.presentation.activities.NavHostActivity
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.ui.navigation.BottomNavigationFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : NavHostActivity(R.layout.activity_main) {

   @Inject
   override lateinit var navigationHandler: Navigator

   override val hostFragmentId: Int = R.id.mainHostFragment

   private val currentBottomNavigationFragment: Fragment?
      get() = supportFragmentManager.findFragmentById(R.id.bottomContainerView)

   override fun onStart() {
      super.onStart()
      navHostController.addOnDestinationChangedListener { _, dest, _ ->
         when (dest.id) {
            R.id.signInFragment -> removeBottomNavigationView()
            R.id.loginFragment -> removeBottomNavigationView()
            else -> addBottomNavigationView()
         }
      }
   }

   private fun addBottomNavigationView() {
      if (currentBottomNavigationFragment == null) {
         supportFragmentManager
            .beginTransaction()
            .add(R.id.bottomContainerView, BottomNavigationFragment.createInstance())
            .addToBackStack(null)
            .commit()
      }
   }

   private fun removeBottomNavigationView() {
      currentBottomNavigationFragment?.let {
         supportFragmentManager
            .beginTransaction()
            .remove(it)
            .commit()
      }
   }

   override fun onSupportNavigateUp(): Boolean {
      return navHostController.navigateUp()
   }
}