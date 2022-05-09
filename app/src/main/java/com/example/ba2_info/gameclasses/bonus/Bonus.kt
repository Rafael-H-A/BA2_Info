package com.example.ba2_info.gameclasses.bonus

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

abstract class Bonus : Pouf {
    var bonusx: Float = 12f
    var bonusy: Float = 12f
    var length: Float = 12f
    var width: Float = 12f
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