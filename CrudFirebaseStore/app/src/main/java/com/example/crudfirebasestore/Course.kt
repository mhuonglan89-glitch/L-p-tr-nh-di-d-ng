package com.example.crudfirebasestore
import com.google.firebase.firestore.Exclude

data class Course(
    var courseID: String? = null,
    var courseName: String? = null,
    var courseDuration: String? = null,
    var courseDescription: String? = null
)