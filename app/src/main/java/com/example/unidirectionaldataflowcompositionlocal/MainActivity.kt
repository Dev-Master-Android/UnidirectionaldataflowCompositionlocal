package com.example.unidirectionaldataflowcompositionlocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIApp()
        }
    }
}

@Composable
fun BMIApp() {
    var height by remember { mutableDoubleStateOf(0.0) }
    var weight by remember { mutableDoubleStateOf(0.0) }

    val bmi = calculateBMI(height, weight)
    val interpretation = interpretBMI(bmi)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BMIInputCard(height, weight, { height = it }, { weight = it }, bmi, interpretation)
    }
}

@Composable
fun BMIInputCard(
    height: Double,
    weight: Double,
    onHeightChange: (Double) -> Unit,
    onWeightChange: (Double) -> Unit,
    bmi: Double,
    interpretation: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Gray, shape = RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Калькулятор ИМТ",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp),
            color = Color.White
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Рост: ${"%.2f".format(height)} м",
                modifier = Modifier
                    .clickable { onHeightChange((height + 0.05).coerceAtMost(2.50)) }
                    .padding(8.dp),
                fontSize = 20.sp
            )
            Text(
                text = "Вес: ${"%.1f".format(weight)} кг",
                modifier = Modifier
                    .clickable { onWeightChange(weight + 5.0) }
                    .padding(8.dp),
                fontSize = 20.sp
            )
            Text(
                text = "Индекс массы тела: ${"%.2f".format(bmi)}",
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp
            )
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                Text(text = interpretation, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            }

            Button(onClick = {
                onHeightChange(0.0)
                onWeightChange(0.0)
            }, modifier = Modifier.padding(top = 24.dp)) {
                Text(text = "Сбросить")
            }
        }
    }
}

fun calculateBMI(height: Double, weight: Double): Double {
    return if (height > 0) weight / (height.pow(2)) else 0.0
}

fun interpretBMI(bmi: Double): String {
    return when {
        bmi < 16 -> "Выраженный дефицит массы тела"
        bmi < 18.5 -> "Недостаточная масса тела"
        bmi < 25 -> "Нормальная масса тела"
        bmi < 30 -> "Избыточная масса тела (предожирение)"
        bmi < 35 -> "Ожирение 1-ой степени"
        bmi < 40 -> "Ожирение 2-ой степени"
        else -> "Ожирение 3-ей степени"
    }
}