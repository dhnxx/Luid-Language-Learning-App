package com.example.luid.classes

import android.database.Cursor

class PhaseOneClass {

    fun getTagalog(cursor: Cursor) : ArrayList<String>{
        var tagalog = ArrayList<String>()
        var index = cursor.getColumnIndex("tagalog")

        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                tagalog.add(cursor.getString(index))
            }
        }

        return tagalog
    }

    fun getEnglish(cursor: Cursor) : ArrayList<String>{
        var english = ArrayList<String>()
        var index = cursor.getColumnIndex("english")

        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                english.add(cursor.getString(index))
            }
        }

        return english
    }

    fun getKapampangan(cursor: Cursor) : ArrayList<String>{
        var kapampangan = ArrayList<String>()
        var index = cursor.getColumnIndex("kapampangan")

        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                kapampangan.add(cursor.getString(index))
            }
        }

        return kapampangan
    }

    fun getPrompt(cursor: Cursor) : ArrayList<String>{
        var decoy = ArrayList<String>()
        var index = cursor.getColumnIndex("question")

        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                decoy.add(cursor.getString(index))
            }
        }

        return decoy
    }
    fun getImg(cursor: Cursor) : ArrayList<Int>{
        var img = ArrayList<Int>()
        var index = cursor.getColumnIndex("drawable")
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                img.add(cursor.getInt(index))
            }
        }
        return img
    }

//    fun questionListAdd(
//        answer : String, question : String, decoy : ArrayList<String>,
//        correctImage : Int, decoyImage : ArrayList<Int>) : ArrayList<WordAssociationClass>{
//
//
//        decoy.removeAll(listOf(answer).toSet())
//        decoyImage.removeAll(listOf(correctImage).toSet())
//
//        var randInd = ArrayList<Int>()
//        var temp = ArrayList<WordAssociationClass>()
//
//        for (i in 1 .. decoy.size){
//            randInd.add(i)
//        }
//        randInd.shuffle()
//
//        temp.add(
//            WordAssociationClass(
//                id[i],
//                question,
//                answer,
//                correctImage,
//                decoy[randInd[0]],
//                decoyImage[randInd[0]],
//                decoy[randInd[1]],
//                decoyImage[randInd[1]],
//                decoy[randInd[2]],
//                decoyImage[randInd[2]]
//            )
//        )
//
//        return temp
//
//    }

}