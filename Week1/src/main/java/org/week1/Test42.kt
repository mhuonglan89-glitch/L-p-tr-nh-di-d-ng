package org.example

data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}

fun main() {
    val question1 = Question<String>("Quack, quack, quack?", "Duck", Difficulty.EASY)
    val question2 = Question<Int>("How many days in a week?", 7, Difficulty.MEDIUM)
    val question3 = Question<Boolean>("Is Kotlin awesome?", true, Difficulty.HARD)

    println(question1.toString())

    val question4 = question1.copy(questionText = "What does the fox say?")
    println(question4)

    println("Question: ${question2.questionText}, Answer: ${question2.answer}, Difficulty: ${question2.difficulty}")
}