package org.example

fun main() {
    val planets = listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
    println(planets.size)
    println(planets[2])
    println(planets.get(3))
    println(planets.indexOf("Earth"))

    val ordersList = mutableListOf<String>()
    ordersList.add("Spaghetti")
    ordersList.add("Tea")
    ordersList.add("Coffee")
    println(ordersList)

    ordersList.remove("Tea")
    println(ordersList)

    println(ordersList.size)
    println(ordersList.isEmpty())
    println(ordersList.contains("Spaghetti"))

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