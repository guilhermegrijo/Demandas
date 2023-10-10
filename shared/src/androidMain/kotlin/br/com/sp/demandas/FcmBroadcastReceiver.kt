package br.com.sp.demandas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FCMOverlordBroadcastReceiver : BroadcastReceiver() {
    private val myhelper: MyHelper by lazy { MyHelper() }

    override fun onReceive(context: Context, intent: Intent) {
        myhelper.onReceive(intent)
    }
}

class MyHelper : KoinComponent {
    private val messageInterface: MessageInterface by inject()

    fun onReceive(intent: Intent) {
        val remoteMessage = RemoteMessage(intent.extras)
        messageInterface.exibirNotificacao(remoteMessage)
        CoroutineScope(Dispatchers.IO).launch {
            for (key in remoteMessage.data.keys) {
                println("$key : ${remoteMessage.data[key]}")
            }
        }
    }
}