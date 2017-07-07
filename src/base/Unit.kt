package base

/**
 * Created by mickeycj on 7/6/2017 AD.
 */
interface Unit : Component {

    fun setPosition(x: Float, y: Float)

    fun eat(other: Unit): Boolean

    fun reset()
}