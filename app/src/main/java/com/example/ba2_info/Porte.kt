package com.example.ba2_info

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Porte(var height : Float = 100f, var length : Float = 50f) {
    var x : Float = 0f
    var y : Float = 0f
    val portepaint = Paint()
    val r = RectF(x, y, x + length, y + height)

    fun goToActivity(context : Context, a : Class<Victory>) {
        context.startActivity(Intent(context, a))
    }

    fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        r.set(x,y,x+length,y+height)
    }

    fun draw(canvas: Canvas) {
        portepaint.color = Color.BLACK
        canvas.drawRect(r, portepaint)
    }

}