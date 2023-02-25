package com.github.clockworkclyde.eshop.ui.categories.model

import com.github.clockworkclyde.core.presentation.adapters.ListItem

abstract class BaseHorizontalItem: ListItem {
   abstract val items: List<ListItem>
}