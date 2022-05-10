package com.example.ba2_info.gameclasses

import android.graphics.*
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.abs

class Accessoires(
    private var power: Int, private val endroit: Int = 0, private var xpos: Float,
    private var ypos: Float, var length: Float, private var width: Float
) :
    Pouf {

    private var couleuraccess : Paint = Paint()
    private var rectobjet: RectF = RectF(xpos, ypos,xpos + length,ypos + width)
    private var objetactuel : Accessoires = GameConstants.accessoireA

    override fun draw(canvas: Canvas?) {
        couleuraccess.color = Color.parseColor("#c25135")
        canvas?.drawOval(rectobjet, couleuraccess)
    }

    private fun setRect() {
        rectobjet.set(xpos, ypos, xpos + length, ypos + width)
    }

    fun appear(x : Float, y : Float, l : Float, w : Float) {
        xpos = x
        ypos = y
        length = l
        width = w
        setRect()
    }

    private fun dress(perso: Personnage){
        perso.equipment[endroit] = this
        perso.power += power
    }

    private fun undress(perso : Personnage){
        objetactuel = perso.equipment[endroit]
        perso.power -= objetactuel.power
    }

    fun updateaccessoires (perso: Personnage) {
        if (abs(perso.r.centerX() - rectobjet.centerX()) < (perso.diametre/2 + length/2)
            && abs(perso.r.centerY() - rectobjet.centerY()) < (perso.diametre/2 - width/2)) {
            undress(perso)
            disappear(rectobjet, perso.view.canvas)
            dress(perso)
        }
    }
}