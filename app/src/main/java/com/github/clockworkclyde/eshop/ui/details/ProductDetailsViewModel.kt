package com.github.clockworkclyde.eshop.ui.details

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.common.FlowResult
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.dto.UEvent
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.domain.model.product.ProductDetails
import com.github.clockworkclyde.domain.usecases.details.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
   private val getProductDetails: GetProductDetailsUseCase
) : BaseFlowViewModel(), DefaultLifecycleObserver {

   val item by lazy {
      resultFlow
         .filter { it is Result.Success }
         .distinctUntilChanged()
         .mapNotNull { it.get() }
         .stateIn(viewModelScope, SharingStarted.Lazily, null)
   }

   override val resultFlow: FlowResult<ProductDetails> by lazy {
      onEventFlow<UEvent.Fetch, Result<ProductDetails>> {
         emit(loadingResult())
         emit(getProductDetails())
      }
   }

   override fun onCreate(owner: LifecycleOwner) {
      super.onCreate(owner)
      processEvent(UEvent.Fetch)
   }

   fun onAddToCartClicked() {
      val quantity = quantity.value
      val item = item.value
   }

   fun onRetryClicked() {
      processEvent(UEvent.Fetch)
   }

   val quantity = MutableStateFlow(INITIAL_QUANTITY)
   val selectedColor = MutableStateFlow(INITIAL_COLOR_INDEX)

   val totalPrice by lazy {
      item.combine(quantity) { item, quantity ->
         item?.price.orDefault() * quantity
      }.stateIn(viewModelScope, SharingStarted.Eagerly, INITIAL_TOTAL_PRICE)
   }

   fun onMoreButtonClicked() {
      quantity.value.let {
         if (it < 25) {
            quantity.value = it.inc()
         }
      }
   }

   fun onLessButtonClicked() {
      quantity.value.let {
         if (it > 1)
            quantity.value = it.dec()
      }
   }

   fun onColorClicked(index: Int, color: String) {
      selectedColor.value = index
   }

   companion object {
      const val INITIAL_TOTAL_PRICE = 0
      const val INITIAL_QUANTITY = 1
      const val INITIAL_COLOR_INDEX = 0
   }
}