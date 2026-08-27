package com.example.ontime.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ontime.ui.alarms.AlarmsScreen
import com.example.ontime.ui.stopwatch.StopwatchScreen
import com.example.ontime.ui.tasks.TasksScreen
import com.example.ontime.ui.timers.TimersScreen

@Composable
fun OnTimeNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val current = OnTimeDestination.fromRoute(backStackEntry?.destination?.route)

    Scaffold(
        bottomBar = {
            OnTimeBottomBar(
                current = current,
                onSelect = { dest ->
                    navController.navigate(dest.route) {
                        // одна копия вкладки в стеке + сохранение/восстановление её состояния
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = OnTimeDestination.START.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(OnTimeDestination.Tasks.route) { TasksScreen() }
            composable(OnTimeDestination.Alarms.route) { AlarmsScreen() }
            composable(OnTimeDestination.Stopwatch.route) { StopwatchScreen() }
            composable(OnTimeDestination.Timers.route) { TimersScreen() }
        }
    }
}
