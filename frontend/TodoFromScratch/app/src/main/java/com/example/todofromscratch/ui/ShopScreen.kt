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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.todofromscratch.R
import com.example.todofromscratch.cache.Cache
import com.example.todofromscratch.model.domain.StoreItem
import com.example.todofromscratch.presenter.StorePresenter
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    onBackClicked: () -> Unit
) {

    /* TODO next.
    *   Update gold when item is purchased.
    * */

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val storeItems = remember {mutableStateListOf<StoreItem>()}
    val playerItemsMap = remember { mutableMapOf<String, Int>()}
    val recomposeToggleState = remember { mutableStateOf(false) } // used to force recompose
    val gettingStoreItems = remember { mutableStateOf(false) }
    val gettingPlayerItems = remember { mutableStateOf(false) }

    open class GeneralView (onSuccess : () -> Unit) : StorePresenter.View {

        var onSuccess: (() -> Unit)? = onSuccess

        override fun showInfoMessage(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }

        override fun taskSuccess(message: String?) {
            onSuccess?.invoke()
        }

        override fun taskFail(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val storePresenter = StorePresenter(GeneralView {for (item in Cache.getInstance().storeItems.items) {
        storeItems.add(item);
    }})

    if (Cache.getInstance().storeItems.items == null) {
        if (!gettingStoreItems.value) {        // get storeItems from database
            gettingStoreItems.value = true;
            println("Store items is null. So getting them");
            storePresenter.getStoreItems();
        }
    }
    else if (storeItems.isEmpty()){
        for (item in Cache.getInstance().storeItems.items) {
            println("Cache has ${item.itemName}")
            storeItems.add(item)
        }
    }

    if (Cache.getInstance().playerItems.isEmpty()) {
        if (!gettingPlayerItems.value) {
            gettingPlayerItems.value = true;

            // get storeItems from database
            println("Player items is null. So getting them");
            val listPlayerItemsPresenter = StorePresenter(GeneralView {
                println("In general view success for listing player items")
                for (item in Cache.getInstance().playerItems) {
                    if (playerItemsMap.containsKey(item.itemName)) {
                        playerItemsMap[item.itemName] = playerItemsMap[item.itemName]!! + 1
                    } else {
                        playerItemsMap[item.itemName] = 1
                    }
                }
                for (item in Cache.getInstance().storeItems.items) {
                    if (playerItemsMap.containsKey(item.itemName)) {
                        continue;
                    }
                    playerItemsMap[item.itemName] = 0
                }
            })
            listPlayerItemsPresenter.listPlayerItems();
        }
    }
    else if (playerItemsMap.isEmpty()) {
        for (item in Cache.getInstance().playerItems) {
            if (playerItemsMap.containsKey(item.itemName)) {
                playerItemsMap[item.itemName] = playerItemsMap[item.itemName]!! + 1
            } else {
                playerItemsMap[item.itemName] = 1
            }
        }
        for (item in Cache.getInstance().storeItems.items) {
            if (playerItemsMap.containsKey(item.itemName)) {
                continue;
            }
            playerItemsMap[item.itemName] = 0
        }
    }

    class PurchaseItemView (onSuccess : (String) -> Unit, val itemName: String) : StorePresenter.View {

        var onSuccess: ((String) -> Unit)? = onSuccess

        override fun taskSuccess(message: String?) {
            onSuccess?.invoke(itemName)
        }
        override fun showInfoMessage(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
        override fun taskFail(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

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
                        onBackClicked()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
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
                        .weight(1f)
                        .fillMaxWidth()
                )
                Box(
                    modifier= Modifier
                        .width(150.dp)
                        .height(50.dp)
                ) {
                    Surface(
//                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        color = Color.LightGray,
                        modifier = Modifier
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
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
                                    .align(Alignment.CenterVertically),
                                tint = Color.Unspecified
                            )
                            Text("x" + Cache.getInstance().currPlayer.gold,
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
                items(storeItems) { item ->
                    Card(
                        modifier = Modifier
                            .padding(15.dp, 10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        onClick = {
                            val purchaseItemPresenter = StorePresenter(PurchaseItemView(
                                itemName = item.itemName,
                                onSuccess = { itemName ->
                                    playerItemsMap[itemName] = playerItemsMap[itemName]!! + 1
                                    recomposeToggleState.value = !recomposeToggleState.value
                                }
                            ))
                            purchaseItemPresenter.addItem(item.itemName)
//                            playerItemsMap[item.itemName] = playerItemsMap[item.itemName]!! + 1
//                            recomposeToggleState.value = !recomposeToggleState.value

                            // TODO update gold
//                            Cache.getInstance().currPlayer.gold = Cache.getInstance().currPlayer.gold - item.cost
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
                                    .size(35.dp)
                                    .align(Alignment.CenterVertically),
                            )
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
                                    item.effects,
                                    modifier = Modifier
                                        .padding(15.dp, 1.dp),
                                    fontSize = 15.sp
                                )
                                Text(
                                    "You currently have ${playerItemsMap[item.itemName]}",
                                    modifier = Modifier
                                        .padding(15.dp, 1.dp),
                                    fontSize = 10.sp
                                )
                            }
//                            Spacer(Modifier.weight(0.5f))
                            Spacer(Modifier)
                            Text(
                                "${item.cost} Gold",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(5.dp, 1.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_android_gold_24dp),
                                contentDescription = "Localized description",
                                modifier = Modifier
                                    .size(15.dp)
                                    .align(Alignment.CenterVertically),
                                tint = Color.Unspecified
                            )

                        }
                    }
                }
            }
        }

        LaunchedEffect(recomposeToggleState.value) {} // used to force recompose
    }
}

@Preview
@Composable
fun ShopScreenPreview() {
    TodoFromScratchTheme {
        ShopScreen(
            onBackClicked = {}
        )
    }
}