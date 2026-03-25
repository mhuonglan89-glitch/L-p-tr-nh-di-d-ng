package org.example

fun main() {
    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    val setOfNumbers = numbers.toSet()
    println(setOfNumbers)

    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 4, 5)

    println(set1.intersect(set2))
    println(set1.union(set2))
}