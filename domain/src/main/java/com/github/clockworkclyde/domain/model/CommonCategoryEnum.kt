package com.github.clockworkclyde.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.clockworkclyde.domain.R

enum class CommonCategoryEnum(
   @StringRes val stringResId: Int,
   @DrawableRes val imageResId: Int
) {
   Phones(
      R.string.cat_phones,
      R.drawable.ic_cat_phones
   ),
   Headphones(
      R.string.cat_headphones,
      R.drawable.ic_cat_headphones
   ),
   Games(
      R.string.cat_games,
      R.drawable.ic_cat_games
   ),
   Cars(
      R.string.cat_cars,
      R.drawable.ic_cat_cars
   ),
   Furniture(
      R.string.cat_furniture,
      R.drawable.ic_cat_furniture
   ),
   Kids(
      R.string.cat_kids,
      R.drawable.ic_cat_kids
   );
}