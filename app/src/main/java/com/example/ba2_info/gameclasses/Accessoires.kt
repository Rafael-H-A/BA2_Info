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
    var objetactuel : Accessoires = GameConstants.accessoire1 // A changer attention

    override fun draw(canvas: Canvas?) {
        couleuraccess.color = Color.parseColor("#c25135")
        canvas?.drawOval(rectobjet, couleuraccess)
    }

    fun setRect() {
        rectobjet.set(xpos, ypos, xpos + length, ypos + width)
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