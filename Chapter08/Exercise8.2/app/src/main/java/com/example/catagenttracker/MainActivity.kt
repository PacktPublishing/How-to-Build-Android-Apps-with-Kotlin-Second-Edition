package com.example.catagenttracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.catagenttracker.RouteTrackingService.Companion.EXTRA_SECRET_CAT_AGENT_ID
import com.example.catagenttracker.worker.CatFurGroomingWorker
import com.example.catagenttracker.worker.CatLitterBoxSittingWorker
import com.example.catagenttracker.worker.CatStretchingWorker
import com.example.catagenttracker.worker.CatSuitUpWorker

class MainActivity : AppCompatActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ensurePermissionGrantedAndDispatchCat()
    }

    private fun ensurePermissionGrantedAndDispatchCat() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            dispatchCat()
            return
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                dispatchCat()
            } else {
                showPermissionRationale {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        when {
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                dispatchCat()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                showPermissionRationale {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun dispatchCat() {
        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val catAgentId = "CatAgent1"
        val catStretchingRequest = OneTimeWorkRequest.Builder(CatStretchingWorker::class.java)
            .setConstraints(networkConstraints)
            .setInputData(
                getCatAgentIdInputData(CatStretchingWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId)
            ).build()
        val catFurGroomingRequest = OneTimeWorkRequest.Builder(CatFurGroomingWorker::class.java)
            .setConstraints(networkConstraints)
            .setInputData(
                getCatAgentIdInputData(CatFurGroomingWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId)
            ).build()
        val catLitterBoxSittingRequest =
            OneTimeWorkRequest.Builder(CatLitterBoxSittingWorker::class.java)
                .setConstraints(networkConstraints)
                .setInputData(
                    getCatAgentIdInputData(
                        CatLitterBoxSittingWorker.INPUT_DATA_CAT_AGENT_ID,
                        catAgentId
                    )
                ).build()
        val catSuitUpRequest = OneTimeWorkRequest.Builder(CatSuitUpWorker::class.java)
            .setConstraints(networkConstraints)
            .setInputData(
                getCatAgentIdInputData(CatSuitUpWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId)
            ).build()

        workManager.getWorkInfoByIdLiveData(catStretchingRequest.id)
            .observe(this) { info ->
                if (info.state.isFinished) {
                    showResult("Agent done stretching")
                }
            }

        workManager.getWorkInfoByIdLiveData(catFurGroomingRequest.id)
            .observe(this) { info ->
                if (info.state.isFinished) {
                    showResult("Agent done grooming its fur")
                }
            }

        workManager.getWorkInfoByIdLiveData(catLitterBoxSittingRequest.id)
            .observe(this) { info ->
                if (info.state.isFinished) {
                    showResult("Agent done sitting in litter box")
                }
            }

        workManager.getWorkInfoByIdLiveData(catSuitUpRequest.id)
            .observe(this) { info ->
                if (info.state.isFinished) {
                    showResult("Agent done suiting up. Ready to go!")
                    launchTrackingService()
                }
            }

        workManager.beginWith(catStretchingRequest)
            .then(catFurGroomingRequest)
            .then(catLitterBoxSittingRequest)
            .then(catSuitUpRequest)
            .enqueue()
    }

    private fun showPermissionRationale(positiveAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Notifications permission")
            .setMessage("To show progress, we need the notifications permission")
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ -> positiveAction() }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun launchTrackingService() {
        RouteTrackingService.trackingCompletion.observe(this) { agentId ->
            showResult("Agent $agentId arrived!")
        }
        val serviceIntent = Intent(this, RouteTrackingService::class.java).apply {
            putExtra(EXTRA_SECRET_CAT_AGENT_ID, "007")
        }
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, LENGTH_SHORT).show()
    }

    private fun getCatAgentIdInputData(catAgentIdKey: String, catAgentIdValue: String) =
        Data.Builder().putString(catAgentIdKey, catAgentIdValue)
            .build()
}
