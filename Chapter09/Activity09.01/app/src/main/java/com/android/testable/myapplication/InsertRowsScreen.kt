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
fun InsertRowsScreen(onButtonClick: (String) -> Unit) {
    var state by remember {
        mutableStateOf(InsertRowsScreenState())
    }
    InsertRowsScreenContent(state, {
        state = state.copy(rowsCount = it)
    }, {
        onButtonClick(state.rowsCount)
    })
}

@Composable
fun InsertRowsScreenContent(
    insertRowsScreenState: InsertRowsScreenState,
    onItemCountChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column {
        OnBackgroundTitleText(text = stringResource(id = R.string.insert_number_rows))
        OnBackgroundBodyTextField(
            value = insertRowsScreenState.rowsCount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onItemCountChange
        )
        PrimaryTextButton(text = stringResource(id = R.string.done), onClick = onButtonClick)
    }
}

data class InsertRowsScreenState(
    val rowsCount: String = ""
)