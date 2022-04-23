package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Personnage (var name : String, var power : Int, var life : Int=1) {
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
    var dy = 3f
    //Le joueur est-il sur l'écran ?
    var playeronscreen = true
    //Le joueur est-il en train de sauter?
    var isjumping = true
    //Équipement sur le personnage
    var equipment = mutableListOf<String>(" "," "," "," ")


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
        r.offset(dx, 0f)
    }


    fun jump() {
        val timeStep = System.currentTimeMillis()
        while(System.currentTimeMillis() - timeStep < 8000/60) {
            r.offset(0f,dy/2.986f)
        }
        val timeStep2 = System.currentTimeMillis()
        while(System.currentTimeMillis() - timeStep2 < 4000/60) {
            r.offset(0f,0f)
        }
        val timeStep3 = System.currentTimeMillis()
        while(System.currentTimeMillis() - timeStep3 < 8000/60) {
            r.offset(0f,-dy/3)
        }
    }


    fun getDown() {
    }


}