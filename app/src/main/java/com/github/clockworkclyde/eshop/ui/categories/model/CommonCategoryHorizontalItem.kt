package com.github.clockworkclyde.eshop.ui.categories.model

data class CommonCategoryHorizontalItem(
   override val items: List<CommonCategory>
): BaseHorizontalItem() {
   override val itemId: Long = items.hashCode().toLong()
}