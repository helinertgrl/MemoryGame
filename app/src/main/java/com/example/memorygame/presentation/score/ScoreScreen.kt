package com.example.memorygame.presentation.score

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorygame.presentation.game.GameViewModel

@Composable
fun ScoreScreen(viewModel: GameViewModel = viewModel()) {

    var name by remember { mutableStateOf("") }

    var playerNames = remember { mutableStateListOf<String>() }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.padding(15.dp))

        Card (
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color = Color.Black),
        elevation = CardDefaults.cardElevation(10.dp)

    ){
        LazyColumn{
            item {
                Row {
                    Text(text = "Moves: ${viewModel.moves.value}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp))

                    Text(text = "Score: ${viewModel.score.value}",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    }
                }
                items(playerNames){ player ->
                    Text(text = "Player: $player")
            }
        }
    }

        Spacer(modifier = Modifier.padding(30.dp))

        TextField(value =name, onValueChange ={newValue -> name = newValue},
            label ={ Text(text = "Enter your name to save your score")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (name.isNotBlank()){
                        playerNames.add(name)
                        name = ""

                    }
                }
            )
        )
    }
}