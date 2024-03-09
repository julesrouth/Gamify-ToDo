package com.example.todofromscratch.ui

import android.widget.Toast
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
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todofromscratch.R
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    val dummyItems = ArrayList<String>()
    dummyItems+="Item 1"
    dummyItems+="Item 2"
    dummyItems+="Item 3"
    dummyItems+="Item 4"
    dummyItems+="Item 5"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(225.dp)
                )
                Box(
                    modifier=Modifier
                        .width(150.dp)
                        .height(50.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp, 1.dp)
                        ) {
                            Text("Gold",
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                                contentDescription = "Localized description",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                            Text("x" + 500,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically))
                        }
                    }
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.baseline_warehouse_24),
                contentDescription = "Item",
                modifier = Modifier
                    .size(105.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(dummyItems) { item ->
                    Card(
                        modifier = Modifier
                            .padding(15.dp, 10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        onClick = {
                            Toast.makeText(
                                context,
                                "Purchasing $item not yet implemented",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 1.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Item",
                                modifier = Modifier
                                    .size(45.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Text(
                                item,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(15.dp, 1.dp),
                                fontSize = 30.sp
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                "50 Gold",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(5.dp, 1.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_android_gold_24dp),
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
//}

@Preview
@Composable
fun ShopScreenPreview() {
    TodoFromScratchTheme {
        ShopScreen()
    }
}