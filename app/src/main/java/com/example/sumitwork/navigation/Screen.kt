package com.example.sumitwork.navigation



sealed class Screen(val route: String) {

    data object Home : Screen("home")
    data object Cart : Screen("?items={items}/cart"){
        fun createRouteWithValues(items: String) = "?items=$items/cart"
    }
}