package com.example.todofromscratch.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    onBackButtonClicked : () -> Unit

) {
    //Set up Variables
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val stat = Cache.getInstance().currPlayer.getStat()
    val imageSize = 50.dp
    val textSize = 30.sp
    val smallTextSize = 25.sp
    val boxSize = 55.dp
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
                        "Shop",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {
                        // TODO implement this
                        onBackButtonClicked()
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

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(Cache.getInstance().currPlayer.characterName,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(40.dp)
                    .width(300.dp),
                    //increase font size
                    fontSize = 30.sp,
                
            )
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(boxSize)
            ){
                //Icon
                //Image
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(imageSize)
                        .align(Alignment.CenterVertically)
                )
                Text("Health: ",

                    fontSize = textSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)
                        //Change color
                        .padding(10.dp, 1.dp),
                    color = Color(ContextCompat.getColor(context, R.color.black))

                )
                Text("${stat.health}",
                    fontSize = smallTextSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)

                )

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(boxSize)
            ){
                //Icon
                //Image
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(imageSize)
                        .align(Alignment.CenterVertically)
                )

                Text("Mana: ",

                    fontSize = textSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)
                        //Change color
                        .padding(10.dp, 1.dp),
                    color = Color(ContextCompat.getColor(context, R.color.black))

                )
                Text("${stat.mana}",
                    fontSize = smallTextSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)

                )

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(boxSize)
            ){
                //Icon
                //Image
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(imageSize)
                        .align(Alignment.CenterVertically)
                )

                Text("Attack: ",

                    fontSize = textSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)
                        //Change color
                        .padding(10.dp, 1.dp),
                    color = Color(ContextCompat.getColor(context, R.color.black))

                )
                Text("${stat.attack}",
                    fontSize = smallTextSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)

                )

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(boxSize)
            ){
                //Icon
                //Image 
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(imageSize)
                        .align(Alignment.CenterVertically)
                )

                Text("Defense: ",

                    fontSize = textSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)
                        //Change color
                        .padding(10.dp, 1.dp),
                        color = Color(ContextCompat.getColor(context, R.color.black))

                )
                Text("${stat.defense}",
                    fontSize = smallTextSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)

                )

            }

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(boxSize)
            ){
                //Icon
                //Image
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(imageSize)
                        .align(Alignment.CenterVertically)
                )

                Text("Speed: ",

                    fontSize = textSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)
                        //Change color
                        .padding(10.dp, 1.dp),
                    color = Color(ContextCompat.getColor(context, R.color.black))

                )
                Text("${stat.speed}",
                    fontSize = smallTextSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)

                )

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(boxSize)
            ){
                //Icon
                //Image
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(imageSize)
                        .align(Alignment.CenterVertically)
                )

                Text("Level: ",

                    fontSize = textSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)
                        //Change color
                        .padding(10.dp, 1.dp),
                    color = Color(ContextCompat.getColor(context, R.color.black))

                )
                Text("${stat.level}",
                    fontSize = smallTextSize,
                    modifier = Modifier
                        //Center this in the row
                        .align(Alignment.CenterVertically)

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
                items(Cache.getInstance().currPlayer.playerItems) { item ->
                    Card(
                        modifier = Modifier
                            .padding(15.dp, 10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
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
                                    item.itemName,
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
fun CharacterScreenPreview() {
    TodoFromScratchTheme {
        CharacterScreen(
            onBackButtonClicked = {}

        )

    }
}

