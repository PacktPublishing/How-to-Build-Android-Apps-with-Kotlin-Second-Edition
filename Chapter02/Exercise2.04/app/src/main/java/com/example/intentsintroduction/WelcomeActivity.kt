package com.example.intentsintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (intent != null) {
            val fullName = intent.getStringExtra(FULL_NAME_KEY)
            findViewById<TextView>(R.id.welcome_text).text = getString(R.string.welcome_text, fullName)
        }

    }
}
