package com.example.ba2_info.gameclasses.bonus

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class Sablier : Bonus() {

    override fun agir(perso : Personnage){
        //time += 5f
        perso.power += 0
    }

    override fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        //couleurbonus.color = Color.parseColor("#FFF8DA")   // Couleur beige
        val bitmapbonus : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.sablier)
        canvas?.drawBitmap(bitmapbonus, null, rectbonus, null)
    }
}
