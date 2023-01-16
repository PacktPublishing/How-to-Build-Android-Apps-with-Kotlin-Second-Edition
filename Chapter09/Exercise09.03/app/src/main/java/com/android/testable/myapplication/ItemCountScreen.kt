package com.android.testable.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.android.testable.myapplication.ui.theme.OnBackgroundTitleText
import com.android.testable.myapplication.ui.theme.PrimaryTextButton

@Composable
fun ItemCountScreen(onButtonClick: (String) -> Unit) {
    var state by remember {
        mutableStateOf(ItemCountScreenState())
    }
    ItemCountScreenContent(state, {
        state = state.copy(itemCount = it)
    }, {
        onButtonClick(state.itemCount)
    })
}

@Composable
fun ItemCountScreenContent(
    itemCountScreenState: ItemCountScreenState,
    onItemCountChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column {
        OnBackgroundTitleText(text = stringResource(id = R.string.enter_number))
        TextField(
            value = itemCountScreenState.itemCount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onItemCountChange
        )
        PrimaryTextButton(text = stringResource(id = R.string.click_me), onClick = onButtonClick)
    }
}

data class ItemCountScreenState(
    val itemCount: String = ""
)