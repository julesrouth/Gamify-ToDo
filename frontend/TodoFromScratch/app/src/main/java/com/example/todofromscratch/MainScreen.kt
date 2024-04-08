package com.example.todofromscratch

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todofromscratch.model.domain.Task
import com.example.todofromscratch.model.domain.User
import com.example.todofromscratch.presenter.TaskPresenter
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme
import java.time.LocalDate
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.time.LocalTime
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuButtonClicked: () -> Unit,
    onAddTaskButtonClicked: () -> Unit,
    onTaskClicked: (Task) -> Unit // Callback to handle task clicks
//    onTaskCompleted: (Task, Boolean) -> Unit
) {

    val context = LocalContext.current

    // State variables to track if we are adding or updating a task
    var isUpdatingTask by remember { mutableStateOf(false) }
    var taskToUpdate by remember { mutableStateOf<Task?>(null) }

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

    // Boolean state to trigger page refresh
    var refreshPage by remember { mutableStateOf(false) }

    // Call getTasks method from TaskPresenter
    val taskPresenter = TaskPresenter(TaskView())

    // Inside your LazyColumn, use LaunchedEffect to trigger a recomposition when refreshPage changes
    LaunchedEffect(refreshPage) {
        println("in launched effect!!!!!!!!!");
//        // Call getTasks method from TaskPresenter
//        val taskPresenter = TaskPresenter(TaskView())

        taskPresenter.getTasks() // Load all tasks
    }

//    val taskPresenter = TaskPresenter(TaskView())

    // Call getTasks method from TaskPresenter
//    taskPresenter.getTasks() //Load all tasks

    val tasks = Tasks.getInstance().getTasks()
    var uncompletedTasks = mutableListOf<Task>()
    val completedTasks = mutableListOf<Task>()
    var dailyTasks = mutableListOf<Task>()
    var weeklyTasks = mutableListOf<Task>()
    var taskTasks = mutableListOf<Task>()

    for (task in tasks) {
        if (!task.completed) {
            uncompletedTasks.add(task)
            if (task.type == "daily") {
                dailyTasks.add(task)
            } else if (task.type == "weekly") {
                weeklyTasks.add(task)
            } else if (task.type == "task") {
                taskTasks.add(task)
            }
        }
    }
    for (task in tasks) {
        if (task.completed) {
            completedTasks.add(task)
        }
    }
    println("tasks at beginning: $tasks")
    println("tasks not completed: $uncompletedTasks")
    println("tasks completed: $completedTasks")
    println("daily Tasks: $dailyTasks")
    println("weekly Tasks: $weeklyTasks")
    println("task Tasks: $taskTasks")
    uncompletedTasks.forEach { task ->
        // Perform operations with each task here
        println("Task description in mainScreen: ${task.description}")
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//    val context = LocalContext.current

    // Function to handle checkbox click
    val onCheckboxClicked: () -> Unit = {
//        taskPresenter.checkTask(task)
        refreshPage = !refreshPage // Toggle refreshPage to trigger recomposition
    }

    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        "All Tasks" to uncompletedTasks,
        "Task Tasks" to taskTasks,
        "Daily Tasks" to dailyTasks,
        "Weekly Tasks" to weeklyTasks
    )

    // Scaffold with top app bar and floating action button
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Todo List",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .clickable { onMenuButtonClicked() }
                            .padding(5.dp)
                    ) {
                        Text("Game",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(2.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.Start,
                            contentDescription = "Localized description",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddTaskButtonClicked()
                })
            {
                Text(
                    text="Add Task",
                    modifier=Modifier
                        .padding(10.dp))
            }
        }
    ) { paddingValues ->
        TodoListScreen(uncompletedTasks = uncompletedTasks, taskTasks = taskTasks, dailyTasks = dailyTasks,
            weeklyTasks = weeklyTasks, onCheckboxClicked = { /*TODO*/ }, onTaskClicked = onTaskClicked)
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            items(uncompletedTasks) { task ->
//                var checkedState = remember { mutableStateOf(task.completed) }
//                Row() {
//                    Checkbox(
//                        checked = checkedState.value,
//                        onCheckedChange = {
//                            checkedState.value = it
//                            task.completed = checkedState.value
//                            if (checkedState.value) {
//                                taskPresenter.checkTask(task)
//                                println("in checkedState.value!!")
//                                onCheckboxClicked()
////                                tasks.remove(task)
////                                println("tasks after remove: $tasks");
////                                onTaskCompleted(task, checkedState.value)
////                                if (it) {
////                                    taskPresenter.checkTask(task)
////                                }
//                            }
////                            refreshPage = true
//                        },
//                        modifier = Modifier.padding(5.dp)
//                    )
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .align(Alignment.CenterVertically)
//                            .clickable(
//                                onClick = {
//                                    // Call the onTaskClicked callback when a task is clicked
//                                    onTaskClicked(task)
//                                }
//                            )
//                    ) {
//                        Text(
//                            text = task.taskName,
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                        if (task.dueDate.isNotBlank()) {
//                            Text(
//                                text = "Due: " + task.dueDate,
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                        }
//                    }
//                }
//                Divider()
//            }
//        }
    }
}

