package com.example.sumitwork.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sumitwork.R
import com.example.sumitwork.component.ProgressDialog
import com.example.sumitwork.component.SearchView
import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.ui.theme.LightOrangeColor
import com.example.sumitwork.ui.theme.OrangeColor
import com.example.sumitwork.ui.theme.TextColor
import com.example.sumitwork.ui.theme.spacingMedium
import com.example.sumitwork.ui.theme.spacingSmall
import com.example.sumitwork.utils.SIDE_EFFECTS_KEY
import com.example.sumitwork.utils.ScreenHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEvent: (event: HomeContract.Event) -> Unit,
    onNavigate: (navigationEffect: HomeContract.Effect.Navigation) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation -> {
                    onNavigate(effect)
                }

                else -> {}
            }
        }?.collect()
    }

    ScreenHolder(
        topBar = {
        },
        content = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White)
                    .padding(spacingMedium)
            ) {

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.toogle
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                            }
                    )

                    Column(modifier = Modifier.clickable {
                        onNavigate(HomeContract.Effect.Navigation.NavigateToCartList)
                    }){
                        Icon(painter = painterResource(id = R.drawable.basket),
                            tint = OrangeColor,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp))

                        Text(
                            text = stringResource(id = R.string.my_basket),
                            style = TextStyle(
                                fontSize = 10.sp,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight(400),
                                color = TextColor,

                                )
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(end = 60.dp),
                    text = stringResource(id = R.string.home_text),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight(400),
                        color = TextColor,
                        )
                )

                Spacer(modifier = Modifier.padding(top = spacingSmall))

                SearchView(
                    hint = R.string.search_text,
                    onSearchInput = {
                       // onEvent(HomeContract.Event.OnSearch(it))
                    }
                )

                Spacer(modifier = Modifier.padding(top = spacingMedium))

                Text(
                    text = stringResource(id = R.string.recommended_combo),
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight(500),
                        color = TextColor,
                    )
                )

                
                LazyVerticalGrid( columns = GridCells.Fixed(2), // 2 columns
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)) {
                    items(state.productList.size) { index ->
                        val item = state.productList.get(index)
                        GridItem(item = item, onEvent)
                    }
                }
            }
        },
        snackBarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomView = {
        }
    )

    if (state.loading) {
        ProgressDialog(
            message = context.getString(R.string.loading),
            onDismiss = {}
        )
    }

}

@Composable
fun GridItem(item: ProductModel, onEvent: (event: HomeContract.Event) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Change the color here
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .clickable {
                onEvent(HomeContract.Event.OnItemSelection(item))
            }
            .padding(spacingMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = item.image,
                contentDescription = "Sample Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(spacingSmall))

            Text(
                text = item.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = TextColor,
                )
            )

            Spacer(modifier = Modifier.height(spacingSmall))

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "₹" + item.price.toString(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = OrangeColor,
                    )
                )

                Box(
                    modifier = Modifier
                        .size(40.dp) // Set the size of the circle
                        .background(color = LightOrangeColor, shape = CircleShape), // Circle with a background color
                    contentAlignment = Alignment.Center // Center the content inside the Box
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, // Add icon
                        contentDescription = "Plus Icon",
                        tint = OrangeColor, // Icon color
                        modifier = Modifier.size(24.dp) // Icon size
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeContract.State(),
        effectFlow = null,
        onEvent = {},
        onNavigate = {})
}