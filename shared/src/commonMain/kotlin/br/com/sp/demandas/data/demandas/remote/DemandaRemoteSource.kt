package br.com.sp.demandas.data.demandas.remote

import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ErrorResponse
import br.com.sp.demandas.core.safeRequest
import br.com.sp.demandas.domain.demandas.Etapa
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class DemandaRemoteSource(
    private val httpClient: HttpClient,
    private val userSettings: AuthCredentials
) {

    suspend fun getDemandas(
        idRegional: String?,
        idPrefeitura: String?,
        idAtividadeEtapa: String?,
        idConvenio: String?,
        idAtividadeStatus: String?,
        aviso: Boolean?,
        alerta: Boolean?,
        alarme: Boolean?,
        numeroDemanda: String?
    ): ApiResponse<List<DemandaResponse>, ErrorResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1.0/Atividade/GetAtividadeMobile")
                contentType(ContentType.Application.Json)
                if (idRegional != null)
                    parameters.append("idRegional", idRegional)
                if (idPrefeitura != null)
                    parameters.append("idPrefeitura", idPrefeitura)
                if (idAtividadeEtapa != null)
                    parameters.append("idAtividadeEtapa", idAtividadeEtapa)
                if (idConvenio != null) {
                    parameters.append("idConvenio", idConvenio)
                }
                if (idAtividadeStatus != null) {
                    parameters.append("idAtividadeStatus", idAtividadeStatus)
                }
                if (aviso != null) {
                    parameter("aviso", aviso)
                }
                if (alerta != null) {
                    parameter("alerta", aviso)
                }
                if (alarme != null) {
                    parameter("alarme", aviso)
                }
                if (numeroDemanda != null) {
                    parameter("numeroDemanda", numeroDemanda)
                }

                header(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }
        }

    suspend fun getDemandaDetalhe(idDemanda: String): ApiResponse<DemandaDetalheResponse, ErrorResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1/Atividade/GetAtividadeMobileDetalhe/${idDemanda}")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }
        }

    suspend fun getEtapaPlanejada(idDemanda: String): ApiResponse<List<EtapaPlanejadaResponse>, ErrorResponse> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Get
                path("api/v1.0/Atividade/etapaPlanejada/${idDemanda}")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }
        }

}
