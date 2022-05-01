package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
    lateinit var canvas: Canvas
    private val backgroundPaint = Paint()
    lateinit var thread: Thread
    private var drawing = true
    //On initialise la hauteur et la largeur de l'écran qu'on modifie dans onSizeChanged
    var screenWidth = 0f
    var screenHeight = 0f
    // On initialise les variables qui vont correspondre à tous nos objets

    var plateforme1 = Obstacle(0f,0f,0f,0f,this)
    var plateforme2 = Obstacle(0f,0f,0f,0f,this)
    var player = Personnage(this,"Force Rouge le Chaperon Rouge", 1, 1, plateforme1, plateforme2)
    var porte = Porte()
    var trap = Trap(-1, 0f, 0f, 0f, 0f, this)
    var hole = Hole(0f, 0f, 0f, 0f, this)


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
            updatePositions(gauche)
            draw()
            sleep()
        }
    }


    fun updatePositions(gauche : Boolean) {
        //On appelle toutes les fonctions qui permettent d'updater les éléments de la GameView sur celle-ci
        if (buttonpressed) {
        player.update(gauche)}
        //plateforme2.blockPerso(player)
        //player.blockPerso(plateforme2)
    }


    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

            //On fait apparaître tous les objets (personnages, plateformes, etc.)
            player.draw(canvas)
            plateforme1.draw(canvas)
            plateforme2.draw(canvas)
            porte.draw(canvas)
            hole.draw(canvas)
            trap.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }


    fun sleep() {
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
        player.paint.color = Color.MAGENTA
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
        plateforme2.obstacleHeigth = 50f
        plateforme2.setRect()

        hole.obstacleBeginX = screenWidth/3 + screenWidth/3
        hole.obstacleLength = screenWidth/8
        hole.obstacleBeginY = screenHeight*3/4
        hole.obstacleHeigth = 50f
        hole.setRect()

        trap.obstacleBeginX = screenWidth/3 - screenWidth/8
        trap.obstacleLength = screenWidth/8
        trap.obstacleBeginY = screenHeight*3/4
        trap.obstacleHeigth = 50f
        trap.setRect()

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