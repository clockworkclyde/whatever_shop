package com.github.clockworkclyde.eshop.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {

   private var navController: NavController? = null

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setUpBottomNavigationView()
   }

   private fun setUpBottomNavigationView() {
      navController = (activity as? MainActivity)?.getNavController()
      navController?.let {
         view
            ?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            ?.setupWithNavController(it)
      }
   }

   companion object {
      fun createInstance() = BottomNavigationFragment()
   }
}