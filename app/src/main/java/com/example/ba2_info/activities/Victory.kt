package com.example.ba2_info.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ba2_info.R
import com.example.ba2_info.gameutilities.GameConstants

class Victory : AppCompatActivity() {
    private lateinit var victorylayout : LinearLayout
    private lateinit var defeatlayout : LinearLayout
    private lateinit var reasontext : TextView
    private lateinit var victorybtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_victory)
        victorylayout = findViewById(R.id.linearlayoutvictory)
        defeatlayout = findViewById(R.id.linearlayoutdefeat)
        reasontext = findViewById(R.id.reasontext)
        victorybtn = findViewById(R.id.menuprincipal)

        if(GameConstants.gameOver) {
            reasontext.text = GameConstants.message
            victorylayout.visibility = View.GONE
            defeatlayout.visibility = View.VISIBLE
        }
        else {
            victorylayout.visibility = View.VISIBLE
            defeatlayout.visibility = View.GONE
        }
        victorybtn.setOnClickListener {
            onDestroy()
            startActivity(Intent(applicationContext, MenuPrincipal::class.java))
        }
    }
}