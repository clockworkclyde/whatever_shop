package com.github.clockworkclyde.core.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes

inline fun <reified T : Any> AutoCompleteTextView.applyUserInteractionTyped(
   crossinline onItemClick: (Int, T) -> Unit
) = apply {
   setOnItemClickListener { adapterView, _, index, _ ->
      (adapterView.getItemAtPosition(index) as? T)?.let {
         onItemClick.invoke(index, it)
      }
   }
}

fun <T : Any> AutoCompleteTextView.setListAdapter(items: List<T>, @LayoutRes itemLayout: Int) {
   val itemsAdapter = ArrayAdapter(
      context, itemLayout, items
   )
   this.setAdapter(itemsAdapter)
}