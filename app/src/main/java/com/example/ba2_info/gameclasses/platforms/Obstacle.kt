package com.example.ba2_info.gameclasses.platforms

import android.graphics.*
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf

open class Obstacle (var obstacleBeginX: Float, var obstacleBeginY: Float,
                     var obstacleLength: Float, var obstacleHeigth: Float,
                     var plain : Boolean = true) :
    Pouf {
    val r = RectF(
        obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength, obstacleBeginY
    )
            val obstaclePaint = Paint()
    private val epsilon = 12f
    private var knockback = 80f
    private var yknockback = 10f

    fun appear(x : Float, y : Float, length : Float, height : Float) {
        obstacleBeginX = x
        obstacleBeginY = y
        obstacleLength = length
        obstacleHeigth = height
        r.set(obstacleBeginX, obstacleBeginY,
            obstacleBeginX + obstacleLength, obstacleBeginY + obstacleHeigth)
    }

    override fun draw(canvas: Canvas?) {
        obstaclePaint.color = Color.parseColor("#ffb342")
        canvas?.drawRect(r, obstaclePaint)
    }

    fun updateobstacleX(perso: Personnage) {
        //Taking the intersection between Personnage and Obstacle, with a margin of epsilon
        //(To make sure the x movement is smooth when to Obstacles are side to side)
        if (perso.r.intersects(r.left, r.top + epsilon, r.left, r.bottom-epsilon)
            && (r.top >= r.top || r.bottom <= r.bottom)) {
            perso.playerMoveRight = false
            if (this is Trap) {
                shortenLife(perso)
                if(perso.life ==0) {perso.view.drawing = false; GameConstants.gameOver = true}
                //When the player touches an obstacle, it hurts and there is a knockback effect
                perso.r.offset(-knockback, 0f)
                perso.x -= knockback
                perso.playerMoveRight = true
            }
        }
        else if (perso.r.intersects(r.right, r.top + epsilon, r.right, r.bottom -epsilon)) {
            perso.playerMoveLeft = false
            if (this is Trap) {
                shortenLife(perso)
                if(perso.life ==0) {perso.view.drawing = false; GameConstants.gameOver = true}
                //When the player touches an obstacle, it hurts and there is a knockback effect
                perso.r.offset(knockback, 0f)
                perso.x += knockback
                perso.playerMoveLeft = true
            }
        }
    }

    fun updateobstacleY(perso: Personnage) {
        if (perso.r.intersects(r.left, r.top, r.right, r.top)) {
            perso.playerMoveDown = false
            if (this is Trap) {
                shortenLife(perso)
                if(perso.life == 0) {perso.view.drawing = false; GameConstants.gameOver = true}
                perso.r.offset(0f, -yknockback)
                perso.y -= yknockback
                perso.playerMoveDown = true
            }
        }
        else if (perso.r.intersects(r.left, r.bottom, r.right, r.bottom)) {
            perso.playerMoveUp = false
            if (this is Trap) {
                shortenLife(perso)
                if(perso.life == 0) {perso.view.drawing = false; GameConstants.gameOver = true}
                perso.r.offset(0f, yknockback)
                perso.y += yknockback
                perso.playerMoveUp = true
            }
        }
    }
}

