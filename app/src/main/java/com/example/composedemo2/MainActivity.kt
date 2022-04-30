package com.example.composedemo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.composedemo2.navigation.Navigation
import com.example.composedemo2.screens.FirstScreen
import com.example.composedemo2.ui.theme.ComposeDemo2Theme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemo2Theme {
                Navigation()
            }
        }
    }
}



@Composable
fun Activity(
    mainViewModel: SupportViewModel,
    navControler: NavHostController,
) {

    FirstScreen(
        sessions = mainViewModel.uiState,
        click = mainViewModel.click,
        navControler = navControler,
        navigate = mainViewModel::navigate,
        addToFavorite = mainViewModel::addToFavorite,
    )

}


