package com.example.ba2_info.gameutilities

import android.graphics.*
import android.graphics.drawable.BitmapDrawable

interface Pouf {

    fun appear(obj : Any, height : Int , length : Int , sprite : BitmapDrawable){

    }

    fun disappear(rect: RectF, canvas: Canvas){
        rect.set(-6f,-10f,-4f,-8f)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY)
    }
}