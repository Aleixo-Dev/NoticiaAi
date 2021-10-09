package com.nicolas.noticiaai.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import com.nicolas.noticiaai.R

class DialogCustom(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom)
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawableResource(R.color.white_transparent)
    }
}