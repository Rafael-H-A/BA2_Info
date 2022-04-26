package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

open class Obstacle (var obstacleDistance: Float, var obstacleDebut: Float, var obstacleFin: Float, var initialObstacleVitesse: Float, var width: Float, var view: GameView, var plain : Boolean = true) {
    val r = RectF(
        obstacleDistance, obstacleDebut,
        obstacleDistance + width, obstacleFin
    )
    val obstaclePaint = Paint()
    lateinit var context: Context
    //Il faudrait changer le constructeur de la plateforme pr que ce soit plus logique pour nous
    //Pcq pour l'instant les plateformes c'est des plateformes verticales et pas horizontales
    //+vérifier que ça fonctionne bien par rapport aux cibles
    //Position de la plateforme
    //var x : Float = 0.0f
    //var y : Float = 0.0f

    fun setRect() { //redimensionne l'animation
        r.set(
            obstacleDistance, obstacleDebut,
            obstacleDistance + width, obstacleFin
        )
    }

    fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.GREEN
        canvas.drawRect(r, obstaclePaint)
    }

    fun blockPerso(perso: Personnage) {
        if (RectF.intersects(r, perso.r) && plain) {
                perso.dy= 0f
            }
            // autre cas ? else if () {}
        }
    }


