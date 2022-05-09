package com.example.ba2_info.gameclasses.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage

class Potions constructor(private var puissance : Int) : Bonus() {

    override fun agir(perso : Personnage){
        perso.power += puissance
    }

    override fun draw(canvas: Canvas?) {
        couleurbonus.color = Color.parseColor("#825fe8")
        canvas?.drawOval(rectbonus, couleurbonus)
    }
}

