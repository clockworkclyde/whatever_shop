package com.github.clockworkclyde.eshop.ui.categories.adapters

import com.github.clockworkclyde.core.presentation.adapters.BaseDiffUtilCallback
import com.github.clockworkclyde.core.presentation.adapters.ListItem
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.domain.model.product.BaseProductCard
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem
import com.github.clockworkclyde.eshop.databinding.LayoutShopProductCategoriesHorizontalBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ShopCategoriesRootAdapter(
   onHorizontalItemExpandClickListener: (ProductCardHorizontalItem) -> Unit,
   onProdCardClick: (Int, BaseProductCard) -> Unit,
   onProdAddToCartClick: (Int, BaseProductCard) -> Unit,
) :
   AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilCallback()) {

   init {
      delegatesManager
         .addDelegate(
            horizontalCategoriesDelegate(
               onHorizontalItemExpandClick = onHorizontalItemExpandClickListener,
               onCardClick = onProdCardClick,
               onAddToCartClick = onProdAddToCartClick
            )
         )
   }

   companion object {
//      private fun horizontalCommonCategoriesDelegate(onItemClick: (CommonCategory) -> Unit) =
//         adapterDelegateViewBinding<CommonCategoryHorizontalItem, ListItem, LayoutShopCommonCategoriesHorizontalBinding>(
//            { inflater, container ->
//               LayoutShopCommonCategoriesHorizontalBinding.inflate(inflater, container, false)
//            }) {
//
//            val adapter = ShopCategoriesCommonAdapter(onCatClick = onItemClick)
//            bind {
//               with(binding) {
//                  recyclerView.adapter = adapter
//                  adapter.items = item.items
//               }
//            }
//         }

      private fun horizontalCategoriesDelegate(
         onHorizontalItemExpandClick: (ProductCardHorizontalItem) -> Unit,
         onCardClick: (Int, BaseProductCard) -> Unit,
         onAddToCartClick: (Int, BaseProductCard) -> Unit,
      ) =
         adapterDelegateViewBinding<ProductCardHorizontalItem, ListItem, LayoutShopProductCategoriesHorizontalBinding>(
            { inflater, container ->
               LayoutShopProductCategoriesHorizontalBinding.inflate(inflater, container, false)
            }) {

            val adapter = ShopCategoriesHorizontalAdapter(
               onCardClick = onCardClick,
               onAddToCartClick = onAddToCartClick
            )

            bind {
               with(binding) {
                  this.recyclerView.adapter = adapter
                  this.titleTextView.text = item.title
                  this.txtBtnShowMore.safeClick { onHorizontalItemExpandClick(item) }
                  adapter.items = item.items
               }
            }
         }
   }
}