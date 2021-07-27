package com.aaron.todolist_android

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TodoService :Service(){
    private lateinit var notificationManager: NotificationManager
    private var customerNotification: Notification? = null
    private var notificationChannel: NotificationChannel? = null
    private var notificationBuilder: Notification.Builder? = null
    private var pendingIntent: PendingIntent?= null
    private val CHANNEL_ONE_NAME = "Channel One"
    private val CHANNEL_ONE_ID = "com.aaron.todolist"


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var intent = Intent(this,MainActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        pendingIntent = PendingIntent.getActivity(this, 0,intent,0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(CHANNEL_ONE_ID,CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(notificationChannel!!)
            notificationBuilder = Notification.Builder(this,CHANNEL_ONE_ID).setChannelId(CHANNEL_ONE_ID)
        }else
            notificationBuilder = Notification.Builder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var num = intent?.extras?.get("num")
        var content = "您還有 $num 項事情還沒做"
        customerNotification = notificationBuilder!!
            .setTicker("Nature")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("剩餘待辦事項")
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, customerNotification)

        return super.onStartCommand(intent, flags, startId)
    }

}