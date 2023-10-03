package br.com.sp.demandas.domain.filtroDemanda.model

import br.com.sp.demandas.domain.demandas.Demanda

data class DemandaState(
    val lista: List<Demanda>? = emptyList(),
    val tipoAcesso: TipoAcesso? = TipoAcesso.TOTAL,
    val filtroInput: DemandaFiltroInput? = DemandaFiltroInput(),
    val filtroState: FiltroState? = FiltroState()
)


enum class TipoAcesso {
    PREFEITURA, REGIONAL, TOTAL,
}

data class FiltroState(
    val regionalFiltro: String? = "",
    val filtroHierarquiaRegional: List<Filtro> = emptyList(),
    val prefeituraFiltro: String? = "",
    val filtroPrefeituras: List<Filtro> = emptyList(),
    val etapaFiltro: String? = "",
    val comboEtapa: List<Filtro> = emptyList(),
    val statusFiltro: String? = "",
    val comboStatus: List<Filtro> = emptyList(),
    val convenioFiltro: String? = "",
    val numeroDemandaFiltro : String? = "",
    val comboConvenio: List<Filtro> = emptyList(),
    val filtroSelect : List<String> = emptyList()
)