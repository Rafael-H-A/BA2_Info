package com.example.ba2_info

import android.graphics.drawable.BitmapDrawable

interface Pouf {

    fun appear(obj : Any, height : Int , length : Int , sprite : BitmapDrawable){

    }

    fun disappear(){

    }
}