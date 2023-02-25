package com.github.clockworkclyde.core.utils

import android.view.View

const val DEFAULT_THROTTLE_DURATION_MS = 250L

inline fun <V : View> V.safeClick(
   throttleDuration: Long = DEFAULT_THROTTLE_DURATION_MS,
   crossinline listener: () -> Unit
) {
   setOnClickListener(SafeClickListener(throttleDuration) { listener.invoke() })
}

class SafeClickListener(
   throttleDuration: Long,
   private val clickListener: View.OnClickListener
) : View.OnClickListener {
   private val doubleClickPreventer = DoubleClickPrevent(throttleDuration)

   override fun onClick(v: View?) {
      if (doubleClickPreventer.isPrevent()) return
      else clickListener.onClick(v)
   }
}

private class DoubleClickPrevent(private val throttleDuration: Long) {
   private var lastClickTiming = 0L

   fun isPrevent(): Boolean {
      val now = System.currentTimeMillis()
      val spentTime = now - lastClickTiming
      return if (spentTime in 1 until throttleDuration) {
         true
      } else {
         lastClickTiming = now
         false
      }
   }
}