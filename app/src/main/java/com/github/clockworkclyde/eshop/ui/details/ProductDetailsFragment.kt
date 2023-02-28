package com.github.clockworkclyde.eshop.ui.details

import androidx.fragment.app.Fragment
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment: BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel>() {

   private val currentChildFragment: Fragment?
      get() =
         childFragmentManager
            .fragments
            .firstOrNull()

   override fun inflateView() = FragmentProductDetailsBinding.inflate(layoutInflater)

   override fun initViews() {
      setUpBottomSheet()
   }

   private fun setUpBottomSheet() {
      if (currentChildFragment == null) {
         ProductBottomSheetLayout.newInstance().also {
            childFragmentManager
               .beginTransaction()
               .add(R.id.bottomSheetLayout, it)
               .addToBackStack(null)
               .commit()
         }
      }
   }
}