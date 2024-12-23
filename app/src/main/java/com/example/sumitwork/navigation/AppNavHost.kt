package com.example.sumitwork.navigation

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.screens.HomeContract
import com.example.sumitwork.screens.HomeScreen
import com.example.sumitwork.screens.HomeViewModel
import com.example.sumitwork.screens.cart.CardViewModel
import com.example.sumitwork.screens.cart.CartContract
import com.example.sumitwork.screens.cart.CartScreen
import com.example.sumitwork.utils.json
import com.example.sumitwork.utils.toType

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            LaunchedEffect(Unit) {
                viewModel.getProducts()
            }
            HomeScreen(
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEvent = { event -> viewModel.setEvent(event) },
                onNavigate = { navigationEffect ->
                    when (navigationEffect) {
                        is HomeContract.Effect.Navigation.NavigateToCartList -> {
                            navController.navigate(
                                Screen.Cart.createRouteWithValues(viewModel.viewState.value.selectedList.json())
                            )
                        }

                        else -> {}
                    }
                }
            )
        }

        composable(Screen.Cart.route) {
            val viewModel = hiltViewModel<CardViewModel>()
            val data = remember { it.arguments?.getString("items").orEmpty() }
            LaunchedEffect(data) {
                if (data.isNotEmpty()) {
                    val res = data.toType<List<ProductModel>>()
                    viewModel.setData(res)
                }
            }
            CartScreen(
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEvent = { event -> viewModel.setEvent(event) },
                onNavigate = { navigationEffect ->
                    when (navigationEffect) {
                        is CartContract.Effect.Navigation.NavigateToBack -> {
                            navController.navigateUp()
                        }

                        is CartContract.Effect.Navigation.NavigateToThankYou ->{}
                    }
                }
            )
        }
    }

}