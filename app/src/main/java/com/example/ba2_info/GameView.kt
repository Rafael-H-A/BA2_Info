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
    private val textObjective = Paint()
            var drawing = true
    private var nh = 0f                                                                //Screen size
            var nw = 0f
            var timeLeft = GameConstants.timeLeft
            var buttonpressed = false
            var left : Boolean = true
    private var pause = false
    private var player = Personnage(
        this, 1, 3, GameConstants.obstacles,
        GameConstants.listeaccess,GameConstants.porte)

    init {backgroundPaint.color = ContextCompat.getColor(context, R.color.SteelBlue)}

    override fun run() {
        while (drawing) {
            draw()
            updatePositions(left)
            testlimits()
            sleep()
            if (!drawing && !pause) {displayEnd()}
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)
            //Affichage de tous les objets
            for (i in 0 until GameConstants.levelSetup.size) {GameConstants.levelSetup[i].draw(canvas)}
            player.draw(canvas)
            //Affichage des textes
            canvas.drawText("Il reste ${player.life} vies",nw - 350f, 80f,  textLifePaint)
            canvas.drawText("Force ${player.power}"       ,nw - 600f, 80f, textPowerPaint)
            val formatted = String.format("%.2f", timeLeft)
            canvas.drawText("Temps restant : $formatted", 150f, 80f, textTimerPaint)
            canvas.drawText("Force requise ${GameConstants.enemyPower + 1}", 1000f, nh - 60f, textObjective)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun updatePositions(gauche : Boolean) {
        if (buttonpressed) {player.update(gauche)}
        player.fall()
    }

    private fun testlimits() {
        val playeronscreen = player.y < nh
        if (!playeronscreen) {
            player.life -= 1
            if (nh == 0f) {player.life += 1}
            if (player.life == 0) {GameConstants.gameOver = true; drawing = false}
            player.appear(nw / 20f,
                GameConstants.floor1.obstacleBeginY - player.diametre)
        }
    }

    private fun sleep() {
        Thread.sleep((1000/120).toLong())
    }

    fun pause() {
        drawing = false
        pause = true
        thread.join()
    }

    fun resume() {
        drawing = true
        pause = false
        thread = Thread(this)
        thread.start()
    }

    fun jump() {
        player.jump()
    }

    private fun displayEnd() {
        GameConstants.message = when {
            GameConstants.gameOver && timeLeft <= 0.1 -> {"Il ne vous reste plus de temps..."}
            GameConstants.gameOver && player.life == 0 -> {"Tu es mort ! Gare aux pièges la prochaine fois"}
            else -> {"Oh non, c'est perdu !"}
        }
        val i = Intent(context, Victory::class.java)
        context.startActivity(i)
    }

    //Fonctions qui permettent de gérer le multithreading
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {thread = Thread(this); thread.start()}
    override fun surfaceDestroyed(holder: SurfaceHolder) {thread.join()}

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        nw = w.toFloat()                //new width
        nh = h.toFloat()                //new height
        val t = 30f                     //thickness
        val fT = 5 * t                  //floor thickness
        //Affichage des textes
        textLifePaint.textSize = nw / 40 ; textLifePaint.isAntiAlias = true
        textPowerPaint.textSize = nw / 40 ; textPowerPaint.isAntiAlias = true
        textTimerPaint.textSize = nw / 40 ; textTimerPaint.isAntiAlias = true
        textObjective.textSize = nw / 40 ; textObjective.isAntiAlias = true
        /* APPARITION DE TOUS LES OBJETS PAR RAPPORT A LA TAILLE DE L'ECRAN */
        //Caractéristiques du sol
        GameConstants.floor1.appear(0f, nh - fT, nw/6, fT)
        GameConstants.hole1.appear(GameConstants.floor1.obstacleLength, nh - fT,nw/10,  fT)
        GameConstants.floor2.appear(GameConstants.floor1.obstacleLength + GameConstants.hole1.obstacleLength,nh - fT, 3*nw/7, fT)
        GameConstants.hole2.appear(GameConstants.floor1.obstacleLength + GameConstants.hole1.obstacleLength + GameConstants.floor2.obstacleLength,nh - fT,nw/10, fT)
        GameConstants.floor3.appear(GameConstants.floor1.obstacleLength + GameConstants.hole1.obstacleLength + GameConstants.floor2.obstacleLength + GameConstants.hole2.obstacleLength,nh - fT,nw - nw/10, fT)
        //Caractéristiques du joueur
        player.appear(nw/20f,GameConstants.floor1.obstacleBeginY - player.diametre,nh / 24f )
        //Caractéristiques de la porte
        GameConstants.porte.appear(nw - GameConstants.porte.length - 20f,nh - GameConstants.porte.height - fT)
        //Plateformes du 1er étage
        GameConstants.pltf11.appear(GameConstants.floor1.obstacleLength,6 * nh /8, GameConstants.hole1.obstacleLength, t)
        GameConstants.pltf12.appear(2*nw/5,6 * nh /8, t*2, t)
        GameConstants.trapvert.appear(2*nw/ 5, 6 * nh /8 + t,t * 2, GameConstants.floor2.obstacleBeginY - GameConstants.pltf12.obstacleBeginY - t)
        GameConstants.pltf13.appear(GameConstants.floor2.obstacleBeginX + 3 * GameConstants.floor2.obstacleLength / 5,6 * nh /8, t * 25 / 2, t)
        GameConstants.trap1.appear(GameConstants.pltf13.obstacleBeginX, GameConstants.pltf13.obstacleBeginY + t,GameConstants.hole2.obstacleBeginX - GameConstants.pltf13.obstacleBeginX, t)
        //Plateformes du 2e étage
        GameConstants.pltf21.appear(GameConstants.pltf11.obstacleBeginX + GameConstants.pltf11.obstacleLength + t,5 * nh /8, GameConstants.pltf11.obstacleLength, t)
        GameConstants.pltf22.appear(GameConstants.floor3.obstacleBeginX - 4*t,5 * nh /8, (GameConstants.pltf11.obstacleLength - GameConstants.pltf12.obstacleLength) + 4*t, t)
        GameConstants.trap2.appear(GameConstants.floor3.obstacleBeginX - 4*t,GameConstants.pltf22.obstacleBeginY + GameConstants.pltf22.obstacleHeigth,(GameConstants.pltf11.obstacleLength - GameConstants.pltf12.obstacleLength) + 4*t, t)
        //Plateformes du 3e étage
        GameConstants.pltf32.appear(GameConstants.hole1.obstacleBeginX,  nh /2,GameConstants.pltf21.obstacleLength*2/3, t)
        GameConstants.holeup.appear(GameConstants.pltf32.obstacleBeginX - GameConstants.pltf32.obstacleLength, nh /2, GameConstants.pltf32.obstacleLength, t)
        GameConstants.pltf31.appear( GameConstants.holeup.obstacleBeginX - GameConstants.holeup.obstacleLength, nh /2, GameConstants.pltf32.obstacleLength, t)
        GameConstants.pltf33.appear(GameConstants.pltf12.obstacleBeginX + t, nh /2,2* GameConstants.pltf13.obstacleLength / 3, t)
        GameConstants.pltf34.appear(GameConstants.porte.x - 3*GameConstants.porte.length, nh/2, 2*GameConstants.porte.length, t)
        //Bonus
        GameConstants.petitcoeur1.appear(GameConstants.pltf13.obstacleBeginX   + GameConstants.pltf13.obstacleLength /2, GameConstants.pltf13.obstacleBeginY - GameConstants.petitcoeur1.length)
        GameConstants.sablier1.appear( GameConstants.pltf12.obstacleBeginX + GameConstants.pltf12.obstacleLength/4,  GameConstants.pltf12.obstacleBeginY - GameConstants.sablier1.length)
        GameConstants.potion1.appear(GameConstants.pltf11.obstacleBeginX + GameConstants.pltf11.obstacleLength / 2, GameConstants.pltf11.obstacleBeginY - GameConstants.potion1.length)
        GameConstants.peaudebanane1.appear(GameConstants.pltf33.obstacleBeginX + GameConstants.pltf33.obstacleLength / 2,  GameConstants.pltf33.obstacleBeginY - GameConstants.peaudebanane1.length)
        //Accessoires
        GameConstants.accessoire1.appear(GameConstants.pltf31.obstacleBeginX + GameConstants.pltf31.obstacleLength / 2, GameConstants.pltf31.obstacleBeginY - GameConstants.accessoire1.length, 50f, -50f)
        GameConstants.accessoire2.appear(GameConstants.floor2.obstacleBeginX +  GameConstants.floor2.obstacleLength * 6 / 7, GameConstants.floor2.obstacleBeginY - GameConstants.accessoire2.length, 50f, -50f)
        GameConstants.accessoire3.appear(GameConstants.pltf34.obstacleBeginX +  GameConstants.pltf34.obstacleLength / 3, GameConstants.pltf34.obstacleBeginY - GameConstants.accessoire3.length, 50f, -50f)
    }
}