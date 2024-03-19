package com.example.todofromscratch.ui

import android.widget.Button
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todofromscratch.R
import com.example.todofromscratch.game.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameMainScreen(
    game: Game,
    onAdventureClicked : () -> Unit,
    onShopClicked : () -> Unit,
    onCharacterClicked : () -> Unit,
    onExitClicked : () -> Unit,
) {

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
//                    .weight(1f)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
//                    color = MaterialTheme.colorScheme.tertiaryContainer
                    color = Color.LightGray,
                ) {
                    Column() {
                        Text("Player Info",
                            fontSize=30.sp)
                        Text("Name: ",
                            fontSize=20.sp)
                        Text("Level: ${game.level}",
                            fontSize=20.sp)
                        Text("Experience:",
                            fontSize=20.sp)
                        Text("Gold: ${game.gold}",
                            fontSize=20.sp)
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .size(55.dp)
                    .weight(1f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ShowButton(onAdventureClicked, "Adventure")
                ShowButton(onShopClicked, "Shop")
            }

            Spacer(
                modifier = Modifier
                    .size(25.dp)

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ShowButton(onCharacterClicked, "Character")
                ShowButton(onExitClicked, "Exit")
            }

            Spacer (
                modifier = Modifier
                    .size(65.dp)
                    .weight(1f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shield_24),
                    contentDescription = "Item",
                    modifier = Modifier
                        .size(105.dp)
                        .align(Alignment.CenterVertically)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_fort_24),
                    contentDescription = "Item",
                    modifier = Modifier
                        .size(105.dp)
                        .align(Alignment.CenterVertically)
                )
                Icon(
                    painter = painterResource(id = R.drawable.shield_24),
                    contentDescription = "Item",
                    modifier = Modifier
                        .size(105.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun ShowButton(
    onClickAction : () -> Unit,
    textString : String
) {
    val buttonWidth = 130.dp //175.dp
    val buttonHeight = 60.dp //150.dp
    val buttonFontSize = 15.sp //25.sp
    Button(
        modifier = Modifier
//            .requiredSize(buttonWidth, buttonHeight)
            .padding(5.dp),
        onClick = {
            onClickAction()
        }
    ) {
        Text(
            textString,
            fontSize = buttonFontSize,
            modifier = Modifier
                .padding(5.dp)
        )
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
            onExitClicked = {},
            game = Game()
        )
    }
}