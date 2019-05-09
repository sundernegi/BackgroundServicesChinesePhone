package com.test.servicetest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        /* enqueue the job */
        AlarmJobIntentService.enqueueWork(context, intent)
    }

    companion object {

        val CUSTOM_INTENT = "com.test.intent.action.ALARM"

        val ctx = MyApplication.getAppContext()

        fun cancelAlarm() {
            val alarm = ctx.getSystemService(Context.ALARM_SERVICE)
                    as AlarmManager

            /* cancel any pending alarm */
            alarm.cancel(pendingIntent)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun setAlarm(force: Boolean) {
            cancelAlarm()
            val alarm = ctx.getSystemService(Context.ALARM_SERVICE)
                    as AlarmManager
            // EVERY N MINUTES
            val delay = (1000 * 60 * 1).toLong()
            var `when` = System.currentTimeMillis()
            if (!force) {
                `when` += delay
            }

            /* fire the broadcast */
            val SDK_INT = Build.VERSION.SDK_INT
            when {
                SDK_INT < Build.VERSION_CODES.KITKAT -> alarm.set(AlarmManager.RTC_WAKEUP, `when`, pendingIntent)
                SDK_INT < Build.VERSION_CODES.M -> alarm.setExact(AlarmManager.RTC_WAKEUP, `when`, pendingIntent)
                else -> alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, `when`, pendingIntent)
            }
        }

        private val pendingIntent: PendingIntent
            get() {
                val alarmIntent = Intent(ctx, MainActivity::class.java)
                alarmIntent.action = CUSTOM_INTENT

                return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            }
    }
}