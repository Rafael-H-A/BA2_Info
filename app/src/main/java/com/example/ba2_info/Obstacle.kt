package com.example.ba2_info

import android.content.Context
import android.graphics.*

open class Obstacle (var obstacleBeginX: Float, var obstacleBeginY: Float, var obstacleLength: Float, var obstacleHeigth: Float, var view: GameView, var plain : Boolean = true) {
    val r = RectF(
        obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength, obstacleBeginY
    )
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

    fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.parseColor("#8FBC8F")
        canvas.drawRect(r, obstaclePaint)
    }


    fun blockPerso() {
        when {
            /*
            (perso.r.left + perso.diametre/2 < r.right && perso.r.bottom > r.top && perso.r.top < r.bottom) -> {
                perso.playerinobstacle = true
                perso.r.offset(perso.dx, 0f)
            }

            (perso.r.right > r.left && perso.r.bottom > r.top && perso.r.top < r.bottom) -> {
                perso.playerinobstacle = true
                perso.r.offset(-1 * perso.dx, 0f)
            }
             */

            (perso.r.bottom < r.top && perso.r.left < r.right && perso.r.right > r.left) -> {
                perso.playerinobstacle = true
                perso.r.set(perso.x, r.top, perso.x + perso.diametre, r.top + perso.diametre)
            }

            /*
            (perso.r.top > r.bottom
                    && perso.r.bottom < r.top
                    && perso.r.left < r.right
                    && perso.r.right > r.left) -> {
                perso.playerinobstacle = true
                perso.r.offset(0f, perso.dy)
                perso.playerinobstacle = false
            }
             */
            else -> {
                perso.playerinobstacle = false
            }
        }
    }
}

