package com.example.pizzeria

// Class này giúp Firebase hiểu cấu trúc dữ liệu của bạn
data class UserModel(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val role: String = "",
    val image: String = ""
) {
    // Constructor trống để Firebase có thể đọc dữ liệu từ Database
    constructor() : this("", "", "", "user", "")
}