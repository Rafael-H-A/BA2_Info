package com.example.ba2_info.gameclasses.platforms

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants

class Trap(
    private val damage: Int, obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
    obstacleHeigth: Float, plain: Boolean = true)
    : Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, plain)
{

    override fun draw(canvas: Canvas?) {
        obstaclePaint.color = Color.parseColor("#d9cff8")
        canvas?.drawRect(r, obstaclePaint)
    }

    fun shortenLife(perso: Personnage) {
        if (perso.life !=0 && perso.life - damage >= 0) {
            perso.life -= damage
        }
        else if (perso.life - damage < 0) {
            perso.life = 0
        }
        if (perso.life == 0){
            //DEFEAT
            perso.view.drawing = false
            GameConstants.gameOver = true
        }
    }
}