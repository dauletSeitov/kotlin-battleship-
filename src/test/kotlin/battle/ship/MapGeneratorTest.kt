package battle.ship

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


//test regarding to rules https://ru.wikipedia.org/wiki/%D0%9C%D0%BE%D1%80%D1%81%D0%BA%D0%BE%D0%B9_%D0%B1%D0%BE%D0%B9_(%D0%B8%D0%B3%D1%80%D0%B0)

class MapGeneratorTest {

    private val mapGenerator = MapGenerator()

    private val size = 10

    @Test
    fun `validate size of map `() {
        val map = mapGenerator.generate()
        assertThat(map.size).isEqualTo(size)
    }


//    @Test
//    fun `exists 4 cell ship`() {
//        val map = mapGenerator.generate()
//
//        fun right(i: Int, k: Int): Int {
//            var fourCellShipCellCount = 0
//            for (index in k until map.size) {
//                if (map[i, index] == 4) {
//                    fourCellShipCellCount++
//                }
//            }
//            return fourCellShipCellCount
//        }
//
//        fun down(i: Int, k: Int): Int {
//            var fourCellShipCellCount = 0
//            for (index in i until map.size) {
//                if (map[index, k] == 4) {
//                    fourCellShipCellCount++
//                }
//            }
//            return fourCellShipCellCount
//        }
//
//        for (i in 0 until size) {
//            for (k in 0 until size) {
//                if (map[i, k] == 4) {
//
//                    if(right(i,k) == 4 |)
//
//
//                }
//            }
//        }
//    }

    @Test
    fun `total ship count must be 10`() {

    }

    @Test
    fun `all ships must be in line `() {

    }

}