package com.github.clockworkclyde.core.utils

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun RequestManager.loadRoundedImage(imageUrl: String, view: ImageView, radius: Int) {
   this.load(imageUrl)
      .transform(
         CenterCrop(),
         RoundedCorners(
            radius
         )
      )
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(view)
}