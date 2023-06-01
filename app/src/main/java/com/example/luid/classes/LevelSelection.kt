package com.example.luid.classes


// for LevelAdapter.kt, level selection data class,
data class LevelSelection(
    val levelID: String,
    val levelTitle: String,
    val levelImage: Int,
    val levelDescription: String,
    var isEnabled: Boolean = true // Add a new property to indicate the enabled/disabled state
)

