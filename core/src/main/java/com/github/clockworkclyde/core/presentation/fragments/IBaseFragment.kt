package com.github.clockworkclyde.core.presentation.fragments

import androidx.databinding.ViewDataBinding
import com.github.clockworkclyde.core.presentation.viewmodels.IEventViewModel

interface IBaseFragment<V: ViewDataBinding, VM: IEventViewModel> {

   val viewModel: VM?

   val binding: V

   fun inflateView(): V

   fun initViews()

   fun handleResultError()

   fun initBinding(binding: V)

}