package org.example

fun main() {
    val peopleAges = mutableMapOf<String, Int>(
        "Fred" to 30,
        "Ann" to 23,
        "Barbara" to 42,
        "Joe" to 51
    )

    val filteredNames = peopleAges.filter { it.key.length < 4 }
    println(filteredNames)

    val descriptions = peopleAges.map { "${it.key} is ${it.value}" }
    println(descriptions)

    val tripleAges = peopleAges.map { it.value * 3 }
    println(tripleAges)

    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    println(colors.zip(animals))

    val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    println(numberSets.flatten())

    val words = listOf("about", "acute", "balloon", "best", "brief", "class", "coffee", "creative")
    val filteredWords = words.filter { it.startsWith("b", ignoreCase = true) }
        .shuffled()
        .take(2)
        .sorted()
    println(filteredWords)
}