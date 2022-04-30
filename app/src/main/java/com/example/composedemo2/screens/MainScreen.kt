package com.example.composedemo2.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.composedemo2.data.Session
import kotlinx.coroutines.CoroutineScope




@Composable
fun FirstScreen(
    sessions: List<Session>,
    click: Int,
    navControler: NavHostController,
    navigate: (navController: NavHostController, item: Session) -> Unit,
    addToFavorite: (session: Session, scope: CoroutineScope, state: ScaffoldState) -> Unit,

    ) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            SearchBar(
                state = textState
            )
        }
    ) {
        if (textState.value.text.isNotEmpty()) {

            val filterSessions = sessions.filter {
                it.speaker.lowercase().contains(textState.value.text.toRegex()) || it.description.lowercase()
                    .contains(textState.value.text.toRegex())
            }

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ItemLazyColumn(
                    sessions = filterSessions,
                    navControler,
                    state = scaffoldState,
                    navigate = navigate,
                    addToFavorite
                )
            }
        } else {

            Column() {

                FavoritesLazyRow(
                    sessions = sessions,
                    click = click,
                    navControler,
                    navigate
                )
                ItemLazyColumn(
                    sessions = sessions,
                    navControler,
                    state = scaffoldState,
                    navigate = navigate,
                    addToFavorite
                )

            }
        }
    }

}


@Composable
fun SearchBar(
    state: MutableState<TextFieldValue>,
) {

    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        placeholder = {
            Text(
                text = "Поиск",
            )
        },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Localized description"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
          ,
        singleLine = true,
    )
}


@Composable
fun FavoritesBox(
    session: Session,
    navControler: NavHostController,
    navigate: (navController: NavHostController, item: Session) -> Unit
) {

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .padding(6.dp)
            .clickable {
                navigate(navControler, session)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Column(modifier = Modifier.padding(6.dp)) {

            Text(
                text = session.timeInterval,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = session.date,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = session.speaker,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = session.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun FavoritesLazyRow(
    sessions: List<Session>,
    click: Int,
    navControler: NavHostController,
    navigate: (navController: NavHostController, item: Session) -> Unit
) {
    if (click > 0) {
        Column(
            Modifier
                .animateContentSize()
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = "Избранное",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp)
            )
            LazyRow(
                Modifier.fillMaxWidth(),
            ) {
                items(sessions) { session ->
                    if (session.isFavourite) FavoritesBox(
                        session,
                        navControler,
                        navigate
                    )
                }
            }
        }
    }
}


@Composable
fun SessionCard(
    session: Session,
    navControler: NavHostController,
    state: ScaffoldState,
    navigate: (navController: NavHostController, item: Session) -> Unit,
    addToFavorite: (session: Session, scope: CoroutineScope, state: ScaffoldState) -> Unit
) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(6.dp)
            .clickable {
                navigate(navControler, session)
            },
        elevation = 6.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(6.dp))

            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterVertically),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),

                ) {
                Image(
                    painter = rememberImagePainter(
                        data = session.imageUrl
                    ),
                    contentDescription = "Avatar"
                )
            }
            Spacer(modifier = Modifier.padding(3.dp))
            Column(
                Modifier
                    .width(280.dp)
                    .align(Alignment.CenterVertically)

            ) {
                Text(
                    text = session.speaker,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = session.timeInterval,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,

                    )
                Text(
                    text = session.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,

                    )
            }
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    addToFavorite(
                        session,
                        scope,
                        state,
                    )
                }
            ) {
                if (!session.isFavourite) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = "Refresh Button",
                    )
                } else {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Refresh Button",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemLazyColumn(
    sessions: List<Session>,
    navControler: NavHostController,
    state: ScaffoldState,
    navigate: (navController: NavHostController, item: Session) -> Unit,
    addToFavorite: (session: Session, scope: CoroutineScope, state: ScaffoldState) -> Unit
) {

    Column {
        Text(
            text = "Сессий",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )
        LazyColumn(
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            val data = sessions.groupBy { it.date }

            data.forEach { (initial, contactsForInitial) ->
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            text = initial,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }
                items(contactsForInitial) { session ->
                    SessionCard(
                        session,
                        navControler,
                        state,
                        navigate,
                        addToFavorite
                    )
                }
            }
        }
    }
}

