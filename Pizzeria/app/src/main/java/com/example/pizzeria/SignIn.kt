package com.example.pizzeria

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignIn(navController: NavController) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh Pizza phía trên load từ URL (giống file Word số 2)
        Image(
            painter = rememberAsyncImagePainter("https://img.freepik.com/free-photo/freshly-baked-pizza-with-tomatoes-cheese-ready-eat_23-2149153504.jpg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Wellcome Back PIZZERIA!", color = Color(0xFFB71C1C), style = MaterialTheme.typography.headlineMedium)

        Column(modifier = Modifier.padding(24.dp)) {
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Lấy UID của người vừa đăng nhập thành công
                                val uid = firebaseAuth.currentUser?.uid

                                // Kết nối tới Realtime Database để kiểm tra Role (Phân quyền 1.0 điểm)
                                val db = com.google.firebase.database.FirebaseDatabase.getInstance(
                                    "https://pizzeria-4f8b7-default-rtdb.firebaseio.com/"
                                ).getReference("users")

                                uid?.let {
                                    db.child(it).get().addOnSuccessListener { snapshot ->
                                        // Lấy giá trị role, nếu null thì mặc định là "user"
                                        val role = snapshot.child("role").value?.toString() ?: "user"

                                        if (role == "admin") {
                                            navController.navigate(Screen.Admin.rout) {
                                                popUpTo(Screen.Signin.rout) { inclusive = true }
                                            }
                                        } else {
                                            // Mọi trường hợp còn lại (user hoặc role lạ) đều vào Home
                                            navController.navigate(Screen.Home.rout) {
                                                popUpTo(Screen.Signin.rout) { inclusive = true }
                                            }
                                        }
                                    }.addOnFailureListener {
                                        // Nếu lỗi mạng không lấy được database, vẫn cho vào Home để xem menu
                                        navController.navigate(Screen.Home.rout)
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C))
            ) {
                Text("Sign In")
            }
        }
    }
}