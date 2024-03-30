package com.example.todofromscratch

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneOffset
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todofromscratch.cache.Cache
import com.example.todofromscratch.model.domain.User
import com.example.todofromscratch.presenter.TaskPresenter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.properties.ReadWriteProperty
import com.example.todofromscratch.model.domain.Task
import com.example.todofromscratch.model.net.Difficulty
import com.example.todofromscratch.model.net.TaskType
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(taskToUpdate: Task? = null, // Task to update if in edit mode
    onNextButtonClicked: () -> Unit, ) {
    val context = LocalContext.current
    val dateState =
        rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    val timeState = rememberTimePickerState()
    val openDateDialog = remember { mutableStateOf(false) }
    val openTimeDialog = remember { mutableStateOf(false) }
    var pickTime by remember { mutableStateOf(false) }
//    var dateStr by remember { mutableStateOf("") }
//    var timeStr by remember {mutableStateOf("")}
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
    cal.set(Calendar.MINUTE, timeState.minute)
    cal.isLenient = false

    class TaskView : TaskPresenter.View {

        override fun displayMessage(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun setLoadingFooter(value: Boolean) {
            if (value) {
                // Add loading footer logic here
                // storyRecyclerViewAdapter.addLoadingFooter()
            } else {
                // Remove loading footer logic here
                // storyRecyclerViewAdapter.removeLoadingFooter()
            }
        }

        override fun addMoreItems(tasks: MutableList<com.example.todofromscratch.model.domain.Task>?) {
//            tasks?.let { storyRecyclerViewAdapter.addItems(it) }
        }

        override fun startUserActivity(user: User?) {
//            user?.let { startActivity(Intent(context, MainActivity::class.java).apply { putExtra(MainActivity.CURRENT_USER_KEY, it) }) }
        }

    }

    val taskPresenter = TaskPresenter(TaskView())

//    var name by remember {
//        mutableStateOf("")
//    }

    var name by remember {
        mutableStateOf(taskToUpdate?.taskName ?: "")
    }

    // Populate date and time fields if in edit mode
    var dateStr by remember { mutableStateOf(taskToUpdate?.dueDate ?: "") }
    var timeStr by remember { mutableStateOf("") }
    if (taskToUpdate != null && taskToUpdate.dueDate.isNotBlank()) {
        dateStr = taskToUpdate.dueDate
        // Extract time from due date if available
        val dateTime = LocalDateTime.parse(taskToUpdate.dueDate)
        timeStr = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
    }

    Scaffold(
        topBar = showTopBar(
            onNextButtonClicked
        ),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                "Type Task Name:",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                value = name,
                onValueChange = { text ->
                    name = text
                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Specify Time",
                    modifier = Modifier
                        .size(55.dp)
                        .align(Alignment.CenterVertically)
                )
                Switch(
                    checked = pickTime,
                    onCheckedChange = {
                        pickTime = it
                        if (pickTime) {
                            dateStr = dateState.selectedDateMillis?.let {
                                Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
                            }
                                ?.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                .toString()
                                timeStr = formatter.format(cal.time)
                        }
                        else {
                            dateStr = ""
                            timeStr = ""
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            if (openDateDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDateDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDateDialog.value = false
                                dateStr = dateState.selectedDateMillis?.let {
                                    Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC) }
                                    ?.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                    .toString()
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDateDialog.value = false
                            }
                        ) {
                            Text("CANCEL")
                        }
                    }
                ) {
                    DatePicker(
                        state = dateState
                    )
                }
            }

            if (openTimeDialog.value) {
                TimePickerDialog(
                    onCancel = { openTimeDialog.value = false },
                    onConfirm = {
                        timeStr = formatter.format(cal.time)
                        openTimeDialog.value = false
                    },
                ) {
                    TimePicker(state = timeState)
                }
            }

            if (pickTime) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = dateStr,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Button(
                        modifier = Modifier
                            .padding(5.dp),
                        onClick = {
                            openDateDialog.value = true
                        }
                    ) {
                        Text(text = "Select Due Date")
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = timeStr,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Button(
                        modifier = Modifier
                            .padding(5.dp),
                        onClick = {
                            openTimeDialog.value = true
                        }
                    ) {
                        Text(text = "Select Time")
                    }
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                onClick = {
                    // If taskToUpdate is null, it means we're in add mode
                    if (taskToUpdate == null) {
                        // Add new task logic
                        val difficulty = Difficulty.EASY.value
                        val type = TaskType.TASK.value
                        var completed: Boolean = false
                        println("UserID in addtaskscreen: " + Cache.getInstance().currUserAuthToken.userId)
                        println("Completed in AddTaskScreen: " + completed)
                        val task = Task(name, "test 2", "$dateStr $timeStr", difficulty, type, Cache.getInstance().currUserAuthToken.userId, completed)
                        //TODO GET THR
                        Tasks.getInstance().addTask(task)
                        taskPresenter.addTask(task)
                    } else {
                        // Update task logic
                        taskToUpdate.taskName = name
                        taskToUpdate.dueDate = "$dateStr $timeStr"
                        // TODO:: Update the task using the presenter or any appropriate method
                    }
                    onNextButtonClicked()
                },
                enabled = (name.isNotBlank())
            ) {
                Text(text = if (taskToUpdate == null) "Add" else "Update")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showTopBar(
    onNextButtonClicked: () -> Unit
) : @Composable () -> Unit {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
   return (@Composable () {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    "Add Task",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onNextButtonClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(
                        context,
                        "This is not yet implemented",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
    })
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AddTaskScreenPreview() {
    AddTaskScreen(onNextButtonClicked = {})
}
