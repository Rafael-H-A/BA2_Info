package com.example.ba2_info.gameclasses.bonus

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.ba2_info.GameView
import com.example.ba2_info.R
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.Pouf
import com.example.ba2_info.gameutilities.TimerClass

class Peaudebanane(var view : GameView) : Bonus() {
    private var deformationx : Float = 1f
    private var deformationy : Float = 9f

    override fun agir(perso : Personnage){
        view.timeLeft -= 5.0
        GameConstants.timeLeft -= 5.0
    }

    override fun draw(canvas: Canvas?, resources: Resources) {
        //On dessine les bonus sur l'écran
        couleurbonus.color = Color.parseColor("#ffd966")
        canvas?.drawRoundRect(rectbonus, deformationx, deformationy,couleurbonus)
        //val bitmapbonus : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.peaudebanane)
        //canvas?.drawBitmap(bitmapbonus, null, rectbonus, null)
    }
}