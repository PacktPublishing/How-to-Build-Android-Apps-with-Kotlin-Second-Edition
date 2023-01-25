package com.example.catagenttracker.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.catagenttracker.MainActivity
import com.example.catagenttracker.R

class CatDispatchWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        setForegroundAsync(createForegroundInfo("Agent stretching"))
        val catAgentId = inputData.getString(INPUT_DATA_CAT_AGENT_ID)
        Thread.sleep(3000L)
        setForegroundAsync(createForegroundInfo("Agent grooming fur"))
        Thread.sleep(3000L)
        setForegroundAsync(createForegroundInfo("Agent sitting in litter box"))
        Thread.sleep(3000L)
        val outputData = Data.Builder()
            .putString(OUTPUT_DATA_CAT_AGENT_ID, catAgentId)
            .build()

        return Result.success(outputData)
    }

    private fun pendingIntent() =
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { FLAG_IMMUTABLE } else { 0 }
        )

    private fun createNotificationChannel(): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val newChannelId = "CatDispatch"
            val channelName = "Cat Dispatch Tracking"
            val channel = NotificationChannel(
                newChannelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
            )
            val service = requireNotNull(
                ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java
                )
            )
            service.createNotificationChannel(channel)
            newChannelId
        } else {
            ""
        }

    private fun notificationBuilder(title: String) =
        NotificationCompat.Builder(context, createNotificationChannel())
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent())

    private fun createForegroundInfo(title: String): ForegroundInfo {
        val builder = notificationBuilder(title)

        return ForegroundInfo(
            NOTIFICATION_ID,
            builder.build()
        )
    }

    companion object {
        const val INPUT_DATA_CAT_AGENT_ID = "inId"
        const val OUTPUT_DATA_CAT_AGENT_ID = "outId"
        const val NOTIFICATION_ID = 0xCA7
    }
}
