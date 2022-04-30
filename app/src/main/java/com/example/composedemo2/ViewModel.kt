package com.example.composedemo2

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.composedemo2.data.Session
import com.example.composedemo2.data.retrofit.ApiInterface
import com.example.composedemo2.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URLEncoder

class SupportViewModel : ViewModel() {


    init {
        getSessionList()
    }

    var uiState by mutableStateOf<List<Session>>(listOf())
        private set

    private var errorMessage: String by mutableStateOf("")



    var click by mutableStateOf(0)

    private fun getSessionList() {
        viewModelScope.launch {
            val apiInterface = ApiInterface.getInstance()
            try {
                val sessionList = apiInterface.getSessionList()
                uiState = sessionList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun navigate(navController: NavHostController, item: Session) {
        val encodedUrl = URLEncoder.encode(item.imageUrl)
        navController.navigate(
            Screen.SessionScreen.route +
                    "/${item.date}/${item.description}/${item.speaker}/${item.timeInterval}/${encodedUrl}"
        )
    }


    fun addToFavorite (session: Session, scope: CoroutineScope, state : ScaffoldState) {
        when {
            session.isFavourite -> deleteItem(session)
            !session.isFavourite -> {
                if (click < 3) {
                    addItem(session)
                } else {
                    scope.launch {
                        state.snackbarHostState.showSnackbar(
                            "Не удалось добавить сессию в избранное",
                            actionLabel = "Ok"
                        )
                        if (session.isFavourite) deleteItem(session)
                    }
                }
            }
        }
    }

    private fun addItem(item: Session) {
        item.isFavourite = true
        ++click
    }

    private fun deleteItem(item: Session) {
        item.isFavourite = false
        --click
    }
}


