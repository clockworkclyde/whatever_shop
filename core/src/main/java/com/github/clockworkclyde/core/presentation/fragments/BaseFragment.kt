package com.github.clockworkclyde.core.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.github.clockworkclyde.core.common.launchAndRepeatOnState
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseFragment<V: ViewBinding, VM: BaseFlowViewModel>: Fragment(), IBaseFragment<V, VM> {

   private var _binding: V? = null
   final override val binding: V = _binding ?: error("Cannot access view")

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = inflateView()
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      initViews()
   }

   override fun initBinding(binding: V) = Unit

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

   protected fun <T> Flow<T>.collectWhileStarted(block: (T) -> Unit) {
      launchAndRepeatOnState(Lifecycle.State.STARTED) {
         collect { block(it) }
      }
   }

   protected fun Fragment.launchWhenStarted(block: suspend () -> Unit) {
      launchAndRepeatOnState(Lifecycle.State.STARTED) {
         block.invoke()
      }
   }
}