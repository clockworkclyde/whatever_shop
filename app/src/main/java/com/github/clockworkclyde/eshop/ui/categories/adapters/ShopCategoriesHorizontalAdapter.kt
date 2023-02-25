package com.github.clockworkclyde.eshop.ui.categories.adapters

import android.app.Activity
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.github.clockworkclyde.core.presentation.adapters.BaseDiffUtilCallback
import com.github.clockworkclyde.core.presentation.adapters.ListItem
import com.github.clockworkclyde.core.utils.loadRoundedImage
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.ItemShopProductCategoriesLargeBinding
import com.github.clockworkclyde.eshop.databinding.ItemShopProductCategoriesLargeProgressBinding
import com.github.clockworkclyde.eshop.databinding.ItemShopProductCategoriesSmallBinding
import com.github.clockworkclyde.eshop.databinding.ItemShopProductCategoriesSmallProgressBinding
import com.github.clockworkclyde.eshop.ui.categories.model.*
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ShopCategoriesHorizontalAdapter(
   onCardClick: (Int, BaseProductCard) -> Unit,
   onAddToCartClick: (Int, BaseProductCard) -> Unit,
) :
   AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilCallback()) {

   init {

      delegatesManager
         .addDelegate(
            productCardDelegate(
               onItemClick = onCardClick,
               onAddToCartClick = onAddToCartClick
            )
         )
         .addDelegate(
            productCardProgressDelegate()
         )
         .addDelegate(
            productCardDiscountDelegate(
               onItemClick = onCardClick,
               onAddToCartClick = onAddToCartClick
            )
         )
         .addDelegate(
            productCardDiscountProgressDelegate()
         )

   }

   companion object {

      private fun productCardDelegate(
         onItemClick: (Int, BaseProductCard) -> Unit,
         onAddToCartClick: (Int, BaseProductCard) -> Unit
      ) =
         adapterDelegateViewBinding<ProductCard, ListItem, ItemShopProductCategoriesSmallBinding>({ inflater, container ->
            ItemShopProductCategoriesSmallBinding.inflate(inflater, container, false)
         }) {
            val resources = context.resources
            val emptyImgHolder = ContextCompat.getDrawable(context, R.drawable.bg_img_progress)
            val priceFormat = resources.getString(R.string.format_price_us)
            val imgRadius = resources.getDimensionPixelOffset(R.dimen.product_card_radius)
            bind {
               with(binding) {
                  item.image?.let {
                     Glide.with(root).loadRoundedImage(it, imageView, imgRadius)
                  } ?: imageView.setImageDrawable(emptyImgHolder)

                  categoryTextView.text = item.category
                  titleTextView.text = item.name
                  priceTextView.text = priceFormat.format(item.price)

                  root.safeClick { onItemClick(absoluteAdapterPosition, item) }
                  btnAddToCart.safeClick { onAddToCartClick(absoluteAdapterPosition, item) }
               }
            }

            onViewRecycled {
               if ((binding.root.context as? Activity)?.isDestroyed?.not() == true) {
                  Glide.with(binding.root).clear(binding.imageView)
               }
            }
         }

      private fun productCardProgressDelegate() =
         adapterDelegateViewBinding<ProductCardProgress, ListItem, ItemShopProductCategoriesSmallProgressBinding>(
            { inflater, container ->
               ItemShopProductCategoriesSmallProgressBinding.inflate(inflater, container, false)
            }) {

            val anim = AnimationUtils.loadAnimation(context, R.anim.anim_loading_shim)
            bind {
               binding.root.startAnimation(anim)
            }
         }

      private fun productCardDiscountDelegate(
         onItemClick: (Int, BaseProductCard) -> Unit,
         onAddToCartClick: (Int, BaseProductCard) -> Unit
      ) =
         adapterDelegateViewBinding<ProductCardDiscount, ListItem, ItemShopProductCategoriesLargeBinding>(
            { inflater, container ->
               ItemShopProductCategoriesLargeBinding.inflate(inflater, container, false)
            }) {
            val resources = context.resources
            val emptyImgHolder = ContextCompat.getDrawable(context, R.drawable.bg_img_progress)
            val priceFormat = resources.getString(R.string.format_price_us)
            val imgRadius = resources.getDimensionPixelOffset(R.dimen.product_card_radius)
            bind {
               with(binding) {
                  item.image?.let {
                     Glide.with(root).loadRoundedImage(it, imageView, imgRadius)
                  } ?: imageView.setImageDrawable(emptyImgHolder)

                  categoryTextView.text = item.category
                  titleTextView.text = item.name
                  priceTextView.text = priceFormat.format(item.price)

                  root.safeClick { onItemClick(absoluteAdapterPosition, item) }
                  btnAddToCart.safeClick { onAddToCartClick(absoluteAdapterPosition, item) }
               }
            }

            onViewRecycled {
               if ((binding.root.context as? Activity)?.isDestroyed?.not() == true) {
                  Glide.with(binding.root).clear(binding.imageView)
               }
            }
         }

      private fun productCardDiscountProgressDelegate() =
         adapterDelegateViewBinding<ProductCardDiscountProgress, ListItem, ItemShopProductCategoriesLargeProgressBinding>(
            { inflater, container ->
               ItemShopProductCategoriesLargeProgressBinding.inflate(inflater, container, false)
            }) {

            val anim = AnimationUtils.loadAnimation(context, R.anim.anim_loading_shim)
            bind {
               binding.root.startAnimation(anim)
            }
         }
   }
}