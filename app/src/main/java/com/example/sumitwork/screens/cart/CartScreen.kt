package com.example.sumitwork.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sumitwork.R
import com.example.sumitwork.component.ProgressDialog
import com.example.sumitwork.component.ToolbarView
import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.ui.theme.OrangeColor
import com.example.sumitwork.ui.theme.TextColor
import com.example.sumitwork.ui.theme.spacingMedium
import com.example.sumitwork.ui.theme.spacingSmall
import com.example.sumitwork.utils.SIDE_EFFECTS_KEY
import com.example.sumitwork.utils.ScreenHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    state: CartContract.State,
    effectFlow: Flow<CartContract.Effect>?,
    onEvent: (event: CartContract.Event) -> Unit,
    onNavigate: (navigationEffect: CartContract.Effect.Navigation) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is CartContract.Effect.Navigation -> {
                    onNavigate(effect)
                }

                else -> {}
            }
        }?.collect()
    }

    ScreenHolder(
        topBar = {
            ToolbarView(
                title = stringResource(id = R.string.my_basket),
                onBackPressed = {
                    onNavigate(CartContract.Effect.Navigation.NavigateToBack)
                },
                topBarColor = OrangeColor
            )
        },
        content = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White)
                    .padding(spacingMedium)
                    .verticalScroll(scrollState)
            ) {
                Column {
                    state.selectedList.forEachIndexed{index, productModel ->
                        RowItem(item = productModel) {

                        }
                    }
                }
            }
        },
        snackBarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomView = {
            Row(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.weight(1f).padding(spacingMedium),
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(id = R.string.total),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(500),
                            color = TextColor,
                        )
                    )

                    Text(
                        text = "₹" + state.totalPrice,
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 32.sp,
                            fontWeight = FontWeight(500),
                            color = TextColor,
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = OrangeColor, shape = RoundedCornerShape(spacingSmall))
                        .clickable {
                            scope.launch {
                                sheetState.show()
                            }
                        }, // Circle with a background color
                    contentAlignment = Alignment.Center // Center the content inside the Box
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = spacingMedium),
                        text = stringResource(id = R.string.checkout),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(500),
                            color = Color.White,
                        )
                    )
                }
            }

        }
    )

    if (state.loading) {
        ProgressDialog(
            message = context.getString(R.string.loading),
            onDismiss = {}
        )
    }
    if (sheetState.isVisible) {
        CoupleSheet(
            sheetState = sheetState,
            onCancelBottomSheet={})
    }

}

@Composable
fun RowItem(item: ProductModel, onEvent: (event: CartContract.Event) -> Unit) {
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(spacingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            AsyncImage(
                model = item.image,
                contentDescription = "Sample Image",
                modifier = Modifier
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.width(spacingMedium))

            Text(
                text = item.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = TextColor,
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "₹" + item.price.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = OrangeColor,
                )
            )
        }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(
        state = CartContract.State(),
        effectFlow = null,
        onEvent = {},
        onNavigate = {})
}