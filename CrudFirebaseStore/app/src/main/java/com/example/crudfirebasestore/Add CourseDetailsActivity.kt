package com.example.crudfirebasestore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Card
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudfirebasestore.ui.theme.CrudFirebaseStoreTheme
import com.example.crudfirebasestore.ui.theme.greenColor
import com.google.firebase.firestore.FirebaseFirestore

class CourseDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudFirebaseStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Tạo list state ở mức Composable để Jetpack Compose theo dõi được thay đổi
                    val courseList = remember { mutableStateListOf<Course?>() }
                    val db = FirebaseFirestore.getInstance()

                    // LaunchedEffect giúp lấy dữ liệu một lần duy nhất khi màn hình mở lên
                    LaunchedEffect(Unit) {
                        db.collection("Courses").get()
                            .addOnSuccessListener { queryDocumentSnapshots ->
                                if (!queryDocumentSnapshots.isEmpty) {
                                    val list = queryDocumentSnapshots.documents
                                    for (d in list) {
                                        val c: Course? = d.toObject(Course::class.java)
                                        if (c != null) {
                                            c.courseID = d.id
                                            courseList.add(c)
                                        }
                                    }
                                } else {
                                    Toast.makeText(this@CourseDetailsActivity, "No data found", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this@CourseDetailsActivity, "Fail to get data", Toast.LENGTH_SHORT).show()
                            }
                    }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Danh Sách Khóa Học",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                },
                                // Lưu ý: Cần import androidx.compose.material3.TopAppBarDefaults nếu muốn đổi màu TopAppBar M3
                            )
                        }
                    ) { innerPadding ->
                        // Truyền innerPadding vào để danh sách không bị đè bởi TopAppBar
                        Column(modifier = Modifier.padding(innerPadding)) {
                            firebaseUI(LocalContext.current, courseList)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun firebaseUI(context: Context, courseList: SnapshotStateList<Course?>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(courseList) { index, item ->
                    Card(
                        onClick = {
                            // ĐÃ MỞ KHÓA: Chuyển sang màn hình Update
                            val i = Intent(context, UpdateCourse::class.java)
                            i.putExtra("courseName", item?.courseName)
                            i.putExtra("courseDuration", item?.courseDuration)
                            i.putExtra("courseDescription", item?.courseDescription)
                            i.putExtra("courseID", item?.courseID)
                            context.startActivity(i)
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        elevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = item?.courseName ?: "No Name",
                                color = greenColor,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Thời gian: ${item?.courseDuration}",
                                color = Color.Black,
                                style = TextStyle(fontSize = 15.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item?.courseDescription ?: "",
                                color = Color.Gray,
                                style = TextStyle(fontSize = 14.sp)
                            )
                        }
                    }
                }
            }
        }
    }
}