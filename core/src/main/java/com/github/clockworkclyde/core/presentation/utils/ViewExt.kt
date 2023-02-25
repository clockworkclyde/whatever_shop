package com.github.clockworkclyde.core.presentation.utils

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.clockworkclyde.core.dto.IEvent
import com.github.clockworkclyde.core.dto.UEvent
import com.github.clockworkclyde.core.presentation.fragments.IBaseFragment
import com.github.clockworkclyde.core.presentation.viewmodels.IEventViewModel
import kotlinx.coroutines.launch

fun Fragment.launchAndRepeatOnState(state: Lifecycle.State, block: suspend () -> Unit) {
   viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(state) {
         block()
      }
   }
}

inline fun <reified V : ViewDataBinding, reified VM : IEventViewModel> IBaseFragment<V, VM>.fetch() =
   this.viewModel?.processEvent(UEvent.Fetch)

inline fun <reified V : ViewDataBinding, reified VM : IEventViewModel> IBaseFragment<V, VM>.processEvent(
   event: IEvent
) =
   this.viewModel?.processEvent(event)

inline fun EditText.onTextChanged(crossinline listener: (String) -> Unit) {
   doOnTextChanged { text, _, _, _ ->
      listener(text?.toString().orEmpty())
   }
}