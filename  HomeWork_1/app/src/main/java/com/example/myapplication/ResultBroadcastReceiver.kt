package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.lang.ref.WeakReference

class ResultBroadcastReceiver(listener: MyListener) : BroadcastReceiver() {

    companion object {
        val TAG: String = ResultBroadcastReceiver::class.java.simpleName
    }

    private val mListener: WeakReference<MyListener> = WeakReference(listener)

    override fun onReceive(context: Context, intent: Intent) {
        val serviceResult = intent.getStringExtra("service_result")
        Log.d(TAG, "onReceive: broadcast receiver got data from service: $serviceResult")

        val listener = mListener.get()
        listener!!.sendData(serviceResult)
    }

}