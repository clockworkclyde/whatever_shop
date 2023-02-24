package com.github.clockworkclyde.core.presentation.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.github.clockworkclyde.core.navigation.INavigationHandler
import com.github.clockworkclyde.core.presentation.utils.launchAndRepeatOnState
import kotlinx.coroutines.flow.Flow

abstract class NavHostActivity : AppCompatActivity() {

   abstract val navigator: INavigationHandler

   abstract val hostFragmentId: Int

   val hostFragment: NavHostFragment? get() = supportFragmentManager.findFragmentById(hostFragmentId) as NavHostFragment?

   val currentFragment: Fragment? get() = hostFragment?.childFragmentManager?.primaryNavigationFragment

   val navHostController: NavController? get() = hostFragment?.navController

   override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
      super.onCreate(savedInstanceState, persistentState)
      handleNavigationCommands()
   }

   private fun handleNavigationCommands() {
      navigator.commands.collectWhileStarted { handler ->
         navHostController?.let(handler)
      }
   }

   protected fun <T> Flow<T>.collectWhileStarted(block: (T) -> Unit) {
      launchAndRepeatOnState(Lifecycle.State.STARTED) {
         collect { block.invoke(it) }
      }
   }
}