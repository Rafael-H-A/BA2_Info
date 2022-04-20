package com.example.ba2_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class Jeu : AppCompatActivity() {
    lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeu)
        gameView = findViewById(R.id.gameView)
        Toast.makeText(applicationContext,"${gameView.player.name} est prêt pour la bataille !", Toast.LENGTH_SHORT).show()
    }

    private fun hideSystemBars() {
        val windowInsetsController: WindowInsetsControllerCompat =
            ViewCompat.getWindowInsetsController(
                window.decorView
            ) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        )
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
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