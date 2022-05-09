package com.example.ba2_info.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ba2_info.GameView
import com.example.ba2_info.R
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.gameutilities.TimerClass

/*
1. Problème avec les pièges :
    - stocker tous les "tops" des pièges pr dire que tant qu'on est sur un piège, alors on ne re diminue pas la vie du joueur
    - essayer de rendre le offset sur le côté plus smooth
    - détecter le piège d'en bas
2. Conditions de victoire :
    - ouvrir une fenêtre pop-up quand on passe la porte (défaite/victoire)
    - vérifier le power du joueur
    - mettre toutes les conditions de défaite possible (plus de vie, plus de temps ou pas assez de pouvoir)
3. Ajouter les bonus :
    - ajouter un bonus de chaque type dans le niveau
    - vérifier les effets de chaque bonus
4. Ajouter les messages liés à chaque évènement dans le jeu
    - Récolte d'accessoire
    - Récolte de bonus
    - Vie retirée par un piège
    - Passage dans un trou
5. Ajouter la storyline en nouvelle activity
6. Changer les règles du jeu
  */
class Jeu : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var timerClass: TimerClass
    @SuppressLint("ClickableViewAccessibility")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu)
        gameView = findViewById(R.id.gameView)
        timerClass = TimerClass(GameConstants.timeLeft, gameView)

        val builder = AlertDialog.Builder(this, R.style.BA2_Info_Alerts)

        val jumpbtn = findViewById<Button>(R.id.jumpbutton)
        val gauchebtn = findViewById<Button>(R.id.button_gauche)
        val droitebtn = findViewById<Button>(R.id.button_droite)
        val pausebtn = findViewById<ImageButton>(R.id.pausebutton)

        jumpbtn.setOnClickListener {gameView.jump()}
        pausebtn.setOnClickListener {
            gameView.pause()
            timerClass.pause()
            builder.setTitle("PAUSE")
                .setCancelable(false)
                .setPositiveButton("Menu Principal") { _, _ -> finish()}
                .setNegativeButton("Reprendre") { dialogInterface, _ -> dialogInterface.cancel(); gameView.resume(); timerClass.resume()  }
                .show()
        }
        //Mode TELEPHONE
        gauchebtn.setOnTouchListener{ _, event -> gameView.gauche = true
            if      (event.action == MotionEvent.ACTION_DOWN)   {gameView.buttonpressed = true}
            else if (event.action == MotionEvent.ACTION_UP)     {gameView.buttonpressed = false}
            true
            }
        droitebtn.setOnTouchListener{ _, event -> gameView.gauche = false
            if (event.action == MotionEvent.ACTION_DOWN)        {gameView.buttonpressed = true}
            else if (event.action == MotionEvent.ACTION_UP)     {gameView.buttonpressed = false}
            true
        }
        /* //Mode EMULATEUR
        gauchebtn.setOnClickListener {
            gameView.gauche = true
            gameView.buttonpressed = true
            Timer().schedule(200){gameView.buttonpressed=false}
        }
        droitebtn.setOnClickListener {
            gameView.gauche = false
            gameView.buttonpressed = true
            Timer().schedule(200){gameView.buttonpressed=false}
        } */
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
        timerClass.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
        timerClass.resume()
    }
}