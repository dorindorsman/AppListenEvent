package com.example.applistenevents.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.example.applistenevents.LogToastHelper
import kotlin.jvm.internal.Intrinsics


class CallReceiver : BroadcastReceiver() {
    private val MSG_PHONE_CALL_STARTED = "Phone Call Is Started"
    private val MSG_PHONE_CALL_ENDED = "Phone Call Is Ended"
    private val MSG_PHONE_CALL_INCOMING = "Incoming Call From: "
    private val TAG: String = javaClass.kotlin.simpleName.toString()
    private val logToastHelper = LogToastHelper()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            logToastHelper.showLogMsg(context, MSG_PHONE_CALL_STARTED, TAG)
        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            logToastHelper.showLogMsg(context, MSG_PHONE_CALL_ENDED, TAG)
        } else if (Intrinsics.areEqual(intent.getStringExtra(TelephonyManager.EXTRA_STATE), TelephonyManager.EXTRA_STATE_RINGING)) {
            logToastHelper.showLogMsg(context, MSG_PHONE_CALL_INCOMING + intent.getStringExtra("incoming_number"), TAG)
        }
    }

//    private fun getCallData(context: Context, intent: Intent) {
//        Intrinsics.checkNotNull(context)
//        val systemService: Any = context.getSystemService("phone")
//        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.telephony.TelephonyManager")
//        val telephony = systemService as TelephonyManager
//        telephony.listen(object : PhoneStateListener() {
//            // from class: com.example.applistenevents.Receivers.CallReceiver$getCallData$1
//            // android.telephony.PhoneStateListener
//            override fun onCallStateChanged(state: Int, incomingNumber: String) {
//                val logToastHelper: LogToastHelper
//                val str: String
//                val logToastHelper2: LogToastHelper
//                val str2: String
//                val str3: String
//                Intrinsics.checkNotNullParameter(incomingNumber, "incomingNumber")
//                super.onCallStateChanged(state, incomingNumber)
//                println("incomingNumber : $incomingNumber" as Any)
//                logToastHelper = this@CallReceiver.logToastHelper
//                val context2: Context = context
//                Intrinsics.checkNotNull(context2)
//                val sb = StringBuilder()
//                str = MSG_PHONE_CALL_INCOMING
//                logToastHelper.showToastMsg(context2, sb.append(str).append(incomingNumber).toString())
//                logToastHelper2 = this@CallReceiver.logToastHelper
//                val context3: Context = context
//                val sb2 = StringBuilder()
//                str2 = MSG_PHONE_CALL_INCOMING
//                val sb3 = sb2.append(str2).append(incomingNumber).toString()
//                str3 = tag
//                Intrinsics.checkNotNull(str3)
//                logToastHelper2.showLogMsg(context3, sb3, str3)
//            }
//        }, 32)
//        val extras = intent.extras
//        Intrinsics.checkNotNull(extras)
//        for (i in extras!!.keySet()) {
//            Log.d("dorin", i + " : " + extras[i])
//        }
//        Log.d("dorin", intent.getStringExtra("incoming_number").toString())
//    }
}