package com.example.shayari

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId= "notification_channel"
const val channelName ="com.example.shayari"

class MyFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification() != null){
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }

    fun getRemoteView(title: String,description:String):RemoteViews{
        val remoteViews=RemoteViews("com.example.shayari",R.layout.notification)
        remoteViews.setTextViewText(R.id.title,title)
        remoteViews.setTextViewText(R.id.description,description)
        remoteViews.setImageViewResource(R.id.app_logo,R.drawable.shayari_logo)
        return remoteViews
    }

fun generateNotification(title:String, description:String){
    val intent = Intent(this,MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    val pendingIntent=PendingIntent.getActivity(this,0,intent,
        FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

    var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext, channelId)
        .setSmallIcon(R.drawable.noti_logo)
        .setAutoCancel(true)
        .setVibrate(longArrayOf(1000,1000,1000,1000))
        .setOnlyAlertOnce(true)
        .setContentIntent(pendingIntent)

    builder=builder.setContent(getRemoteView(title,description))

    val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val notificationChannel=NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    notificationManager.notify(0,builder.build())

}

}