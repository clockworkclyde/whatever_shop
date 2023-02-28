package com.github.clockworkclyde.eshop.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProductDetailsBinding
import com.github.clockworkclyde.eshop.databinding.ItemProductColorBinding
import com.google.android.material.card.MaterialCardView
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
      setUpColorList()
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

   private fun setUpColorList() {
      viewModel.item.collectWhileStarted {
         it?.let {
            addColorItemsView(binding.colorsLayout, it.colors)
         }
      }
   }

   private fun addColorItemsView(parent: ViewGroup, colors: List<String>) {
      colors.forEachIndexed { index, item ->
         val inflater = LayoutInflater.from(parent.context)
         val itemBinding = ItemProductColorBinding.inflate(inflater, parent, false)
         itemBinding.apply {
            (this.root as MaterialCardView).let { view ->
               view.setCardBackgroundColor(Color.parseColor(item))
            }
            this.root.safeClick { viewModel.onColorClicked(index, item) }
         }.let { parent.addView(it.root) }
      }
   }
}