package com.example.luid.classes

data class SentenceFragment(val id : Int, val question: String, var sentence: String, val sentenceImage: Int) {

    private val deconstructedSentence: ArrayList<String> = ArrayList()

    init {
        val words = sentence.split(" ")
        for (word in words) {
            deconstructedSentence.add(word)
            deconstructedSentence.shuffle()
        }
    }

    fun getDeconstructedSentence(): ArrayList<String> {
        return deconstructedSentence
    }
}


