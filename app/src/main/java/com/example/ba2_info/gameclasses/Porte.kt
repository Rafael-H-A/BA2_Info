package com.example.ba2_info.gameclasses

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.abs

class Porte : Pouf {
            var x : Float = 0f
    private var y : Float = 0f
            var height : Float = 100f
            var length : Float = 50f
            val r = RectF(x, y, x + length, y + height)
    private val portepaint = Paint()

    fun appear(xx : Float, yy : Float) {
        x = xx
        y = yy
        r.set(x, y, x + length, y + height)
    }

    override fun draw(canvas: Canvas?) {
        portepaint.color = Color.BLACK
        canvas?.drawRoundRect(r, 5f, 5f, portepaint)
    }

    fun updateporte(perso:Personnage) {
        if (abs(perso.r.centerX() - r.centerX()) < (perso.diametre/2 + length/2)
            && abs(perso.r.centerY() - r.centerY()) < (perso.diametre/2 + height/2)) {
            if (GameConstants.enemyPower >= perso.power) {
                GameConstants.gameOver = true                                           //DEFEAT
                perso.view.drawing = false}
            else {GameConstants.gameOver = false                                        //VICTORY
                  perso.view.drawing = false}

        }
    }

}