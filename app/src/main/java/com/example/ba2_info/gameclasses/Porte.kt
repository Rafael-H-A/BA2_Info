package com.example.ba2_info.gameclasses

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.abs

class Porte(var height : Float = 100f, var length : Float = 50f) : Pouf {
    var x : Float = 0f
    var y : Float = 0f
    val r = RectF(x, y, x + length, y + height)
    private val portepaint = Paint()

    fun setRect() {
        r.set(x,y,x+length,y+height)
    }

    override fun draw(canvas: Canvas?) {
        portepaint.color = Color.BLACK
        canvas?.drawRoundRect(r, 5f, 5f, portepaint)
    }

    fun updateporte(perso:Personnage) {
        if (abs(perso.r.centerX() - r.centerX()) < (perso.diametre/2 + length/2)
            && abs(perso.r.centerY() - r.centerY()) < (perso.diametre/2 + height/2)) {
            GameConstants.gameOver = perso.power <= GameConstants.enemyPower
            perso.view.displayEnd()
        }
    }

}