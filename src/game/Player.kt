package game

import base.AbstractUnit
import base.Unit
import processing.core.PApplet
import processing.core.PImage
import processing.core.PVector

/**
 * Created by mickeycj on 7/6/2017 AD.
 */
class Player(pApplet: PApplet, face: PImage)
    : AbstractUnit(pApplet, face, (pApplet.width / 2).toFloat(), pApplet.height / 2 + 61.5f, 16f, 1) {

    var score: Int = 0
    var alive: Boolean = true

    fun levelUp() {
        if (level < 3) {
            level++
            when (level) {
                1 -> color = 0xFFE5E19C.toInt()
                2 -> color = 0xFF9BB37E.toInt()
                3 -> color = 0xFF85ADAF.toInt()
            }
        }
    }

    fun die() {
        alive = false
    }

    override fun eat(other: Unit): Boolean {
        if (super.eat(other)) {
            score += (other as AbstractUnit).value
            return true
        }
        return false
    }

    override fun reset() {
        super.reset()

        position.x = (pApplet.width / 2).toFloat()
        position.y = pApplet.height / 2 + 61.5f
        radius = 16f
        score = 0
        alive = true
    }

    override fun update() {
        val newVector = PVector(pApplet.mouseX - position.x, pApplet.mouseY - position.y)
        newVector.setMag(3f)
        velocity.lerp(newVector, 0.5f)

        super.update()
    }

    override fun render() {
        pApplet.fill(color)

        super.render()
    }
}