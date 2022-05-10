package com.example.ba2_info.gameclasses.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants

class Peaudebanane : Bonus() {
    private var deformationx : Float = 1f
    private var deformationy : Float = 9f

    override fun agir(perso : Personnage){
        perso.view.timeLeft -= 5.0
    }

    override fun draw(canvas: Canvas?) {
        couleurbonus.color = Color.parseColor("#ffd966")
        canvas?.drawRoundRect(rectbonus, deformationx, deformationy,couleurbonus)
    }
}