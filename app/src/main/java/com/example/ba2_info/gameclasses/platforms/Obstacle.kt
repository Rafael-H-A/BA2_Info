package com.example.ba2_info.gameclasses.platforms

import android.graphics.*
import com.example.ba2_info.gameclasses.Personnage

open class Obstacle (var obstacleBeginX: Float, var obstacleBeginY: Float, var obstacleLength: Float, var obstacleHeigth: Float, var plain : Boolean = true) {
    val r = RectF(
        obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength, obstacleBeginY
    )
    val obstaclePaint = Paint()
    lateinit var perso: Personnage

    fun setRect() { //redimensionne l'animation
        r.set(obstacleBeginX, obstacleBeginY,
            obstacleBeginX + obstacleLength, obstacleBeginY + obstacleHeigth)
    }

    open fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.parseColor("#8FBC8F")
        canvas.drawRect(r, obstaclePaint)
    }
}

