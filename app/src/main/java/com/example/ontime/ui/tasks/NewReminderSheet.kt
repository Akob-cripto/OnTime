package com.example.ontime.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ontime.ui.tasks.model.Reminder
import com.example.ontime.ui.theme.AccentBlue
import com.example.ontime.ui.theme.AccentOrange
import com.example.ontime.ui.theme.Ink
import com.example.ontime.ui.theme.Separator
import com.example.ontime.ui.theme.Surface1
import com.example.ontime.ui.theme.TextPrimary
import com.example.ontime.ui.theme.TextSecondary

/**
 * Лист создания напоминания (iOS-style modal sheet).
 * Поля: название, заметки, «Подробнее» (пока заглушка), выбор списка.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReminderSheet(
    userLists: List<String>,
    onDismiss: () -> Unit,
    onSave: (title: String, notes: String, listName: String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedList by remember { mutableStateOf(Reminder.DEFAULT_LIST) }
    var listMenuOpen by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Ink,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 20.dp),
        ) {
            // Шапка: Отменить · Новое напоминание · Добавить
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = onDismiss,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.align(Alignment.CenterStart),
                    colors = ButtonDefaults.textButtonColors(contentColor = AccentBlue),
                ) { Text("Отменить") }

                Text(
                    text = "Новое напоминание",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center),
                )

                TextButton(
                    onClick = { onSave(title, notes, selectedList) },
                    enabled = title.isNotBlank(),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.align(Alignment.CenterEnd),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AccentBlue,
                        disabledContentColor = TextSecondary,
                    ),
                ) { Text("Добавить", fontWeight = FontWeight.Bold) }
            }

            Spacer(Modifier.size(16.dp))

            // Карточка: название + заметки
            Surface(color = Surface1, shape = RoundedCornerShape(12.dp)) {
                Column {
                    FieldBox(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = "По названию",
                        singleLine = true,
                        modifier = Modifier.focusRequester(focusRequester),
                    )
                    HorizontalDivider(color = Separator)
                    FieldBox(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = "Заметки",
                        singleLine = false,
                        minHeight = 72.dp,
                    )
                }
            }

            Spacer(Modifier.size(16.dp))

            // Подробнее (заглушка)
            Surface(
                color = Surface1,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.clickable { /* TODO: экран деталей напоминания */ },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Подробнее",
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = TextSecondary)
                }
            }

            Spacer(Modifier.size(12.dp))

            // Список
            Box {
                Surface(
                    color = Surface1,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { listMenuOpen = true },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(AccentOrange),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ListAlt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Список",
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            selectedList,
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(Modifier.width(6.dp))
                        Icon(
                            Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = TextSecondary,
                        )
                    }
                }
                DropdownMenu(
                    expanded = listMenuOpen,
                    onDismissRequest = { listMenuOpen = false },
                ) {
                    (listOf(Reminder.DEFAULT_LIST) + userLists).forEach { name ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedList = name
                                listMenuOpen = false
                            },
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }
}

@Composable
private fun FieldBox(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
    minHeight: androidx.compose.ui.unit.Dp = 0.dp,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .heightIn(min = minHeight),
    ) {
        if (value.isEmpty()) {
            Text(
                placeholder,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
            cursorBrush = SolidColor(AccentBlue),
            modifier = modifier.fillMaxWidth(),
        )
    }
}
