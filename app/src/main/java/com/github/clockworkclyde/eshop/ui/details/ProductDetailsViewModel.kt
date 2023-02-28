package com.github.clockworkclyde.eshop.ui.details

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.common.FlowResult
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.dto.UEvent
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.utils.get
import com.github.clockworkclyde.core.utils.loadingResult
import com.github.clockworkclyde.core.utils.onEventFlow
import com.github.clockworkclyde.domain.model.product.ProductDetails
import com.github.clockworkclyde.domain.usecases.details.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

   }

   fun onRetryClicked() {
      processEvent(UEvent.Fetch)
   }

   val quantity = MutableStateFlow(VALUE_INITIAL_QUANTITY)

   fun onMoreButtonClicked() {
      quantity.value = quantity.value.inc()
   }

   fun onLessButtonClicked() {
      quantity.value = quantity.value.dec()
   }

   fun onColorClicked(index: Int, color: String) {

   }

   companion object {
      const val VALUE_INITIAL_QUANTITY = 1
   }

}