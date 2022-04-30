package com.example.composedemo2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.material.Icon
import androidx.compose.ui.res.painterResource
import com.example.composedemo2.R
import java.net.URLDecoder


@Composable
fun SessionScreen(
    image: String,
    speaker: String,
    date: String,
    timeInterval: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),

            ) {
            Image(
                painter = rememberImagePainter(
                    data = URLDecoder.decode(image)
                ),
                contentDescription = "Avatar"
            )
        }
        Text(
            text = speaker,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(modifier = Modifier.padding(start = 24.dp))
            Icon(
                painter = painterResource(R.drawable.ic_baseline_calendar_today_24),
                contentDescription = "Дата",
                modifier = Modifier.padding(end = 6.dp)
            )

            Text(
                text = "$date , $timeInterval",
                fontWeight = FontWeight.ExtraLight,
            )
        }

        Text(
            text = description,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 24.dp, top = 6.dp),
            fontWeight = FontWeight.Light
        )
    }
}


@Preview
@Composable
fun Preview() {
    SessionScreen(
        date = "19 апреля",
        description = "Доклад: Краткий экскурс в мир многопоточности",
        image = "https://static.tildacdn.com/tild3432-3435-4561-b136-663134643162/photo_2021-04-16_18-.jpg",
        speaker = "Степан Чурюканов",
        timeInterval = "10:00-11:00"

    )
}

/*
val session = Session(
    date = "19 апреля",
    description = "Доклад: Краткий экскурс в мир многопоточности",
    id = "1",
    imageUrl = "https://static.tildacdn.com/tild3432-3435-4561-b136-663134643162/photo_2021-04-16_18-.jpg",
    isFavourite = false,
    speaker = "Степан Чурюканов",
    timeInterval = "10:00-11:00"
)


 */