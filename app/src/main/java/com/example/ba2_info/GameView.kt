package com.example.ba2_info

import android.content.Context
import android.graphics.Canvas
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
    lateinit var canvas : Canvas
    private val backgroundPaint = Paint()
    lateinit var thread: Thread
    private var drawing = true

    //On initialise la hauteur et la largeur de l'écran qu'on modifie dans onSizeChanged
    var screenWidth = 0f
    var screenHeight = 0f

    // Initialisation de toutes les plateformes
    var sol = Obstacle(0f,0f,0f,0f,this)
    var plateforme2 = Obstacle(0f,0f,0f,0f,this)
    var plateforme3 = Obstacle(0f,0f,0f,0f,this)
    var plateformeDebut = Obstacle(0f,0f,0f,0f,this)
    var plateforme4 = Obstacle(0f,0f,0f,0f,this)

    // Initialisation des pièges et des trous
    var trap1 = Trap(-1, 0f, 0f, 0f, 0f, this)
    var trapVerti = Trap(-2, 0f, 0f, 0f, 0f, this)
    var trap2 = Trap(-1, 0f, 0f, 0f, 0f, this)
    var trap3 = Trap(-1, 0f, 0f, 0f, 0f, this)
    var hole = Hole(0f, 0f, 0f, 0f, this)

    // Création de la liste des obstacles (obstacles, traps & holes)
    var obstacles = listOf(sol, plateforme2, plateforme3, plateformeDebut, plateforme4, trap1, trapVerti, trap2, trap3, hole)
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
            sol.draw(canvas)
            plateforme2.draw(canvas)
            plateforme3.draw(canvas)
            plateformeDebut.draw(canvas)
            plateforme4.draw(canvas)
            porte.draw(canvas)
            hole.draw(canvas)
            trap1.draw(canvas)
            trapVerti.draw(canvas)
            trap2.draw(canvas)
            trap3.draw(canvas)
            GameConstants.accessoire1.draw(canvas)
            GameConstants.accessoire2.draw(canvas)
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
        sol.obstacleBeginX = 0f
        sol.obstacleLength = screenWidth
        sol.obstacleHeigth = 150f
        sol.obstacleBeginY = screenHeight - sol.obstacleHeigth
        sol.setRect()

        //Caractéristiques du joueur
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = sol.obstacleBeginY - player.diametre
        player.setRect()


        porte.x = screenWidth - 60f
        porte.y = screenHeight - porte.height - sol.obstacleHeigth
        porte.setRect()


        // Caractéristiques des obstacles
        val thickness = 30f
        val trapLength = screenWidth/13
        val split = screenHeight/7 // hauteur entre les plateformes et obstacles
        val margeEasy = 40f
        //Pr se faciliter la vie, on pourrait définir une liste des "étages" où on place toutes les plateformes
        //Et ttes ces valeurs ont un bon écart entre elles pr pouvoir sauter d'un étage à l'autre

        plateforme2.obstacleBeginX = screenWidth/3
        plateforme2.obstacleLength = screenWidth/7
        plateforme2.obstacleBeginY = screenHeight*3/4
        plateforme2.obstacleHeigth = thickness
        plateforme2.setRect()

        trap1.obstacleBeginX = screenWidth/3 - trapLength
        trap1.obstacleLength = trapLength
        trap1.obstacleBeginY = screenHeight*3/4
        trap1.obstacleHeigth = thickness
        trap1.setRect()

        trapVerti.obstacleBeginX = trap1.obstacleBeginX
        trapVerti.obstacleLength = thickness
        trapVerti.obstacleBeginY = trap1.obstacleBeginY
        trapVerti.obstacleHeigth = sol.obstacleBeginY - trap1.obstacleBeginY
        trapVerti.setRect()

        trap2.obstacleBeginX = plateforme2.obstacleBeginX + plateforme2.obstacleLength
        trap2.obstacleLength = trapLength
        trap2.obstacleBeginY = plateforme2.obstacleBeginY
        trap2.obstacleHeigth = thickness
        trap2.setRect()

        plateforme3.obstacleBeginX = screenWidth - 2*screenWidth/5 - margeEasy
        plateforme3.obstacleLength = screenWidth/9
        plateforme3.obstacleBeginY = screenHeight*3/4 - split
        plateforme3.obstacleHeigth = thickness
        plateforme3.setRect()

        hole.obstacleBeginX = trap2.obstacleBeginX + trap2.obstacleLength - margeEasy
        hole.obstacleLength = plateforme3.obstacleBeginX - hole.obstacleBeginX
        hole.obstacleBeginY = plateforme3.obstacleBeginY
        hole.obstacleHeigth = thickness
        hole.setRect()

        trap3.obstacleBeginX = plateforme3.obstacleBeginX + plateforme3.obstacleLength
        trap3.obstacleLength = trapLength
        trap3.obstacleBeginY = plateforme3.obstacleBeginY
        trap3.obstacleHeigth = thickness
        trap3.setRect()

        plateformeDebut.obstacleBeginX = player.x + 2*margeEasy
        plateformeDebut.obstacleLength = screenWidth/4
        plateformeDebut.obstacleBeginY = trap1.obstacleBeginY - split
        plateformeDebut.obstacleHeigth = thickness
        plateformeDebut.setRect()

        plateforme4.obstacleBeginX = trap3.obstacleBeginX + trap3.obstacleLength - 2*margeEasy
        plateforme4.obstacleLength = trapLength
        plateforme4.obstacleBeginY = trap3.obstacleBeginY - split
        plateforme4.obstacleHeigth = thickness
        plateforme4.setRect()

        GameConstants.accessoire1.xpos = plateforme3.obstacleBeginX + plateforme3.obstacleLength / 2
        GameConstants.accessoire1.ypos = plateforme3.r.top -4f
        GameConstants.accessoire1.length = 50f
        GameConstants.accessoire1.width = -50f
        GameConstants.accessoire1.setRect()

        GameConstants.accessoire2.xpos = plateformeDebut.obstacleBeginX + plateformeDebut.obstacleLength / 2
        GameConstants.accessoire2.ypos = plateformeDebut.r.top -4f
        GameConstants.accessoire2.length = 50f
        GameConstants.accessoire2.width = -50f
        GameConstants.accessoire2.setRect()
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