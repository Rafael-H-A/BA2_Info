package com.example.ba2_info.gameclasses.bonus

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.abs


abstract class Bonus : Pouf {
    var bonusx: Float = 30f
    var bonusy: Float = 30f
    var length: Float = 30f
    var width: Float = 30f
    private var bonus: Int = 0
    val couleurbonus: Paint = Paint()
    var rectbonus: RectF = RectF(bonusx, bonusy, bonusx + length, bonusy + width)

    open fun agir(perso: Personnage) {
        perso.life = bonus
    }

    open fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        couleurbonus.color = Color.TRANSPARENT
        canvas?.drawOval(rectbonus, couleurbonus)
    }

    open fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        rectbonus.set(bonusx, bonusy, bonusx + length, bonusy + width)
    }

    override fun disappear(rect: RectF, canvas: Canvas) {
        super.disappear(rect, canvas)
    }

    fun updatebonus(perso:Personnage) {
        if (abs(perso.r.centerX() - rectbonus.centerX()) < (perso.diametre/2 + length/2)
            && abs(perso.r.centerY() - rectbonus.centerY()) < (perso.diametre/2 + width/2)) {     // prendre le rectangle de chaque objet, a mettre dans le constructeur ?
            agir(perso)
            disappear(rectbonus, perso.view.canvas)          // utiliser l'interface Pouf
        }
    }
}


/*
when {
    bonus == 1 ->
    { perso.life += 1
        couleur.color.blue}      /*  Petit coeur */
    bonus == 2 -> { perso.power += 1}     /* Potion */

    bonus == 3 -> { perso.power -= 1}    /*
                bonus == 4 -> { perso.power += 2}    /* Super potion */


 */