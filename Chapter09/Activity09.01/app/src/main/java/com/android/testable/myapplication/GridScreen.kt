package com.android.testable.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.testable.myapplication.ui.theme.OnBackgroundItemText

@Composable
fun GridScreen(rowCount: String, columnCount: String) {
    val items = mutableListOf<List<String>>()
    (1..rowCount.toInt()).forEach { rowIndex ->
        items.add((1..columnCount.toInt()).toList().map { columnIndex ->
            stringResource(
                id = R.string.item_format,
                formatArgs = arrayOf("$rowIndex", "$columnIndex")
            )
        })
    }
    GridScreenContent(gridScreenState = GridScreenState(items = items))
}

@Composable
fun GridScreenContent(
    gridScreenState: GridScreenState
) {
    LazyColumn {
        items(gridScreenState.items) { rowItem ->
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                LazyRow {
                    items(rowItem) { item ->
                        Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                            OnBackgroundItemText(text = item)
                        }
                    }
                }
            }
        }
    }
}

data class GridScreenState(
    val items: List<List<String>> = emptyList()
)