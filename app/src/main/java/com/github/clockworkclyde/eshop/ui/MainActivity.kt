package com.github.clockworkclyde.eshop.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.clockworkclyde.core.navigation.Navigator
import com.github.clockworkclyde.core.presentation.activities.NavHostActivity
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.ui.bottomnav.BottomNavigationFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : NavHostActivity(R.layout.activity_main) {

   @Inject
   override lateinit var navigationHandler: Navigator

   override val hostFragmentId: Int = R.id.mainHostFragment

   private var currentBottomNavigationFragment: Fragment? = null

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
         currentBottomNavigationFragment = BottomNavigationFragment.createInstance().also {
            supportFragmentManager
               .beginTransaction()
               .add(R.id.bottomContainerView, it)
               .addToBackStack(null)
               .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
               .commit()
         }
      }
   }

   private fun removeBottomNavigationView() {
      currentBottomNavigationFragment?.also {
         supportFragmentManager
            .beginTransaction()
            .remove(it)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .commit()
      }
      currentBottomNavigationFragment = null
   }

   override fun onSupportNavigateUp(): Boolean {
      return navHostController.navigateUp()
   }
}