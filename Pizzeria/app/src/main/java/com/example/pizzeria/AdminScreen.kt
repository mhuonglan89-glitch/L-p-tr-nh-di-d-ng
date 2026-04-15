package com.example.pizzeria

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@Composable
fun AdminScreen(navController: NavController) {

    val database = FirebaseDatabase
        .getInstance("https://pizzeria-4f8b7-default-rtdb.firebaseio.com/")
        .getReference("users")

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("user") }
    var imageUrl by remember { mutableStateOf("") }
    var editingUserId by remember { mutableStateOf<String?>(null) }

    val userList = remember { mutableStateListOf<UserModel>() }

    // ================== LISTENER ==================
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                snapshot.children.forEach {
                    val user = it.getValue(UserModel::class.java)
                    if (user != null) userList.add(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        database.addValueEventListener(listener)
        onDispose { database.removeEventListener(listener) }
    }

    // ================== UI ==================
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ===== HEADER =====
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "QUẢN TRỊ VIÊN",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFB71C1C),
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                auth.signOut()
                navController.navigate(Screen.Signin.rout) { popUpTo(0) }
            }) {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Đăng xuất",
                    tint = Color.Gray
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        // ===== INPUT =====
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = role,
            onValueChange = { role = it },
            label = { Text("Role (admin/user)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Link ảnh") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        // ===== BUTTON =====
        Button(
            onClick = {

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Nhập thiếu thông tin!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (editingUserId == null) {

                    // ===== THÊM USER =====
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val uid = result.user?.uid ?: ""

                            val user = UserModel(
                                id = uid,
                                username = email,
                                password = password,
                                role = role,
                                image = imageUrl
                            )

                            database.child(uid).setValue(user)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Tạo user thành công!", Toast.LENGTH_SHORT).show()

                                    email = ""
                                    password = ""
                                    role = "user"
                                    imageUrl = ""
                                }
                        }

                } else {

                    // ===== UPDATE DATABASE ONLY =====
                    val user = UserModel(
                        id = editingUserId!!,
                        username = email,
                        password = password,
                        role = role,
                        image = imageUrl
                    )

                    database.child(editingUserId!!)
                        .setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()

                            email = ""
                            password = ""
                            role = "user"
                            imageUrl = ""
                            editingUserId = null
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C))
        ) {
            Text(if (editingUserId == null) "TẠO MỚI" else "CẬP NHẬT")
        }

        HorizontalDivider(Modifier.padding(vertical = 12.dp))

        // ===== LIST USER =====
        LazyColumn {
            items(userList) { user ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = rememberAsyncImagePainter(
                                user.image.ifEmpty {
                                    "https://cdn-icons-png.flaticon.com/512/149/149071.png"
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(55.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                        ) {
                            Text(user.username, fontWeight = FontWeight.Bold)
                            Text("Quyền: ${user.role}")
                        }

                        // ===== EDIT =====
                        IconButton(onClick = {
                            email = user.username
                            password = user.password
                            role = user.role
                            imageUrl = user.image
                            editingUserId = user.id
                        }) {
                            Icon(Icons.Default.Edit, null, tint = Color.Blue)
                        }

                        // ===== DELETE =====
                        IconButton(onClick = {
                            database.child(user.id).removeValue()
                        }) {
                            Icon(Icons.Default.Delete, null, tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}