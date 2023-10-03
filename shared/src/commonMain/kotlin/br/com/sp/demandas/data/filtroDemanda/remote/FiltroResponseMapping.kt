package br.com.sp.demandas.data.filtroDemanda.remote

import br.com.sp.demandas.domain.filtroDemanda.model.Filtro

fun FiltroResponse.toDomain() = Filtro(
    value,
    text,
    false
)