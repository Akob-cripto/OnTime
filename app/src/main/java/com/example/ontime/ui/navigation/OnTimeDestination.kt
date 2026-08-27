package com.example.ontime.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Верхнеуровневые вкладки нижней панели.
 * Порядок = порядок в BottomBar (как в iOS-«Часах», но «Мировые часы» → «Задачи»).
 */
enum class OnTimeDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Tasks("tasks", "Задачи", Icons.AutoMirrored.Filled.ListAlt),
    Alarms("alarms", "Будильники", Icons.Filled.Alarm),
    Stopwatch("stopwatch", "Секундомер", Icons.Filled.Timer),
    Timers("timers", "Таймеры", Icons.Filled.HourglassEmpty);

    companion object {
        val START = Tasks

        fun fromRoute(route: String?): OnTimeDestination =
            entries.firstOrNull { it.route == route } ?: START
    }
}
