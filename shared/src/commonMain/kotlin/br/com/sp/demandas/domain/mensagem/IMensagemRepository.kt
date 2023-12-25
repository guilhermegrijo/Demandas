package br.com.sp.demandas.domain.mensagem

interface IMensagemRepository {

    suspend fun buscarMensagem(): List<Mensagem>

    suspend fun buscarMensagemLocal(): List<Mensagem>

    suspend fun enviarToken(idUsuario: Long, token: String)
}