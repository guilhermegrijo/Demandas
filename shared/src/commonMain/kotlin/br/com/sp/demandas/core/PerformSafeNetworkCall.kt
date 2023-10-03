package br.com.sp.demandas.core

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> =
    try {
        val response = request { block() }
        when (response.status.value) {
            in 200..300 -> {
                ApiResponse.Success(response.body())
            }

            404 -> {
                ApiResponse.Error.HttpError(
                    code = response.status.value,
                    errorBody = response.body(),
                    errorMessage = "Status Code: ${response.status.value}",
                )
            }

            403 -> {
                ApiResponse.Error.HttpError(
                    code = response.status.value,
                    errorBody = response.body(),
                    errorMessage = "Status Code: ${response.status.value}",
                )
            }

            500 -> {
                ApiResponse.Error.HttpError(
                    code = response.status.value,
                    errorBody = response.body(),
                    errorMessage = "Status Code: ${response.status.value}",
                )
            }

            else -> {
                ApiResponse.Error.GenericError(
                    response.status.value.toString(),
                    response.body<String?>().toString()
                )
            }
        }


    } catch (exception: ClientRequestException) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = "Status Code: ${exception.response.status.value}",
        )
    } catch (exception: HttpExceptions) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = exception.message,
        )
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError(e.message, "Alguma coisa deu errado")
    } catch (e: Exception) {
        ApiResponse.Error.GenericError(e.message, e.message)
    }

sealed class ApiResponse<out T, out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {
        /**
         * Represents server errors.
         * @param code HTTP Status code
         * @param errorBody Response body
         * @param errorMessage Custom error message
         */
        data class HttpError<E>(
            val code: Int,
            val errorBody: String?,
            val errorMessage: String?,
        ) : Error<E>()

        /**
         * Represent SerializationExceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class SerializationError(
            val message: String?,
            val errorMessage: String?,
        ) : Error<Nothing>()

        /**
         * Represent other exceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class GenericError(
            val message: String?,
            val errorMessage: String?,
        ) : Error<Nothing>()
    }
}

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String,
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}