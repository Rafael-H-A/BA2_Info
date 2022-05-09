package com.example.ba2_info.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ba2_info.R

class MenuPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        val buttonBeginPlay = findViewById<Button>(R.id.button_begin_game)
        val buttonRules = findViewById<Button>(R.id.button_rules)
        val buttonStory = findViewById<Button>(R.id.button_story)

        buttonBeginPlay.setOnClickListener {beginPlay()}
        buttonRules.setOnClickListener {openRules()}
        buttonStory.setOnClickListener {openStory()}
    }

    private fun beginPlay() {
        val switchActivityIntent= Intent(this, Jeu::class.java)
        startActivity(switchActivityIntent)}

    private fun openStory() {
        val switchActivityIntent= Intent(this, Story::class.java)
        startActivity(switchActivityIntent)}

    private fun openRules() {
        val switchActivityIntent= Intent(this, Rules::class.java)
        startActivity(switchActivityIntent)}

}