package com.jess.wodtimer.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @since jess
 * @author 2020.08.12
 */
object DeviceUtils {

    /**
     * 네이게이션바 높이
     */
    fun getNavigationBarHeight(context: Context?): Int {
        return context?.let {
            val resourceIdBottom: Int =
                it.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceIdBottom > 0) {
                it.resources.getDimensionPixelSize(resourceIdBottom)
            } else {
                0
            }
        } ?: run {
            0
        }
    }

    /**
     * 키보드 숨김
     */
    fun hideKeyboard(context: Context?, view: View?) {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputManager?.hideSoftInputFromWindow(
            view?.windowToken,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 키보드 보임
     */
    fun showKeyboard(context: Context?, view: View?) {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        view?.requestFocus()
        inputManager?.showSoftInput(
            view,
            InputMethodManager.SHOW_IMPLICIT
        )
    }
}