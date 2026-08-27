package com.example.ontime.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ontime.ui.theme.AccentOrange
import com.example.ontime.ui.theme.Ink
import com.example.ontime.ui.theme.TextSecondary

@Composable
fun OnTimeBottomBar(
    current: OnTimeDestination,
    onSelect: (OnTimeDestination) -> Unit,
) {
    NavigationBar(containerColor = Ink) {
        OnTimeDestination.entries.forEach { dest ->
            NavigationBarItem(
                selected = dest == current,
                onClick = { onSelect(dest) },
                icon = { Icon(dest.icon, contentDescription = dest.label) },
                label = { Text(dest.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AccentOrange,
                    selectedTextColor = AccentOrange,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
