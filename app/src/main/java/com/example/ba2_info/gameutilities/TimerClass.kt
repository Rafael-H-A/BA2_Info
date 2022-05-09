package com.example.ba2_info.gameutilities

import android.view.SurfaceHolder
import com.example.ba2_info.GameView
import com.example.ba2_info.gameclasses.bonus.Peaudebanane
import com.example.ba2_info.gameclasses.bonus.Sablier
import java.lang.Thread.sleep


class TimerClass(var timeLeft : Double,var gameView: GameView) : SurfaceHolder.Callback, Runnable{
    private val updateTime = 0.1
    lateinit var thread: Thread
    var running = true

    override fun run() {
        while(running) {
            sleep((updateTime * 1000).toLong())
            timeLeft = GameConstants.timeLeft
            GameConstants.timeLeft -= updateTime
            gameView.timeLeft = timeLeft
            if (timeLeft <= 0.1) {
                timeLeft = 00.00
                running = false
                GameConstants.gameOver = true
                gameView.openFight()
            }
        }
    }

    fun pause() {
        running = false
        thread.join()
    }

    fun resume() {
        running = true
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()}
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join() }
}