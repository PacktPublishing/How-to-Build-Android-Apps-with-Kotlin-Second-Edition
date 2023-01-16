package com.android.testable.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.android.testable.myapplication.ui.theme.OnBackgroundBodyTextField
import com.android.testable.myapplication.ui.theme.OnBackgroundTitleText
import com.android.testable.myapplication.ui.theme.PrimaryTextButton

@Composable
fun InsertColumnsScreen(onButtonClick: (String) -> Unit) {
    var state by remember {
        mutableStateOf(InsertColumnsScreenState())
    }
    InsertColumnsScreenContent(state, {
        state = state.copy(columnCount = it)
    }, {
        onButtonClick(state.columnCount)
    })
}

@Composable
fun InsertColumnsScreenContent(
    insertColumnsScreenState: InsertColumnsScreenState,
    onItemCountChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column {
        OnBackgroundTitleText(text = stringResource(id = R.string.insert_number_columns))
        OnBackgroundBodyTextField(
            value = insertColumnsScreenState.columnCount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onItemCountChange
        )
        PrimaryTextButton(text = stringResource(id = R.string.done), onClick = onButtonClick)
    }
}

data class InsertColumnsScreenState(
    val columnCount: String = ""
)