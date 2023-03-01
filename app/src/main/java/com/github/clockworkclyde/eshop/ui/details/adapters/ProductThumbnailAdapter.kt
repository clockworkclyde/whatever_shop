package com.github.clockworkclyde.eshop.ui.details.adapters

import com.bumptech.glide.Glide
import com.github.clockworkclyde.core.presentation.adapters.BaseDiffUtilCallback
import com.github.clockworkclyde.core.presentation.adapters.ListItem
import com.github.clockworkclyde.core.utils.loadRoundedImage
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.domain.model.product.ProductPhoto
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.ItemProductDetailsThumbnailBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ProductThumbnailAdapter(
   onItemClickListener: (Int, ProductPhoto) -> Unit,
   initialSelectedItem: Int = 0,
) : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilCallback()) {

   init {
      delegatesManager
         .addDelegate(contentDelegate(onItemClickListener, initialSelectedItem))
   }

   companion object {

      private fun loadingDelegate() = Unit

      private fun contentDelegate(
         onItemClick: (Int, ProductPhoto) -> Unit,
         initialSelectedItem: Int
      ) =
         adapterDelegateViewBinding<ProductPhoto, ListItem, ItemProductDetailsThumbnailBinding>(
            { inflater, container ->
               ItemProductDetailsThumbnailBinding.inflate(inflater, container, false)
            }) {
            bind {
               val resources = context.resources
               val radius = resources.getDimensionPixelOffset(R.dimen.radius_details_thumbnail)

               var selected = initialSelectedItem
               with(binding) {
                  Glide.with(root).loadRoundedImage(item.url, imageView, radius)

                  itemView.isSelected = selected == bindingAdapterPosition
                  root.safeClick {
                     onItemClick(absoluteAdapterPosition, item)
                     selected = bindingAdapterPosition
                  }
               }
            }
         }
   }
}