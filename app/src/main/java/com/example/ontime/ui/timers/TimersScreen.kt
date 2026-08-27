package com.example.ontime.ui.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ontime.ui.common.PlaceholderScreen
import com.example.ontime.ui.theme.OnTimeTheme

@Composable
fun TimersScreen() {
    // TODO: wheel-picker ч/мин/с, кнопки «Отмена» / «Старт», «Название» + «По окончании», «Недавние»
    PlaceholderScreen(title = "Таймеры")
}

@Preview(showBackground = true)
@Composable
private fun TimersScreenPreview() {
    OnTimeTheme { TimersScreen() }
}
