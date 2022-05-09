package com.example.ba2_info.gameclasses.bonus
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class PetitCoeur () : Bonus(){
    var puissance : Int = 1
    override fun agir(perso : Personnage){
        perso.life += puissance
    }

    override fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        //couleurbonus.color = Color.parseColor("#FF69B4")  // Couleur rose
        //canvas.drawOval(rectbonus, couleurbonus)
        val bitmapbonus : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.coeur)
        canvas?.drawBitmap(bitmapbonus, null, rectbonus, null)
    }

    override fun setRect() {
        //Permet de modifier la taille du carré si la taille de l'écran change
        rectbonus.set(bonusx, bonusy, bonusx + length, bonusy + width)
    }
}