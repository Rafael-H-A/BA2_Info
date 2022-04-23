package com.example.ba2_info

import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Inventory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        val testimage = BitmapDrawable(resources, "app/src/main/res/drawable/wallpaper_question.jpg")
        val imgview = findViewById<ImageView>(R.id.imageView)
        imgview.setBackgroundResource(R.drawable.wallpaper_question)

    }
}