package com.example.applistenevents.ContentProvider

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.Telephony
import com.example.applistenevents.LogToastHelper


class SMSContentObserver(context: Context, handler: Handler?) : ContentObserver(handler) {
    private val context: Context = context
    private val logToastHelper: LogToastHelper = LogToastHelper()
    private val TAG: String = javaClass.kotlin.simpleName.toString()

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        uri?.let {
            if (uri.pathSegments.isNotEmpty()) {
                newSms(uri)
            } else {
                deleteSms(uri)
            }
        }
    }


    private fun newSms(uri: Uri) {
        val cursor = context.getContentResolver().query(uri, null, null, null, null)
        val path: Int? = uri.pathSegments[0].toIntOrNull()
        if (path != null && cursor != null) {
            cursor.moveToFirst()
            if (cursor.getColumnCount() > 0) {
                val body: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val address: String = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                if (cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE)) == Telephony.Sms.Inbox.MESSAGE_TYPE_INBOX) {
                    logToastHelper.showLogMsg(context, "SMS From $address : $body", TAG + " Inbox")
                } else if (cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE)) == Telephony.Sms.Inbox.MESSAGE_TYPE_SENT) {
                    logToastHelper.showLogMsg(context, "SMS Sent To $address : $body", TAG + " Outbox")
                }
            }
            cursor.close()
        }
    }

    private fun deleteSms(uri: Uri) {


    }
}
