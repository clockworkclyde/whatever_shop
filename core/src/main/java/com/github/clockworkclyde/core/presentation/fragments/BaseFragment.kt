package com.github.clockworkclyde.core.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.github.clockworkclyde.core.utils.launchAndRepeatOnState
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseFragment<V: ViewDataBinding, VM: BaseFlowViewModel>: Fragment(), IBaseFragment<V, VM> {

   override val viewModel: VM? = null

   private var _binding: V? = null
   override val binding: V get() = _binding ?: throw IllegalStateException("Cannot access view")

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = inflateView()
      initBinding(binding)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      initViews()
   }

   override fun initViews() = Unit

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