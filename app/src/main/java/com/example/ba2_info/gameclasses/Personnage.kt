package com.example.ba2_info.gameclasses

import android.content.res.Resources
import android.graphics.*
import androidx.core.content.ContextCompat
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.GameView
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.platforms.Obstacle
import com.example.ba2_info.gameclasses.platforms.Trap
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.*

class Personnage (var view : GameView, var name : String, var power : Int, var life : Int=1,
                  var obstacles : List<Obstacle>, var accessoires : List<Accessoires>, var porte : Porte
) : Pouf {
    var x : Float = 0f                                                                              //Position du personnage et step de déplacement
    var y : Float = 0f
    var dy = 0f
    val paint = Paint()                                                                             //Infos graphiques du personnage et contexte
    var diametre = 50f                                                                              //Représentation du personnage (A changer par après en Bitmap)
    val r = RectF(x, y, x + diametre, y + diametre)
    var playerinlimits = true                                                                       //Le joueur est-il sur l'écran ?
    var playerMoveUp = true                                                                         //Mouvements possibles
    var playerMoveDown = true
    var playerMoveRight = true
    var playerMoveLeft = true
    var equipment = mutableListOf(GameConstants.accessoireA)              //Équipement sur le personnage
    var notOnTrap = false

    init {                                                                                          /*QUE SE PASSE-T-IL LORS DE LA CREATION D'UN OBJET PERSONNAGE ?*/
        paint.color = Color.BLACK
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, paint)
    }

    fun setRect() {                                                                                 /*CREATION DU RECTANGLE CONTENANT LE PERSONNAGE LORS D'UN CHANGEMENT DE TAILLE D'ECRAN*/
        r.set(x, y, x + diametre, y + diametre)
    }

    fun update(gauche: Boolean) {                                                                   /*GESTION DE TOUS LES MOUVEMENTS EN X*/
        val s : Float
        var dx = 10f                                                                                //Vérifier la possibilité de Down pour faire
        //Vérification que le personnage ne dépasse pas les limites du niveau                       //Tomber automatiquement le personnage qd il
        playerinlimits = !((x < 0 && gauche)||x > view.screenWidth - 3/2*diametre && !gauche)
        //Vérification de la direction du mouvement (gauche ou droite)
        blockPersoX()
        if(playerinlimits) {
            s = when {
                !playerMoveRight -> {if (gauche) {-1f} else {0f}}
                !playerMoveLeft -> {if (gauche) {0f} else {1f}}
                else -> {if (gauche) {-1f} else {1f}}
            }
            for (a in accessoires) {a.updateaccessoires(this)}
            porte.updateporte(this)
            for (b in GameConstants.bonuses) {b.updatebonus(this)}
            val incr = abs(dx)*s
            dx = incr
            x += incr
            r.offset(dx,0f)}
    }

    fun jump() {
        /*GESTION DE TOUS LES MOUVEMENTS EN Y*/
        loop@for (i in 1..75) {
            val w = i/150f
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f)
            blockPersoY()
            if (!playerMoveUp) {dy = 0f}
            r.offset(0f, -dy)
            y -= dy
            Thread.sleep(2)
        }
        loop@for (i in 76..200) { //On jump plus bas pour retomber au sol
            val w = i / 150f                                                                        //Si jamais on a un "falling" mis en place
            dy = 10 * (3 * (w.toDouble().pow(2).toFloat()) - 3 * w + 0.75f)                      //on peut peut-être le retirer ?
            blockPersoY()
            if (!playerMoveDown) {dy = 0f}
            r.offset(0f, dy)
            y += dy
            Thread.sleep(2)
        }
        notOnTrap = false
    }

    private fun blockPersoX() {                                                                   /*BLOQUE LES MOUVEMENTS EN X SI NECESSAIRE*/
        playerMoveRight = true
        playerMoveLeft = true
        for (ob in obstacles) {
            if (ob.plain) {ob.updateobstacleX(this)}
        }
    }

    private fun blockPersoY() {                                                                   /*BLOQUE LES MOUVEMENTS EN Y SI NECESSAIRE*/
        playerMoveUp = true
        playerMoveDown = true
        for (ob in obstacles) {
            if (ob.plain) {ob.updateobstacleY(this)}
        }
    }

    fun fall() {
        dy = 5f
        blockPersoY()
        if(playerMoveDown){
            r.offset(0f, dy)
            y += dy}
    }
}