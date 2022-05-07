package com.example.ba2_info

import android.graphics.Canvas
import android.graphics.Color

// fait perdre une vie au joueur; si life = 0 : game over,
// couleur rose

class Trap(
    private val damage: Int, obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
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


    fun shortenLife(perso: Personnage) { // à mettre avec la fonction intersection !
        var lifeCount = perso.life
        println(perso.life)
        //if (r.intersect(perso.r)) {
        if (lifeCount !=0 && lifeCount - damage >= 0) {
            lifeCount -= damage
        }
        else if (lifeCount - damage < 0) {
            println("coucou")
            lifeCount = 0}

        if (lifeCount == 0){
            perso.paint.color = Color.RED
            println("SI ON EST MORT " + lifeCount)
            //game over
            }
        perso.life = lifeCount
        println(perso.life)
        }
    }