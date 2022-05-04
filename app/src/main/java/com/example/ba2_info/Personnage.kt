package com.example.ba2_info

import android.content.Context
import android.graphics.*
import kotlin.math.*

class Personnage (var view : GameView, var name : String, var power : Int, var life : Int=1,
                  var obstacles : List<Obstacle>) {
    var x : Float = 0f                                                                              //Position du personnage et step de déplacement
    var y : Float = 0f
    var dy = 0f
    val paint = Paint()                                                                             //Infos graphiques du personnage et contexte
    lateinit var context : Context
    var diametre = 50f                                                                              //Représentation du personnage (A changer par après en Bitmap)
    val r = RectF(x, y, x + diametre, y + diametre)
    var playerinlimits = true                                                                       //Le joueur est-il sur l'écran ?
    var playerMoveUp = true                                                                         //Mouvements possibles
    var playerMoveDown = true
    var playerMoveRight = true
    var playerMoveLeft = true
    var equipment = mutableListOf<Accessoires>()                                                    //Équipement sur le personnage

    init {                                                                                          /*QUE SE PASSE-T-IL LORS DE LA CREATION D'UN OBJET PERSONNAGE ?*/
        paint.color = Color.BLACK
    }

    fun draw(canvas: Canvas?) {                                                                     /*DESSIN DU PERSONNAGE SUR LE CANVAS DE LA GAMEVIEW*/
        canvas?.drawOval(r, paint)
    }

    fun setRect() {                                                                                 /*CREATION DU RECTANGLE CONTENANT LE PERSONNAGE LORS D'UN CHANGEMENT DE TAILLE D'ECRAN*/
        r.set(x, y, x + diametre, y + diametre)
    }

    fun update(gauche: Boolean) {                                                                   /*GESTION DE TOUS LES MOUVEMENTS EN X*/
        var dx = 8f
        //Vérification que le personnage ne dépasse pas les limites du niveau
        playerinlimits = if (x < 0 && gauche) {false}
                         else !(x > view.screenWidth - diametre && !gauche)
        //Vérification de la direction du mouvement (gauche ou droite)
        var s = 0f
        blockPersoX()
        //Vérification des conditions de déplacement et déplacement
        if(playerinlimits) {
            when {
                !playerMoveRight -> {
                    s = if (gauche) {-1f} else {0f}
                }
                !playerMoveLeft -> {
                    s = if (gauche) {0f} else {1f}
                }
                else -> {
                    s = if (gauche) {-1f} else {1f}
                }
            }
            println(s)
            val incr = abs(dx)*s
            dx = incr
            x += incr
            r.offset(dx,0f)}
        }

    fun jump() {                                                                                    /*GESTION DE TOUS LES MOUVEMENTS EN Y*/
        loop@for (i in 1..75) {
            val w = i/150f
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f)
            if (blockPersoY() && !playerMoveUp) {dy = 0f}
            r.offset(0f, -dy)
            Thread.sleep(2)
        }
        loop@for (i in 76..200) {
            val w = i / 150f
            dy = 10 * (3 * (w.toDouble().pow(2).toFloat()) - 3 * w + 0.75f)
            if (blockPersoY() && !playerMoveDown) {dy = 0f}
            r.offset(0f, dy)
            Thread.sleep(2)
        }
    }


    fun blockPersoX() : Boolean {                                                                   /*BLOQUE LES MOUVEMENTS EN X SI NECESSAIRE*/
        var res = false
        playerMoveRight = true
        playerMoveLeft = true
        for (ob in obstacles) {
            if (ob.plain) {
                if (r.intersects(ob.r.left, ob.r.top +3f, ob.r.left, ob.r.bottom-3f)
                    && (r.top >= ob.r.top || r.bottom <= ob.r.bottom)) {
                    paint.color = Color.GREEN
                    res = true
                    playerMoveRight = false
                }
                else if (r.intersects(ob.r.right, ob.r.top +3f, ob.r.right, ob.r.bottom -3f)) {
                    paint.color = Color.RED
                    res = true
                    playerMoveLeft = false
                }
            }
        }
        return res
    }

    fun blockPersoY() : Boolean {                                                                   /*BLOQUE LES MOUVEMENTS EN Y SI NECESSAIRE*/
        var res = false
        playerMoveUp = true
        playerMoveDown = true
        for (ob in obstacles) {
            if (ob.plain) {
                if (r.intersects(ob.r.left, ob.r.top, ob.r.right, ob.r.top)) {
                    paint.color = Color.YELLOW
                    res = true
                    playerMoveDown = false
                }
                else if (r.intersects(ob.r.left, ob.r.bottom, ob.r.right, ob.r.bottom)) {
                    paint.color = Color.BLUE
                    res = true
                    playerMoveUp = false
                }
            }
        }
        return res
    }
}