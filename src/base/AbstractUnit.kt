package base

import processing.core.PApplet
import processing.core.PImage
import processing.core.PVector

/**
 * Created by mickeycj on 7/6/2017 AD.
 */
abstract class AbstractUnit(val pApplet: PApplet, val face: PImage, x: Float, y: Float, r: Float, l: Int) : Unit {

    val position: PVector = PVector(x, y)
    val velocity: PVector = PVector()

    var radius: Float = r
    var level: Int = l
    var value: Int = 0
    var color: Int = 0xFFE5E19C.toInt()

    override fun setPosition(x: Float, y: Float) {
        position.x = x
        position.y = y
    }

    override fun eat(other: Unit): Boolean {
        val o = other as AbstractUnit
        val dist = PVector.dist(position, o.position)
        if (dist < radius * 0.65f + o.radius && radius > o.radius) {
            radius += 0.05f * o.radius
            return true
        }
        return false
    }

    override fun reset() {
        velocity.mult(0f)
        level = 1
        color = 0xFFE5E19C.toInt()
    }

    override fun update() {
        position.add(velocity)
        val r = radius * 1.25f
        if (position.y <= 123 + r) {
            position.y = 123 + r
        } else if (position.y > pApplet.height - r) {
            position.y = pApplet.height - r
        } else if (position.x <= r) {
            position.x = r
        } else if (position.x > pApplet.width - r) {
            position.x = pApplet.width - r
        }
    }

    override fun render() {
        pApplet.stroke(color)
        pApplet.strokeWeight(1.5f)
        pApplet.ellipse(position.x, position.y, radius * 2.5f, radius * 2.5f)
        pApplet.image(face, position.x, position.y, radius * 2, radius * 2)
    }
}