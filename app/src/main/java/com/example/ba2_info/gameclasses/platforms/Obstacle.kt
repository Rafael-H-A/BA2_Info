package com.example.ba2_info.gameclasses.platforms

import android.graphics.*
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants

open class Obstacle (var obstacleBeginX: Float, var obstacleBeginY: Float, var obstacleLength: Float, var obstacleHeigth: Float, var plain : Boolean = true) {
    val r = RectF(
        obstacleBeginX, obstacleBeginY + obstacleHeigth,
        obstacleBeginX + obstacleLength, obstacleBeginY
    )
    val obstaclePaint = Paint()
    private val epsilon = 3f
    var knockback = 80f

    fun setRect() { //redimensionne l'animation
        r.set(obstacleBeginX, obstacleBeginY,
            obstacleBeginX + obstacleLength, obstacleBeginY + obstacleHeigth)
    }

    open fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.parseColor("#ffb342")
        canvas.drawRect(r, obstaclePaint)
    }

    fun updateobstacleX(perso: Personnage) {
        if (perso.r.intersects(r.left, r.top + epsilon, r.left, r.bottom-epsilon)     //Si jms on a plusieurs plateformes collées
            && (r.top >= r.top || r.bottom <= r.bottom)) {                            //On veut pas que ça bloque le personnage
            perso.playerMoveRight = false
            if (this is Trap) {
                shortenLife(perso)
                if(perso.life ==0) {perso.view.openFight()}
                perso.r.offset(-knockback, 0f)
                perso.x -= knockback
            }
        }
        else if (perso.r.intersects(r.right, r.top + epsilon, r.right, r.bottom -epsilon)) {
            perso.playerMoveLeft = false
            if (this is Trap) {
                shortenLife(perso)
                if(perso.life ==0) {perso.view.openFight()}
                GameConstants.traphasbeentouched = true
                perso.r.offset(knockback, 0f)
                perso.x += knockback
            }
        }
    }

    fun updateobstacleY(perso: Personnage) {
        if (perso.r.intersects(r.left, r.top, r.right, r.top)) {
            if (this is Trap) {shortenLife(perso)}
            if(perso.life == 0) {perso.view.openFight()}
            perso.playerMoveDown = false
        }
        else if (perso.r.intersects(r.left, r.bottom, r.right, r.bottom)) {
            if (this is Trap) {shortenLife(perso)}
            if(perso.life == 0) {perso.view.openFight()}
            perso.playerMoveUp = false
        }
    }
}

