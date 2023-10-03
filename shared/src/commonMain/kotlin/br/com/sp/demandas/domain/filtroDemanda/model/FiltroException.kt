package br.com.sp.demandas.domain.filtroDemanda.model

import br.com.sp.demandas.core.AppException

class SelecioneRegionalException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)