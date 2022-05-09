package com.example.ba2_info.gameclasses.platforms

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants

// fait perdre une vie au joueur; si life = 0 : game over,
// couleur rose

class Trap(
    val damage: Int, obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
    obstacleHeigth: Float, plain: Boolean = true)
    : Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, plain)
{

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        obstaclePaint.color = Color.parseColor("#d9cff8")
        canvas.drawRect(r, obstaclePaint)
    }

    /*override fun draw(canvas: Canvas) {
        //for (i in 1..3) {
         //   if (i==2){
            obstaclePaint.color = Color.parseColor("#FF8CA7")
            canvas.drawRect(r, obstaclePaint)
    }*/
    fun shortenLife(perso: Personnage) {
        if (perso.life !=0 && perso.life - damage >= 0) {
            perso.life -= damage
            GameConstants.traphasbeentouched = true
        }
        else if (perso.life - damage < 0) {
            perso.life = 0
        }
        if (perso.life == 0){
            GameConstants.gameOver = true
        }
    }
}