package com.phune.baitaptuan2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Blue = Color(0xFF304777)
private val Gray = Color(0xFF7B8089)
private val Background = Color(0xFFF4F3F8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // Đổi số ở đây để chạy từng bài.
                val selectedExercise = 1

                when (selectedExercise) {
                    1 -> CalculatorScreen()
                    2 -> StudentListScreen()
                    3 -> ClassificationScreen()
                    4 -> ScoreboardScreen()
                }
            }
        }
    }
}

// ==================== MODEL ====================

data class Student(
    val name: String,
    val email: String?,
    val phone: String?
)

data class ScoreStudent(
    val name: String,
    val score: Double?
)


// FUNCTION dùng chung =)
fun classify(score: Double): String {
    return if (!score.isFinite() || score < 0 || score > 10) {
        "Không hợp lệ"
    } else if (score >= 8) {
        "Giỏi"
    } else if (score >= 6.5) {
        "Khá"
    } else if (score >= 5) {
        "Trung bình"
    } else {
        "Yếu"
    }
}

fun classificationColor(score: Double): Color {
    return if (!score.isFinite() || score < 0 || score > 10) {
        Gray
    } else if (score >= 8) {
        Color(0xFF387448)
    } else if (score >= 6.5) {
        Color(0xFF3865AE)
    } else if (score >= 5) {
        Color(0xFFA46A24)
    } else {
        Color(0xFFB84B45)
    }
}

fun formatNumber(number: Double): String {
    return if (number == 0.0) {
        "0"
    } else {
        java.math.BigDecimal.valueOf(number)
            .stripTrailingZeros()
            .toPlainString()
    }
}

// Khung Man Hình chung
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text(title, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}

@Composable
fun WhiteCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = content
    )
}

// Bài 1: Máy Tính

@Composable
fun CalculatorScreen() {
    var firstInput by rememberSaveable { mutableStateOf("") }
    var secondInput by rememberSaveable { mutableStateOf("") }
    var operation by rememberSaveable { mutableStateOf("") }


    val first = firstInput.replace(',', '.').toDoubleOrNull()
    val second = secondInput.replace(',', '.').toDoubleOrNull()

    val result: String = if (operation.isEmpty()) {
        ""
    } else if (first == null || second == null) {
        "Nhập đủ hai số hợp lệ"
    } else if (!first.isFinite() || !second.isFinite()) {
        "Số nhập không hợp lệ"
    } else if (operation == "/" && second == 0.0) {
        "Không thể chia cho 0"
    } else {
        val value = when (operation) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            else -> first / second
        }

        if (value.isFinite()) {
            formatNumber(value)
        } else {
            "Kết quả vượt giới hạn"
        }
    }

    val symbols = listOf("+", "-", "*", "/")
    val colors = listOf(
        Color(0xFFD94740),
        Color(0xFFDCA846),
        Color(0xFF623BDB),
        Color(0xFF222222)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(100.dp))

        Text(
            text = "Thực hành 03",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(60.dp))

        OutlinedTextField(
            value = firstInput,
            onValueChange = { firstInput = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Nhập số thứ nhất") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (index in symbols.indices) {
                val symbol = symbols[index]

                Button(
                    onClick = { operation = symbol },
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors[index]
                    ),
                    border = if (operation == symbol) {
                        BorderStroke(3.dp, Color.Black)
                    } else {
                        null
                    }
                ) {
                    Text(symbol, fontSize = 24.sp)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = secondInput,
            onValueChange = { secondInput = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Nhập số thứ hai") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Kết quả: $result",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp
        )
    }
}

// Bài 2:Nullable

