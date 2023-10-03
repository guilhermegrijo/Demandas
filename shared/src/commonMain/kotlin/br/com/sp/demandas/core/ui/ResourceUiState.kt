package br.com.sp.demandas.core.ui

sealed interface ResourceUiState<out T> {
    data class Success<T>(val data: T) : ResourceUiState<T>
    data class Error(val throwable: Throwable?) : ResourceUiState<Nothing>
    object Loading : ResourceUiState<Nothing>
    data class Empty(val message : String?) : ResourceUiState<Nothing>
    object Idle : ResourceUiState<Nothing>
}