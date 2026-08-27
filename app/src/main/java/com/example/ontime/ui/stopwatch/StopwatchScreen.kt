package com.example.ontime.ui.stopwatch

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ontime.ui.common.PlaceholderScreen
import com.example.ontime.ui.theme.OnTimeTheme

@Composable
fun StopwatchScreen() {
    // TODO: моноширинное 00:00.00, кнопки «Круг» / «Старт», список кругов
    PlaceholderScreen(title = "Секундомер")
}

@Preview(showBackground = true)
@Composable
private fun StopwatchScreenPreview() {
    OnTimeTheme { StopwatchScreen() }
}
