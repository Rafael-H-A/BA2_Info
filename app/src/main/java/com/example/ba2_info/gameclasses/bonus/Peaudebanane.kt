package com.example.ba2_info.gameclasses.bonus

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.Pouf

class Peaudebanane : Bonus() {
    private var deformationx : Float = 1f
    private var deformationy : Float = 9f

    override fun agir(perso : Personnage){
        //time -= 5f
        perso.power += 0   // à enlever mais c'est juste pour éviter les erreurs
    }
    override fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        couleurbonus.color = Color.YELLOW
        canvas?.drawRoundRect(rectbonus, deformationx, deformationy,couleurbonus)
        val bitmapbonus : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.peaudebanane)
        canvas?.drawBitmap(bitmapbonus, null, rectbonus, null)
    }
}