package com.example.ba2_info

import android.graphics.RectF
import com.example.ba2_info.Trap as Trap1

class Trap(damage : Int, obstacleDistance: Float, obstacleDebut: Float, obstacleFin: Float,
           initialObstacleVitesse: Float, width: Float, view: GameView, plain: Boolean)
    : Obstacle(obstacleDistance, obstacleDebut, obstacleFin, initialObstacleVitesse, width, view, plain)
{
    fun shortenLife(perso: Personnage) {
        if (RectF.intersects(this.r, perso.r)) {
            perso.life -= 1
        }
    }
}