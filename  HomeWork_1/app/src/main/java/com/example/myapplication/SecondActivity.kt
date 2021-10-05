package com.example.myapplication

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SecondActivity : AppCompatActivity(), View.OnClickListener, MyListener {

    companion object {
        val TAG: String = SecondActivity::class.java.simpleName
    }

    private var mResultBroadcastReceiver: ResultBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<View>(R.id.start_service_button).setOnClickListener(this)
        findViewById<View>(R.id.stop_service_button).setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter(TimerService.ACTION_CLOSE)
        mResultBroadcastReceiver = ResultBroadcastReceiver(this)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mResultBroadcastReceiver!!, intentFilter)
    }

    override fun onStop() {
        super.onStop()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mResultBroadcastReceiver!!)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start_service_button -> {
                Log.d(TAG, "start_service clicked")

                startService()
            }
            R.id.stop_service_button -> {
                Log.d(TAG, "stop_service clicked")

                stopService()
            }
        }
    }

    private fun startService() {
        val intent = Intent(this, TimerService::class.java)
        startService(intent)
    }

    private fun stopService() {
        val intent = Intent(this, TimerService::class.java)
        stopService(intent)
    }

    override fun sendData(serviceResult: String?) {
        val firstActivityIntent = Intent()
        firstActivityIntent.putExtra("service_result", serviceResult)
        setResult(RESULT_OK, firstActivityIntent)
        Log.d(TAG, "finishing second activity")
        finish()
    }
}