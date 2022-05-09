package com.example.ba2_info.gameclasses.platforms

import android.graphics.Canvas
import android.graphics.Color

// si on va dans le trou, pas de collision OU le jeu recommence si trou dans
// la plateforme du bas, couleur vert foncé

class Hole (obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
            obstacleHeigth: Float, plain: Boolean = false) :
    Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, plain) {

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        obstaclePaint.color = Color.parseColor("#7fbbf1")
        canvas.drawRect(r, obstaclePaint)
    }
    }
