package com.example.composedemo2.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composedemo2.Activity
import com.example.composedemo2.SupportViewModel
import com.example.composedemo2.screens.SessionScreen



@Composable
fun Navigation() {


    val navControler = rememberNavController()

    NavHost(navController = navControler, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
         val supportViewModel = viewModel<SupportViewModel>()
            Activity(mainViewModel = supportViewModel, navControler = navControler)
        }
        composable(
            route = Screen.SessionScreen.route + "/{data}/{description}/{speaker}/{time}/{image}",  // /{data}/{image}/{description}/{speaker}/{timeInterval}
            arguments = listOf(
                navArgument("data") {
                    type = NavType.StringType
                },
                navArgument("description") {
                    type = NavType.StringType
                },
                navArgument("speaker") {
                    type = NavType.StringType
                },
                navArgument("time") {
                    type = NavType.StringType
                },
                navArgument("image") {
                    type = NavType.StringType
                },
            )
            ) {
            val data = it.arguments?.getString("data").toString()
            val description = it.arguments?.getString("description").toString()
            val speaker = it.arguments?.getString("speaker").toString()
            val time = it.arguments?.getString("time").toString()
            val image = it.arguments?.getString("image").toString()
            SessionScreen(
                date = data,
                description = description,
                speaker = speaker,
                timeInterval = time,
                image = image,
            )
        }
    }

}


/*
data class Session(
    val date: String,
    val description: String,
    val id: String,
    val imageUrl: String,
    var isFavourite: Boolean,
    val speaker: String,
    val timeInterval: String
)
 */