package com.example.ba2_info

import android.content.Context
import android.graphics.*
import android.widget.Toast
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.schedule
import kotlin.math.*

class Personnage (var view : GameView, var name : String, var power : Int, var life : Int=1) {
    //Position du personnage
    var x : Float = 0.0f
    var y : Float = 0.0f
    //Infos graphiques du personnage et contexte
    val paint = Paint()
    lateinit var context : Context
    //Représentation du personnage dans un carré r
    var diametre = 50f
    val r = RectF(x, y, x + diametre, y + diametre)
    //Step de déplacement
    var dx = 50f
    var dy = 3f
    //Le joueur est-il sur l'écran ?
    var playeronscreen = true
    var playerinlimits = true
    //Le joueur est-il en train de sauter?
    var isjumping = true
    //Équipement sur le personnage
    var equipment = mutableListOf<String>(" "," "," "," ")
    lateinit var obstacle: Obstacle
    var hauteursaut = 100f

    init {
        paint.color = Color.BLACK
    }

    fun draw(canvas: Canvas?) {
        //On dessine le joueur sur l'écran
        canvas?.drawOval(r, paint)
    }


    fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        r.set(x, y, x + diametre, y + diametre)
    }


    fun update(gauche: Boolean) {
        var s = 1
        if (gauche) {s = -1}

        if (x < 0f + diametre + dx/6) {
            playerinlimits = false
            paint.color = Color.BLUE
        }
        else if (x > view.screenWidth - diametre - dx/6) {
            playerinlimits = false
            paint.color = Color.BLUE
        }
        else {playerinlimits = true
            paint.color = Color.YELLOW
        }

        if (playeronscreen && playerinlimits) {
            r.offset(s*dx, 0f)
            x += s*dx                               //Meme en mettant un offset, pas oublier de changer la coordonnee
        }
        else {
            r.offset(0f,0f)

        }
    }


    fun update_(gauche : Boolean) {
        var s : Int = 1
        if (gauche) {s = -1}

        playerinlimits = !((x < 0f + diametre + dx/6 && gauche) || (x > view.screenWidth - diametre - dx/6 && !gauche))
        if (playeronscreen && playerinlimits) {
            x = x + s * dx
            //y =  y + s * dy
            paint.color = Color.BLUE
            setRect()
        }
        else {
            paint.color = Color.YELLOW
            x = x - s * dx
            //y =  y - s * dy
        }
    }


    fun action() {
    }


    fun move() {
        r.offset(dx, 0f)
    }


    fun jump() {
        dy = 100f
        Timer().schedule(500){
        r.offset(0f,dy)}
        r.offset(0f,-dy)

        /*
        for (i in 0..25) {
                y = (y - hauteursaut * i / 50)
            Timer().schedule(300) {setRect()}
        }
        Timer().schedule(300) {
            for (i in 0..25) {
                    y = (y + hauteursaut * i / 50)
                    setRect()
            }
        }
         */
    }

    fun getDown() {
    }


}