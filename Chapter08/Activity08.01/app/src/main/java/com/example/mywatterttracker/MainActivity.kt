package com.example.mywatterttracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mywatterttracker.WaterTrackingService.Companion.EXTRA_INTAKE_AMOUNT_MILLILITERS

class MainActivity : AppCompatActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val waterButton: View
        by lazy { findViewById(R.id.main_water_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waterButton.setOnClickListener {
            launchTrackingService(250f)
        }
        waterButton.isEnabled = false

        ensurePermissionGrantedAndLaunchTracking()
    }

    private fun ensurePermissionGrantedAndLaunchTracking() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            launchTrackingService()
            return
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchTrackingService()
            } else {
                showPermissionRationale {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        when {
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                launchTrackingService()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                showPermissionRationale {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun launchTrackingService(intakeAmount: Float = 0f) {
        waterButton.isEnabled = true
        val serviceIntent = Intent(this, WaterTrackingService::class.java).apply {
            putExtra(EXTRA_INTAKE_AMOUNT_MILLILITERS, intakeAmount)
        }
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun showPermissionRationale(positiveAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Notifications permission")
            .setMessage("To show your current fluid balance, we need the notifications permission")
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ -> positiveAction() }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
