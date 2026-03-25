package org.example

fun main() {
    var favoriteNumber: Int? = null
    println(favoriteNumber)

    favoriteNumber = 10
    println(favoriteNumber)

    var number: Int = 5
    println(number is Int)

    val str: Any = "Hello"
    val safeStr: String? = str as? String
    println(safeStr)

    val unsafeStr: String = str as String
    println(unsafeStr)
}