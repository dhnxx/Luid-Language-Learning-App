package com.example.luid.classes

data class WordAssociationClass(
    val id : Int,
    val questions: String,
    val correct: String,
    val correctImg: Int,
    val decoy1: String,
    val decoy1Img: Int,
    val decoy2: String,
    val decoy2Img: Int,
    val decoy3: String,
    val decoy3Img: Int
)
