package com.example.ba2_info.gameclasses.bonus

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.ba2_info.GameView
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf
import com.example.ba2_info.gameutilities.TimerClass
import kotlin.concurrent.timer

class Sablier(var view : GameView) : Bonus() {

    override fun agir(perso : Personnage){
        view.timeLeft -= 5.0
        GameConstants.timeLeft += 5.0
    }

    override fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        //couleurbonus.color = Color.parseColor("#FFF8DA")   // Couleur beige
        //val bitmapbonus : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.sablier)
        //canvas?.drawBitmap(bitmapbonus, null, rectbonus, null)
        couleurbonus.color = Color.parseColor("#ce7e00")
        canvas?.drawOval(rectbonus, couleurbonus)
    }
}
