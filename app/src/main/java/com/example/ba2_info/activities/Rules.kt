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

        //GOAL
        val buttonGoal = findViewById<Button>(R.id.button_goal)
        val imageGOAL = findViewById<ImageView>(R.id.txtBut)
        imageGOAL.visibility = View.INVISIBLE
        //VOCABULARY
        val buttonVoc = findViewById<Button>(R.id.button_vocabulary)
        val imageVOC = findViewById<ImageView>(R.id.txtVoc)
        imageVOC.visibility = View.INVISIBLE


        buttonGoal.setOnClickListener {
            if (imageGOAL.visibility == View.INVISIBLE) {
                imageGOAL.visibility = View.VISIBLE
                imageVOC.visibility = View.INVISIBLE}
            else {imageGOAL.visibility = View.INVISIBLE}}
        buttonVoc.setOnClickListener {
            if (imageVOC.visibility == View.INVISIBLE) {
                imageVOC.visibility = View.VISIBLE
                imageGOAL.visibility = View.INVISIBLE}
            else {imageVOC.visibility = View.INVISIBLE }}
    }
}