package com.github.clockworkclyde.domain.model.product

import com.github.clockworkclyde.core.presentation.adapters.BaseHorizontalItem
import com.github.clockworkclyde.core.presentation.adapters.ListItem
import com.github.clockworkclyde.domain.model.product.BaseProductCard

data class ProductCardHorizontalItem(
   val title: String,
   override val items: List<ListItem>
): BaseHorizontalItem() {

   override val itemId: Long = title.hashCode().toLong()

}