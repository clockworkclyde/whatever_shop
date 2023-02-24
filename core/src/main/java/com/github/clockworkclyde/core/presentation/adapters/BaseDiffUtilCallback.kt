package com.github.clockworkclyde.core.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {

   override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
      oldItem.itemId == newItem.itemId

   override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
      oldItem.equals(newItem)
}