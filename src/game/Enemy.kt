package game

import base.AbstractUnit
import processing.core.PApplet
import processing.core.PImage
import processing.core.PVector

/**
 * Created by mickeycj on 7/6/2017 AD.
 */
class Enemy(pApplet: PApplet, face: PImage) : AbstractUnit(pApplet, face, 0f, 0f, 0f, 0) {

    lateinit var destination: PVector

    init {
        setDestination()
    }

    fun setRadiusAndSize(lvl: Int) {
        level = lvl
        radius = (level - 1) * 7.5f + 15f
        when (level) {
            1 -> {
                value = 1
                color = 0xFFE5E19C.toInt()
            }
            2 -> {
                value = 3
                color = 0xFF9BB37E.toInt()
            }
            3 -> {
                value = 5
                color = 0xFF85ADAF.toInt()
            }
            4 -> {
                value = 10
                color = 0xFFB28077.toInt()
            }
            5 -> {
                value = 20
                color = 0xFF9B87AA.toInt()
            }
        }
    }

    fun setDestination() {
        destination = PVector((Math.random() * pApplet.width).toFloat(), (Math.random() * pApplet.height).toFloat())
    }

    override fun reset() {
        super.reset()

        position.x = 0f
        position.y = 0f
        radius = 0f
        setDestination()
    }

    override fun update() {
        if (Math.abs(destination.x - position.x) < 1 && Math.abs(destination.y - position.y) < 1) {
            setDestination()
        }
        val newVector = PVector(destination.x - position.x, destination.y - position.y)
        newVector.setMag(level.toFloat())
        velocity.lerp(newVector, 0.2f)

        super.update()
    }

    override fun render() {
        pApplet.noFill()

        super.render()
    }
}