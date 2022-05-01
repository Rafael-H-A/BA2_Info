package com.example.ba2_info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule


class Jeu : AppCompatActivity() {
    lateinit var gameView: GameView
    lateinit var porte : Porte
    @SuppressLint("ClickableViewAccessibility")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu)
        gameView = findViewById(R.id.gameView)
        //Toast.makeText(applicationContext,"${gameView.player.name} est prêt pour la bataille !", Toast.LENGTH_SHORT).show()
        val jumpbtn = findViewById<Button>(R.id.jumpbutton)
        jumpbtn.setOnClickListener {gameView.jump()
            //Toast.makeText(applicationContext,"Vers l'infini et l'au-delàààààààà !", Toast.LENGTH_SHORT).show()
            }

        val gauchebtn = findViewById<Button>(R.id.button_gauche)
        val droitebtn = findViewById<Button>(R.id.button_droite)



        //Mode EMULATEUR
        //On force le joueur à avancer pendant un certain nombre de millisecondes
        /*
        gauchebtn.setOnClickListener {
            gameView.gauche = true
            gameView.buttonpressed = true
            Timer().schedule(200){gameView.buttonpressed=false}
        }
        droitebtn.setOnClickListener {
            gameView.gauche = false
            gameView.buttonpressed = true
            Timer().schedule(200){gameView.buttonpressed=false}
        }
         */

        //Mode TELEPHONE
        //On détecte qd on commence à appuyer pour désactiver le dx = 0, puis dès qu'on relève
        //on remet à 0
        gauchebtn.setOnTouchListener{ v, event ->
        gameView.gauche = true
        if (event.action == MotionEvent.ACTION_DOWN) {
            gameView.buttonpressed = true
        } else if (event.action == MotionEvent.ACTION_UP) {
            gameView.buttonpressed = false
        }
        true
        }
        droitebtn.setOnTouchListener{ v, event ->
            gameView.gauche = false
            if (event.action == MotionEvent.ACTION_DOWN) {
                gameView.buttonpressed = true
            } else if (event.action == MotionEvent.ACTION_UP) {
                gameView.buttonpressed = false
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
}