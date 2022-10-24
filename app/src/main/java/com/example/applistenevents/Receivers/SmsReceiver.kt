package com.example.applistenevents.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import androidx.annotation.RequiresApi
import com.example.applistenevents.LogToastHelper
import kotlin.jvm.internal.Intrinsics


class SmsReceiver : BroadcastReceiver() {
    private var messageBody: String? = null
    private var phoneNumber: String? = null
    private val MSG_SMS_RECEIVED = "SMS Received From "
    private val TAG: String = javaClass.kotlin.simpleName.toString()
    private val logToastHelper = LogToastHelper()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (intent.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                val smsMessage: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                messageBody = smsMessage[0].messageBody
                phoneNumber = smsMessage[0].originatingAddress
                logToastHelper.showLogMsg(context, "$MSG_SMS_RECEIVED$phoneNumber : $messageBody", TAG)
            }
        }
    }

//    private fun getSmsData(context: Context, intent: Intent) {
//        val extras = intent.extras
//        if (extras != null) {
//            val obj = extras["pdus"]
//            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.Any>")
//            val sms = obj as Array<Any>?
//            for (obj2 in sms!!) {
//                val format = extras.getString("format")
//                val smsMessage: SmsMessage = SmsMessage.createFromPdu(obj2 as ByteArray, format)
//                phoneNumber = java.lang.String.valueOf(smsMessage.getOriginatingAddress())
//                messageText = smsMessage.getMessageBody().toString()
//                val append = StringBuilder().append("From: ")
//                var str = phoneNumber
//                var str2: String? = null
//                if (str == null) {
//                    Intrinsics.throwUninitializedPropertyAccessException("phoneNumber")
//                    str = null
//                }
//                val append2 = append.append(str).append(" Message: ")
//                val str3 = messageText
//                if (str3 == null) {
//                    Intrinsics.throwUninitializedPropertyAccessException("messageText")
//                } else {
//                    str2 = str3
//                }
//                msgSmsData = append2.append(str2).toString()
//            }
//        }
//        try {
//            val smsMessage2: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
//            val messageBody: String = smsMessage2[0].getMessageBody()
//            val smsMessage3: SmsMessage = smsMessage2[0]
//            Log.d(" dorin RESULT=", smsMessage2[0].getOriginatingAddress().toString() + " : " + messageBody)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        val resultcode = resultCode
//        Log.d(" dorin RESULT=", Integer.toString(resultcode))
//    }
}