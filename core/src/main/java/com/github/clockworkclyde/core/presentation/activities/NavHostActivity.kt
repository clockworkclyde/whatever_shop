package com.github.clockworkclyde.core.presentation.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.clockworkclyde.core.navigation.INavigationEventHandler
import com.github.clockworkclyde.core.utils.launchAndRepeatOnState
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

abstract class NavHostActivity : AppCompatActivity {

   constructor() : super()
   constructor(@LayoutRes layoutId: Int) : super(layoutId)

   abstract val navigationHandler: INavigationEventHandler

   abstract val hostFragmentId: Int

   protected val hostFragment: NavHostFragment? get() = supportFragmentManager.findFragmentById(hostFragmentId) as NavHostFragment?

   protected val currentFragment: Fragment? get() = hostFragment?.childFragmentManager?.primaryNavigationFragment

   protected val navHostController: NavController get() = hostFragment?.navController ?: throw IllegalStateException("Cannot find nav controller")

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      handleNavigationCommands()
   }

   open fun handleNavigationCommands() {
      navigationHandler.commands.collectWhileCreated { handler ->
         navHostController.let(handler)
         Timber.e("Navigation command was handled when current fragment is $currentFragment")
      }
   }

   fun getNavController(): NavController? = hostFragment?.navController

   protected fun <T> Flow<T>.collectWhileStarted(block: (T) -> Unit) {
      launchAndRepeatOnState(Lifecycle.State.STARTED) {
         collect { block.invoke(it) }
      }
   }

   protected fun <T> Flow<T>.collectWhileCreated(block: (T) -> Unit) {
      launchAndRepeatOnState(Lifecycle.State.CREATED) {
         collect { block.invoke(it) }
      }
   }
}