package com.example.memorygame.presentation.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorygame.presentation.game.GameViewModel
import com.example.memorygame.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {

    val emojilist = listOf(
        R.drawable.alien,
        R.drawable.monkey,
        R.drawable.sad,
        R.drawable.poop,
        R.drawable.heart,
        R.drawable.evil,
        R.drawable.pumpkin,
        R.drawable.ghost,
        R.drawable.alien,
        R.drawable.monkey,
        R.drawable.sad,
        R.drawable.poop,
        R.drawable.heart,
        R.drawable.evil,
        R.drawable.pumpkin,
        R.drawable.ghost
    )

    val shuffledList = remember { emojilist.shuffled() }

    val faceUp = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(16) {
                add(false)
            }
        }
    }

    val matched = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(16) {
                add(false)
            }
        }
    }

    val selected = remember { mutableStateListOf<Int>() }

    val toClose = remember { mutableStateListOf<Int>() }


    LaunchedEffect(toClose.toList()) {
        if (toClose.size == 2) {
            delay(800)
            toClose.forEach { index ->
                faceUp[index] = false
            }
            toClose.clear()
            selected.clear()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Game Screen") }) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Moves: ${viewModel.moves.value}",
                fontSize = 20.sp
            )
            Text(
                text = "Score: ${viewModel.score.value}",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))


            rowCard(
                shuffledList = shuffledList,
                faceUp = faceUp,
                matched = matched,
                selected = selected,
                toClose = toClose,
                onMove = { viewModel.increaseMove() },
                onScore = { viewModel.increaseScore() })
        }
    }
}




@Composable
fun rowCard(
    shuffledList: List<Int>,
    faceUp: SnapshotStateList<Boolean>,
    matched: SnapshotStateList<Boolean>,
    selected: SnapshotStateList<Int>,
    toClose: SnapshotStateList<Int>,
    onMove: ()-> Unit,
    onScore: ()-> Unit
) {

    val grouped = shuffledList.chunked(4)


    grouped.forEachIndexed { rowIndex, rowItems ->
        Row {
            rowItems.forEachIndexed { colIndex, imageRes ->
                val globalIndex = rowIndex * 4 +colIndex
                myCard(imageRes = shuffledList[globalIndex],
                    isFaceUp = faceUp[globalIndex],
                    isMatched = matched[globalIndex],
                    onClick = {
                        if (!faceUp[globalIndex] && !matched[globalIndex]){
                            faceUp[globalIndex] = true
                            selected.add(globalIndex)

                            if (selected.size == 2){
                                onMove()
                                val first = selected[0]
                                val second = selected[1]

                                if (shuffledList[first] == shuffledList[second]){
                                    matched[first] = true
                                    matched[second] = true
                                    selected.clear()
                                    onScore()
                                }else {
                                    toClose.add(first)
                                    toClose.add(second)
                                }
                            }

                        }
                    }
                )
            }
        }
    }
}

@Composable
fun myCard(
    imageRes: Int,
    isFaceUp: Boolean,
    isMatched: Boolean,
    onClick: () -> Unit){

    Card (
        modifier = Modifier.size(95.dp)
            .padding(5.dp)
            .clickable{
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp,Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ){
        if (isFaceUp || isMatched == true){    //true yazmasak da olur.
            Image(painterResource(imageRes), contentDescription = "$imageRes")
        }else {
            Box(Modifier.fillMaxSize())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun preview(){
    GameScreen()
}


