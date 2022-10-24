package com.example.applistenevents

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.applistenevent.R
import com.example.applistenevents.ContentProvider.ContactContentObserver
import com.example.applistenevents.ContentProvider.SMSContentObserver
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private val WRITE_PERMISSION_REQUEST = 0
    private var contactContentObserver: ContactContentObserver? = null
    private var mHandler: MyHandler? = null
    private var smsContentObserver: SMSContentObserver? = null
    private val CALL_REQUEST_CODE = 369
    private val SMS_REQUEST_CODE = 111
    private val MMS_REQUEST_CODE = 105
    private val READ_PHONE_REQUEST_CODE = 111
    private val READ_PERMISSION_REQUEST = 1

    init {
        mHandler = MyHandler(this)
        smsContentObserver = SMSContentObserver(this, mHandler)
        contactContentObserver = ContactContentObserver(this, mHandler)
    }


    class MyHandler(activity: MainActivity?) : Handler() {
        private val softReference: WeakReference<MainActivity> = WeakReference(activity)


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.READ_PHONE_STATE) , CALL_REQUEST_CODE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.RECEIVE_SMS"), SMS_REQUEST_CODE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_MMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_MMS), MMS_REQUEST_CODE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_NUMBERS), READ_PHONE_REQUEST_CODE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.READ_CALL_LOG), READ_PHONE_REQUEST_CODE)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_CONTACTS), WRITE_PERMISSION_REQUEST)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), READ_PERMISSION_REQUEST)
        }
        val contentResolverInbox : ContentResolver = getContentResolver()
        smsContentObserver?.let { contentResolverInbox.registerContentObserver(Telephony.Sms.CONTENT_URI, true,it)}
        val contentResolverContact : ContentResolver  = getContentResolver()
        contactContentObserver?.let { contentResolverContact.registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, it)}
    }

}
