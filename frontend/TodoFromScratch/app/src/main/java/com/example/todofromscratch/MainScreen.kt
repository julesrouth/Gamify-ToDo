package com.example.todofromscratch

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todofromscratch.model.domain.Task
import com.example.todofromscratch.model.domain.User
import com.example.todofromscratch.presenter.TaskPresenter
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme
import java.time.LocalDate
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuButtonClicked: () -> Unit,
    onAddTaskButtonClicked: () -> Unit,
    onEditTaskButtonClicked: (Task) -> Unit,
    onTaskClicked: (Task) -> Unit, // Callback to handle task clicks
    onLogoutClicked : () -> Unit
) {

    val context = LocalContext.current

    // State variables to track if we are adding or updating a task
    var isUpdatingTask by remember { mutableStateOf(false) }
    var taskToUpdate by remember { mutableStateOf<Task?>(null) }
    var gettingTasksFromServer by remember {mutableStateOf(false)}

    var goldVisibility by remember { mutableStateOf(false) }
    val goldAnimationEnter : Long = 1000;
    val goldAnimationExit : Long = 1000;

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

        if (!gettingTasksFromServer) {
            gettingTasksFromServer = true
            taskPresenter.getTasks() // Load all tasks
        }
    }

//    val taskPresenter = TaskPresenter(TaskView())

    // Call getTasks method from TaskPresenter
//    taskPresenter.getTasks() //Load all tasks

    var tasks by remember { mutableStateOf(Tasks.getInstance().getTasks().toMutableList()) }
    val uncompletedTasks = mutableListOf<Task>()
    val completedTasks = mutableListOf<Task>()

    for (task in tasks) {
        if (!task.completed) {
            uncompletedTasks.add(task)
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
                navigationIcon = {
                    Row(
                        modifier = Modifier
                            .clickable {onLogoutClicked()}
                            .padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Localized description",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(2.dp)
                        )
                        Text("Logout",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .clickable {onMenuButtonClicked()}
                            .padding(5.dp)
                    ) {
                        Text("Game",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(2.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowForwardIos,
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
        AnimatedVisibility(
            visible = goldVisibility,
            enter = fadeIn(animationSpec = tween(goldAnimationEnter.toInt())),
            exit = fadeOut(animationSpec = tween(goldAnimationExit.toInt()))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.gold_ingots_gold_svgrepo_com),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        //                .align(Alignment.Center)
//                        .padding(paddingValues),
                        .weight(1f)
                        .fillMaxSize(),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Gold!",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 30.sp,
//                    color = Color.Yellow
                    color=Color(0xF6,0xBB, 0x42,0xFF)
                )
                Spacer(modifier = Modifier.weight(1f))

            }
        }
        AnimatedVisibility(
            visible = !goldVisibility,
            enter = fadeIn(animationSpec = tween(goldAnimationEnter.toInt())),
            exit = fadeOut(animationSpec = tween(goldAnimationExit.toInt()))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(uncompletedTasks) { task ->
                    var checkedState = remember { mutableStateOf(task.completed) }
                    Row(
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                task.completed = checkedState.value
                                if (checkedState.value) {
                                    taskPresenter.checkTask(task)
                                    println("in checkedState.value!!")
                                    onCheckboxClicked()
                                    goldVisibility = true
//                                tasks.remove(task)
//                                println("tasks after remove: $tasks");
//                                onTaskCompleted(task, checkedState.value)
//                                if (it) {
//                                    taskPresenter.checkTask(task)
//                                }
                                }
//                            refreshPage = true
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clickable(
                                    onClick = {
                                        // Call the onTaskClicked callback when a task is clicked
                                        onTaskClicked(task)
                                    }
                                )
                        ) {
                            Text(
                                text = task.taskName,
                            )
                            if (task.dueDate.isNotBlank()) {
                                Text(
                                        text = "Due: " + task.dueDate,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Row(
                                modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Add",
                                    modifier = Modifier
                                            .clickable {
                                                onEditTaskButtonClicked(task)
                                            }
                                            .padding(end = 2.dp) // Optional: Add padding between icons
                            )
                            Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier
                                            .clickable {
                                                taskPresenter.deleteTask(task.taskId)
                                                Tasks.getInstance().deleteTask(task.taskId)
                                                tasks = Tasks.getInstance().getTasks().toMutableList()
                                            }
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
    LaunchedEffect(
        key1=goldVisibility,
        block= {
            delay((goldAnimationEnter + goldAnimationExit))
            goldVisibility = false
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainScreenPreview() {
    TodoFromScratchTheme {
        MainScreen(
            onAddTaskButtonClicked = {},
                onEditTaskButtonClicked = {},
            onTaskClicked = {}, // Dummy implementation for onTaskClicked
            onMenuButtonClicked = {},
            onLogoutClicked = {}
        )
    }
}