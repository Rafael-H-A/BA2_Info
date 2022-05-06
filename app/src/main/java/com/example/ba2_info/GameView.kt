package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat


class GameView @JvmOverloads constructor (context: Context,
                                          attributes: AttributeSet? = null,
                                          defStyleAttr: Int = 0):
    SurfaceView(context, attributes,defStyleAttr),
    SurfaceHolder.Callback, Runnable
{
    lateinit var canvas : Canvas
    private val backgroundPaint = Paint()
    lateinit var thread: Thread
    private var drawing = true
    //On initialise la hauteur et la largeur de l'écran qu'on modifie dans onSizeChanged
    var screenWidth = 0f
    var screenHeight = 0f
    // Initialisation de toutes les plateformes
    var plateforme1 = Obstacle(0f,0f,0f,0f,this)
    var plateforme2 = Obstacle(0f,0f,0f,0f,this)

    // Initialisation des pièges et des trous
    var trap = Trap(-1, 0f, 0f, 0f, 0f, this)
    var hole = Hole(0f, 0f, 0f, 0f, this)

    // Création de la liste des obstacles (obstacles, traps & holes)
    var obstacles = listOf<Obstacle>(plateforme1, plateforme2, trap, hole)
    var porte = Porte()
    var player = Personnage(this,"Force Rouge le Chaperon Rouge", 0, 1, obstacles, GameConstants.listeaccess, porte)

    var buttonpressed = false
    var gauche : Boolean = true

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
            fall()
            draw()
            updatePositions(gauche)
            sleep()
        }
    }

    private fun fall() {
        player.fall()
    }

    private fun updatePositions(gauche : Boolean) {
        //On appelle toutes les fonctions qui permettent d'updater les éléments de la GameView sur celle-ci
        if (buttonpressed) {
        player.update(gauche)
        }
    }


    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

            //On fait apparaître tous les objets (personnages, plateformes, etc.)
            plateforme1.draw(canvas)
            plateforme2.draw(canvas)
            porte.draw(canvas)
            hole.draw(canvas)
            trap.draw(canvas)
            GameConstants.accessoire1.draw(canvas)
            player.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }


    private fun sleep() {
        Thread.sleep((1000/60).toLong())
    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        //On redéfinit les dimensions des éléments par rapport à la taille de l'écran
        //Faire gaffe à définir TOUTES les carac géométriques des objets

        //Caractéristiques du sol
        plateforme1.obstacleBeginX = 0f
        plateforme1.obstacleLength = screenWidth
        plateforme1.obstacleHeigth = 150f
        plateforme1.obstacleBeginY = screenHeight - plateforme1.obstacleHeigth
        plateforme1.setRect()

        //Caractéristiques du joueur
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = plateforme1.obstacleBeginY - player.diametre
        player.setRect()

        porte.x = screenWidth - 60f
        porte.y = screenHeight - porte.height - plateforme1.obstacleHeigth
        porte.setRect()

        plateforme2.obstacleBeginX = screenWidth/3
        plateforme2.obstacleLength = screenWidth/3
        plateforme2.obstacleBeginY = screenHeight*3/4
        plateforme2.obstacleHeigth = 150f
        plateforme2.setRect()

        hole.obstacleBeginX = screenWidth/3 + screenWidth/3
        hole.obstacleLength = screenWidth/8
        hole.obstacleBeginY = screenHeight*3/4
        hole.obstacleHeigth = 30f
        hole.setRect()

        trap.obstacleBeginX = screenWidth/3 - screenWidth/8
        trap.obstacleLength = screenWidth/8
        trap.obstacleBeginY = screenHeight*3/4
        trap.obstacleHeigth = 30f
        trap.setRect()

        GameConstants.accessoire1.xpos = screenWidth /2
        GameConstants.accessoire1.ypos = plateforme2.r.top -4f
        GameConstants.accessoire1.length = 50f
        GameConstants.accessoire1.width = -50f
        GameConstants.accessoire1.setRect()
    }

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