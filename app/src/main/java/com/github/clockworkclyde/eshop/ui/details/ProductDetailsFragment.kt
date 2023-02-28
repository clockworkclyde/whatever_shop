package com.github.clockworkclyde.eshop.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment: BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel>() {

   override val viewModel: ProductDetailsViewModel by viewModels({ this@ProductDetailsFragment })

   private val currentChildFragment: Fragment?
      get() =
         childFragmentManager
            .fragments
            .firstOrNull()

   override fun inflateView() = FragmentProductDetailsBinding.inflate(layoutInflater)

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      lifecycle.addObserver(viewModel)
   }

   override fun initBinding(binding: FragmentProductDetailsBinding) {
      binding.viewModel = viewModel
      binding.lifecycleOwner = viewLifecycleOwner
   }

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