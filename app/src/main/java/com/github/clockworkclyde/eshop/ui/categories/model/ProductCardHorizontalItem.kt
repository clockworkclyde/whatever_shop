package com.github.clockworkclyde.eshop.ui.categories.model

data class ProductCardHorizontalItem(
   val title: String,
   override val items: List<BaseProductCard>
): BaseHorizontalItem() {
   override val itemId: Long = title.hashCode().toLong()
}