package com.github.clockworkclyde.core.presentation.fragments

import androidx.viewbinding.ViewBinding
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel

interface IBaseFragment<V: ViewBinding, VM: BaseFlowViewModel> {

   val binding: V

   fun inflateView(): V

   fun initViews()

   fun initBinding(binding: V)

}