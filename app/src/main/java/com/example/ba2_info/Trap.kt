package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates


//import com.example.ba2_info.Trap as Trap1

// fait perdre une vie au joueur; si life = 0 : game over,
// couleur rose

class Trap(
    private val damage : Int, obstacleBeginX: Float, obstacleBeginY: Float, obstacleLength: Float,
    obstacleHeigth: Float, view: GameView, plain: Boolean = true)
    : Obstacle(obstacleBeginX, obstacleBeginY, obstacleLength, obstacleHeigth, view, plain)
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
        //if (r.intersect(perso.r)) {
        if (lifeCount > 0) {
            lifeCount -= damage
        }
            //if (lifeCount == 0) {
        else{
            perso.paint.color = Color.RED
            System.out.println("SI ON EST MORT " + lifeCount)
            //game over
            }
        }
    }