package br.com.sp.demandas.data.auth.remote

import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ErrorResponse
import br.com.sp.demandas.core.MensagemRetorno
import br.com.sp.demandas.core.safeRequest
import br.com.sp.demandas.domain.auth.model.LoginModel
import br.com.sp.demandas.domain.auth.model.RefreshTokenModel
import br.com.sp.demandas.domain.auth.model.TrocarSenhaModel
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class LoginRemoteSource(
    private val httpClient: HttpClient,
) {

    suspend fun doLogin(loginModel: LoginModel): ApiResponse<LoginResponse, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1.0/Auth/Token")
                setBody(loginModel)
                contentType(ContentType.Application.Json)
            }
        }

    suspend fun forgotPassword(usuario: String): ApiResponse<Unit, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1.0/Auth/EsqueciSenha/$usuario")
                contentType(ContentType.Application.Json)
            }
        }

    suspend fun checkCode(
        user: String,
        code: String
    ): ApiResponse<MensagemRetorno, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1.0/Auth/CheckEsqueciSenha/${user}/$code")
                contentType(ContentType.Application.Json)
            }
            /*headers {
                append(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }*/
        }

    suspend fun trocarSenha(trocarSenhaModel: TrocarSenhaModel): ApiResponse<Unit, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1.0/Auth/TrocaSenha")
                contentType(ContentType.Application.Json)
                setBody(trocarSenhaModel)
            }
            /*headers {
                append(HttpHeaders.Authorization, "Bearer ${userSettings.getToken()}")
            }*/
        }

    suspend fun refreshToken(refreshTokenModel: RefreshTokenModel): ApiResponse<LoginResponse, MensagemRetorno> =
        httpClient.safeRequest {
            url {
                method = HttpMethod.Post
                path("api/v1.0/Auth/RefreshToken")
                contentType(ContentType.Application.Json)
                setBody(refreshTokenModel)
            }
        }
}