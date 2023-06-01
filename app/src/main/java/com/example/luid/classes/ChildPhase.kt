package com.example.luid.classes

import android.view.View

data class ChildPhase(
    val title: String,
    val description: String,
    val image: Int,
    val onClickListener: View.OnClickListener,
    var isEnabled: Boolean = true // Add a new property to indicate the enabled/disabled state
) {


}
