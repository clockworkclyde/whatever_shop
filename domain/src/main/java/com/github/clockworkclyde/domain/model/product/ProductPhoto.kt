package com.github.clockworkclyde.domain.model.product

import com.github.clockworkclyde.core.presentation.adapters.ListItem

data class ProductPhoto(
   val url: String
): ListItem {
   override val itemId: Long get() = url.hashCode().toLong()
}