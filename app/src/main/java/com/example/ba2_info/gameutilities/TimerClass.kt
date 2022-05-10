package com.example.ba2_info.gameutilities

import android.view.SurfaceHolder
import com.example.ba2_info.GameView
import java.lang.Thread.sleep

class TimerClass(private var gameView: GameView) : SurfaceHolder.Callback, Runnable{
    private val updateTime = 0.1
    private lateinit var thread: Thread
    private var running = true

    override fun run() {
        while(running) {
            sleep((updateTime * 1000).toLong())
            gameView.timeLeft -= updateTime
            if (gameView.timeLeft <= 0.0) {
                gameView.timeLeft = 00.00
                running = false
                GameConstants.gameOver = true
                gameView.drawing = false
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