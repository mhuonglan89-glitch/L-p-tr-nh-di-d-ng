package org.example

fun main() {
    val s1 = "Hello"
    val s2 = "Hello"
    val s3 = "hello"

    println(s1 == s2)
    println(s1 == s3)

    val s4 = "HELLO"
    println(s1.equals(s4, ignoreCase = true))

    val s5 = "How are you?"
    println(s5.contains("how", ignoreCase = true))

    val s6 = "Welcome to Kotlin"
    println(s6.substring(0, 7))
    println(s6.substring(11))

    val s7 = "123-456-789"
    val parts = s7.split("-")
    println(parts)
}