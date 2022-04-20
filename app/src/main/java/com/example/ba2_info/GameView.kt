package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat

class GameView @JvmOverloads constructor (context: Context,
                                          attributes: AttributeSet? = null,
                                          defStyleAttr: Int = 0):
    SurfaceView(context, attributes,defStyleAttr),
    SurfaceHolder.Callback, Runnable
{
    lateinit var canvas: Canvas
    private val backgroundPaint = Paint()
    lateinit var thread: Thread
    private var screenWidth = 0f
    private var screenHeight = 0f
    private var drawing = true
    // On initialise les variables qui vont correspondre à tous nos objets
    var player = Personnage("Force Rouge le Chaperon Rouge", 100, 1)


    init {
        backgroundPaint.color = ContextCompat.getColor(context, R.color.SteelBlue)
    }


    fun pause() {
        drawing = false
        thread.join()
    }


    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }


    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS = (currentTime-previousFrameTime).toDouble()

            //A chaque itération, on dessine puis on update
            draw()
            updatePositions(elapsedTimeMS)

            previousFrameTime = currentTime
        }
    }


    fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0

        //On appelle toutes les fonctions qui permettent d'updater les éléments de la GameView sur celle-ci
        player.update(interval)

    }


    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()


        //On redéfinit les dimensions des éléments par rapport à la taille de l'écran
        player.x = w / 50f
        player.y = h * 11 / 12f
        player.diametre = h / 24f
        player.setRect()
    }


    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

            //On fait apparaître tous les objets (personnages, plateformes, etc.)
            player.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }


    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            //On définit ce qu'on veut faire lors d'une ACION_DOWN = quand on touche l'écran
            if (e.rawX > screenWidth/2) {
                player.dx = 50f
                player.move()}
            else {
                player.dx = -50f
                player.move()}
        }
        return true
    }


    //Fonctions qui permettent de gérer le multithreading
    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()}
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()}
}