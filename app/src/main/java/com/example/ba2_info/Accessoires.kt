package com.example.ba2_info

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import java.lang.NullPointerException

class Accessoires(var name: String="NoName", var power: Int=0, val endroit: Int = 0, var rectobjet: RectF = RectF(100f,100f,200f,200f)) : Pouf {
    var xpos : Float = 50f
    var ypos : Float = 50f
    var length : Float = 10f
    var width : Float = 10f
    var couleuracess : Paint = Paint(Color.RED)
    var listeaccess = listOf<Accessoires>(
        GameConstants.accessoire1, GameConstants.accessoire2, GameConstants.accessoire3,
        GameConstants.accessoire4, GameConstants.accessoire5, GameConstants.accessoire6,
        GameConstants.accessoire7, GameConstants.accessoire8, GameConstants.accessoire9,
        GameConstants.accessoire10, GameConstants.accessoire11, GameConstants.accessoire12)     // mettre la liste de tous les objets

    //var rectobjet : RectF = RectF(xpos,ypos,xpos + length, ypos + width)
    lateinit var objetactuel : Accessoires

    fun draw(canvas: Canvas?) {
        //On dessine le joueur sur l'écran
        canvas?.drawOval(rectobjet, couleuracess)
    }

    fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        rectobjet.set(xpos, ypos, xpos + length, ypos + width)
    }

    fun dress(perso: Personnage){
        perso.equipment[endroit] = listeaccess[listeaccess.indexOf(this)]                   /* Doit changer la puissance du perso */
        perso.power += power                       /* On change dans le dico/ le mec la valeur */


    }/* Problème de type*/
    fun undress(perso : Personnage){
        perso.equipment[endroit] = listeaccess[listeaccess.indexOf(objetactuel)]
        perso.power -= power
    }

    override fun disappear(rect: RectF, sprite: BitmapDrawable, canvas: Canvas, paint: Paint) {
        //super.disappear(rect, sprite, canvas, paint)
        rect.set(-6f,-10f,-4f,-8f)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY)
    }


    fun updateaccessoires (perso: Personnage, listeaccess: List<Accessoires>) {
        for (i in 1..(listeaccess.size)) { // la len de la liste d'objets pour parourir toute la liste
            if (perso.r.intersect(listeaccess.get(i).rectobjet)) {     // prendre le rectangle de chaque objet, a mettre dans le constructeur ?
                undress(perso)
                dress(perso)
                //listeaccess.get(i).rectobjet.disappear()            // utiliser l'interface Pouf
            }
        }
    }
}



/* Légende des endroits :
        0 -> Tête
        1 -> Haut
        2 -> Bas
        3 -> Pieds
 ?*/