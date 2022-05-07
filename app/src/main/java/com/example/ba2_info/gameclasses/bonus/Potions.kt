package com.example.ba2_info.gameclasses.bonus
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class Potions constructor(private var puissance : Int) : Bonus(), Pouf {
    private var deformationx : Float = 6f
    private var deformationy : Float = 6f

    override fun agir(perso : Personnage){
        perso.power += puissance
    }

    override fun draw(canvas: Canvas?) {
        //On dessine les bonus sur l'écran
        couleurbonus.color = Color.parseColor("#5D3FD3")   // Couleur purple
        canvas?.drawRoundRect(rectbonus, deformationx, deformationy,couleurbonus)
    }
}

