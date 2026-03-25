package org.example
import kotlinx.coroutines.*

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "30°C"
}

fun main() {
    runBlocking {
        println("Weather forecast")

        try {
            val forecast: Deferred<String> = async {
                getForecast()
            }

            val temperature: Deferred<String> = async {
                getTemperature()
            }

            println("${forecast.await()} ${temperature.await()}")
        } catch (e: Exception) {
            println("Caught exception: $e")
        }

        println("Have a nice day!")
    }
}