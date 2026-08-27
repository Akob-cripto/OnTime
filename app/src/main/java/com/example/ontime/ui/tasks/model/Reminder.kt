package com.example.ontime.ui.tasks.model

/** Напоминание. Пока хранится в памяти (без Room). */
data class Reminder(
    val id: Long,
    val title: String,
    val notes: String = "",
    val listName: String = DEFAULT_LIST,
    val isCompleted: Boolean = false,
) {
    companion object {
        /** Список по умолчанию (как «Напоминания» в iOS). */
        const val DEFAULT_LIST = "Напоминания"
    }
}
