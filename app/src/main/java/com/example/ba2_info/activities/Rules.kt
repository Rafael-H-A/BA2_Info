package com.example.ba2_info.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ba2_info.R


class Rules : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)

        // POUR TUTORIEL
        val buttonTuto = findViewById<Button>(R.id.button_tutorial)
        val imageTUTO = findViewById<ImageView>(R.id.imgTUTO)
        imageTUTO.visibility = View.INVISIBLE

        buttonTuto.setOnClickListener {
            if (imageTUTO.visibility == View.INVISIBLE) {imageTUTO.visibility = View.VISIBLE }
            else {imageTUTO.visibility = View.INVISIBLE }}

        //POUR BUT DU JEU
        val buttonGoal = findViewById<Button>(R.id.button_goal)
        val imageGOAL = findViewById<ImageView>(R.id.imgGOAL)
        imageGOAL.visibility = View.INVISIBLE

        buttonGoal.setOnClickListener {
            if (imageGOAL.visibility == View.INVISIBLE) {imageGOAL.visibility = View.VISIBLE }
            else {imageGOAL.visibility = View.INVISIBLE }}

        //POUR VOCABULAIRE
        val buttonVoc = findViewById<Button>(R.id.button_vocabulary)
        val imageVOC = findViewById<ImageView>(R.id.imgVOC)
        imageVOC.visibility = View.INVISIBLE

        buttonVoc.setOnClickListener {
            if (imageVOC.visibility == View.INVISIBLE) {imageVOC.visibility = View.VISIBLE }
            else {imageVOC.visibility = View.INVISIBLE }}
    }
}