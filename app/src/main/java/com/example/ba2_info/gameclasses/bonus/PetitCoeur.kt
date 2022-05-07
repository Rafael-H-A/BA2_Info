package com.example.ba2_info.gameclasses.bonus
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class PetitCoeur : Bonus(), Pouf {
    private var puissance : Int = 1
    override fun agir(perso : Personnage){
        perso.life += puissance
    }
    override fun draw(canvas: Canvas?) {
        //On dessine les bonus sur l'écran
        couleurbonus.color = Color.parseColor("#FF69B4")  // Couleur rose
        canvas?.drawOval(rectbonus, couleurbonus)
    }
}