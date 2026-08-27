package com.example.ontime.ui.tasks

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.ontime.ui.tasks.model.Reminder
import com.example.ontime.ui.tasks.model.ReminderList
import com.example.ontime.ui.tasks.model.SmartGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Состояние экрана «Задачи». Пока всё в памяти (без Room) — цель увидеть UI.
 */
class TasksViewModel : ViewModel() {

    private val _lists = MutableStateFlow<List<ReminderList>>(emptyList())
    val lists: StateFlow<List<ReminderList>> = _lists.asStateFlow()

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders.asStateFlow()

    private var nextListId = 1L
    private var nextReminderId = 1L

    fun addList(name: String, color: Color) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        _lists.update { current ->
            current + ReminderList(id = nextListId++, name = trimmed, color = color)
        }
    }

    fun addReminder(title: String, notes: String, listName: String) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isEmpty()) return
        _reminders.update { current ->
            current + Reminder(
                id = nextReminderId++,
                title = trimmedTitle,
                notes = notes.trim(),
                listName = listName,
            )
        }
    }

    /** Счётчики умных групп. Даты пока не поддерживаются, поэтому Сегодня/В планах = 0. */
    fun smartCounts(reminders: List<Reminder>): Map<SmartGroup, Int> = mapOf(
        SmartGroup.Today to 0,
        SmartGroup.Scheduled to 0,
        SmartGroup.All to reminders.count { !it.isCompleted },
        SmartGroup.Completed to reminders.count { it.isCompleted },
    )
}
