package com.example.ba2_info.gameclasses.platforms

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage

// fait perdre une vie au joueur; si life = 0 : game over,
// couleur rose

class Trap(
    val damage: Int, obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
    obstacleHeigth: Float,
    var traphasbeentouched: Boolean = false, plain: Boolean = true)
    : Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, plain)
{

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        obstaclePaint.color = Color.parseColor("#FF8CA7")
        canvas.drawRect(r, obstaclePaint)
    }

    /*override fun draw(canvas: Canvas) {
        //for (i in 1..3) {
         //   if (i==2){
            obstaclePaint.color = Color.parseColor("#FF8CA7")
            canvas.drawRect(r, obstaclePaint)
    }*/
    fun shortenLife(perso: Personnage) : Int { // à mettre avec la fonction intersection ! ///// : Boolean
        var lifeCount = perso.life
        if (lifeCount !=0 && lifeCount - damage >= 0 ) { //!dejaAffaibli
            lifeCount -= damage
            println("touché -1")
        }
        else if (lifeCount - damage < 0) {
            lifeCount = 0
            println("t'es mort")
        }

        if (lifeCount == 0){
            println("SI ON EST MORT " + lifeCount)
            //game over
        }
        println(lifeCount)
        //return dejaAffaibli
        return lifeCount
    }
    }