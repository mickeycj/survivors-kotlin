package game

import processing.core.PApplet
import processing.core.PImage

/**
 * Created by mickeycj on 7/7/2017 AD.
 */
class Game(val pApplet: PApplet, faces: List<PImage>) {

    val world: World = World(pApplet, faces)

    var started: Boolean = false

    fun isEnded(): Boolean = !world.isPlayerAlive()

    fun start() {
        started = true
    }

    fun restart() {
        world.reset()
    }

    fun showStats() {
        pApplet.fill(0xFF666666.toInt())
        pApplet.textSize(35f)
        pApplet.text(world.getPlayerScore(), 805f, 378f)
        pApplet.text(world.highScore, 896f, 415f)
    }

    fun update() {
        if (world.isNumEnemiesTooLow() && pApplet.frameCount % 180 == 0) {
            world.addRandomEnemies()
        }
        world.update()
        world.render()
        pApplet.fill(0xFFFFFFFF.toInt())
        pApplet.textSize(27f)
        pApplet.text(world.getPlayerLevel(), 277f, 44f)
        pApplet.textSize(23f)
        pApplet.text(world.getPlayerScore(), 251f, 74f)
        pApplet.text(world.highScore, 304f, 102f)
    }
}