package com.example.applistenevents.ContentProvider

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.provider.ContactsContract
import androidx.core.database.getStringOrNull
import com.example.applistenevents.LogToastHelper


class ContactContentObserver(context: Context, handler: Handler?) : ContentObserver(handler) {
    private val context: Context = context
    private val logToastHelper: LogToastHelper = LogToastHelper()

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        val cur: Cursor? = context.getContentResolver()
            .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + " Desc")
        cur?.let{
            cur.moveToFirst()
            val id : String? = cur.getStringOrNull((cur.getColumnIndex(ContactsContract.Contacts._ID)))
            val name: String? = cur.getStringOrNull(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            if (cur.getInt(cur.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                val phoneCursor: Cursor? = context.getContentResolver()
                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "contact_last_updated_timestamp DESC")
                phoneCursor?.let {
                    phoneCursor.moveToFirst()
                    val number: String = phoneCursor.getStringOrNull(phoneCursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1)) ?: "NO Number"
                    logToastHelper.showLogMsg(context, "New Contact: name: $name number: $number", "ContactContentObserver")
                    phoneCursor.close()
                }
            }
            cur.close()
        }
    }



}
