package org.example

class Person(var name: String, var age: Int, var city: String) {
    fun moveTo(newCity: String) { city = newCity }
    fun incrementAge() { age++ }
}

fun main() {
    val person = Person("Alice", 20, "London")

    val nextAge = person.let {
        it.incrementAge()
        it.age
    }
    println(nextAge)

    val resultWith = with(person) {
        println(name)
        println(age)
        "Finished printing"
    }
    println(resultWith)

    val resultRun = person.run {
        name = "Bob"
        age = 25
        "Person updated to $name"
    }
    println(resultRun)

    val personApply = person.apply {
        name = "Charlie"
        city = "Paris"
    }
    println(personApply.name)

    val personAlso = person.also {
        println("Logging person: ${it.name}")
    }
}