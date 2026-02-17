package com.example.memorygame.presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardGrid(
    shuffledList: List<Int>,
    faceUpCard: List<Int>,
    matchedCards: List<Int>,
    onCardClick: (Int) -> Unit
){
    val grouped = shuffledList.chunked(4)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        grouped.forEachIndexed { rowIndex, rowItems ->
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                rowItems.forEachIndexed { colIndex, _ ->
                    val globalIndex = rowIndex * 4 + colIndex
                    MyCard(
                        imageRes = shuffledList[globalIndex],
                        isFaceUp = faceUpCard.contains(globalIndex),
                        isMatched = matchedCards.contains(globalIndex),
                        onClick = { onCardClick(globalIndex) }
                    )
                    if (colIndex < 3)
                        Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}