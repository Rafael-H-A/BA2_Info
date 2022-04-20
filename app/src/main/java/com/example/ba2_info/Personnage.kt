package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Personnage (var name : String, power : Int, life : Int=1) {

    //Position du personnage
    var x : Float = 0.0f
    var y : Float = 0.0f
    //Infos graphiques du personnage et contexte
    val paint = Paint()
    lateinit var context : Context
    //Représentation du personnage dans un carré r
    var diametre = 50f
    val r = RectF(x, y, x + diametre, y + diametre)
    val personnage = r
    //Vitesse de déplacement
    var dx = 1f
    var dy = 0f
    //Le joueur est-il sur l'écran ?
    var playeronscreen = true


    fun draw(canvas: Canvas?) {
        //On dessine le joueur sur l'écran
        paint.color = Color.BLACK
        canvas?.drawOval(r, paint)
    }


    fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        personnage.set(x, y, x + diametre, y + diametre)
    }


    fun update(interval: Double) {
        if (playeronscreen) {
            x += (interval * dx).toFloat()
            y += (interval * dy).toFloat()
        }
    }


    fun action() {
    }


    fun move() {
        r.offset(dx, dy)
    }


    fun jump() {
    }


    fun getDown() {
    }


}