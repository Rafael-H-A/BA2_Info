package com.example.ba2_info.gameclasses

import android.graphics.*
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.abs

class Accessoires(var name: String="NoName", var power: Int, val endroit: Int = 0,
                    var xpos : Float, var ypos : Float, var length : Float, var width : Float) :
    Pouf {

    var couleuraccess : Paint = Paint()
    var rectobjet: RectF = RectF(xpos, ypos,xpos + length,ypos + width)
    // mettre la liste de tous les objets


    //var rectobjet : RectF = RectF(xpos,ypos,xpos + length, ypos + width)
    var objetactuel : Accessoires = GameConstants.accessoire1 // A changer attention


    fun draw(canvas: Canvas?) {
        //On dessine le joueur sur l'écran
        couleuraccess.color = Color.parseColor("#c25135")
        canvas?.drawOval(rectobjet, couleuraccess)
    }

    fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        rectobjet.set(xpos, ypos, xpos + length, ypos + width)
    }

    fun dress(perso: Personnage){
        perso.equipment[endroit] = this                   /* Doit changer la puissance du perso */
        perso.power += power                       /* On change dans le dico/ le mec la valeur */

    }/* Problème de type*/
    fun undress(perso : Personnage){
        objetactuel = perso.equipment[endroit]
        perso.power -= objetactuel.power
    }

    override fun disappear(rect: RectF, canvas: Canvas) {
        super.disappear(rect, canvas)
    }

    fun updateaccessoires (perso: Personnage) {
        if (abs(perso.r.centerX() - rectobjet.centerX()) < (perso.diametre/2 + length/2)
            && abs(perso.r.centerY() - rectobjet.centerY()) < (perso.diametre/2 - width/2)) {     // prendre le rectangle de chaque objet, a mettre dans le constructeur ?
            undress(perso)
            disappear(rectobjet, perso.view.canvas)            // utiliser l'interface Pouf
            dress(perso)
        }
    }
}


/* Légende des endroits :
        0 -> Tête
        1 -> Haut
        2 -> Bas
        3 -> Pieds
 ?*/