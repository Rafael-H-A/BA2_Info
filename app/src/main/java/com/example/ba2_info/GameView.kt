package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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
    private var drawing = true
    //On initialise la hauteur et la largeur de l'écran qu'on modifie dans onSizeChanged
    var screenWidth = 0f
    var screenHeight = 0f
    // On initialise les variables qui vont correspondre à tous nos objets
    var player = Personnage(this,"Force Rouge le Chaperon Rouge", 1, 1)
    var plateforme1 = Obstacle(0f,0f,0f,0f,0f,this)
    var porte = Porte()

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
        while (drawing) {
            draw()
            //updatePositions()
        }
    }


    fun updatePositions(gauche : Boolean) {

        //On appelle toutes les fonctions qui permettent d'updater les éléments de la GameView sur celle-ci
        player.update(gauche)
    }


    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        plateforme1.obstacleDistance = 0f
        plateforme1.obstacleDebut = screenHeight - 100f
        plateforme1.obstacleFin = screenHeight
        plateforme1.width = screenWidth
        plateforme1.initialObstacleVitesse= 0f
        plateforme1.setRect()

        //On redéfinit les dimensions des éléments par rapport à la taille de l'écran
        player.paint.color = Color.MAGENTA
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = plateforme1.obstacleDebut - player.diametre
        player.setRect()



        porte.x = screenWidth - 60f
        porte.y = screenHeight - porte.height - (screenHeight - plateforme1.obstacleDebut)
        porte.setRect()

    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

            //On fait apparaître tous les objets (personnages, plateformes, etc.)
            player.draw(canvas)
            plateforme1.draw(canvas)
            porte.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

/*
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        var gauche : Boolean = true
        if (action == MotionEvent.ACTION_DOWN) {

            //On définit ce qu'on veut faire lors d'une ACION_DOWN = quand on touche l'écran
            if (e.rawX > screenWidth / 2) {
                player.x += player.dx
                //player.r.offset(player.dx,0f)
                gauche = false
            } else {
                player.x -= player.dx
                //player.r.offset(-player.dx,0f)
                //gauche = true
            }
            updatePositions(gauche)
        }
        return true
    }
 */

    fun jump() {
        player.jump()
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