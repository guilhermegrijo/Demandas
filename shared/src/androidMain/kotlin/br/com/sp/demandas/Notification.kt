package br.com.sp.demandas

import com.google.firebase.messaging.RemoteMessage

interface MessageInterface {

    fun exibirNotificacao(remoteMessage: RemoteMessage)

}