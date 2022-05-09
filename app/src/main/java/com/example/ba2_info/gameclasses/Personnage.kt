package com.example.ba2_info.gameclasses

import android.content.res.Resources
import android.graphics.*
import androidx.core.content.ContextCompat
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.GameView
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.platforms.Obstacle
import com.example.ba2_info.gameclasses.platforms.Trap
import kotlin.math.*

class Personnage (var view : GameView, var name : String, var power : Int, var life : Int=1,
                  var obstacles : List<Obstacle>, var accessoires : List<Accessoires>, var porte : Porte
) {
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
    var equipment = mutableListOf<Accessoires>(
        GameConstants.accessoireC,
        GameConstants.accessoireB, GameConstants.accessoireA, GameConstants.accessoireD
    )              //Équipement sur le personnage
    val epsilon = 3f
    var notOnTrap = false
    var knockback = 80f

    init {                                                                                          /*QUE SE PASSE-T-IL LORS DE LA CREATION D'UN OBJET PERSONNAGE ?*/
        paint.color = Color.BLACK
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, paint)
        //val bitmapaccess : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.smilingsmiley)
        //canvas?.drawBitmap(bitmapaccess, null, r, null)
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
            updateaccessoires()
            updateporte()
            updatebonus()
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
        loop@for (i in 76..200) {                                                                   //On jump plus bas pour retomber au sol
            val w = i / 150f                                                                        //Si jamais on a un "falling" mis en place
            dy = 10 * (3 * (w.toDouble().pow(2).toFloat()) - 3 * w + 0.75f)                      //on peut peut-être le retirer ?
            if (blockPersoY() && !playerMoveDown) {dy = 0f}
            r.offset(0f, dy)
            Thread.sleep(2)
        }
        notOnTrap = false
    }

    private fun blockPersoX() {                                                                   /*BLOQUE LES MOUVEMENTS EN X SI NECESSAIRE*/
        playerMoveRight = true
        playerMoveLeft = true
        for (ob in obstacles) {
            if (ob.plain) {
                if (r.intersects(ob.r.left, ob.r.top + epsilon, ob.r.left, ob.r.bottom-epsilon)     //Si jms on a plusieurs plateformes collées
                    && (r.top >= ob.r.top || r.bottom <= ob.r.bottom)) {                            //On veut pas que ça bloque le personnage
                    playerMoveRight = false
                    if (ob is Trap) {
                        life = ob.shortenLife(this)
                        if(life ==0) {view.openFight()}
                        r.offset(-knockback, 0f)
                        x -= knockback
                    }
                }
                else if (r.intersects(ob.r.right, ob.r.top + epsilon, ob.r.right, ob.r.bottom -epsilon)) {
                    playerMoveLeft = false
                    if (ob is Trap) {
                        life = ob.shortenLife(this)
                        if(life ==0) {view.openFight()}
                        ob.traphasbeentouched = true
                        r.offset(knockback, 0f)
                        x += knockback
                    }
                }
            }
        }
    }

    private fun blockPersoY() : Boolean {                                                                   /*BLOQUE LES MOUVEMENTS EN Y SI NECESSAIRE*/
        var res = false
        playerMoveUp = true
        playerMoveDown = true
        for (ob in obstacles) {
            if (ob.plain) {
                if (r.intersects(ob.r.left, ob.r.top, ob.r.right, ob.r.top)) {
                    res = true
                    playerMoveDown = false
                    if (ob is Trap) {ob.shortenLife(this)}
                }
                else if (r.intersects(ob.r.left, ob.r.bottom, ob.r.right, ob.r.bottom)) {
                    res = true
                    playerMoveUp = false
                    if (ob is Trap && !notOnTrap) {ob.shortenLife(this)}
                }
            }
        }
        return res
    }

    private fun updateaccessoires () {
        for (i in 1 until (accessoires.size)) { // la len de la liste d'objets pour parourir toute la liste
            if (abs(r.centerX() - accessoires[i].rectobjet.centerX()) < (diametre/2 + accessoires[i].length/2)
                && abs(r.centerY() - accessoires[i].rectobjet.centerY()) < (diametre/2 - accessoires[i].width/2)) {     // prendre le rectangle de chaque objet, a mettre dans le constructeur ?
                accessoires[i].undress(this)
                accessoires[i].disappear(accessoires[i].rectobjet, view.canvas)            // utiliser l'interface Pouf
                accessoires[i].dress(this)
            }
        }
    }

    private fun updateporte() {
        if (abs(r.centerX() - porte.r.centerX()) < (diametre/2 + porte.length/2)
            && abs(r.centerY() - porte.r.centerY()) < (diametre/2 + porte.height/2)) {
            GameConstants.gameOver = power <= GameConstants.enemyPower
            view.openFight()
        }
    }

    private fun updatebonus() {
        for (i in 0 until (GameConstants.bonuses.size)) { // la len de la liste d'objets pour parourir toute la liste
            println("écart en y : " + abs(r.centerY() - GameConstants.bonuses[i].rectbonus.centerY()).toString())
            println("condition y : " + (diametre/2 + GameConstants.bonuses[i].width/2).toString())
            if (abs(r.centerX() - GameConstants.bonuses[i].rectbonus.centerX()) < (diametre/2 + GameConstants.bonuses[i].length/2)
                && abs(r.centerY() - GameConstants.bonuses[i].rectbonus.centerY()) < (diametre/2 + GameConstants.bonuses[i].width/2)) {     // prendre le rectangle de chaque objet, a mettre dans le constructeur ?
                println("Je suis dans le if")
                GameConstants.bonuses[i].agir(this)
                GameConstants.bonuses[i].disappear(GameConstants.bonuses[i].rectbonus, view.canvas)          // utiliser l'interface Pouf
            }
        }
    }

    fun fall() {
        y = 5f
        blockPersoY()
        if(playerMoveDown){r.offset(0f, y)}
    }
}