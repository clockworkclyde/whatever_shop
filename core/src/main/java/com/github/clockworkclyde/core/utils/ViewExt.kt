package com.github.clockworkclyde.core.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.launch

fun Fragment.launchAndRepeatOnState(state: Lifecycle.State, block: suspend () -> Unit) {
   viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(state) {
         block()
      }
   }
}

fun AppCompatActivity.launchAndRepeatOnState(state: Lifecycle.State, block: suspend () -> Unit) {
   lifecycleScope.launch {
      repeatOnLifecycle(state) {
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
      //if (this.hasError()) this.clearError()
      listener(text?.toString().orEmpty())
   }
}

fun EditText.clearError() {
   this.error = null
}

fun EditText.hasError(): Boolean = this.error != null

fun Fragment.toast(message: Any? = "", duration: Int = Toast.LENGTH_SHORT) =
   Toast.makeText(requireContext(), message.toString(), duration).show()

fun AsyncListDifferDelegationAdapter<*>.clear() {
   this.items = null
}

fun ViewGroup.setClipToOutline() {
   this.clipToOutline = true
}

fun View.hideKeyboard() {
   val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
   imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Any.alertDialog(
   context: Context,
   message: String,
   title: String = "Title",
   onSubmitText: String = "OK",
   onCancelText: String = "Cancel",
   onSubmit: (() -> Unit)? = null,
   onCancel: () -> Unit = {}
) {
   val resources = context.resources
   return AlertDialog.Builder(context)
      .setTitle(title)
      .setMessage(message)
      .apply {
         if (onSubmit != null) {
            this.setPositiveButton(onSubmitText) { _, _ ->
               onSubmit()
            }
         }
      }
      .setNegativeButton(onCancelText) { dialog, _ ->
         dialog.dismiss()
         onCancel()
      }
      .create()
      .show()
}

fun Uri.asBitmap(activity: Activity): Bitmap =
   MediaStore.Images.Media.getBitmap(activity.contentResolver, this)