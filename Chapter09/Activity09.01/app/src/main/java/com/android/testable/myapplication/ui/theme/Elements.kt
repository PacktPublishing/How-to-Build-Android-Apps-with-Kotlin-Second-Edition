package com.android.testable.myapplication.ui.theme

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(text = text, color = MaterialTheme.colors.onBackground)
}

@Composable
fun TitleText(text: String, color: Color) {
    Text(text = text, style = MaterialTheme.typography.h5, color = color)
}

@Composable
fun OnBackgroundBodyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    BodyTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        textColor = MaterialTheme.colors.onBackground
    )
}

@Composable
fun BodyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    textColor: Color
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(textColor = textColor)
    )
}

@Composable
fun OnBackgroundItemText(text: String) {
    ItemText(text = text, color = MaterialTheme.colors.onBackground)
}

@Composable
fun ItemText(text: String, color: Color) {
    Text(text = text, style = MaterialTheme.typography.body1, color = color)
}

@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    TextButton(text = text, textColor = Color.Red, onClick = onClick)
}

@Composable
fun TextButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(contentColor = textColor)
    ) {
        Text(text = text, style = MaterialTheme.typography.button)
    }
}
