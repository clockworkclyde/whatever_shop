package com.github.clockworkclyde.eshop.ui.details.adapters

import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.github.clockworkclyde.core.presentation.adapters.BaseDiffUtilCallback
import com.github.clockworkclyde.core.presentation.adapters.ListItem
import com.github.clockworkclyde.core.utils.loadRoundedImage
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.domain.model.product.ProductPhoto
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.ItemProductDetailsPhotoBinding
import com.github.clockworkclyde.eshop.databinding.ItemProductDetailsPhotoProgressBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ProductDetailsAdapter(
   onItemClickListener: (Int, ProductPhoto) -> Unit
) : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilCallback()) {

   init {
      delegatesManager
         .addDelegate(loadingDelegate())
         .addDelegate(contentDelegate(onItemClickListener))
   }

   companion object {

      private fun loadingDelegate() =
         adapterDelegateViewBinding<ProductPhotoProgress, ListItem, ItemProductDetailsPhotoProgressBinding>({ inflater, container ->
            ItemProductDetailsPhotoProgressBinding.inflate(inflater, container, false)
         }) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.anim_loading_shim)
            bind {
               binding.root.startAnimation(anim)
            }
         }

      private fun errorDelegate() = Unit

      private fun contentDelegate(
         onItemClick: (Int, ProductPhoto) -> Unit
      ) =
         adapterDelegateViewBinding<ProductPhoto, ListItem, ItemProductDetailsPhotoBinding>({ inflater, container ->
            ItemProductDetailsPhotoBinding.inflate(inflater, container, false)
         }) {
            val resources = context.resources
            val radius = resources.getDimensionPixelOffset(R.dimen.product_card_radius)
            bind {
               with(binding) {
                  root.safeClick { onItemClick.invoke(absoluteAdapterPosition, item) }
                  Glide.with(root).loadRoundedImage(item.url, imageView, radius)
               }
            }
         }
   }
}