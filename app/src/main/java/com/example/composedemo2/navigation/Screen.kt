package com.example.composedemo2.navigation


sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object SessionScreen: Screen("session_screen")
}
