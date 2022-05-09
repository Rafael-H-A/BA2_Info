package com.example.ba2_info.gameutilities

import android.graphics.*

interface Pouf {

    fun draw(canvas: Canvas?){
        canvas?.drawOval(RectF(0f,0f,0f,0f), Paint())
    }

    fun disappear(rect: RectF, canvas: Canvas){
        rect.set(-6f,-10f,-4f,-8f)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY)
    }
}