package com.example.luid.classes

data class StoryClass(
    val char: String,
    val boolean: Boolean,
    val message: String,
    val messageAlt: String,
    val charImage: Int,
    val actionMsg: String? = null,
) {
}