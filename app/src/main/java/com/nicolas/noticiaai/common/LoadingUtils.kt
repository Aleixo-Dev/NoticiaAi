package com.nicolas.noticiaai.common

import android.content.Context

open class LoadingUtils {

    companion object {
        private var dialogCustom: DialogCustom? = null
        fun showDialog(
            context: Context?,
            isCancelable: Boolean
        ) {
            hideDialog()
            if (context != null) {
                try {
                    dialogCustom = DialogCustom(context)
                    dialogCustom?.let { jarvisLoader ->
                        jarvisLoader.setCanceledOnTouchOutside(true)
                        jarvisLoader.setCancelable(isCancelable)
                        jarvisLoader.show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun hideDialog() {
            if (dialogCustom != null && dialogCustom?.isShowing!!) {
                dialogCustom = try {
                    dialogCustom?.dismiss()
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }

    }
}