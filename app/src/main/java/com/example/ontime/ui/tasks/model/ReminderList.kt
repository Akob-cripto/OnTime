package com.example.ontime.ui.tasks.model

import androidx.compose.ui.graphics.Color
import com.example.ontime.ui.theme.AccentBlue
import com.example.ontime.ui.theme.AccentGreen
import com.example.ontime.ui.theme.AccentOrange
import com.example.ontime.ui.theme.AccentRed

/**
 * Пользовательский список напоминаний (одна «папка» в секции «Мои списки»).
 * Это сущность самого списка, а не коллекция — напоминания хранятся отдельно
 * и ссылаются на список по имени ([Reminder.listName]).
 */
data class ReminderList(
    val id: Long,
    val name: String,
    val color: Color,
    val count: Int = 0,
)

/** Палитра для выбора цвета списка при создании. */
val ReminderListColors: List<Color> = listOf(
    AccentRed,
    AccentOrange,
    Color(0xFFFFD60A), // жёлтый
    AccentGreen,
    AccentBlue,
    Color(0xFFBF5AF2), // фиолетовый
)
