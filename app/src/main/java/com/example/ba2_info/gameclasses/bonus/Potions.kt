package com.example.ba2_info.gameclasses.bonus
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class Potions constructor(private var puissance : Int) : Bonus() {

    override fun agir(perso : Personnage){
        perso.power += puissance
    }

    override fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        //val bitmapbonus : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.potion)
        //canvas?.drawBitmap(bitmapbonus, null, rectbonus, null)
        couleurbonus.color = Color.parseColor("#825fe8")
        canvas?.drawOval(rectbonus, couleurbonus)
    }
}

