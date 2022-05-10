package com.example.ba2_info.gameclasses.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage

class Sablier : Bonus() {

    override fun agir(perso : Personnage){
        perso.view.timeLeft += 5.0

    }

    override fun draw(canvas: Canvas?) {
        couleurbonus.color = Color.parseColor("#ce7e00")
        canvas?.drawOval(rectbonus, couleurbonus)
    }
}
