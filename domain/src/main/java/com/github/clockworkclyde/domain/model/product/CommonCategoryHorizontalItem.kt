package com.github.clockworkclyde.domain.model.product

import com.github.clockworkclyde.core.presentation.adapters.BaseHorizontalItem
import com.github.clockworkclyde.domain.model.product.CommonCategory

data class CommonCategoryHorizontalItem(
   override val items: List<CommonCategory>
): BaseHorizontalItem() {

   override val itemId: Long = items.hashCode().toLong()

}