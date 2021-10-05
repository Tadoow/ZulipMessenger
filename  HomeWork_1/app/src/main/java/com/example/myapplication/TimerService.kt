package com.example.myapplication

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myapplication.SecondActivity

class TimerService : Service() {

    companion object {
        val TAG: String = TimerService::class.java.simpleName
        private const val TIME_COUNTDOWN = 1000 * 10L
        private const val TIMER_PERIOD = 1000L
        private const val CHANNEL_ID = "channel_id_2"
        private const val NOTIFICATION_ID = 1
        const val ACTION_CLOSE = "TIMER_SERVICE_ACTION_CLOSE"
    }

    private var mCountDownTimer: CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        Log.d(TAG, "onCreate() called")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(
            TAG,
            "onStartCommand() called with: intent = [$intent], flags = [$flags], startId = [$startId]"
        )

        if (ACTION_CLOSE == intent.action) {
            stopSelf()

            sendCustomBroadcast(" Service has been interrupted!")
        } else {
            stopCountDownTimer()
            startCountDownTimer(TIME_COUNTDOWN, TIMER_PERIOD)

            startForeground(NOTIFICATION_ID, createNotification(10))
        }

        return START_NOT_STICKY
    }

    private fun sendCustomBroadcast(data: String) {
        val closeServiceIntent = Intent(ACTION_CLOSE)
        closeServiceIntent.putExtra("service_result", data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(closeServiceIntent)
        Log.d(TAG, "sendBroadcast intent from service")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Service channel 1"
            val description = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(currentTime: Long): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

        val intent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val intentCloseService = Intent(this, TimerService::class.java)
        intentCloseService.action = ACTION_CLOSE
        val pendingIntentCloseService = PendingIntent.getService(this, 0, intentCloseService, 0)

        builder.setContentTitle("Best Timer Ever")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentText("Time: $currentTime")
            .setOnlyAlertOnce(true)
            .addAction(0, "Stop service", pendingIntentCloseService)
            .setContentIntent(pendingIntent)

        return builder.build()
    }

    private fun updateNotification(notification: Notification) {
        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy() called")

        stopCountDownTimer()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun startCountDownTimer(time: Long, period: Long) {
        mCountDownTimer = object : CountDownTimer(time, period) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(
                    TAG,
                    "onTick() called with: millisUntilFinished = [" + millisToSeconds(
                        millisUntilFinished
                    ) + "]"
                )

                updateNotification(createNotification(millisToSeconds(millisUntilFinished)))
            }

            override fun onFinish() {
                Log.d(TAG, "onFinish() called")

                stopSelf()
                sendCustomBroadcast(" Service has finished his job!")
            }
        }.start()
    }

    private fun stopCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
            mCountDownTimer = null
        }
    }

    private fun millisToSeconds(time: Long): Long {
        return time / 1000L
    }
}