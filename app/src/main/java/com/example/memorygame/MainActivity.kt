package com.example.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.ui.theme.MemoryGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryGameTheme {
                memoryGame()
                }
            }
        }
    }


@Composable
fun memoryGame(){
   val navController = rememberNavController()

    Scaffold (
        bottomBar = {myBottomNavi(navController)}
    ) {
        Box(modifier = Modifier.padding(it)){
            NavHost(navController = navController, startDestination = Main.route){
                composable (route = Main.route) {
                    MainMenu(navController)
                }
                composable(route = Game.route) {
                    GameScreen(viewModel = viewModel ())
                }
                composable (route = Score.route) {
                    ScoreScreen(viewModel = viewModel())
                }
            }
        }
    }
}

@Composable
fun myBottomNavi(navController: NavController) {

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

interface Destinations{
    val route: String
    val icon: ImageVector
    val title : String
}

object Main: Destinations{
    override val route = "Main"
    override val icon = Icons.Filled.Home
    override val title = "Main"
}

object Game: Destinations{
    override val route = "Game"
    override val icon = Icons.Filled.Star
    override val title = "Game"
}

object Score: Destinations{
    override val route = "Score"
    override val icon = Icons.Filled.CheckCircle
    override val title = "Score"
}