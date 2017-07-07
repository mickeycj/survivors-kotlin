import game.Game
import processing.core.PApplet
import processing.core.PFont
import processing.core.PImage
import java.awt.Font
import java.awt.FontFormatException
import java.io.File
import java.io.FileInputStream

/**
 * Created by mickeycj on 7/7/2017 AD.
 */
class Survivors : PApplet() {

    lateinit var faces: MutableList<PImage>
    lateinit var startScreen: PImage
    lateinit var mainScreen: PImage
    lateinit var gameOverScreen: PImage

    lateinit var game: Game

    override fun settings() {
        size(1080, 640)
        faces = ArrayList()
        startScreen = loadImage("res/images/backgrounds/start.png")
        mainScreen = loadImage("res/images/backgrounds/main.png")
        gameOverScreen = loadImage("res/images/backgrounds/game_over.png")
        File("res/images/faces").listFiles().forEach { file ->
            if (file.name.endsWith(".png")) {
                if (file.name == "0.png") {
                    faces.add(0, loadImage(file.path))
                } else {
                    faces.add(loadImage(file.path))
                }
            }
        }
    }

    override fun setup() {
        try {
            textFont(PFont(Font.createFont(Font.TRUETYPE_FONT, FileInputStream("res/fonts/OCR")), true))
        } catch (e: FontFormatException) {
            e.printStackTrace()
        }
        imageMode(CENTER)
        image(startScreen, (width / 2).toFloat(), (height / 2).toFloat())
        game = Game(this@Survivors, faces)
    }

    override fun draw() {
        if (game.started) {
            if (!game.isEnded()) {
                image(mainScreen, (width / 2).toFloat(), (height / 2).toFloat())
                game.update()
            } else {
                image(gameOverScreen, (width / 2).toFloat(), (height / 2).toFloat())
                game.showStats()
            }
        }
    }

    override fun keyPressed() {
        if (!game.started) {
            game.start()
        } else if (game.isEnded()) {
            game.restart()
        }
    }
}

fun main(args: Array<String>) {
    PApplet.main("Survivors")
}