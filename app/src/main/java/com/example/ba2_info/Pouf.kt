package com.example.ba2_info

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable

interface Pouf {

    fun appear(obj : Any, height : Int , length : Int , sprite : BitmapDrawable){

    }

    fun disappear(rect: RectF, sprite: BitmapDrawable, canvas: Canvas, paint: Paint)
}