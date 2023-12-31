package br.com.sp.demandas.data.mensagem.remote

import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ErrorResponse
import br.com.sp.demandas.core.MensagemRetorno
import br.com.sp.demandas.core.safeRequest
import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.data.filtroDemanda.remote.FiltroResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class MensagemRemoteDataSource(
    private val httpClient: HttpClient,
    private val userSettings: AuthCredentials,
) {

    suspend fun getMensagens(): ApiResponse<List<MensagemResponse>, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1.0/Alerta/AlertaMensagem/GetMensagemMobile")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }
        }

    suspend fun enviarToken(tokenRequest: TokenRequest) : ApiResponse<Unit, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1.0/Alerta/firebase/SetFirebaseToken")
                contentType(ContentType.Application.Json)
                setBody(tokenRequest)
                header(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }
        }

}