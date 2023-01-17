package com.android.testable.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val assetFileManager: AssetFileManager by lazy {
        AssetFileManager(applicationContext.assets)
    }
    private val providerFileManager: ProviderFileManager by lazy {
        ProviderFileManager(
            applicationContext,
            FileToUriMapper(),
            Executors.newSingleThreadExecutor()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.activity_main_file_provider).setOnClickListener {
            val newFileName = "Copied.txt"
            providerFileManager.writeStream(newFileName, assetFileManager.getMyAppFileInputStream())
        }
        val createDocumentResult =
            registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
                uri?.let {
                    val newFileName = "Copied.txt"
                    providerFileManager.writeStreamFromUri(
                        newFileName,
                        assetFileManager.getMyAppFileInputStream(),
                        uri
                    )
                }
            }

        findViewById<Button>(R.id.activity_main_saf).setOnClickListener {
            createDocumentResult.launch("Copied.txt")
        }
    }
}
