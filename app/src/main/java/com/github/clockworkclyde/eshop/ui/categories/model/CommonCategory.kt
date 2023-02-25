package com.github.clockworkclyde.eshop.ui.categories.model

import com.github.clockworkclyde.core.presentation.adapters.ListItem

data class CommonCategory(
   val title: String,
   val imageId: Int
) : ListItem {

   override val itemId: Long = title.hashCode().toLong()

}