package br.com.sp.demandas.domain.filtroDemanda

import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.demandas.IDemandaRepository
import br.com.sp.demandas.domain.filtroDemanda.model.DemandaState
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import br.com.sp.demandas.domain.filtroDemanda.model.FiltroState
import br.com.sp.demandas.domain.filtroDemanda.model.TipoAcesso
import kotlinx.coroutines.CoroutineDispatcher

class GetDemandasFiltroUseCase(
    private val demandaRepository: IDemandaRepository,
    private val filtroDemandaRepository: IFiltroDemandaRepository,

    dispatcher: CoroutineDispatcher
) : BaseUseCase<DemandaState, DemandaState>(dispatcher) {
    override suspend fun block(param: DemandaState): DemandaState {


        if (param.filtroInput?.regional != null && param.filtroInput.prefeitura == null) {


            val listPrefeituras =
                filtroDemandaRepository.getHierarquiaPrefeitura(param.filtroInput.regional.id.toInt())
                    .toMutableList()
            listPrefeituras.add(0, Filtro("", "", false))

            val list =
                demandaRepository.getDemandas(
                    idRegional = param.filtroInput.regional.id,
                    idPrefeitura = param.filtroInput.prefeitura?.id,
                    idAtividadeEtapa = param.filtroInput.idAtividadeEtapa?.id,
                    idAtividadeStatus = param.filtroInput.idAtividadeStatus?.id,
                    idConvenio = param.filtroInput.idConvenio?.id,
                    alarme = param.filtroInput.alarme,
                    alerta = param.filtroInput.alerta,
                    aviso = param.filtroInput.aviso
                )
            return DemandaState(
                list,
                param.tipoAcesso,
                param.filtroInput,
                filtroState = param.filtroState?.copy(
                    filtroPrefeituras = listPrefeituras.toMutableList(),
                    regionalFiltro = param.filtroInput.regional.texto,
                )
            )
        } else if (param.filtroInput?.regional != null && param.filtroInput.prefeitura != null) {

            val list =
                demandaRepository.getDemandas(
                    idRegional = param.filtroInput.regional.id,
                    idPrefeitura = param.filtroInput.prefeitura.id,
                    idAtividadeEtapa = param.filtroInput.idAtividadeEtapa?.id,
                    idAtividadeStatus = param.filtroInput.idAtividadeStatus?.id,
                    idConvenio = param.filtroInput.idConvenio?.id,
                    alarme = param.filtroInput.alarme,
                    alerta = param.filtroInput.alerta,
                    aviso = param.filtroInput.aviso,
                    numeroDemanda = param.filtroInput.numeroDemanda
                )
            return DemandaState(
                list,
                param.tipoAcesso,
                param.filtroInput,
                filtroState = param.filtroState
            )

        } else {
            val hierarquiaRegional = filtroDemandaRepository.getHierarquiaRegional()
            var listAtividadeStatus: List<Filtro>
            var listAtividadeEtapa: List<Filtro>
            var listConvenio: List<Filtro>

            listAtividadeStatus =
                filtroDemandaRepository.getAtividadeStatusFiltro().sortedBy { it.texto }
                    .toMutableList()
            listAtividadeStatus.add(0, Filtro("", "", false))

            listAtividadeEtapa =
                filtroDemandaRepository.getAtividadeEtapaFiltro().sortedBy { it.texto }
                    .toMutableList()
            listAtividadeEtapa.add(0, Filtro("", "", false))

            listConvenio =
                filtroDemandaRepository.getAtividadeConvenioFiltro().sortedBy { it.texto }
                    .toMutableList()
            listConvenio.add(0, Filtro("", "", false))

            if (hierarquiaRegional.isEmpty() || hierarquiaRegional.size == 1) {
                if (hierarquiaRegional.size == 1) {
                    val listPrefeituras =
                        filtroDemandaRepository.getHierarquiaPrefeitura(hierarquiaRegional.first().id.toInt())
                            .toMutableList()

                    listPrefeituras.add(0, Filtro("", "", false))


                    return DemandaState(
                        emptyList(),
                        TipoAcesso.REGIONAL,
                        param.filtroInput?.copy(
                            regional = Filtro(
                                hierarquiaRegional.first().id,
                                hierarquiaRegional.first().texto,
                                true
                            )
                        ),
                        FiltroState(
                            filtroHierarquiaRegional = hierarquiaRegional,
                            filtroPrefeituras = listPrefeituras,
                            comboConvenio = listConvenio,
                            comboEtapa = listAtividadeEtapa,
                            comboStatus = listAtividadeStatus,
                        ),

                        )

                } else {
                    val listPrefeituras =
                        filtroDemandaRepository.getHierarquiaPrefeitura(0)
                            .toMutableList()

                    val list =
                        demandaRepository.getDemandas(
                            idRegional = "0",
                            idPrefeitura = listPrefeituras.first().id,
                            idAtividadeEtapa = param.filtroInput?.idAtividadeEtapa?.id,
                            idAtividadeStatus = param.filtroInput?.idAtividadeStatus?.id,
                            idConvenio = param.filtroInput?.idConvenio?.id,
                            alarme = param.filtroInput?.alarme,
                            alerta = param.filtroInput?.alerta,
                            aviso = param.filtroInput?.aviso,
                            numeroDemanda = param.filtroInput?.numeroDemanda
                        )
                    return DemandaState(
                        list,
                        TipoAcesso.PREFEITURA,
                        param.filtroInput?.copy(
                            prefeitura = Filtro(
                                listPrefeituras.first().id,
                                listPrefeituras.first().texto,
                                true
                            )
                        ),
                        FiltroState(
                            prefeituraFiltro = listPrefeituras.first().texto,
                            filtroPrefeituras = listPrefeituras,
                            comboConvenio = listConvenio,
                            comboEtapa = listAtividadeEtapa,
                            comboStatus = listAtividadeStatus,
                        )
                    )
                }
            } else {

                val list = if (param.filtroInput?.numeroDemanda?.isNotEmpty() == true) {
                    demandaRepository.getDemandas(
                        numeroDemanda = param.filtroInput.numeroDemanda
                    )
                } else {
                    emptyList()
                }


                return DemandaState(
                    list,
                    TipoAcesso.TOTAL,
                    param.filtroInput,
                    FiltroState(
                        filtroHierarquiaRegional = hierarquiaRegional,
                        comboConvenio = listConvenio,
                        comboEtapa = listAtividadeEtapa,
                        comboStatus = listAtividadeStatus,
                    ),

                    )
            }
        }
    }
}