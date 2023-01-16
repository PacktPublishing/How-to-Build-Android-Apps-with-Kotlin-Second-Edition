package com.android.testable.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreen()
        }
    }
}

@Composable
fun MyScreen() {
    var state by remember {
        mutableStateOf(MyScreenState())
    }
    val context = LocalContext.current
    MyScreenContent(state, {
        state = state.copy(itemCount = it)
    }, {
        state = state.copy(items = (1..state.itemCount.toInt()).toList().map {
            context.getString(R.string.item_format, "$it")
        })
    })
}

@Composable
fun MyScreenContent(
    myScreenState: MyScreenState,
    onItemCountChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(id = R.string.enter_number))
                TextField(
                    value = myScreenState.itemCount,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = onItemCountChange
                )
                Button(onClick = onButtonClick) {
                    Text(text = stringResource(id = R.string.click_me))
                }
            }
        }
        items(myScreenState.items) { item ->
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = item)
            }
        }
    }
}

data class MyScreenState(
    val itemCount: String = "",
    val items: List<String> = emptyList()
)


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyScreen()
}