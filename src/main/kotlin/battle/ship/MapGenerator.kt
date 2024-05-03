package battle.ship

import java.util.*

class MapGenerator {
    enum class Direction {
        UP, DOWN, RIGHT, LEFT;
    }

    fun empty(): BattleshipMap {
        val mapSize = 10
        val arr = MutableList(mapSize) { MutableList(mapSize) { -2 } }
        return BattleshipMap(arr, mapSize)
    }

    fun generate(): BattleshipMap {

        fun validateCoordinates(i: Int, j: Int, shipType: Int, arr: MutableList<MutableList<Int>>) {
            println("validate: $i  $j")
            if (i < 0 || j < 0) {
                throw CustomException("error while creating ship $shipType on i=$i and j = $j index ix less than zero")
            }

            if (i >= arr.size || j >= arr.size) {
                throw CustomException("error while creating ship $shipType on i=$i and j = $j index is more than map size")
            }

            if (arr[i][j] != 0) throw CustomException("error while creating ship $shipType on i=$i and j = $j this index already bussy")


            fun check(internalI: Int, internalJ: Int): Boolean {

                if (internalI < 0 || internalJ < 0) {
                    return true
                }
                if (internalI >= arr.size || internalJ >= arr.size) {
                    return true
                }

                if (arr[internalI][internalJ] in listOf(0, shipType)) {
                    return true
                } else {
                    throw CustomException("error while creating ship $shipType on i=$i and j = $j adjacent ship ${arr[internalI][internalJ]}exist ")
                }

            }

            if (check(i + 1, j) && check(i - 1, j) && check(i, j + 1) && check(i, j - 1)) {

            } else {
                throw CustomException("error while creating ship $shipType on i=$i and j = $j")
            }

        }

        fun go(i: Int, j: Int, shipType: Int, arr: MutableList<MutableList<Int>>) {
            arr[i][j] = shipType
        }


        val mapSize = 10
        val ships = LinkedList<Pair<Int, Int>>()
        ships.add(4 to 1)
        ships.add(3 to 2)
        ships.add(2 to 3)
        ships.add(1 to 4)


        val arr = MutableList(mapSize) { MutableList(mapSize) { 0 } }
        var dd = 1
        while (ships.isNotEmpty()) {

            val ship = ships.pop()

            for (shipCount in 1..ship.second) {
                var shipPlacementFailed = true

                do {
                    try {

                        val randomStartX = Random().nextInt(0, 10)
                        val randomStartY = Random().nextInt(0, 10)

                        val randomDirection = Direction.entries[Random().nextInt(Direction.entries.size)]

                        when (randomDirection) {
                            Direction.UP -> {
                                for (index in randomStartX downTo randomStartX - ship.first + 1) {
                                    validateCoordinates(index, randomStartY, dd, arr)
                                }
                                for (index in randomStartX downTo randomStartX - ship.first + 1) {
                                    go(index, randomStartY, dd, arr)
                                }
                            }

                            Direction.DOWN -> {
                                for (index in randomStartX until randomStartX + ship.first) {
                                    validateCoordinates(index, randomStartY, dd, arr)
                                }
                                for (index in randomStartX until randomStartX + ship.first) {
                                    go(index, randomStartY, dd, arr)
                                }
                            }

                            Direction.LEFT -> {
                                for (index in randomStartY downTo randomStartY - ship.first + 1) {
                                    validateCoordinates(randomStartX, index, dd, arr)
                                }
                                for (index in randomStartY downTo randomStartY - ship.first + 1) {
                                    go(randomStartX, index, dd, arr)
                                }
                            }

                            Direction.RIGHT -> {
                                for (index in randomStartY until randomStartY + ship.first) {
                                    validateCoordinates(randomStartX, index, dd, arr)
                                }
                                for (index in randomStartY until randomStartY + ship.first) {
                                    go(randomStartX, index, dd, arr)
                                }
                            }
                        }
                        shipPlacementFailed = false
                        dd++
                    } catch (e: CustomException) {
                        println("error: $e.message")
                    }
                } while (shipPlacementFailed)
            }

        }

        for (i in 0 until mapSize) {
            for (j in 0 until mapSize) {
                arr[i][j] = when (arr[i][j]) {
                    in 7..10 -> 1
                    in 4..6 -> 2
                    in 2..3 -> 3
                    1 -> 4
                    else -> 0
                }
            }
        }

        return BattleshipMap(arr, mapSize)
    }
}

data class BattleshipMap(private var map: MutableList<MutableList<Int>>, val size: Int) {

    private val EMPTY_CELL_SYMBOL = '□'
    private val FILLED_CELL_SYMBOL = '■'

    operator fun get(i: Int, j: Int) = map[i][j]
    operator fun set(i: Int, j: Int, value: Int) {
        map[i][j] = value
    }

    override fun toString(): String {

        val mapOf = mapOf(0 to EMPTY_CELL_SYMBOL, -1 to 'X', -2 to '?')

        val sb = StringBuilder("  ")
        for (i in 0..9) {
            sb.append("$i ")
        }
        sb.append("\n")
        for (i in 0 until size) {
            sb.append((i+65).toChar()).append(" ")
            for (k in 0 until size) {
                val i1 = mapOf.getOrDefault(this.map[i][k], FILLED_CELL_SYMBOL)
                sb.append("$i1 ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }


}

class CustomException(s: String) : Exception(s)

fun main() {

    val generate = MapGenerator().generate()
    println(generate)
}