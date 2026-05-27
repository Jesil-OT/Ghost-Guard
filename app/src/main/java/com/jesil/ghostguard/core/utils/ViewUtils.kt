package com.jesil.ghostguard.core.utils

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

object ViewUtils {

    fun FragmentActivity.disableBackPress(){
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing: Trap the user here
            }
        })
    }
}