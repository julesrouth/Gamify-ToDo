package com.example.todofromscratch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.todofromscratch.R
import com.example.todofromscratch.cache.Cache
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme
import com.example.todofromscratch.model.domain.Player
import com.example.todofromscratch.model.domain.Stat
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdventureScreen(

) {
    //Set up Variables
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val stat = Cache.getInstance().currPlayer.getStat()
    val imageSize = 50.dp
    val textSize = 30.sp
    val smallTextSize = 25.sp
    val boxSize = 55.dp
    val game = Cache.getInstance().game
    val context = LocalContext.current

    // Your blank screen UI goes here

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(ContextCompat.getColor(context, R.color.light_green)),
                    titleContentColor = Color(ContextCompat.getColor(context, R.color.black)),
                ),
                title = {
                    Text(
                        "Adventure",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {
                        // TODO implement this
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { paddingValues ->


        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .padding(50.dp)

        ) {
            val textContent = remember { mutableStateOf(
                "Health: ${game.playerStat.health}\n" +
                        "Enemy Health: ${game.getEnemyStat().health}\n" +
                        "Player Initiative: ${game.getPlayerInitiative()}\n" +
                        "Enemy Initiative: ${game.getEnemyInitiative()}"
            )}

            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .size(width = 250.dp, height = 175.dp)
                    .clickable(onClick = {
                        println("Advancing Combat")
                        game.combatTick()
                        println(game.playerStat.health)
                    val newText = "Health: ${game.playerStat.health}\n" +
                                "Enemy Health: ${game.getEnemyStat().health}\n" +
                                "Player Initiative: ${game.getPlayerInitiative()}\n" +
                                "Enemy Initiative: ${game.getEnemyInitiative()}"
                        // Set the new text
                        // This assumes you have access to the Text composable and can update its content
                        // You may need to modify this part based on your actual implementation
                        textContent.value = "Health: ${game.playerStat.health}\n" +
                                "Enemy Health: ${game.getEnemyStat().health}\n" +
                                "Player Initiative: ${game.getPlayerInitiative()}\n" +
                                "Enemy Initiative: ${game.getEnemyInitiative()}"

                    }),

                ){
                Text(
                    textContent.value,
                     fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp).align(Alignment.Center)

                )

            }

            Box(
                modifier = Modifier
                    .padding(paddingValues),
            ){

            }



            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(Cache.getInstance().game.getPlayerMoves()) { move ->
                    Card(
                        modifier = Modifier
                            .padding(15.dp, 10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp,

                        ),
                        onClick = {
                            println(move.getName())
                            game.setPlayerMove(move);
                        }
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 1.dp),
                        ) {

                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(1f)
                            ) {
                                Text(
                                    move.getName(),
                                    modifier = Modifier
                                        .padding(15.dp, 1.dp),
                                    fontSize = 30.sp
                                )
                                Text(
                                    "Something",
                                    modifier = Modifier
                                        .padding(15.dp, 1.dp),
                                    fontSize = 15.sp
                                )
                                Text(
                                    "You currently have ",
                                    modifier = Modifier
                                        .padding(15.dp, 1.dp),
                                    fontSize = 10.sp
                                )
                            }
//                            Spacer(Modifier.weight(0.5f))
                            Spacer(Modifier)
                            Text(
                                " Gold",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(5.dp, 1.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.gold_ingots_gold_svgrepo_com),
                                contentDescription = "Localized description",
                                modifier = Modifier
                                    .size(15.dp)
                                    .align(Alignment.CenterVertically)
                            )

                        }
                    }
                }
            }
        }

    }
}





@Preview
@Composable
fun AdventureScreenPreview() {
    TodoFromScratchTheme {
        AdventureScreen(

        )

    }
}

