package org.example

fun main() {
    val equipment = "fish net" to "catching fish"
    println(equipment.first)
    println(equipment.second)

    val numbers = Triple(6, 9, 42)
    println(numbers.first)
    println(numbers.second)
    println(numbers.third)

    val (tool, use) = equipment
    println("$tool is used for $use")

    val (n1, n2, n3) = numbers
    println("$n1 $n2 $n3")

    try {
        val division = 10 / 0
    } catch (e: Exception) {
        println("Caught an exception: ${e.message}")
    } finally {
        println("This always executes")
    }
}

@Target(AnnotationTarget.PROPERTY)
annotation class OnBehalfOf(val name: String)

class Example {
    @OnBehalfOf("Google")
    val info = "Kotlin Guide"
}