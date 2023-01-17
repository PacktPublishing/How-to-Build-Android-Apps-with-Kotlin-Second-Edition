package com.android.testable.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViewById<Button>(R.id.activity_settings_button).setOnClickListener {
            (application as MainApplication).preferencesWrapper.saveNumberOfResults(
                findViewById<EditText>(
                    R.id.activity_settings_edit_text
                ).text.toString().toIntOrNull() ?: 0
            )
        }
    }
}