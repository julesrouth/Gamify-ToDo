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
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import java.time.format.DateTimeFormatter
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.properties.ReadWriteProperty

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(onNextButtonClicked: () -> Unit, ) {
    val context = LocalContext.current
    val dateState =
        rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    val timeState = rememberTimePickerState()
    val openDateDialog = remember { mutableStateOf(false) }
    val openTimeDialog = remember { mutableStateOf(false) }
    var pickTime by remember { mutableStateOf(false) }
    var dateStr by remember { mutableStateOf("") }

    var name by remember {
        mutableStateOf("")
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
                        }
                        else {
                            dateStr = ""
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

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp),
                    onClick = {
                        Toast.makeText(
                            context,
                            "This is not yet implemented",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text(text = "Select Time")
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                onClick = {
                    Tasks.getInstance().addTask(Task(name, dateStr))
                    onNextButtonClicked()
                },
                enabled = (name.isNotBlank())
            ) {
                Text(text = "Add")
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SimpleComposablePreview() {
    AddTaskScreen(onNextButtonClicked = {})
}
