package com.example.catagenttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.catagenttracker.RouteTrackingService.Companion.EXTRA_SECRET_CAT_AGENT_ID
import com.example.catagenttracker.worker.CatDispatchWorker

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catDispatchingRequest = enqueueCatDispatch("CatAgent1")

        workManager.getWorkInfoByIdLiveData(catDispatchingRequest.id)
            .observe(this) { info ->
                if (info.state.isFinished) {
                    showResult("Agent ${info.outputData.getString(CatDispatchWorker.OUTPUT_DATA_CAT_AGENT_ID)} done suiting up. Ready to go!")
                }
            }
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

    private fun enqueueCatDispatch(catAgentId: String): OneTimeWorkRequest {
        val catDispatchingRequest =
            OneTimeWorkRequest.Builder(CatDispatchWorker::class.java)
                .setInputData(
                    Data.Builder().putString(CatDispatchWorker.INPUT_DATA_CAT_AGENT_ID, catAgentId)
                        .build()
                ).build()

        workManager.enqueue(catDispatchingRequest)
        return catDispatchingRequest
    }
}
