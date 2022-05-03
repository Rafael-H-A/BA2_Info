package com.example.ba2_info

import android.content.Context
import android.graphics.*
import kotlin.math.*

class Personnage (var view : GameView,var name : String,var power : Int,var life : Int=1,var obstacles : List<Obstacle>) {
    //Position du personnage
    var x : Float = 0.0f
    var y : Float = 0.0f
    //Step de déplacement
    var dx = 1f
    var dy = 1f
    //Infos graphiques du personnage et contexte
    val paint = Paint()
    lateinit var context : Context
    //Représentation du personnage dans un carré r
    var diametre = 50f
    val r = RectF(x, y, x + diametre, y + diametre)
    //Le joueur est-il sur l'écran ?
    var playerinlimits = true
    //Le joueur entre-t-il en contact avec un obstacle ?
    var playerinobstaclex = false
    var playerinobstacley = false
    //Quel.s mouvement.s le joueur est-il autorisé à faire ?
    var playermoveLeft = true
    var playermoveRight = true
    var playermoveUp = true
    var playermoveDown = true
    //Équipement sur le personnage
    var equipment = mutableListOf<Accessoires>() //Changer en accessoires


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
        val interval = (1000/60).toFloat()
        var s = 1f
        if (gauche) {s = -1f}

        //Vérification que le personnage ne dépasse pas les limites du niveau
        playerinlimits = if (x < 0f + diametre && gauche) {false} else !(x > view.screenWidth - diametre && !gauche)

        //Vérification que le personnage n'entre pas dans un obstacle
        for (ob in obstacles) {blockPersoX(ob)
        // Si le joueur est dans l'écran, dans les limites données et pas dans un obstacle, on bouge
        if (playerinlimits && !playerinobstaclex) {  // Agit seulement sur le x
            val incr = s*(interval * dx) /obstacles.size
            //Meme en mettant un offset, pas oublier de changer la coordonnee
            x += incr
            r.offset(incr,0f)
        }
        else {r.offset(0f,0f)}
    }}


    fun blockPersoX(obstacle: Obstacle) {
        if(obstacle.plain && RectF.intersects(obstacle.rleft, r)) {
            paint.color = Color.GREEN
            playerinobstaclex = true
        }
        else if(obstacle.plain && RectF.intersects(obstacle.rright, r)) {
            paint.color = Color.RED
            playerinobstaclex = true
        }
    }


    fun blockPersoY(obstacle: Obstacle) {
        if(obstacle.plain && RectF.intersects(obstacle.r,r) && (r.bottom <= obstacle.r.top  + 3f
                    && r.bottom >= obstacle.r.top - 3f)) {
            playerinobstacley = true
            playermoveUp = true
            playermoveDown = false
            paint.color = Color.YELLOW

        }
        else if(obstacle.plain && RectF.intersects(obstacle.r,r) && (r.top >= obstacle.r.bottom -4f && r.top <= obstacle.r.bottom +4f)){
            playerinobstacley = true
            playermoveUp = false
            playermoveDown = true
            paint.color = Color.BLUE
        }
        else {
            playerinobstacley = false
            playermoveUp = true
            playermoveDown = true
            //paint.color = Color.DKGRAY
        }
    }


    fun blockPerso(obstacle: Obstacle) {
        if(obstacle.plain && RectF.intersects(obstacle.r, r)) {
            playerinobstaclex = true
            when {
                //On est sous un obstacle
                r.top >= obstacle.r.bottom - 3f
                        && r.top <= obstacle.r.bottom + 3f
                -> {paint.color = Color.YELLOW
                    playermoveUp = false
                    //playermoveRight = true
                    playermoveDown = true
                    //playermoveLeft = true
                }
                /*
                //On est à gauche d'un obstacle
                r.right >= obstacle.r.left - 6f
                        && r.right <= obstacle.r.left + 6f
                -> {paint.color = Color.BLUE
                    playermoveUp = true
                    playermoveRight = false
                    playermoveDown = true
                    playermoveLeft = true
                } */
                //On est sur un obstacle
                r.bottom <= obstacle.r.top  + 3f
                        && r.bottom >= obstacle.r.top - 3f
                -> {paint.color = Color.RED
                    playermoveUp = true
                    //playermoveRight = true
                    playermoveDown = false
                    //playermoveLeft = true
                }
                /*
                //On est à droite d'un obstacle
                r.left <= obstacle.r.right + 6f
                        && r.left >= obstacle.r.right - 6f
                -> {paint.color = Color.GREEN
                    playermoveUp = true
                    playermoveRight = true
                    playermoveDown = true
                    playermoveLeft = false
                }*/
            }
        }
        /*
        else {
            paint.color = Color.MAGENTA
            playerinobstacle = false
            playermoveUp = true
            playermoveRight = true
            playermoveDown = true
            playermoveLeft = true
            p=0
        }*/
    }

    fun jump() {
        loop@for (i in 1..75) {
            val w = i/150f
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f) / obstacles.size
            for (ob in obstacles) {
                blockPersoY(ob)
                if (!(playerinobstacley) && playermoveUp) {
                    r.offset(0f, -dy)
                }
                else {
                    break@loop
                }
            }

            Thread.sleep(2)
        }
        loop@for (i in 76..170) {
            val w = i / 150f
            dy = 10 * (3 * (w.toDouble().pow(2).toFloat()) - 3 * w + 0.75f) / obstacles.size
            for (ob in obstacles) {
                blockPersoY(ob)
                if (!playerinobstacley && playermoveDown) {
                    r.offset(0f, dy)
                }
                else {
                    break@loop
                }
            }
            Thread.sleep(2)
        }
        playerinobstacley = false
    }


    fun getDown() {
    }


}