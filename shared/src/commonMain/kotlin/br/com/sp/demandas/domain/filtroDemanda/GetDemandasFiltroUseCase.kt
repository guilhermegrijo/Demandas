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
                param.filtroInput.regional.id.toIntOrNull()?.let {
                    filtroDemandaRepository.getHierarquiaPrefeitura(it)
                        .toMutableList()
                }
            listPrefeituras?.add(0, Filtro("", "", false))

            return DemandaState(
                emptyList(),
                param.tipoAcesso,
                param.filtroInput,
                filtroState = param.filtroState?.copy(
                    filtroPrefeituras = listPrefeituras?.toMutableList() ?: emptyList(),
                    regionalFiltro = param.filtroInput.regional.texto,
                )
            )
        } else {
            val hierarquiaRegional = filtroDemandaRepository.getHierarquiaRegional()
            var listAtividadeStatus: List<Filtro> = emptyList()
            var listAtividadeEtapa: List<Filtro> = emptyList()
            var listConvenio: List<Filtro> = emptyList()

            /*            listAtividadeStatus =
                            filtroDemandaRepository.getAtividadeStatusFiltro().sortedBy { it.texto }
                                .toMutableList()
                        listAtividadeStatus.add(0, Filtro("", "", false))*/

            listAtividadeEtapa =
                filtroDemandaRepository.getAtividadeEtapaFiltro().sortedBy { it.texto }
                    .toMutableList()
            listAtividadeEtapa.add(0, Filtro("", "", false))

            /*            listConvenio =
                            filtroDemandaRepository.getAtividadeConvenioFiltro().sortedBy { it.texto }
                                .toMutableList()
                        listConvenio.add(0, Filtro("", "", false))*/

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
                        param.filtroState?.copy(
                            filtroHierarquiaRegional = hierarquiaRegional,
                            filtroPrefeituras = listPrefeituras,
                            comboConvenio = listConvenio,
                            comboEtapa = listAtividadeEtapa,
                            comboStatus = listAtividadeStatus
                        )
                    )

                } else {
                    val listPrefeituras =
                        filtroDemandaRepository.getHierarquiaPrefeitura(0)
                            .toMutableList()


                    return DemandaState(
                        emptyList(),
                        TipoAcesso.PREFEITURA,
                        param.filtroInput?.copy(
                            prefeitura = Filtro(
                                listPrefeituras.first().id,
                                listPrefeituras.first().texto,
                                true
                            )
                        ),
                        param.filtroState?.copy(
                            prefeituraFiltro = listPrefeituras.first().texto,
                            filtroPrefeituras = listPrefeituras,
                            comboConvenio = listConvenio,
                            comboEtapa = listAtividadeEtapa,
                            comboStatus = listAtividadeStatus,
                        )
                    )
                }
            } else {


                return DemandaState(
                    emptyList(),
                    TipoAcesso.TOTAL,
                    param.filtroInput,
                    FiltroState(
                        filtroHierarquiaRegional = hierarquiaRegional,
                        comboConvenio = listConvenio,
                        comboEtapa = listAtividadeEtapa,
                        comboStatus = listAtividadeStatus,
                        numeroDemandaFiltro = param.filtroInput?.numeroDemanda
                    ),

                    )
            }
        }
    }
}