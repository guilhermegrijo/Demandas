package br.com.sp.demandas.android

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.sp.demandas.MessageInterface
import com.google.firebase.messaging.RemoteMessage


class NotificationService constructor(private val context: Context) : MessageInterface {


    override fun exibirNotificacao(remoteMessage: RemoteMessage) {
        val notificationManager = NotificationManagerCompat.from(context)

        with(notificationManager) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(353, createNotification(remoteMessage))
        }
    }

    private fun createNotification(remoteMessage: RemoteMessage): Notification {
        val notificationChannelId = "MESSAGE_CHANEL"


        val intent: Intent = Intent(
            context,
            MainActivity::class.java
        )
//you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        //you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//Add Any key-value to pass extras to intent
        //Add Any key-value to pass extras to intent
        intent.putExtra("pushnotification", "yes")
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                notificationChannelId,
                "message notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Sync channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(false)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            notificationChannelId
        )
        val icon = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.icon_notification
        )

        return builder
            .setContentTitle(remoteMessage.data["titulo"] ?: "")
            .setContentText(remoteMessage.data["descricao"] ?: "")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.drawable.icon_notification)
            .setLargeIcon(icon)
            .setStyle(NotificationCompat.BigTextStyle())
            .setAutoCancel(true)
            .setTicker("Notificação")
            .build()
    }
}