package com.github.clockworkclyde.core.presentation.utils

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.github.clockworkclyde.core.common.UnitHandler

@BindingAdapter("android:safeClick")
fun safeClickUnitHandler(view: View, onSafeClick: UnitHandler) {
   view.safeClick { onSafeClick() }
}

@BindingAdapter("android:onValueChange")
fun onValueChangeOrEmpty(view: EditText, onValueChange: (String) -> Unit) {
   view.onTextChanged { onValueChange(it) }
}
