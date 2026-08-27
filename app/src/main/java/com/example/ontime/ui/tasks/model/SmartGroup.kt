package com.example.ontime.ui.tasks.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ontime.ui.theme.AccentBlue
import com.example.ontime.ui.theme.AccentRed
import com.example.ontime.ui.theme.TextSecondary

/**
 * Фиксированные «умные» группы задач (плитки в верхней части экрана).
 * Порядок = порядок отображения в сетке.
 */
enum class SmartGroup(
    val title: String,
    val icon: ImageVector,
    val tint: Color,
) {
    Today("Сегодня", Icons.Filled.Today, AccentBlue),
    Scheduled("В планах", Icons.Filled.CalendarMonth, AccentRed),
    All("Все", Icons.Filled.Inbox, TextSecondary),
    Completed("Завершено", Icons.Filled.CheckCircle, TextSecondary),
}
