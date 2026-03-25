package org.example

fun main() {
    val age = 5
    val layers = 3
    val num = 4
    val rollResult = (1..6).random()
    val luckyNumber = 4

    repeat(23) {
        print("=")
    }
    println()

    repeat(layers) {
        repeat(age + 2) {
            print("@")
        }
        println()
    }

    if (num > 4) {
        println("The variable is greater than 4")
    } else if (num == 4) {
        println("The variable is equal to 4")
    } else {
        println("The variable is less than 4")
    }

    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("So sorry! You rolled a 1. Try again!")
        2 -> println("Sadly, you rolled a 2. Try again!")
        3 -> println("Unfortunately, you rolled a 3. Try again!")
        4 -> println("No luck! You rolled a 4. Try again!")
        5 -> println("Don't cry! You rolled a 5. Try again!")
        6 -> println("Apologies! you rolled a 6. Try again!")
    }

    val message = when (rollResult) {
        luckyNumber -> "Congratulations!"
        else -> "Better luck next time!"
    }
    println(message)
}