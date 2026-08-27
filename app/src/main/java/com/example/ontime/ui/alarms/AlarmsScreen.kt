package com.example.ontime.ui.alarms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ontime.ui.common.PlaceholderScreen
import com.example.ontime.ui.theme.OnTimeTheme

@Composable
fun AlarmsScreen() {
    // TODO: блок «Сон | Пробуждение» + секция «Другие» со списком будильников и Switch
    PlaceholderScreen(title = "Будильники")
}

@Preview(showBackground = true)
@Composable
private fun AlarmsScreenPreview() {
    OnTimeTheme { AlarmsScreen() }
}
