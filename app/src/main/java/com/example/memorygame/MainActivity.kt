package com.example.memorygame

import android.app.GameManager
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IdRes
import androidx.annotation.experimental.Experimental
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.ui.theme.MemoryGameTheme
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants
import org.jetbrains.annotations.ApiStatus

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