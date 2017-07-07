package game

import base.Component
import processing.core.PApplet
import processing.core.PImage

/**
 * Created by mickeycj on 7/6/2017 AD.
 */
class World(val pApplet: PApplet, faces: List<PImage>) : Component {

    val player: Player = Player(pApplet, faces[0])
    val enemies: MutableList<Enemy> = ArrayList()
    val enemyPool: MutableList<Enemy> = ArrayList()

    val enemySizes = floatArrayOf(
            15f,
            22.5f,
            30f,
            37.5f,
            45f
    ).toTypedArray()
    val enemyLimitsPerLevel = arrayOf(
            intArrayOf(8, 5, 2, 0, 0),
            intArrayOf(3, 5, 5, 3, 0),
            intArrayOf(2, 3, 5, 7, 5)
    )

    var enemyCounts = IntArray(5)
    var highScore: Int = 0

    init {
        faces.indices.mapTo(enemyPool) { Enemy(pApplet, faces[it]) }
        initEnemies()
    }

    fun isPlayerAlive(): Boolean = player.alive

    fun getPlayerLevel(): Int = player.level

    fun getPlayerScore(): Int = player.score

    fun isNumEnemiesTooLow(): Boolean = enemies.size < limitOfLevel(player.level)

    fun addRandomEnemies() {
        (1..player.level + 2)
                .filter { !enemyPool.isEmpty() && enemyCounts[it-1] != enemyLimitsPerLevel[player.level-1][it-1] }
                .forEach { addRandomEnemy(it) }
    }

    fun reset() {
        initEnemies()
    }

    private fun initEnemies() {
        player.reset()
        enemyPool.addAll(enemies)
        enemies.clear()
        enemyPool.forEach { it.reset() }
        enemyCounts = IntArray(5)
        var level = 1
        for (i in 0..limitOfLevel(player.level) - 1) {
            if (enemyCounts[level-1] == enemyLimitsPerLevel[0][level-1]) {
                level++
            }
            addRandomEnemy(level)
        }
    }

    private fun addRandomEnemy(level: Int) {
        val index = (Math.random() * enemyPool.size).toInt()
        val enemy = enemyPool[index]
        enemyPool.removeAt(index)
        val position = randomPosition()
        enemy.setPosition(position[0], position[1])
        enemy.setRadiusAndSize(level)
        enemies.add(enemy)
        enemyCounts[level-1]++
    }

    private fun limitOfLevel(lvl: Int): Int = enemyLimitsPerLevel.indices.sumBy { enemyLimitsPerLevel[lvl-1][it] }

    private fun randomPosition(): Array<Float> {
        val x = (Math.random() * pApplet.width).toFloat()
        val y = (Math.random() * pApplet.height).toFloat()
        return if (x > player.position.x - 250 && x < player.position.x + 250
                && y > player.position.y - 33 && y < player.position.y + 273) {
            randomPosition()
        } else {
            floatArrayOf(x, y).toTypedArray()
        }
    }

    override fun update() {
        for (i in enemies.indices) {
            val enemy = enemies[i]
            if (player.eat(enemy)) {
                if (player.radius >= enemySizes[player.level]) {
                    player.levelUp()
                }
                enemyPool.add(enemy)
                enemyCounts[enemy.level-1]--
                if (player.score > highScore) {
                    highScore = player.score
                }
            } else {
                if (enemy.eat(player)) {
                    player.die()
                } else if ((pApplet.frameCount + i * 100) % 120 == 0) {
                    enemy.setDestination()
                }
                enemy.update()
            }
        }
        enemies.removeAll(enemyPool)
        player.update()
    }

    override fun render() {
        enemies.forEach { it.render() }
        player.render()
    }
}