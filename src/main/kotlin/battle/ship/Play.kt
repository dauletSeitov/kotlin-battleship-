package battle.ship

import kotlin.system.exitProcess


class Play

val DESTROYED_CELL = -1
fun main(args: Array<String>) {


    println("welcome to Battleship game!")
    println("enter 's' to start or 'q' to exit")
    val readLine = readlnOrNull()
    if (readLine == "q") {
        exitProcess(1)
    }

    if (readLine == "s") {
        val generate = MapGenerator().generate()
        val empty = MapGenerator().empty()
        val sips = mutableListOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
        var attempts = 0
        println(generate)
        println(empty)
        while (true) {
            println("enter coordinats in 0-9 a-j in format a5")
            val (x, y) = readCordinates()
            if (x == -1) continue
            if (empty[x, y] != -2) {
                println("you are repeating your choice")
                continue
            }
            attempts++
            if (generate[x, y] == 0) {
                empty[x, y] = 0
                println("you have missed")
            } else {
                val shipType = generate[x, y]
                val isDestroyed = dfsIsDestroyed(generate, x, y)
                empty[x, y] = -1
                if (isDestroyed) {
                    println("you have destroyed the $shipType cell ship CONGRATS")
                    sips.remove(shipType)
                    dfsOpen(empty, generate, x, y)
                } else {
                    println("you have hit the ship")
                }
            }
            println(empty)
            println("left ships: $sips")

            if (sips.isEmpty()) {
                println("VICTORY!!!")
                println("attempts: $attempts")
                exitProcess(0)
            }
        }
    }


}

fun dfsOpen(empty: BattleshipMap, generate: BattleshipMap, x: Int, y: Int) {
    fun open(empty: BattleshipMap, i: Int, j: Int) {
        if (i < 0 || j < 0 || i >= generate.size || j >= generate.size) {
            return
        }

        if (empty[i, j] == -2) {
            empty[i, j] = 0
        }

    }

    fun verticalOpen() {

        for (i in x until generate.size) {//right
            if (generate[i, y] == 0) {
                open(empty, i, y)
                break
            }
            if (generate[i, y] == DESTROYED_CELL) {
                open(empty, i, y + 1)
                open(empty, i, y - 1)
            }
        }
        for (i in x downTo 0) {//left
            if (generate[i, y] == 0) {
                open(empty, i, y)
                break
            }
            if (generate[i, y] == DESTROYED_CELL) {
                open(empty, i, y + 1)
                open(empty, i, y - 1)
            }
        }
    }

    fun horisontalOpen() {

        for (i in y until generate.size) {//down
            if (generate[x, i] == 0) {
                open(empty, x, i)
                break
            }
            if (generate[x, i] == DESTROYED_CELL) {
                open(empty, x + 1, i)
                open(empty, x - 1, i)
            }
        }
        for (i in y - 1 downTo 0) {//up
            if (generate[x, i] == 0) {
                open(empty, x, i)
                break
            }
            if (generate[x, i] == DESTROYED_CELL) {
                open(empty, x + 1, i)
                open(empty, x - 1, i)
            }
        }
    }
//    open(empty, x, y)
    horisontalOpen()
    verticalOpen()
}

fun dfsIsDestroyed(generate: BattleshipMap, x: Int, y: Int): Boolean {

    fun verticalCheck(): Int {
        var count = 0
        for (i in x until generate.size) {//right
            if (generate[i, y] == 0) {
                break
            }
            if (generate[i, y] == DESTROYED_CELL) {
                count++
            }
        }
        for (i in x downTo 0) {//left
            if (generate[i, y] == 0) {
                break
            }
            if (generate[i, y] == DESTROYED_CELL) {
                count++
            }
        }
        return count
    }

    fun horisontalCheck(): Int {

        var count = 0
        for (i in y until generate.size) {//down
            if (generate[x, i] == 0) {
                break
            }
            if (generate[x, i] == DESTROYED_CELL) {
                count++
            }
        }
        for (i in y - 1 downTo 0) {//up
            if (generate[x, i] == 0) {
                break
            }
            if (generate[x, i] == DESTROYED_CELL) {
                count++
            }
        }
        return count
    }

    val b = horisontalCheck() + verticalCheck() + 1 == generate[x, y]
    generate[x, y] = -1
    return b

}

fun readCordinates(): Pair<Int, Int> {
    try {
        val coordinatesString = readlnOrNull()!!
        val character = coordinatesString[0].lowercaseChar() - 'a'
        val number = coordinatesString[1].digitToInt()
        if (character in 0..9 && number in 0..9) {
            return character to number
        }
    } catch (e: Exception) {
        println(e)
    }
    return -1 to -1
}
