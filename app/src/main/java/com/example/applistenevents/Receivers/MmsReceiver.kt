package com.example.applistenevents.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.applistenevents.LogToastHelper


class MmsReceiver : BroadcastReceiver() {
    private val logToastHelper = LogToastHelper()
    private var phoneNumber: String? = null
    private val MMS_DATA_TYPE = "application/vnd.wap.mms-message"
    private val MSG_MMS_RECEIVED = "MMS Received From: "
    private val TAG = "MmsReceiver"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action.equals(Telephony.Sms.Intents.WAP_PUSH_RECEIVED_ACTION) && it.type.equals(MMS_DATA_TYPE)) {
                val bundle = it.extras
                if (bundle != null) {
                    val data = bundle.getByteArray("data")?.let { it1 -> String(it1) }
                    val char = data!!.substringAfter("+", data)
                    phoneNumber = "+" + char!!.subSequence(0, 12)
                    logToastHelper.showLogMsg(context!!, MSG_MMS_RECEIVED + "${phoneNumber}", TAG!!)
                }
            }
        }
    }
}