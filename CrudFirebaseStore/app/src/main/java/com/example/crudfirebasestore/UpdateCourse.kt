package com.example.crudfirebasestore

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudfirebasestore.ui.theme.CrudFirebaseStoreTheme
import com.example.crudfirebasestore.ui.theme.greenColor
import com.google.firebase.firestore.FirebaseFirestore

class UpdateCourse : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudFirebaseStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                backgroundColor = greenColor,
                                title = {
                                    Text(
                                        text = "Update Course",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    ) { innerPadding ->
                        val name = intent.getStringExtra("courseName")
                        val duration = intent.getStringExtra("courseDuration")
                        val description = intent.getStringExtra("courseDescription")
                        val id = intent.getStringExtra("courseID")

                        Column(modifier = Modifier.padding(innerPadding)) {
                            updateUI(
                                LocalContext.current,
                                name,
                                duration,
                                description,
                                id
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun updateUI(
    context: Context,
    name: String?,
    duration: String?,
    description: String?,
    id: String?
) {
    val courseName = remember { mutableStateOf(name ?: "") }
    val courseDuration = remember { mutableStateOf(duration ?: "") }
    val courseDescription = remember { mutableStateOf(description ?: "") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = courseName.value,
            onValueChange = { courseName.value = it },
            placeholder = { Text(text = "Enter your course name") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseDuration.value,
            onValueChange = { courseDuration.value = it },
            placeholder = { Text(text = "Enter your course duration") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseDescription.value,
            onValueChange = { courseDescription.value = it },
            placeholder = { Text(text = "Enter your course description") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (TextUtils.isEmpty(courseName.value)) {
                    Toast.makeText(context, "Please enter course name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(courseDuration.value)) {
                    Toast.makeText(context, "Please enter course duration", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(courseDescription.value)) {
                    Toast.makeText(context, "Please enter course description", Toast.LENGTH_SHORT).show()
                } else {
                    updateDataToFirebase(
                        id,
                        courseName.value,
                        courseDuration.value,
                        courseDescription.value,
                        context
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Update Data", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                deleteDataFromFirebase(id, context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Delete Course", modifier = Modifier.padding(8.dp), color = Color.White)
        }
    }
}

private fun updateDataToFirebase(
    id: String?,
    name: String,
    duration: String,
    description: String,
    context: Context
) {
    val updatedCourse = Course(id, name, duration, description)
    val db = FirebaseFirestore.getInstance()
    if (id != null) {
        db.collection("Courses").document(id).set(updatedCourse)
            .addOnSuccessListener {
                Toast.makeText(context, "Course Updated successfully..", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Fail to update course", Toast.LENGTH_SHORT).show()
            }
    }
}

private fun deleteDataFromFirebase(id: String?, context: Context) {
    val db = FirebaseFirestore.getInstance()
    if (id != null) {
        db.collection("Courses").document(id).delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Course Deleted successfully..", Toast.LENGTH_SHORT).show()
                (context as ComponentActivity).finish()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Fail to delete course", Toast.LENGTH_SHORT).show()
            }
    }
}
