package com.example.ba2_info

import android.graphics.RectF
import com.example.ba2_info.Trap as Trap1

class Trap(damage : Int, obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
           obstacleHeigth: Float, view: GameView, plain: Boolean)
    : Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, view, plain)
{
    fun shortenLife(perso: Personnage) {
        if (RectF.intersects(this.r, perso.r)) {
            perso.life -= 1
        }
    }
}