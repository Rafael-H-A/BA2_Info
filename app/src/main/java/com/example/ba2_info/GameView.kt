package com.example.ba2_info

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants


class GameView @JvmOverloads constructor (context: Context,
                                          attributes: AttributeSet? = null,
                                          defStyleAttr: Int = 0):
    SurfaceView(context, attributes,defStyleAttr),
    SurfaceHolder.Callback, Runnable
{
    lateinit var canvas : Canvas
    private val backgroundPaint = Paint()
    lateinit var thread: Thread
    lateinit var thread2 : Thread
    private var drawing = true
    var screenWidth = 0f
    var screenHeight = 0f
    var buttonpressed = false
    var gauche : Boolean = true
    var player = Personnage(this,"Force Rouge le Chaperon Rouge", 0,3,
                            GameConstants.obstacles, GameConstants.listeaccess, GameConstants.porte)
    val textLifePaint = Paint()
    val textPowerPaint = Paint()
    val textMessagesPaint = Paint()

    //val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.wallpaperorange)
    //val bitmapfinale : Bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth.toInt(), screenHeight.toInt(), true)

    init {
        backgroundPaint.color = ContextCompat.getColor(context, R.color.SteelBlue)
        textLifePaint.textSize= screenWidth/40
        textLifePaint.color = Color.BLACK
        textPowerPaint.textSize = screenWidth/40
        textPowerPaint.color = Color.BLACK
        textMessagesPaint.textSize = screenWidth/60
        textMessagesPaint.color = Color.BLACK
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
            //canvas.drawBitmap(bitmapfinale, 0f, 0f, backgroundPaint)

            //Affichage life / power / time
            canvas.drawText("Il reste ${player.life} vies",screenWidth - 350f, 80f, textLifePaint)
            canvas.drawText("Force ${player.power}",screenWidth - 600f, 80f, textPowerPaint)
            canvas.drawText("${player.name} est prêt pour la bataille !", 800f, screenHeight - 50f, textMessagesPaint)

            //On fait apparaître tous les objets (personnages, plateformes, etc.)
            for (i in 0 until GameConstants.obstacles.size) {
                GameConstants.obstacles[i].draw(canvas)
            }
            GameConstants.porte.draw(canvas)
            GameConstants.accessoire1.draw(canvas)
            GameConstants.accessoire2.draw(canvas)

            player.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }


    private fun sleep() {
        Thread.sleep((1000/120).toLong())

    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        textLifePaint.textSize = screenWidth / 40
        textLifePaint.isAntiAlias = true
        textPowerPaint.textSize = screenWidth / 40
        textPowerPaint.isAntiAlias = true
        textMessagesPaint.textSize = screenWidth / 40
        textMessagesPaint.isAntiAlias = true

        //On redéfinit les dimensions des éléments par rapport à la taille de l'écran
        //Faire gaffe à définir TOUTES les carac géométriques des objets

        //Caractéristiques du sol
        GameConstants.sol.obstacleBeginX = 0f
        GameConstants.sol.obstacleLength = screenWidth
        GameConstants.sol.obstacleHeigth = 150f
        GameConstants.sol.obstacleBeginY = screenHeight - GameConstants.sol.obstacleHeigth
        GameConstants.sol.setRect()

        //Caractéristiques du joueur
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = GameConstants.sol.obstacleBeginY - player.diametre
        player.setRect()


        GameConstants.porte.x = screenWidth - 60f
        GameConstants.porte.y = screenHeight - GameConstants.porte.height - GameConstants.sol.obstacleHeigth
        GameConstants.porte.setRect()


        // Caractéristiques des obstacles
        val thickness = 30f
        val trapLength = screenWidth/13
        val split = screenHeight/7 // hauteur entre les plateformes et obstacles
        val margeEasy = 40f
        //Pr se faciliter la vie, on pourrait définir une liste des "étages" où on place toutes les plateformes
        //Et ttes ces valeurs ont un bon écart entre elles pr pouvoir sauter d'un étage à l'autre

        GameConstants.plateforme2.obstacleBeginX = screenWidth/3
        GameConstants.plateforme2.obstacleLength = screenWidth/7
        GameConstants.plateforme2.obstacleBeginY = screenHeight*3/4
        GameConstants.plateforme2.obstacleHeigth = thickness
        GameConstants.plateforme2.setRect()

        GameConstants.trap1.obstacleBeginX = screenWidth/3 - trapLength
        GameConstants.trap1.obstacleLength = trapLength
        GameConstants.trap1.obstacleBeginY = screenHeight*3/4
        GameConstants.trap1.obstacleHeigth = thickness
        GameConstants.trap1.setRect()

        GameConstants.trapVerti.obstacleBeginX = GameConstants.trap1.obstacleBeginX
        GameConstants.trapVerti.obstacleLength = thickness
        GameConstants.trapVerti.obstacleBeginY = GameConstants.trap1.obstacleBeginY
        GameConstants.trapVerti.obstacleHeigth = GameConstants.sol.obstacleBeginY - GameConstants.trap1.obstacleBeginY
        GameConstants.trapVerti.setRect()

        GameConstants.trap2.obstacleBeginX = GameConstants.plateforme2.obstacleBeginX + GameConstants.plateforme2.obstacleLength
        GameConstants.trap2.obstacleLength = trapLength
        GameConstants.trap2.obstacleBeginY = GameConstants.plateforme2.obstacleBeginY
        GameConstants.trap2.obstacleHeigth = thickness
        GameConstants.trap2.setRect()

        GameConstants.plateforme3.obstacleBeginX = screenWidth - 2*screenWidth/5 - margeEasy
        GameConstants.plateforme3.obstacleLength = screenWidth/9
        GameConstants.plateforme3.obstacleBeginY = screenHeight*3/4 - split
        GameConstants.plateforme3.obstacleHeigth = thickness
        GameConstants.plateforme3.setRect()

        GameConstants.hole.obstacleBeginX = GameConstants.trap2.obstacleBeginX + GameConstants.trap2.obstacleLength - margeEasy
        GameConstants.hole.obstacleLength = GameConstants.plateforme3.obstacleBeginX - GameConstants.hole.obstacleBeginX
        GameConstants.hole.obstacleBeginY = GameConstants.plateforme3.obstacleBeginY
        GameConstants.hole.obstacleHeigth = thickness
        GameConstants.hole.setRect()

        GameConstants.trap3.obstacleBeginX = GameConstants.plateforme3.obstacleBeginX + GameConstants.plateforme3.obstacleLength
        GameConstants.trap3.obstacleLength = trapLength
        GameConstants.trap3.obstacleBeginY = GameConstants.plateforme3.obstacleBeginY
        GameConstants.trap3.obstacleHeigth = thickness
        GameConstants.trap3.setRect()

        GameConstants.plateformeDebut.obstacleBeginX = player.x + 2*margeEasy
        GameConstants.plateformeDebut.obstacleLength = screenWidth/4
        GameConstants.plateformeDebut.obstacleBeginY = GameConstants.trap1.obstacleBeginY - split
        GameConstants.plateformeDebut.obstacleHeigth = thickness
        GameConstants.plateformeDebut.setRect()

        GameConstants.plateforme4.obstacleBeginX = GameConstants.trap3.obstacleBeginX + GameConstants.trap3.obstacleLength - 2*margeEasy
        GameConstants.plateforme4.obstacleLength = trapLength
        GameConstants.plateforme4.obstacleBeginY = GameConstants.trap3.obstacleBeginY - split
        GameConstants.plateforme4.obstacleHeigth = thickness
        GameConstants.plateforme4.setRect()

        GameConstants.accessoire1.xpos = GameConstants.plateforme3.obstacleBeginX + GameConstants.plateforme3.obstacleLength / 2
        GameConstants.accessoire1.ypos = GameConstants.plateforme3.r.top -4f
        GameConstants.accessoire1.length = 50f
        GameConstants.accessoire1.width = -50f
        GameConstants.accessoire1.setRect()

        GameConstants.accessoire2.xpos = GameConstants.plateformeDebut.obstacleBeginX + GameConstants.plateformeDebut.obstacleLength / 2
        GameConstants.accessoire2.ypos = GameConstants.plateformeDebut.r.top -4f
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
        thread.join()
        thread2.join()}
}