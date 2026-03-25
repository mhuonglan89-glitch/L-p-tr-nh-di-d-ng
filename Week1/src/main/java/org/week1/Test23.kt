package org.example

fun main() {
    val guests = listOf("Veronica", "Riccardo", "Gwen", "Marcus", "Subito")

    var index = 0
    while (index < guests.size) {
        println(guests[index])
        index++
    }

    for (guest in guests) {
        println(guest)
    }
}