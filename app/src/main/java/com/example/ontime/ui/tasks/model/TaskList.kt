package com.example.ontime.ui.tasks.model

import androidx.compose.ui.graphics.Color
import com.example.ontime.ui.theme.AccentBlue
import com.example.ontime.ui.theme.AccentGreen
import com.example.ontime.ui.theme.AccentOrange
import com.example.ontime.ui.theme.AccentRed

/** Пользовательский список задач (секция «Мои списки»). */
data class TaskList(
    val id: Long,
    val name: String,
    val color: Color,
    val count: Int = 0,
)

/** Палитра для выбора цвета списка при создании. */
val TaskListColors: List<Color> = listOf(
    AccentRed,
    AccentOrange,
    Color(0xFFFFD60A), // жёлтый
    AccentGreen,
    AccentBlue,
    Color(0xFFBF5AF2), // фиолетовый
)
