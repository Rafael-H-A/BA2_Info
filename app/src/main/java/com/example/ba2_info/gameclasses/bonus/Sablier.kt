package com.example.ba2_info.gameclasses.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class Sablier : Bonus(), Pouf {
    private var deformationx : Float = 9f
    private var deformationy : Float = 0f

    override fun agir(perso : Personnage){
        //time += 5f
        perso.power += 0
    }

    override fun draw(canvas: Canvas?) {
        //On dessine les bonus sur l'écran
        couleurbonus.color = Color.parseColor("#FFF8DA")   // Couleur beige
        canvas?.drawRoundRect(rectbonus, deformationx, deformationy,couleurbonus)
    }
}
