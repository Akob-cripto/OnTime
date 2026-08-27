package com.example.ontime.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ontime.ui.theme.Ink
import com.example.ontime.ui.theme.TextPrimary
import com.example.ontime.ui.theme.TextSecondary

/**
 * Временная заглушка вкладки: iOS-подобный large-title + подпись по центру.
 * Каждая вкладка заменит это своим содержимым.
 */
@Composable
fun PlaceholderScreen(
    title: String,
    subtitle: String = "Экран в разработке",
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = title,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text(text = subtitle, color = TextSecondary)
        }
    }
}
