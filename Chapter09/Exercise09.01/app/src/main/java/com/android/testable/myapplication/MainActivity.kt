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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val items = (1..100).toList().map {
                stringResource(id = R.string.item_format, formatArgs = arrayOf("$it"))
            }
            MyScreen(items)
        }
    }
}

@Composable
fun MyScreen(
    items: List<String>
) {
    LazyColumn {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(id = R.string.enter_number))
                TextField(
                    value = "",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {

                    })
                Button(onClick = { }) {
                    Text(text = stringResource(id = R.string.click_me))
                }
            }
        }
        items(items) { item ->
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(text = item)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyScreen(listOf("Item 1", "Item 2", "Item 3"))
}