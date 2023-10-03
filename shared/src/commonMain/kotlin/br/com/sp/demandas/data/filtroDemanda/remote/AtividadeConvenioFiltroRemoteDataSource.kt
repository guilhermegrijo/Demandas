package br.com.sp.demandas.data.filtroDemanda.remote

import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ErrorResponse
import br.com.sp.demandas.core.safeRequest
import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class AtividadeConvenioFiltroRemoteDataSource(
    private val httpClient: HttpClient,
    private val userSettings: AuthCredentials
) {

    suspend fun getAtividadeConvenioFiltro(): ApiResponse<List<FiltroResponse>, ErrorResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1.0/Atividade/GetComboConvenio")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }
        }
}