package com.example.ba2_info

import android.content.Context
import android.graphics.*

open class Obstacle (var obstacleBeginX: Float, var obstacleBeginY: Float, var obstacleLength: Float, var obstacleHeigth: Float, var view: GameView, var plain : Boolean = true) {
    val r = RectF(
        obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength, obstacleBeginY
    )
    //Lignes mises en rectangles pour détecter les intersections
    val rleft = RectF(obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + 1f, obstacleBeginY)
    val rright = RectF(obstacleBeginX + obstacleLength, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength + 1f, obstacleBeginY)
    val rup = RectF(obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength, obstacleBeginY + obstacleHeigth + 1f)
    val rdown = RectF(obstacleBeginX, obstacleBeginY,
        obstacleBeginX + obstacleLength, obstacleBeginY + 1f)

    val obstaclePaint = Paint()
    lateinit var perso: Personnage
    lateinit var context: Context
    //Il faudrait changer le constructeur de la plateforme pr que ce soit plus logique pour nous
    //Pcq pour l'instant les plateformes c'est des plateformes verticales et pas horizontales
    //+vérifier que ça fonctionne bien par rapport aux cibles
    //Position de la plateforme
    //var x : Float = 0.0f
    //var y : Float = 0.0f

    fun setRect() { //redimensionne l'animation
        r.set(
            obstacleBeginX, obstacleBeginY,
            obstacleBeginX + obstacleLength, obstacleBeginY + obstacleHeigth
        )
    }

    open fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.parseColor("#8FBC8F")
        canvas.drawRect(r, obstaclePaint)
    }
}

