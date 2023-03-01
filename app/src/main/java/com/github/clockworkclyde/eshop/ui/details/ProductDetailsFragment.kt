package com.github.clockworkclyde.eshop.ui.details

import android.graphics.Color
import android.os.Bundle
import android.transition.Slide
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.applyIfError
import com.github.clockworkclyde.core.utils.applyIfLoading
import com.github.clockworkclyde.core.utils.applyIfSuccess
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.domain.model.product.ProductPhoto
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProductDetailsBinding
import com.github.clockworkclyde.eshop.databinding.ItemProductColorBinding
import com.github.clockworkclyde.eshop.ui.details.adapters.ProductDetailsAdapter
import com.github.clockworkclyde.eshop.ui.details.adapters.ProductPhotoProgress
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment :
   BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel>() {

   override val viewModel: ProductDetailsViewModel by viewModels({ this@ProductDetailsFragment })

   private val photoAdapter by lazy {
      ProductDetailsAdapter(::onPhotoClick)
   }

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
      setUpPhotoList()
      setUpColorList()
   }

   private fun setUpBottomSheet() {
      if (currentChildFragment == null) {
         ProductBottomSheetLayout.newInstance().also {
            childFragmentManager
               .beginTransaction()
               .add(R.id.bottomSheetLayout, it)
               .addToBackStack(it.javaClass.name)
               .show(it)
               .commit()
         }
      }
   }

   private fun setUpPhotoList() {
      binding.photosPager.adapter = photoAdapter
      viewModel.resultFlow.collectWhileStarted { detailsResult ->
         detailsResult
            .applyIfLoading { photoAdapter.items = listOf(ProductPhotoProgress) }
            .applyIfSuccess { photoAdapter.items = it.imageUrls }
            .applyIfError { photoAdapter.items = listOf(ProductPhotoProgress) }
      }
   }

   private fun setUpColorList() {
      viewModel.item.collectWhileStarted {
         it?.let {
            addColorItemsView(binding.colorsLayout, it.colors)
            observeColorSelected()
         }
      }
   }

   private fun addColorItemsView(parent: ViewGroup, colors: List<String>) {
      parent.removeAllViewsInLayout()
      colors.forEachIndexed { index, item ->
         val inflater = LayoutInflater.from(parent.context)
         val itemBinding = ItemProductColorBinding.inflate(inflater, parent, false)
         itemBinding.apply {
            (this.root as? MaterialCardView)
               ?.setCardBackgroundColor(Color.parseColor(item))
            this.root
               .safeClick { viewModel.onColorClicked(index, item) }
         }.let { parent.addView(it.root) }
      }
   }

   private fun observeColorSelected() {
      val notSelected = ContextCompat.getColor(requireContext(), R.color.stroke_product_color)
      val selected = ContextCompat.getColor(requireContext(), R.color.base_primary)
      viewModel.selectedColor.collectWhileStarted { i ->
         binding.colorsLayout.children.forEachIndexed { index, view ->
            (view as? MaterialCardView)?.strokeColor = if (i == index) selected else notSelected
         }
      }
   }

   private fun onPhotoClick(index: Int, item: ProductPhoto) {

   }
}