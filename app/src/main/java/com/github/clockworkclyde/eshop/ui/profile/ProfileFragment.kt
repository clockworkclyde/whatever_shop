package com.github.clockworkclyde.eshop.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.alertDialog
import com.github.clockworkclyde.core.utils.safeClick
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProfileBinding
import com.github.clockworkclyde.eshop.databinding.ItemMenuProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

   override val viewModel: ProfileViewModel by viewModels()

   override fun inflateView() = FragmentProfileBinding.inflate(layoutInflater)

   override fun initViews() {
      setUpMenuItems()
      setUpOnProfilePicClick()
   }

   private fun setUpMenuItems() {
      addMenuItemsView(binding.menuItemsLayout, viewModel.menuItems)
   }

   private fun addMenuItemsView(parent: ViewGroup, dates: List<ProfileMenuItem>) {
      val balanceFormat = getString(R.string.format_price_us)
      dates.forEachIndexed { index, item ->
         val inflater = LayoutInflater.from(parent.context)
         val itemBinding = ItemMenuProfileBinding.inflate(inflater, parent, false)
         itemBinding.apply {
            this.iconImageView.setImageDrawable(
               ContextCompat.getDrawable(
                  requireContext(),
                  item.iconResId
               )
            )
            this.itemNameTextView.text = getString(item.titleResId)
            this.arrowImgBtn.isVisible = item.balance != null
            this.balanceTextView.text = balanceFormat.format(item.balance)
            this.itemMenuLayout.safeClick { onMenuItemClick(index, item) }
         }.let { parent.addView(it.root) }
      }
   }

   private fun onMenuItemClick(index: Int, item: ProfileMenuItem) {
      when (item) {
         is ProfileMenuItem.Logout -> logoutAlert(onSubmit = {
            viewModel.onMenuItemClicked(
               index = index,
               item = item
            )
         })
         else -> viewModel.onMenuItemClicked(index = index, item = item)
      }
   }

   private fun logoutAlert(onSubmit: () -> Unit, onCancel: () -> Unit = {}) {
      alertDialog(
         context = requireContext(),
         message = getString(R.string.alert_logout_msg),
         title = getString(R.string.alert_logout_title),
         onSubmit = onSubmit,
         onCancel = onCancel
      )
   }

   private fun setUpOnProfilePicClick() {
      with(binding) {
         uploadItemBtn.safeClick { onChangeProfilePicture() }
      }
   }

   private fun onChangeProfilePicture() {
      // todo: choose pic from phone gallery and store it to internal directory to show
   }
}