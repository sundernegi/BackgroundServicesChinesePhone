package com.test.servicetest

import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import android.util.Log

class AlarmJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        /* your code here */
        /* reset the alarm */
        Log.e("test", "t");
        AlarmReceiver.setAlarm(true)
        stopSelf()
    }

    companion object {

        /* Give the Job a Unique Id */
        private val JOB_ID = 1000

        fun enqueueWork(ctx: Context, intent: Intent) {
            JobIntentService.enqueueWork(ctx, AlarmReceiver::class.java, JOB_ID, intent)
        }
    }
}
