package com.example.ba2_info

import android.content.Context
import android.graphics.*
import android.widget.Toast
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.schedule
import kotlin.math.*

class Personnage (var view : GameView, var name : String, var power : Int, var life : Int=1, val obstacle1: Obstacle, val obstacle2 : Obstacle) {
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
    var dx = 1f
    var dy = 1f
    //Le joueur est-il sur l'écran ?
    var playeronscreen = true
    var playerinlimits = true
    var playerinobstacle = false
    //Le joueur est-il en train de sauter?
    var isjumping = true
    //Équipement sur le personnage
    var equipment = mutableListOf<String>(" "," "," "," ") //Changer en accessoires
    lateinit var obstacle : Obstacle

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
        val interval = 1000/60
        var s= 1
        if (gauche) {s = -1}

        //Vérification que le personnage ne dépasse pas les limites du niveau
        if (x < 0f + diametre && gauche) {playerinlimits = false}
        else if (x > view.screenWidth - diametre && !gauche) {playerinlimits = false}
        else {playerinlimits = true}

        //Vérification que le personnage n'entre pas dans un obstacle
        blockPerso(obstacle1)
        blockPerso(obstacle2)

        if (playeronscreen && playerinlimits && !playerinobstacle) {
            val incr = s*(interval * dx)
            //Meme en mettant un offset, pas oublier de changer la coordonnee
            x += incr
            r.offset(incr,0f)
        }
        else {r.offset(0f,0f)}
    }

/*
    fun update_(gauche : Boolean) {
        var s : Int = 1
        if (gauche) {s = -1}

        playerinlimits = !((x < 0f + diametre + dx/6 && gauche) || (x > view.screenWidth - diametre - dx/6 && !gauche))
        if (playeronscreen && playerinlimits) {
            x += s * dx
            //y =  y + s * dy
            paint.color = Color.BLUE
            setRect()
        }
        else {
            paint.color = Color.YELLOW
            x -= s * dx
            //y =  y - s * dy
        }
    }
*/

/*
    fun blockPerso_old(obstacle: Obstacle) {
        when {
            (r.left + diametre/2 < obstacle.r.right && r.bottom > obstacle.r.top && r.top < obstacle.r.bottom) -> {
                playerinobstacle = true
                r.offset(dx, 0f)
            }

            (r.right > obstacle.r.left && r.bottom > obstacle.r.top && r.top < obstacle.r.bottom) -> {
                playerinobstacle = true
                r.offset(-1*dx, 0f)
            }


            (r.bottom < obstacle.r.top && r.left < obstacle.r.right && r.right > obstacle.r.left) -> {
                playerinobstacle = true
                r.set(x,obstacle.r.top,x + diametre, obstacle.r.top + diametre)
            }

            (r.top > obstacle.r.bottom
                    && r.bottom < obstacle.r.top
                    && r.left < obstacle.r.right
                    && r.right > obstacle.r.left) -> {
                playerinobstacle = true
                r.offset(0f,dy)
                playerinobstacle = false
            }
            else -> {
                playerinobstacle = false
            }
        }
    }
 */


    fun blockPerso(obstacle: Obstacle) {
        when {
            //Le perso entre dans l'obstacle en voulant aller vers le bas
            (r.bottom > obstacle.r.top) -> {
                paint.color = Color.RED
                r.set(x,obstacle.obstacleBeginY - diametre, x + diametre, obstacle.obstacleBeginY)}
            //Le perso entre dans l'obstacle en voulant aller à gauche
            (r.left + diametre/2 < obstacle.r.right) -> {
                paint.color = Color.CYAN
                r.set(obstacle.r.left, y, obstacle.r.left + diametre, y + diametre)
            }
            //Le perso entre dans l'obstacle en voulant aller vers le haut
            (r.top < obstacle.r.bottom) -> {
                paint.color = Color.BLUE
            }
            //Le perso entre dans l'obstacle en voulant aller à droite
            (r.right + diametre/2 > obstacle.r.left && r.bottom > obstacle.r.top && r.top < obstacle.r.bottom) -> {
                paint.color = Color.YELLOW
                r.set(obstacle.r.left - diametre, y, obstacle.r.left, y + diametre)
            }
            else -> {}
            }
    }

    fun action() {
    }


    fun move() {
        r.offset(dx, 0f)
    }


    fun jump() {
        for (i in 1..75) {
            val w = i/150f
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f)
            if (!playerinobstacle) {
                r.offset(0f, -dy)
            }
            Thread.sleep(2)
        }
        for (i in 76..149) {
            val w = i/150f
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f)
            if (!playerinobstacle) {
                r.offset(0f, dy)
            }
            Thread.sleep(2)
        }
    }


    fun jump_old() {
        dy = 10f
        if(!playerinobstacle) {

            loop@ for (i in 1..10) {
                if (!playerinobstacle) {
                    r.offset(0f, -dy)
                }
                else {
                    r.offset(0f,i*dy)
                    break@loop
                }
            }

        Timer().schedule(500){
            loop@for (i in 1..10) {
                if (!playerinobstacle) {
                    r.offset(0f, dy)
                }
                else {
                    r.offset(0f,-i*dy)
                    break@loop
                }
            }
        }
        }

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