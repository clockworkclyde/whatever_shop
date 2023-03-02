package com.github.clockworkclyde.domain.model.product

import com.github.clockworkclyde.core.presentation.adapters.ListItem

abstract class BaseProductCard: ListItem {
   abstract val category: String
   abstract val name: String
   abstract val price: Double
   abstract val imageUrl: String?
}