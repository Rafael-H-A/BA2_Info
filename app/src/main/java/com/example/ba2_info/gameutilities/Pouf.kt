package com.example.ba2_info.gameutilities

import android.graphics.*

interface Pouf {

    fun draw(canvas: Canvas?){
        canvas?.drawOval(RectF(0f,0f,0f,0f), Paint())
    }

    fun disappear(rect: RectF, canvas: Canvas){
        rect.set(0f,0f,0f,0f)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY)
    }
}