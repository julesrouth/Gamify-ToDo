package com.example.todofromscratch

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme
import java.time.LocalDate
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onAddTaskButtonClicked: () -> Unit,) {

    // populate with sample tasks
    if (Tasks.getInstance().getTasks().size == 0) {
        populateExampleTasks()
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(Tasks.getInstance().getTasks()) { task ->
                val checkedState = remember { mutableStateOf(task.completed) }
                Row () {
                    Checkbox(
                        checked=checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it
                            task.completed = checkedState.value
                                          },
                        modifier=Modifier
                            .padding(5.dp)
                    )
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterVertically)
                            .clickable(
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        task.name,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        )
                    ) {
                        Text(
                            text = task.name,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (task.date.isNotBlank()) {
                            Text(
                                text = "Due: " + task.date,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
                Divider()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun populateExampleTasks() {
    Tasks.getInstance().addTask(Task("Task 1", ""))
    Tasks.getInstance().addTask(Task("Task 2", LocalDate.now().toString()))
    Tasks.getInstance().addTask(Task("Task 3", ""))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainScreenPreview() {
    TodoFromScratchTheme {
        MainScreen(
            onAddTaskButtonClicked = {},)
    }
}