package br.com.sp.demandas.domain.mensagem

interface IMensagemRepository {

    suspend fun buscarMensagem() : List<Mensagem>
}