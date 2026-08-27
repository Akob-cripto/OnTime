package com.example.ontime.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ontime.ui.tasks.model.SmartGroup
import com.example.ontime.ui.tasks.model.ReminderList
import com.example.ontime.ui.tasks.model.ReminderListColors
import com.example.ontime.ui.theme.AccentBlue
import com.example.ontime.ui.theme.Ink
import com.example.ontime.ui.theme.OnTimeTheme
import com.example.ontime.ui.theme.Separator
import com.example.ontime.ui.theme.Surface1
import com.example.ontime.ui.theme.Surface2
import com.example.ontime.ui.theme.TextPrimary
import com.example.ontime.ui.theme.TextSecondary

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = viewModel(),
) {
    val lists by viewModel.lists.collectAsState()
    val reminders by viewModel.reminders.collectAsState()
    var showNewReminder by remember { mutableStateOf(false) }

    val listsWithCounts = remember(lists, reminders) {
        lists.map { list ->
            list.copy(count = reminders.count { !it.isCompleted && it.listName == list.name })
        }
    }

    TasksScreenContent(
        lists = listsWithCounts,
        smartCounts = viewModel.smartCounts(reminders),
        onAddList = viewModel::addList,
        onOpenSmartGroup = { /* TODO: экран списка задач */ },
        onOpenList = { /* TODO: экран списка задач */ },
        onNewReminder = { showNewReminder = true },
    )

    if (showNewReminder) {
        NewReminderSheet(
            userLists = lists.map { it.name },
            onDismiss = { showNewReminder = false },
            onSave = { title, notes, listName ->
                viewModel.addReminder(title, notes, listName)
                showNewReminder = false
            },
        )
    }
}

@Composable
private fun TasksScreenContent(
    lists: List<ReminderList>,
    smartCounts: Map<SmartGroup, Int>,
    onAddList: (name: String, color: Color) -> Unit,
    onOpenSmartGroup: (SmartGroup) -> Unit,
    onOpenList: (ReminderList) -> Unit,
    onNewReminder: () -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var menuOpen by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }

    val visibleLists = remember(lists, query) {
        if (query.isBlank()) lists
        else lists.filter { it.name.contains(query.trim(), ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink),
    ) {
        // Верхняя панель: только кнопка «...» справа
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                CircleIconButton(Icons.Filled.MoreHoriz) { menuOpen = true }
                DropdownMenu(
                    expanded = menuOpen,
                    onDismissRequest = { menuOpen = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("Добавить список") },
                        leadingIcon = { Icon(Icons.Filled.Add, contentDescription = null) },
                        onClick = {
                            menuOpen = false
                            showAddDialog = true
                        },
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { SearchField(query = query, onQueryChange = { query = it }) }

            item { SmartGroupGrid(counts = smartCounts, onClick = onOpenSmartGroup) }

            item {
                Text(
                    text = "Мои списки",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            if (visibleLists.isEmpty()) {
                item {
                    Text(
                        text = if (query.isBlank()) "Нет списков" else "Ничего не найдено",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            } else {
                item { MyListsCard(lists = visibleLists, onOpenList = onOpenList) }
            }
        }

        HorizontalDivider(color = Separator)

        NewReminderButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = onNewReminder,
        )
    }

    if (showAddDialog) {
        AddListDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, color ->
                onAddList(name, color)
                showAddDialog = false
            },
        )
    }
}

@Composable
private fun CircleIconButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(Surface2)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Меню",
            tint = AccentBlue,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun SearchField(query: String, onQueryChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Surface2)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(6.dp))
        Box(modifier = Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text("Поиск", color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
                cursorBrush = SolidColor(AccentBlue),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Icon(
            Icons.Filled.Mic,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
private fun SmartGroupGrid(
    counts: Map<SmartGroup, Int>,
    onClick: (SmartGroup) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SmartGroup.entries.chunked(2).forEach { rowGroups ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowGroups.forEach { group ->
                    SmartGroupTile(
                        group = group,
                        count = counts[group] ?: 0,
                        onClick = { onClick(group) },
                        modifier = Modifier.weight(1f),
                    )
                }
                if (rowGroups.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SmartGroupTile(
    group: SmartGroup,
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = Surface1,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(group.tint),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        group.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp),
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = count.toString(),
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = group.title,
                color = TextSecondary,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun MyListsCard(
    lists: List<ReminderList>,
    onOpenList: (ReminderList) -> Unit,
) {
    Surface(color = Surface1, shape = RoundedCornerShape(12.dp)) {
        Column {
            lists.forEachIndexed { index, list ->
                ReminderListRow(list = list, onClick = { onOpenList(list) })
                if (index != lists.lastIndex) {
                    HorizontalDivider(
                        color = Separator,
                        modifier = Modifier.padding(start = 52.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ReminderListRow(list: ReminderList, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(list.color),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.AutoMirrored.Filled.List,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp),
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = list.name,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = list.count.toString(),
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun NewReminderButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Filled.AddCircle, contentDescription = null, tint = AccentBlue)
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Новое напоминание",
            color = AccentBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun AddListDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, color: Color) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(ReminderListColors.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface1,
        title = { Text("Новый список", color = TextPrimary) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    placeholder = { Text("Название") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ReminderListColors.forEach { color ->
                        val selected = color == selectedColor
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (selected) 3.dp else 0.dp,
                                    color = TextPrimary,
                                    shape = CircleShape,
                                )
                                .clickable { selectedColor = color },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, selectedColor) },
                enabled = name.isNotBlank(),
            ) {
                Text("Готово")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    OnTimeTheme {
        TasksScreenContent(
            lists = listOf(
                ReminderList(1, "Покупки", ReminderListColors[1], count = 3),
                ReminderList(2, "Работа", ReminderListColors[4], count = 7),
            ),
            smartCounts = SmartGroup.entries.associateWith { 0 },
            onAddList = { _, _ -> },
            onOpenSmartGroup = {},
            onOpenList = {},
            onNewReminder = {},
        )
    }
}
