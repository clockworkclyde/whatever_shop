package com.github.clockworkclyde.eshop.ui.bottomnav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.github.clockworkclyde.core.utils.applyIfError
import com.github.clockworkclyde.core.utils.applyIfSuccess
import com.github.clockworkclyde.core.utils.launchAndRepeatOnState
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {

   private var navController: NavController? = null

   val viewModel: BottomNavigationViewModel by viewModels()

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setUpBottomNavigationView()
   }

   private fun setUpBottomNavigationView() {
      navController = (activity as? MainActivity)?.getNavController()
      navController?.let {
         view
            ?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            ?.also { v -> observeShoppingCartCount(v) }
            ?.setupWithNavController(it)
      }
   }

   private fun observeShoppingCartCount(bottomView: BottomNavigationView) {
      launchAndRepeatOnState(Lifecycle.State.CREATED) {
         bottomView.getOrCreateBadge(R.id.shopCartFragment).apply {
            viewModel.resultFlow.collect { result ->
               result.applyIfSuccess { count ->
                  this.isVisible = count > 0
                  this.number = count
               }.applyIfError {
                  this.isVisible = false
               }
            }
         }
      }
   }

   companion object {
      fun createInstance() = BottomNavigationFragment()
   }
}