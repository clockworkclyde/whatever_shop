package com.github.clockworkclyde.eshop.ui.categories.adapters

import androidx.core.content.ContextCompat
import com.github.clockworkclyde.core.presentation.adapters.BaseDiffUtilCallback
import com.github.clockworkclyde.core.presentation.adapters.ListItem
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.eshop.databinding.ItemShopCommonCategoriesBinding
import com.github.clockworkclyde.domain.model.product.CommonCategory
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ShopCategoriesCommonAdapter(
   onCatClick: (CommonCategory) -> Unit
) : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilCallback()) {

   init {
      delegatesManager.addDelegate(categoryDelegate(onCatClick))
   }

   companion object {
      private fun categoryDelegate(
        onItemClick: (CommonCategory) -> Unit
      ) =
         adapterDelegateViewBinding<CommonCategory, ListItem, ItemShopCommonCategoriesBinding>({ inflater, container ->
            ItemShopCommonCategoriesBinding.inflate(inflater, container, false)
         }) {
            bind {
               with(binding) {
                  titleTextView.text = item.title
                  ContextCompat.getDrawable(context, item.imageId)
                     .let { iconImageView.setImageDrawable(it) }

                  root.safeClick {
                     onItemClick.invoke(item)
                  }
               }
            }
         }
   }
}