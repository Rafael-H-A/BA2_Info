package com.example.ba2_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Jeu : AppCompatActivity() {
    lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu)
        gameView = findViewById(R.id.gameView)
        Toast.makeText(applicationContext,"${gameView.player.name} est prêt pour la bataille !", Toast.LENGTH_SHORT).show()
        val jumpbtn = findViewById<Button>(R.id.jumpbutton)
        jumpbtn.setOnClickListener {gameView.jump()
            Toast.makeText(applicationContext,"Vers l'infini et l'au-delàààààààà !", Toast.LENGTH_SHORT).show()}
        val gauchebtn = findViewById<Button>(R.id.button_gauche)
        gauchebtn.setOnClickListener {gameView.updatePositions(gauche = true)}
        val droitebtn = findViewById<Button>(R.id.button_droite)
        droitebtn.setOnClickListener {gameView.updatePositions(gauche= false)}
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