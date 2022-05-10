package com.example.ba2_info.gameclasses

import android.graphics.*
import com.example.ba2_info.gameutilities.GameConstants
import com.example.ba2_info.GameView
import com.example.ba2_info.gameclasses.platforms.Obstacle
import com.example.ba2_info.gameutilities.Pouf
import kotlin.math.*

class Personnage(
    var view: GameView, var power: Int, var life: Int = 1, private var obstacles: List<Obstacle>,
    private var accessoires: List<Accessoires>, private var porte: Porte
) : Pouf {
            var x : Float = 0f
            var y : Float = 0f
    private var dx = 10f
    private var dy = 0f
            var diametre = 50f
    private val paint = Paint()
            val r = RectF(x, y, x + diametre, y + diametre)
    private var playerinlimits = true
            var playerMoveUp = true
            var playerMoveDown = true
            var playerMoveRight = true
            var playerMoveLeft = true
            var equipment = mutableListOf(GameConstants.accessoireA)

    init {paint.color = Color.BLACK}

    override fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, paint)
    }

    fun appear(xx : Float, yy : Float, diam : Float = diametre) {
        x = xx
        y = yy
        diametre = diam
        r.set(x, y, x + diametre, y + diametre)
    }

    fun update(gauche: Boolean) {
        val s : Float
        //Does the player stay in the level limits ?
        playerinlimits = !((x < 0 && gauche)||x > view.nw - 3/2*diametre && !gauche)
        blockPersoX()
        //In which direction does the player is walking ?
        if(playerinlimits) {
            s = when {
                !playerMoveRight -> {if (gauche) {-1f} else {0f}}
                !playerMoveLeft -> {if (gauche) {0f} else {1f}}
                else -> {if (gauche) {-1f} else {1f}}
            }
            //Is there an intersection with an accessory, a bonus or the door ?
            for (a in accessoires) {a.updateaccessoires(this)}
            for (b in GameConstants.bonuses) {b.updatebonus(this)}
            porte.updateporte(this)
            //Moving the player
            val incr = abs(dx)*s
            dx = incr
            x += incr
            r.offset(dx,0f)}
    }

    fun jump() {
        //Player is going up
        for (i in 1..75) {
            val w = i/150f
            //Parabola to simulate a "realistic" jump
            dy = 10*(3*(w.toDouble().pow(2).toFloat()) - 3*w + 0.75f)
            blockPersoY()
            //If movement possible, moving the player
            if (!playerMoveUp) {dy = 0f}
            r.offset(0f, -dy)
            y -= dy
            Thread.sleep(2)}
        //Player is going down
        for (i in 76..200) {
            val w = i / 150f
            //Parabola to simulate a "realistic" jump
            dy = 10 * (3 * (w.toDouble().pow(2).toFloat()) - 3 * w + 0.75f)
            blockPersoY()
            //If movement possible, moving the player
            if (!playerMoveDown) {dy = 0f}
            r.offset(0f, dy)
            y += dy
            Thread.sleep(2)}
    }

    private fun blockPersoX() {
        playerMoveRight = true
        playerMoveLeft = true
        //Is it necessary to disable a move ?
        for (ob in obstacles) {
            if (ob.plain) {ob.updateobstacleX(this)}
        }
    }

    private fun blockPersoY() {
        playerMoveUp = true
        playerMoveDown = true
        //Is it necessary to disable a move ?
        for (ob in obstacles) {
            if (ob.plain) {ob.updateobstacleY(this)}
        }
    }

    fun fall() {
        //If the player is not on a platform, falling
        dy = 5f
        blockPersoY()
        if(playerMoveDown){
            r.offset(0f, dy)
            y += dy}
    }
}