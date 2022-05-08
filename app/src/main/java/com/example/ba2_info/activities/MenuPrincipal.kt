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
        val buttonInventory = findViewById<Button>(R.id.button_inventory)

        buttonBeginPlay.setOnClickListener {beginLevel1()}
        buttonRules.setOnClickListener {openRules()}
        buttonInventory.setOnClickListener {openInventory()}
    }

    private fun beginLevel1() {
        val switchActivityIntent= Intent(this, Jeu::class.java)
        startActivity(switchActivityIntent)}

    private fun openInventory() {
        val switchActivityIntent= Intent(this, Inventory::class.java)
        startActivity(switchActivityIntent)}

    private fun openRules() {
        val switchActivityIntent= Intent(this, Rules::class.java)
        startActivity(switchActivityIntent)}

}