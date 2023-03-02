package com.github.clockworkclyde.eshop.ui.details

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.domain.model.product.ProductPhoto
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProductDetailsBinding
import com.github.clockworkclyde.eshop.databinding.ItemProductColorBinding
import com.github.clockworkclyde.eshop.ui.details.adapters.ProductPhotoAdapter
import com.github.clockworkclyde.eshop.ui.details.adapters.ProductPhotoProgress
import com.github.clockworkclyde.eshop.ui.details.adapters.ProductThumbnailAdapter
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment :
   BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel>() {

   override val viewModel: ProductDetailsViewModel by viewModels({ this@ProductDetailsFragment })

   private var currentPage: Int = DEFAULT_PAGE

   private val photoAdapter by lazy {
      ProductPhotoAdapter(::onPhotoClick)
   }

   private val thumbnailAdapter by lazy {
      ProductThumbnailAdapter(::onThumbnailClick)
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
      currentPage = savedInstanceState?.getInt(KEY_PAGE) ?: DEFAULT_PAGE
   }

   override fun initBinding(binding: FragmentProductDetailsBinding) {
      binding.viewModel = viewModel
      binding.lifecycleOwner = viewLifecycleOwner
   }

   override fun initViews() {
      initToolbar()
      setUpBottomSheet()
      observePhotoItems()
      setUpThumbnailList()
      setUpPhotoList()
      setUpColorList()
      setUpShareClick()
   }

   private fun initToolbar() {
      binding.toolbarLayout.setNavigationOnClickListener { findNavController().navigateUp() }
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
      binding.photosPager.apply {
         //selectPhoto(currentPage)
         registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
               super.onPageSelected(position)
               currentPage = position
               selectThumbnail(position)
            }
         })
      }
   }

   private fun observePhotoItems() {
      viewModel.resultFlow.collectWhileStarted { detailsResult ->
         detailsResult
            .applyIfLoading {
               photoAdapter.items = listOf(ProductPhotoProgress)
               thumbnailAdapter.clear()
            }
            .applyIfSuccess {
               photoAdapter.items = it.imageUrls
               thumbnailAdapter.items = it.imageUrls
            }
            .applyIfError {
               photoAdapter.items = listOf(ProductPhotoProgress)
               thumbnailAdapter.clear()
            }
      }
   }

   private fun setUpThumbnailList() {
      binding.thumbnailRV.adapter = thumbnailAdapter
      binding.thumbnailRV.apply {
         setInfinite(false)
         set3DItem(false)
         setIntervalRatio(0.72f)
         setAlpha(false)
         setFlat(false)
      }
      //selectThumbnail(currentPage)
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

   private fun onThumbnailClick(index: Int, item: ProductPhoto) {
      selectThumbnail(index)
      selectPhoto(index)
      currentPage = index
   }

   private fun setUpShareClick() {
      binding.shareBtn.safeClick {
         viewModel.item.value?.let {
            val sendIntent = Intent().apply {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TEXT, it.toString())
               type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, null))
         }
      }
   }

   private fun selectThumbnail(index: Int) {
      binding.thumbnailRV.getCarouselLayoutManager().apply {
         smoothScrollToPosition(binding.thumbnailRV, RecyclerView.State(), index)
      }
   }

   private fun selectPhoto(index: Int, smooth: Boolean = false) {
      binding.photosPager.setCurrentItem(index, smooth)
   }

   override fun onSaveInstanceState(outState: Bundle) {
      super.onSaveInstanceState(outState)
      outState.putInt(KEY_PAGE, currentPage)
   }

   companion object {
      private const val KEY_PAGE = "current_page"
      private const val DEFAULT_PAGE = 0
   }
}