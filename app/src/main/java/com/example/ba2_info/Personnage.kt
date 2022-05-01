package com.example.ba2_info

import android.content.Context
import android.graphics.*
import kotlin.math.*

class Personnage (var view : GameView,var name : String,var power : Int,var life : Int=1,var obstacles : List<Obstacle>) {
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

    //Le joueur touche-t-il un objet sur un côté, en haut, ou en bas ?
    var playerinobstacle = false
    var playeroverobstacle = false
    var playerunderobstacle = false
    //Équipement sur le personnage
    var equipment = mutableListOf<String>(" "," "," "," ") //Changer en accessoires


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
        playerinlimits = if (x < 0f + diametre && gauche) {false} else !(x > view.screenWidth - diametre && !gauche)
        //Vérification que le personnage n'entre pas dans un obstacle
        for (ob in obstacles) {
            blockPerso(ob)
        }
        // Si le joueur est dans l'écran, dans les limites données et pas dans un obstacle, on bouge
        if (playeronscreen && playerinlimits && !playerinobstacle) {
            val incr = s*(interval * dx)
            //Meme en mettant un offset, pas oublier de changer la coordonnee
            x += incr
            r.offset(incr,0f)
        }
        else {r.offset(0f,0f)}
    }


    fun blockPerso(obstacle: Obstacle) {
        if(obstacle.plain && RectF.intersects(obstacle.r, r)) {
            playerinobstacle = true
            when {
                //On est sous un obstacle
                r.top >= obstacle.r.bottom - 3f
                        && r.top <= obstacle.r.bottom + 3f
                -> {paint.color = Color.YELLOW}
                //On est sur un obstacle
                r.bottom <= obstacle.r.top  + 3f
                        && r.bottom >= obstacle.r.top - 3f
                -> {paint.color = Color.RED}
                //On est à droite d'un obstacle
                r.left <= obstacle.r.right + 6f
                        && r.left >= obstacle.r.right - 6f
                -> {paint.color = Color.GREEN}
                //On est à gauche d'un obstacle
                r.right >= obstacle.r.left - 6f
                        && r.right <= obstacle.r.left + 6f
                -> {paint.color = Color.BLUE}
                else -> {paint.color = Color.MAGENTA}
            }
        }
    }

    fun jump() {
        loop@for (i in 1..75) {
            val w = i/150f
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f)
            if (!playerinobstacle && !playerunderobstacle) {
                r.offset(0f, -dy)
            }
            else {
                break@loop
            }
            for (ob in obstacles) {
                blockPerso(ob)
            }
            Thread.sleep(2)
        }
        loop@for (i in 76..149) {
            val w = i / 150f
            dy = 10 * (3 * (w.toDouble().pow(2).toFloat()) - 3 * w + 0.75f)
            if (!playerinobstacle && !playerunderobstacle) {
                r.offset(0f, dy)
            }
            else {
                break@loop
            }
            for (ob in obstacles) {
                blockPerso(ob)
            }
            Thread.sleep(2)
        }
        playerinobstacle = false
    }


    fun getDown() {
    }


}