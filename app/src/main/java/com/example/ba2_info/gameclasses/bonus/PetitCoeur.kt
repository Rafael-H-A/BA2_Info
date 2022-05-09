package com.example.ba2_info.gameclasses.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage

class PetitCoeur : Bonus(){
    var puissance : Int = 1

    override fun agir(perso : Personnage){
        perso.life += puissance
    }

    override fun draw(canvas: Canvas?) {
        couleurbonus.color = Color.parseColor("#f4cccc")
        canvas?.drawOval(rectbonus, couleurbonus)
    }
}