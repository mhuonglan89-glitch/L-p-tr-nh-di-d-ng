package com.example.myapplication
fun printHello() {
    println("Hello Kotlin")
}
// This is a comment line.
// This is another comment.
fun printBorder(border: String, timesToRepeat: Int) {
    repeat(timesToRepeat) {
        print(border)
    }
    println()
}

fun roll(): Int {
    val randomNumber = (1..6).random()
    return randomNumber
}

fun main() {
    println("Hello, world!")
    println("This is the text to print!")

    val age = "5"
    val name = "Rover"
    var roll = 6
    var rolledValue: Int = 4

    println("You are already ${age}!")
    println("You are already ${age} days old, ${name}!")

    printHello()

    printBorder("=", 23)

    val diceRoll = roll()
    println(diceRoll)

    val diceRange = 1..6
    val randomNumber = diceRange.random()
    println(randomNumber)
}