package com.example.memorygame.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.example.memorygame.presentation.Game
import com.example.memorygame.presentation.Main
import com.example.memorygame.presentation.Score

@Composable
fun BottomNavi(navController: NavController) {

    val destinationlist = listOf(
        Main,
        Game,
        Score
    )

    val selectedindex = rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        destinationlist.forEachIndexed { index, destinations ->
            NavigationBarItem(
                label = { Text(text = destinations.title) },
                icon = {
                    Icon(
                        imageVector = destinations.icon,
                        contentDescription = destinations.title
                    )
                },
                selected = index == selectedindex.value,
                onClick = {
                    selectedindex.value = index
                    navController.navigate(destinationlist[index].route){
                        popUpTo(route = Main.route)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}