@Composable
fun StudentListScreen() {
    val students = listOf(
        Student("Nguyễn Văn An", "an@sv.edu.vn", "0901 234 567"),
        Student("Trần Thị Bình", null, "0912 888 999"),
        Student("Lê Minh Chi", "chi@sv.edu.vn", null),
        Student("Phạm Quốc Dũng", null, null),
        Student("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222")
    )

    var missingEmail = 0

    for (student in students) {
        if (student.email == null) {
            missingEmail++
        }
    }

    ExerciseScreen("Danh sách sinh viên") {
        for (student in students) {
            WhiteCard {
                Text(
                    text = student.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )


                Text(
                    text = "Email: ${student.email ?: "Chưa cập nhật"}",
                    color = if (student.email == null) {
                        Color(0xFFBA654B)
                    } else {
                        Gray
                    },
                    fontSize = 13.sp
                )

                Text(
                    text = "SĐT: ${student.phone ?: "Chưa cập nhật"}",
                    color = if (student.phone == null) {
                        Color(0xFFBA654B)
                    } else {
                        Gray
                    },
                    fontSize = 13.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFE6EBF5),
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tổng: ${students.size} sinh viên", fontSize = 13.sp)
            Text("Thiếu email: $missingEmail", fontSize = 13.sp)
        }
    }
}

//Bài 3: If ELSE

@Composable
fun ClassificationScreen() {
    val score = 8.5
    val testScores = listOf(9.0, 7.0, 5.5, 3.0, 11.0)
    val color = classificationColor(score)

    ExerciseScreen("Xếp loại học lực") {
        WhiteCard {
            Text(
                text = "Điểm trung bình",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Gray
            )

            Text(
                text = score.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = classify(score),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color.copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = color,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            "KIỂM TRA THÊM",
            color = Gray,
            fontWeight = FontWeight.Bold
        )

        WhiteCard {
            for (value in testScores) {
                val itemColor = classificationColor(value)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Điểm ${formatNumber(value)}")

                    Text(
                        text = classify(value),
                        color = itemColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .background(
                                itemColor.copy(alpha = 0.1f),
                                RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// Bài 4

@Composable
fun ScoreboardScreen() {
    val students = listOf(
        ScoreStudent("Nguyễn Văn An", 9.0),
        ScoreStudent("Trần Thị Bình", 7.2),
        ScoreStudent("Lê Minh Chi", null),
        ScoreStudent("Phạm Quốc Dũng", 4.5),
        ScoreStudent("Võ Thanh Em", 5.8)
    )

    var total = 0.0
    var scoredCount = 0
    var excellentCount = 0
    var missingCount = 0
    var highest: Double? = null

    for (student in students) {
        val score = student.score

        if (score == null) {
            missingCount++
        } else if (score.isFinite() && score >= 0 && score <= 10) {
            total += score
            scoredCount++

            if (score >= 8) {
                excellentCount++
            }

            val currentHighest = highest
            if (currentHighest == null || score > currentHighest) {
                highest = score
            }
        }
    }

    val averageText = if (scoredCount > 0) {
        String.format(java.util.Locale.US, "%.2f", total / scoredCount)
    } else {
        "Chưa có điểm"
    }

    ExerciseScreen("Bảng điểm lớp") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Blue, RoundedCornerShape(14.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Điểm trung bình lớp", color = Color.White.copy(0.7f))

            Text(
                averageText,
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Statistic("Giỏi", "$excellentCount")
                Statistic("Chưa có điểm", "$missingCount")
                Statistic("Cao nhất", highest?.toString() ?: "—")
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            "DANH SÁCH · ${students.size} SINH VIÊN",
            color = Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        for (student in students) {
            val score = student.score

            WhiteCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            student.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(4.dp))

                        if (score == null) {
                            Text(
                                "Chưa có điểm",
                                color = Gray,
                                fontSize = 13.sp
                            )
                        } else {
                            Text(
                                classify(score),
                                color = classificationColor(score),
                                fontSize = 13.sp
                            )
                        }
                    }

                    Text(
                        text = score?.toString() ?: "—",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (score == null) Gray else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun Statistic(label: String, value: String) {
    Column {
        Text(
            label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
        Text(
            value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
