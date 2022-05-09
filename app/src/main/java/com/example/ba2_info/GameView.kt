package com.example.ba2_info

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.ba2_info.activities.Victory
import com.example.ba2_info.gameclasses.Personnage
import com.example.ba2_info.gameutilities.GameConstants


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null,
                                          defStyleAttr: Int = 0) : SurfaceView(context, attributes,
                                          defStyleAttr), SurfaceHolder.Callback, Runnable
{
            lateinit var canvas : Canvas
    private lateinit var thread: Thread
    private val backgroundPaint = Paint()
    private val textLifePaint = Paint()
    private val textPowerPaint = Paint()
    private val textTimerPaint = Paint()
    private var drawing = true
    private var screenHeight = 0f
            var screenWidth = 0f
            var timeLeft = GameConstants.timeLeft
            var buttonpressed = false
            var gauche : Boolean = true
    private var player = Personnage(this,"Force Rouge le Chaperon Rouge", 1,3,
                            GameConstants.obstacles, GameConstants.listeaccess, GameConstants.porte)

    init {backgroundPaint.color = ContextCompat.getColor(context, R.color.LightSteelBlue)}

    override fun run() {
        while (drawing) {
            draw()
            updatePositions(gauche)
            sleep()
            testlimits()
            if (!drawing) {openFight()}
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

            //Affichage de tous les objets
            for (i in 0 until GameConstants.levelSetup.size) {
                GameConstants.levelSetup[i].draw(canvas)}
            player.draw(canvas)

            //Affichage des textes
            canvas.drawText("Il reste ${player.life} vies",screenWidth - 350f, 80f,  textLifePaint)
            canvas.drawText("Force ${player.power}"       ,screenWidth - 600f, 80f, textPowerPaint)
            val formatted = String.format("%.2f", timeLeft)
            canvas.drawText("Temps restant : $formatted", 150f, 80f, textTimerPaint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun testlimits() {
        val playeronscreen = player.y < screenHeight
        if (!playeronscreen) {
            player.life -= 1
            if (screenHeight == 0f) {player.life += 1}
            if (player.life == 0) {GameConstants.gameOver = true}
            player.x = screenWidth / 20f
            player.y = GameConstants.floor1.obstacleBeginY - player.diametre
            player.setRect()
        }
    }

    private fun updatePositions(gauche : Boolean) {
        if (buttonpressed) {player.update(gauche)}
        player.fall()
    }


    private fun sleep() {
        Thread.sleep((1000/120).toLong())

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

    fun jump() {
        player.jump()
    }

    //Fonctions qui permettent de gérer le multithreading
    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    fun openFight() {
        GameConstants.message = when {
            GameConstants.gameOver && timeLeft <= 0.1 -> {"Il ne vous reste plus de temps..."}
            GameConstants.gameOver && player.life == 0 -> {"Vous êtes mort ! Gare aux pièges la prochaine fois"}
            else -> {"T'as perdu !"}
        }
        val i = Intent(context, Victory::class.java)
        context.startActivity(i)
    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        textLifePaint.textSize = screenWidth / 40
        textLifePaint.isAntiAlias = true
        textPowerPaint.textSize = screenWidth / 40
        textPowerPaint.isAntiAlias = true
        textTimerPaint.textSize = screenWidth / 40
        textTimerPaint.isAntiAlias = true

        //Caractéristiques du joueur
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = GameConstants.floor1.obstacleBeginY - player.diametre
        player.setRect()

        // Caractéristiques des obstacles
        val thickness = 30f
        val trapLength = screenWidth/13
        val split = screenHeight/7 // hauteur entre les plateformes et obstacles
        val margeEasy = 40f

        GameConstants.porte.x = screenWidth - 60f
        GameConstants.porte.y = screenHeight - GameConstants.porte.height - GameConstants.floor3.obstacleHeigth
        GameConstants.porte.setRect()

        //Caractéristiques du sol
        GameConstants.floor1.obstacleBeginX = 0f
        GameConstants.floor1.obstacleLength = screenWidth / 6
        GameConstants.floor1.obstacleHeigth = thickness * 5
        GameConstants.floor1.obstacleBeginY = screenHeight - GameConstants.floor1.obstacleHeigth
        GameConstants.floor1.setRect()

        GameConstants.hole1.obstacleBeginX = GameConstants.floor1.obstacleLength
        GameConstants.hole1.obstacleLength = screenWidth / 10
        GameConstants.hole1.obstacleHeigth = thickness * 5
        GameConstants.hole1.obstacleBeginY = screenHeight - GameConstants.hole1.obstacleHeigth
        GameConstants.hole1.setRect()

        GameConstants.floor2.obstacleBeginX = GameConstants.hole1.obstacleLength + GameConstants.hole1.obstacleBeginX
        GameConstants.floor2.obstacleLength =  3 * screenWidth / 7
        GameConstants.floor2.obstacleHeigth = thickness * 5
        GameConstants.floor2.obstacleBeginY = screenHeight - GameConstants.floor2.obstacleHeigth
        GameConstants.floor2.setRect()

        GameConstants.hole2.obstacleBeginX = GameConstants.floor2.obstacleLength + GameConstants.floor2.obstacleBeginX
        GameConstants.hole2.obstacleLength = screenWidth / 10
        GameConstants.hole2.obstacleHeigth = thickness * 5
        GameConstants.hole2.obstacleBeginY = screenHeight - GameConstants.hole2.obstacleHeigth
        GameConstants.hole2.setRect()

        GameConstants.floor3.obstacleBeginX = GameConstants.hole2.obstacleLength + GameConstants.hole2.obstacleBeginX
        GameConstants.floor3.obstacleLength = screenWidth - GameConstants.hole2.obstacleLength
        GameConstants.floor3.obstacleHeigth = thickness * 5
        GameConstants.floor3.obstacleBeginY = screenHeight - GameConstants.floor3.obstacleHeigth
        GameConstants.floor3.setRect()

        //Caractéristiques du joueur
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = GameConstants.floor1.obstacleBeginY - player.diametre
        player.setRect()

        // Caractéristique de la porte
        GameConstants.porte.x = screenWidth - thickness * 3
        GameConstants.porte.y = screenHeight - GameConstants.porte.height - GameConstants.floor3.obstacleHeigth
        GameConstants.porte.setRect()

        // 1e étage

        GameConstants.pltf11.obstacleBeginX = GameConstants.floor1.obstacleLength
        GameConstants.pltf11.obstacleLength = GameConstants.hole1.obstacleLength
        GameConstants.pltf11.obstacleHeigth = thickness
        GameConstants.pltf11.obstacleBeginY = 6 * screenHeight /8
        GameConstants.pltf11.setRect()

        GameConstants.pltf12.obstacleBeginX = screenWidth/ 2
        GameConstants.pltf12.obstacleLength = thickness * 2
        GameConstants.pltf12.obstacleHeigth = thickness
        GameConstants.pltf12.obstacleBeginY = 6 * screenHeight /8
        GameConstants.pltf12.setRect()

        GameConstants.trapvert.obstacleBeginX = 2 * screenWidth/ 5
        GameConstants.trapvert.obstacleLength = thickness * 2
        GameConstants.trapvert.obstacleHeigth = GameConstants.floor2.obstacleBeginY - GameConstants.pltf12.obstacleBeginY - thickness
        GameConstants.trapvert.obstacleBeginY = 6 * screenHeight /8 + thickness
        GameConstants.trapvert.setRect()

        GameConstants.pltf12.obstacleBeginX = 2*  screenWidth/ 5
        GameConstants.pltf12.obstacleLength = thickness * 2
        GameConstants.pltf12.obstacleHeigth = thickness
        GameConstants.pltf12.obstacleBeginY = 6 * screenHeight /8
        GameConstants.pltf12.setRect()

        GameConstants.pltf13.obstacleBeginX = GameConstants.floor2.obstacleBeginX + 3 * GameConstants.floor2.obstacleLength / 5
        GameConstants.pltf13.obstacleLength = GameConstants.floor3.obstacleBeginX - GameConstants.pltf13.obstacleBeginX
        GameConstants.pltf13.obstacleHeigth = thickness
        GameConstants.pltf13.obstacleBeginY = 6 * screenHeight /8
        GameConstants.pltf13.setRect()

        GameConstants.trap1.obstacleBeginX = GameConstants.pltf13.obstacleBeginX
        GameConstants.trap1.obstacleLength = GameConstants.hole2.obstacleBeginX - GameConstants.pltf13.obstacleBeginX
        GameConstants.trap1.obstacleHeigth = thickness
        GameConstants.trap1.obstacleBeginY = GameConstants.pltf13.obstacleBeginY + thickness
        GameConstants.trap1.setRect()

        // 2e étage

        GameConstants.pltf21.obstacleBeginX = GameConstants.pltf11.obstacleBeginX + GameConstants.pltf11.obstacleLength + thickness
        GameConstants.pltf21.obstacleLength = GameConstants.pltf11.obstacleLength
        GameConstants.pltf21.obstacleHeigth = thickness
        GameConstants.pltf21.obstacleBeginY = 5 * screenHeight /8
        GameConstants.pltf21.setRect()

        GameConstants.pltf22.obstacleBeginX = GameConstants.floor3.obstacleBeginX + thickness
        GameConstants.pltf22.obstacleLength = GameConstants.pltf11.obstacleLength - GameConstants.pltf12.obstacleLength
        GameConstants.pltf22.obstacleHeigth = thickness
        GameConstants.pltf22.obstacleBeginY = 5 * screenHeight /8
        GameConstants.pltf22.setRect()

        GameConstants.trap2.obstacleBeginX = GameConstants.floor3.obstacleBeginX + thickness
        GameConstants.trap2.obstacleLength = GameConstants.pltf11.obstacleLength - GameConstants.pltf12.obstacleLength
        GameConstants.trap2.obstacleHeigth = thickness
        GameConstants.trap2.obstacleBeginY = GameConstants.pltf22.obstacleBeginY + GameConstants.pltf22.obstacleHeigth
        GameConstants.trap2.setRect()

        // 3e étage
        GameConstants.pltf32.obstacleBeginX = GameConstants.hole1.obstacleBeginX
        GameConstants.pltf32.obstacleLength = GameConstants.pltf22.obstacleLength
        GameConstants.pltf32.obstacleHeigth = thickness
        GameConstants.pltf32.obstacleBeginY = 4 * screenHeight /8
        GameConstants.pltf32.setRect()


        GameConstants.holeup.obstacleBeginX = GameConstants.pltf32.obstacleBeginX - GameConstants.pltf32.obstacleLength
        GameConstants.holeup.obstacleLength = GameConstants.pltf32.obstacleLength
        GameConstants.holeup.obstacleHeigth = thickness
        GameConstants.holeup.obstacleBeginY = 4 * screenHeight /8
        GameConstants.holeup.setRect()


        GameConstants.pltf31.obstacleBeginX = GameConstants.holeup.obstacleBeginX - GameConstants.holeup.obstacleLength
        GameConstants.pltf31.obstacleLength = GameConstants.pltf32.obstacleLength
        GameConstants.pltf31.obstacleHeigth = thickness
        GameConstants.pltf31.obstacleBeginY = 4 * screenHeight /8
        GameConstants.pltf31.setRect()

        GameConstants.pltf33.obstacleBeginX = GameConstants.pltf12.obstacleBeginX + thickness
        GameConstants.pltf33.obstacleLength = 2* GameConstants.pltf13.obstacleLength / 3
        GameConstants.pltf33.obstacleHeigth = thickness
        GameConstants.pltf33.obstacleBeginY = 4 * screenHeight /8
        GameConstants.pltf33.setRect()

        GameConstants.pltf34.obstacleBeginX = GameConstants.porte.x - GameConstants.porte.length
        GameConstants.pltf34.obstacleLength = GameConstants.porte.length * 2
        GameConstants.pltf34.obstacleHeigth = thickness
        GameConstants.pltf34.obstacleBeginY = 4 * screenHeight /8
        GameConstants.pltf34.setRect()

        GameConstants.petitcoeur1.bonusx = GameConstants.pltf13.obstacleBeginX   + GameConstants.pltf13.obstacleLength /2
        GameConstants.petitcoeur1.bonusy = GameConstants.pltf13.obstacleBeginY - GameConstants.petitcoeur1.length
        GameConstants.petitcoeur1.setRect()

        GameConstants.sablier1.bonusx = GameConstants.pltf12.obstacleBeginX + GameConstants.pltf12.obstacleLength/4
        GameConstants.sablier1.bonusy = GameConstants.pltf12.obstacleBeginY - GameConstants.sablier1.length
        GameConstants.sablier1.setRect()

        GameConstants.potion1.bonusx = GameConstants.pltf11.obstacleBeginX + GameConstants.pltf11.obstacleLength / 2
        GameConstants.potion1.bonusy = GameConstants.pltf11.obstacleBeginY - GameConstants.potion1.length
        GameConstants.potion1.setRect()

        GameConstants.peaudebanane1.bonusx = GameConstants.pltf33.obstacleBeginX + GameConstants.pltf33.obstacleLength / 2
        GameConstants.peaudebanane1.bonusy = GameConstants.pltf33.obstacleBeginY - GameConstants.peaudebanane1.length
        GameConstants.peaudebanane1.setRect()

        GameConstants.accessoire1.xpos = GameConstants.pltf31.obstacleBeginX + GameConstants.pltf31.obstacleLength / 2
        GameConstants.accessoire1.ypos = GameConstants.pltf31.obstacleBeginY - GameConstants.accessoire1.length
        GameConstants.accessoire1.length = 50f
        GameConstants.accessoire1.width = -50f
        GameConstants.accessoire1.setRect()

        GameConstants.accessoire2.xpos = GameConstants.floor2.obstacleBeginX +  GameConstants.floor2.obstacleLength * 6 / 7
        GameConstants.accessoire2.ypos = GameConstants.floor2.obstacleBeginY - GameConstants.accessoire2.length
        GameConstants.accessoire2.length = 50f
        GameConstants.accessoire2.width = -50f
        GameConstants.accessoire2.setRect()

        GameConstants.accessoire3.xpos = GameConstants.pltf34.obstacleBeginX +  GameConstants.pltf34.obstacleLength / 3
        GameConstants.accessoire3.ypos = GameConstants.pltf34.obstacleBeginY - GameConstants.accessoire3.length
        GameConstants.accessoire3.length = 50f
        GameConstants.accessoire3.width = -50f
        GameConstants.accessoire3.setRect()
    }
}