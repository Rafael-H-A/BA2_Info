package com.example.ba2_info.gameclasses.bonus

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
    private var width: Float = 30f
    private var bonus: Int = 0
    val couleurbonus: Paint = Paint()
    var rectbonus: RectF = RectF(bonusx, bonusy, bonusx + length, bonusy + width)

    open fun agir(perso: Personnage) {
        perso.life = bonus
    }

    override fun draw(canvas: Canvas?) {
        couleurbonus.color = Color.TRANSPARENT
        canvas?.drawOval(rectbonus, couleurbonus)
    }

    open fun setRect() {
        rectbonus.set(bonusx, bonusy, bonusx + length, bonusy + width)
    }

    fun updatebonus(perso:Personnage) {
        if (abs(perso.r.centerX() - rectbonus.centerX()) < (perso.diametre/2 + length/2)
            && abs(perso.r.centerY() - rectbonus.centerY()) < (perso.diametre/2 + width/2)) {
            agir(perso)
            disappear(rectbonus, perso.view.canvas)
        }
    }
}