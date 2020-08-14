package com.jess.wodtimer.common.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.jess.wodtimer.R

/**
 * @author jess
 * @since 2020.08.11
 */
class ProgressDialog(
    context: Context
) : Dialog(context, R.style.DialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_dialog)
        initDialog()
    }

    private fun initDialog() {
        window?.attributes = WindowManager.LayoutParams().apply {
            copyFrom(window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }
}