@Composable
fun TodoListScreen(
    uncompletedTasks: List<Task>,
    taskTasks: List<Task>,
    dailyTasks: List<Task>,
    weeklyTasks: List<Task>,
    onCheckboxClicked: () -> Unit,
    onTaskClicked: (Task) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("All", "Task", "Daily", "Weekly")

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(60.dp))

        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (tabIndex) {
            0 -> AllTabContent(uncompletedTasks, onCheckboxClicked, onTaskClicked)
            1 -> TaskTabContent(taskTasks, onCheckboxClicked, onTaskClicked)
            2 -> DailyTabContent(dailyTasks, onCheckboxClicked, onTaskClicked)
            3 -> WeeklyTabContent(weeklyTasks, onCheckboxClicked, onTaskClicked)
        }
    }
}

@Composable
fun AllTabContent(uncompletedTasks: List<Task>, onCheckboxClicked: () -> Unit, onTaskClicked: (Task) -> Unit) {
    LazyColumn {
        items(uncompletedTasks) { task ->
            var checkedState by remember { mutableStateOf(task.completed) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = { isChecked ->
                        checkedState = isChecked
                        task.completed = isChecked
                        // Call the necessary callbacks
                        if (isChecked) {
                            onCheckboxClicked()
                        }
                    },
                    modifier = Modifier.padding(end = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTaskClicked(task) }
                ) {
                    Text(text = task.taskName)
                    if (task.dueDate.isNotBlank()) {
                        Text(text = "Due: ${task.dueDate}")
                    }
                }
            }
            Divider()
        }
    }
}

@Composable
fun TaskTabContent(taskTasks: List<Task>, onCheckboxClicked: () -> Unit, onTaskClicked: (Task) -> Unit) {
    LazyColumn {
        items(taskTasks) { task ->
            var checkedState by remember { mutableStateOf(task.completed) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = { isChecked ->
                        checkedState = isChecked
                        task.completed = isChecked
                        // Call the necessary callbacks
                        if (isChecked) {
                            onCheckboxClicked()
                        }
                    },
                    modifier = Modifier.padding(end = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTaskClicked(task) }
                ) {
                    Text(text = task.taskName)
                    if (task.dueDate.isNotBlank()) {
                        Text(text = "Due: ${task.dueDate}")
                    }
                }
            }
            Divider()
        }
    }
}

@Composable
fun DailyTabContent(dailyTasks: List<Task>, onCheckboxClicked: () -> Unit, onTaskClicked: (Task) -> Unit) {
    LazyColumn {
        items(dailyTasks) { task ->
            var checkedState by remember { mutableStateOf(task.completed) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = { isChecked ->
                        checkedState = isChecked
                        task.completed = isChecked
                        // Call the necessary callbacks
                        if (isChecked) {
                            onCheckboxClicked()
                        }
                    },
                    modifier = Modifier.padding(end = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTaskClicked(task) }
                ) {
                    Text(text = task.taskName)
                    if (task.dueDate.isNotBlank()) {
                        Text(text = "Due: ${task.dueDate}")
                    }
                }
            }
            Divider()
        }
    }
}

@Composable
fun WeeklyTabContent(weeklyTasks: List<Task>, onCheckboxClicked: () -> Unit, onTaskClicked: (Task) -> Unit) {
    LazyColumn {
        items(weeklyTasks) { task ->
            var checkedState by remember { mutableStateOf(task.completed) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = { isChecked ->
                        checkedState = isChecked
                        task.completed = isChecked
                        // Call the necessary callbacks
                        if (isChecked) {
                            onCheckboxClicked()
                        }
                    },
                    modifier = Modifier.padding(end = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTaskClicked(task) }
                ) {
                    Text(text = task.taskName)
                    if (task.dueDate.isNotBlank()) {
                        Text(text = "Due: ${task.dueDate}")
                    }
                }
            }
            Divider()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainScreenPreview() {
    TodoFromScratchTheme {
        MainScreen(
            onAddTaskButtonClicked = {},
            onTaskClicked = {}, // Dummy implementation for onTaskClicked
            onMenuButtonClicked = {},
        )
    }
}