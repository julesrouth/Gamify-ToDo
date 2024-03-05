package com.example.todofromscratch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameMainScreen(
    onAdventureClicked : () -> Unit,
    onShopClicked : () -> Unit,
    onCharacterClicked : () -> Unit,
    onExitClicked : () -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val buttonFontSize = 25.sp
    val buttonWidth = 175.dp
    val buttonHeight = 150.dp

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Game Menu",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Text("Character Info placeholder")
                }
            }
            Spacer(
                modifier = Modifier
                    .size(35.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .requiredSize(buttonWidth, buttonHeight)
                        .padding(5.dp),
                    onClick = {
                        onAdventureClicked()
                    }
                ) {
                    Text("Adventure",
                        fontSize = buttonFontSize
                    )
                }
                Button (
                    modifier = Modifier
                        .requiredSize(buttonWidth, buttonHeight)
                        .padding(5.dp),
                    onClick = {
                        onShopClicked()
                    }
                ) {
                    Text("Shop",
                        fontSize = buttonFontSize)
                }
            }

            Spacer(
                modifier = Modifier
                    .size(15.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .requiredSize(buttonWidth, buttonHeight)
                        .padding(5.dp),
                    onClick = {
                        onCharacterClicked()
                    }
                ) {
                    Text("Character",
                        fontSize = buttonFontSize
                    )
                }
                Button (
                    modifier = Modifier
                        .requiredSize(buttonWidth, buttonHeight)
                        .padding(5.dp),
                    onClick = {
                        onExitClicked()
                    }
                ) {
                    Text("Exit",
                        fontSize = buttonFontSize
                    )
                }
            }

            Spacer (
                modifier = Modifier
                    .size(25.dp)
            )
        }
    }
}

@Preview
@Composable
fun GameMainScreenPreview() {
    TodoFromScratchTheme {
        GameMainScreen(
            onAdventureClicked = {},
            onShopClicked = {},
            onCharacterClicked = {},
            onExitClicked = {}
        )
    }
}