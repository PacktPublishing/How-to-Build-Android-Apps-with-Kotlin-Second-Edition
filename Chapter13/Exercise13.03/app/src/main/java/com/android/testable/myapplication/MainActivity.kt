package com.android.testable.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.numberLiveData.observe(
            this
        ) {
            findViewById<TextView>(R.id.activity_main_text_view).text = it.toString()
        }
        findViewById<TextView>(R.id.activity_main_button).setOnClickListener {
            mainViewModel.generateNextNumber()
        }
    }
}
