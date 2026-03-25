package org.example

abstract class Dwelling(private var residents: Int) {
    abstract val buildingMaterial: String
    abstract val capacity: Int

    fun hasRoom(): Boolean {
        return residents < capacity
    }
}

class SquareCabin(residents: Int) : Dwelling(residents) {
    override val buildingMaterial = "Wood"
    override val capacity = 6
}

// Thêm 'open' vào đây để RoundTower có thể kế thừa
open class RoundHut(residents: Int) : Dwelling(residents) {
    override val buildingMaterial = "Straw"
    override val capacity = 4
}

class RoundTower(residents: Int, floors: Int = 2) : RoundHut(residents) {
    override val buildingMaterial = "Stone"
    override val capacity = 4 * floors
}

fun main() {
    val squareCabin = SquareCabin(6)
    val roundHut = RoundHut(3)
    val roundTower = RoundTower(4)

    with(squareCabin) {
        println("\nSquare Cabin\n============")
        println("Capacity: $capacity")
        println("Material: $buildingMaterial")
        println("Has room? ${hasRoom()}")
    }

    with(roundHut) {
        println("\nRound Hut\n=========")
        println("Capacity: $capacity")
        println("Material: $buildingMaterial")
        println("Has room? ${hasRoom()}")
    }

    with(roundTower) {
        println("\nRound Tower\n==========")
        println("Capacity: $capacity")
        println("Material: $buildingMaterial")
        println("Has room? ${hasRoom()}")
    }
}