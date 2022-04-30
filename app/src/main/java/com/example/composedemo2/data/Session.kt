package com.example.composedemo2.data

import android.os.Bundle
import androidx.navigation.NavType

data class Session(
    val date: String,
    val description: String,
    val id: String,
    val imageUrl: String,
    var isFavourite: Boolean,
    val speaker: String,
    val timeInterval: String
)

