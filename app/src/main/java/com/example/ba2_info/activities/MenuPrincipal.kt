package com.example.ba2_info.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ba2_info.R

class MenuPrincipal : AppCompatActivity() {                 /* Mettre toutes les activités */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        //Raf: j'ai mis les variables des boutons dans la fonction onCreate sinon l'appli se lançait pas
        val button_begin_play=findViewById<Button>(R.id.button_begin_game)
        val button_rules=findViewById<Button>(R.id.button_rules)
        val button_inventory=findViewById<Button>(R.id.button_inventory)

        button_begin_play.setOnClickListener {begin_level1()}
        button_rules.setOnClickListener {open_rules()}
        button_inventory.setOnClickListener {open_inventory()}
    }

    fun begin_level1() {
        val switch_activity_intent= Intent(this, Jeu::class.java)
        startActivity(switch_activity_intent)}

    fun open_inventory() {
        val switch_activity_intent= Intent(this, Inventory::class.java)
        startActivity(switch_activity_intent)}
    fun open_rules() {
        val switch_activity_intent= Intent(this, Rules::class.java)
        startActivity(switch_activity_intent)}

}