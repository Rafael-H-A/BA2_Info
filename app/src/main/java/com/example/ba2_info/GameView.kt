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
import com.example.ba2_info.gameclasses.bonus.*
import com.example.ba2_info.gameutilities.GameConstants


open class GameView @JvmOverloads constructor (context: Context,
                                               attributes: AttributeSet? = null,
                                               defStyleAttr: Int = 0):
    SurfaceView(context, attributes,defStyleAttr),
    SurfaceHolder.Callback, Runnable
{
    lateinit var canvas : Canvas
    private val backgroundPaint = Paint()
    lateinit var thread: Thread
    var drawing = true
    var screenWidth = 0f
    var screenHeight = 0f
    var buttonpressed = false
    var gauche : Boolean = true
    var player = Personnage(this,"Force Rouge le Chaperon Rouge", 1,3,
                            GameConstants.obstacles, GameConstants.listeaccess, GameConstants.porte)
    var peaudebanane1 = Peaudebanane(this)
    var sablier1 = Sablier(this)
    var bonuses = listOf<Bonus>(GameConstants.petitcoeur1, GameConstants.potion1, sablier1, peaudebanane1)
    val textLifePaint = Paint()
    val textPowerPaint = Paint()
    val textMessagesPaint = Paint()
    val textTimerPaint = Paint()
    var timeLeft = GameConstants.timeLeft



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
        textTimerPaint.textSize = screenWidth/40
        textTimerPaint.color = Color.BLACK
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
            updatePositions(gauche)
            sleep()
            testlimits()
        }
    }

    fun testlimits() {
        val playeronscreen = player.y < screenHeight
        if (!playeronscreen) {
            player.life -= 1
            if (screenHeight == 0f) {player.life += 1}
            if (player.life == 0) {
                GameConstants.gameOver = true
                openFight()
            }
            player.x = screenWidth / 20f
            player.y = GameConstants.floor1.obstacleBeginY - player.diametre
            player.setRect()
        }
    }

    private fun updatePositions(gauche : Boolean) {
        //On appelle toutes les fonctions qui permettent d'updater les éléments de la GameView sur celle-ci
        if (buttonpressed) {
        player.update(gauche)
        }
        player.fall()
    }


    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

            /* // Affichage background
            val bitmapbackground : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.wallpaperblue2)
            bitmapbackground.scale(canvas.width, canvas.height)
            val rectcan  = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
            canvas.drawBitmap(bitmapbackground, null, rectcan , null ) */

            //On fait apparaître tous les objets (personnages, plateformes, etc.)
            for (i in 0 until GameConstants.obstacles.size) {
                GameConstants.obstacles[i].draw(canvas)
            }
            GameConstants.porte.draw(canvas)
            GameConstants.accessoire1.draw(canvas)
            GameConstants.accessoire2.draw(canvas)
            GameConstants.accessoire3.draw(canvas)

            GameConstants.potion1.draw(canvas, resources)
            GameConstants.petitcoeur1.draw(canvas,resources)
            sablier1.draw(canvas,resources)
            peaudebanane1.draw(canvas, resources)

            player.draw(canvas)                                         //Affichage du personnage

            //Affichage des textes
            canvas.drawText("Il reste ${player.life} vies",screenWidth - 350f, 80f,  textLifePaint)
            canvas.drawText("Force ${player.power}"       ,screenWidth - 600f, 80f, textPowerPaint)
            canvas.drawText("${player.name} est prêt pour la bataille !", 660f,screenHeight - 60f, textMessagesPaint)
            val formatted = String.format("%.2f", timeLeft)
            canvas.drawText("Temps restant : $formatted", 150f, 80f, textTimerPaint)

            holder.unlockCanvasAndPost(canvas)
        }
    }


    private fun sleep() {
        Thread.sleep((1000/120).toLong())

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
        textMessagesPaint.textSize = screenWidth / 50
        textMessagesPaint.isAntiAlias = true
        textTimerPaint.textSize = screenWidth / 40
        textTimerPaint.isAntiAlias = true

        //On redéfinit les dimensions des éléments par rapport à la taille de l'écran
        //Faire gaffe à définir TOUTES les carac géométriques des objets


        //Caractéristiques du joueur
        player.x = screenWidth / 20f
        player.diametre = screenHeight / 24f
        player.y = GameConstants.floor1.obstacleBeginY - player.diametre
        player.setRect()


        GameConstants.porte.x = screenWidth - 60f
        GameConstants.porte.y = screenHeight - GameConstants.porte.height - GameConstants.floor3.obstacleHeigth
        GameConstants.porte.setRect()


        // Caractéristiques des obstacles
        val thickness = 30f
        val trapLength = screenWidth/13
        val split = screenHeight/7 // hauteur entre les plateformes et obstacles
        val margeEasy = 40f

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

        GameConstants.trapverti.obstacleBeginX = 2 * screenWidth/ 5
        GameConstants.trapverti.obstacleLength = thickness * 2
        GameConstants.trapverti.obstacleHeigth = GameConstants.floor2.obstacleBeginY - GameConstants.pltf12.obstacleBeginY - thickness
        GameConstants.trapverti.obstacleBeginY = 6 * screenHeight /8 + thickness
        GameConstants.trapverti.setRect()

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

        sablier1.bonusx = GameConstants.pltf12.obstacleBeginX + GameConstants.pltf12.obstacleLength/4
        sablier1.bonusy = GameConstants.pltf12.obstacleBeginY - sablier1.length
        sablier1.setRect()

        GameConstants.potion1.bonusx = GameConstants.pltf11.obstacleBeginX + GameConstants.pltf11.obstacleLength / 2
        GameConstants.potion1.bonusy = GameConstants.pltf11.obstacleBeginY - GameConstants.potion1.length
        GameConstants.potion1.setRect()

        peaudebanane1.bonusx = GameConstants.pltf33.obstacleBeginX + GameConstants.pltf33.obstacleLength / 2
        peaudebanane1.bonusy = GameConstants.pltf33.obstacleBeginY - peaudebanane1.length
        peaudebanane1.setRect()

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






        //Pr se faciliter la vie, on pourrait définir une liste des "étages" où on place toutes les plateformes
        //Et ttes ces valeurs ont un bon écart entre elles pr pouvoir sauter d'un étage à l'autre
        /*
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

        GameConstants.potion1.bonusx = (player.x/4).toFloat()
        GameConstants.potion1.bonusy = (player.y).toFloat()
        GameConstants.potion1.length = 45f
        GameConstants.potion1.width= 45f
        GameConstants.potion1.setRect()

        GameConstants.petitcoeur1.bonusx = (player.x * 2).toFloat()
        GameConstants.petitcoeur1.bonusy = (player.y).toFloat()
        GameConstants.petitcoeur1.length = 50f
        GameConstants.petitcoeur1.width= 50f
        GameConstants.petitcoeur1.setRect()

        GameConstants.sablier1.bonusx = (player.x * 3).toFloat()
        GameConstants.sablier1.bonusy = (player.y).toFloat()
        GameConstants.sablier1.length = 45f
        GameConstants.sablier1.width= 45f
        GameConstants.sablier1.setRect()

         */

    }
}