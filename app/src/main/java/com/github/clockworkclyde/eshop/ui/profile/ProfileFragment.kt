package com.github.clockworkclyde.eshop.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentProfileBinding
import com.github.clockworkclyde.eshop.databinding.ItemMenuProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      lifecycle.addObserver(viewModel)
   }

   override val viewModel: ProfileViewModel by navGraphViewModels(R.id.host_nav_graph) { defaultViewModelProviderFactory }

   override fun inflateView() = FragmentProfileBinding.inflate(layoutInflater)

   override fun initViews() {
      initToolbar()
      setUpMenuItems()
      setUpProfileData()
      setUpOnProfilePicClick()
      observeResultError()
      observeProfilePhoto()
   }

   private fun setUpMenuItems() {
      addMenuItemsView(binding.menuItemsLayout, viewModel.menuItems)
   }

   private fun setUpProfileData() {
      viewModel.profile.collectWhileStarted { profileDataResult ->
         profileDataResult
            .applyIfSuccess {
               setUserName(it.firstName, it.lastName)
            }
      }
   }

   private fun observeProfilePhoto() {
      viewModel.resultFlow.collectWhileStarted { profilePhoto ->
         profilePhoto
            .applyIfLoading {
               setEmptyPhotoTemplate(binding.profilePicImgView)
            }
            .applyIfEmpty {
               setEmptyPhotoTemplate(binding.profilePicImgView)
            }
            .applyIfSuccess {
               loadUserPhotoInto(binding.profilePicImgView, it.pic?.bitmap)
            }
      }
   }

   private val emptyPhotoTemplate: Drawable? by lazy {
      ContextCompat.getDrawable(requireContext(), R.drawable.ic_user_photo)
   }

   private fun setEmptyPhotoTemplate(view: ImageView) =
      view.setImageDrawable(emptyPhotoTemplate)

   private fun setUserName(first: String?, last: String?) {
      binding.usernameTxtView.text = if (first != null && last != null) {
         "$first $last"
      } else {
         getString(R.string.template_username)
      }
   }

   private fun loadUserPhotoInto(view: ImageView, bitmap: Bitmap?) {
      if (bitmap == null) {
         setEmptyPhotoTemplate(view)
         return
      }
      Glide.with(binding.root)
         .loadCircleRoundedBitmap(bitmap, view)
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
            this.arrowImgBtn.isVisible = item.balance == null || item.arrowVisibility
            this.balanceTextView.isVisible = item.balance != null && !item.arrowVisibility
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

   private fun observeResultError() {
      viewModel.supportFlow.collectWhileStarted { toast(it) }
   }

   private fun initToolbar() {
      binding.toolbarLayout.setNavigationOnClickListener { findNavController().navigateUp() }
   }

   private fun setUpOnProfilePicClick() {
      with(binding) {
         uploadItemBtn.safeClick { onChangeProfilePicture() }
      }
   }

   private fun onChangeProfilePicture() {
      val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
      launcherForPhotoResult.launch(intent)
   }

   private val launcherForPhotoResult = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
   ) { result ->
      if (result.resultCode == Activity.RESULT_OK && result.data != null) {
         val uri = result.data?.data
         uri?.asBitmap(requireActivity())?.let { viewModel.onNewPhotoSelected(it) }
      }
   }

   /** if user needs to take fresh photo **/
//   private fun onChangeProfilePicture() {
//      val manager = PermissionManager(requireContext())
//      val permission = PermissionManager.Permissions.READ_EXTERNAL_STORAGE
//      if (!manager.checkPermissionGranted(permission)) {
//         val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//         launcherForPhotoResult.launch(intent)
//      }

//   private val launcherForPermissionResult = registerForActivityResult(
//      ActivityResultContracts.RequestPermission()
//   ) { granted ->
//      when {
//         granted -> {
//            onChangeProfilePicture()
//         }
//         else -> alertDialog(
//            context = requireContext(),
//            title = getString(R.string.alert_ext_permission_title),
//            message = getString(R.string.alert_ext_permission_message),
//            onCancelText = getString(R.string.alert_ext_permission_btn)
//         )
//      }
//   }
}