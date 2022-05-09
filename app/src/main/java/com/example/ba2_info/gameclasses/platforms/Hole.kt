package com.example.ba2_info.gameclasses.platforms

import android.graphics.Canvas
import android.graphics.Color

class Hole (obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
            obstacleHeigth: Float, plain: Boolean = false) :
    Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, plain) {

    override fun draw(canvas: Canvas?) {
        obstaclePaint.color = Color.parseColor("#7fbbf1")
        canvas?.drawRect(r, obstaclePaint)
    }
    }
