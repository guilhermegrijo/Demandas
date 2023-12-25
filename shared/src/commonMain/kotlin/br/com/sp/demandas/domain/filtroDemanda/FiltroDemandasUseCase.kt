package br.com.sp.demandas.domain.filtroDemanda

import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.demandas.IDemandaRepository
import br.com.sp.demandas.domain.filtroDemanda.model.DemandaState
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import br.com.sp.demandas.domain.filtroDemanda.model.FiltroState
import br.com.sp.demandas.domain.filtroDemanda.model.TipoAcesso
import kotlinx.coroutines.CoroutineDispatcher

class FiltroDemandasUseCase(
    private val demandaRepository: IDemandaRepository,
    private val filtroDemandaRepository: IFiltroDemandaRepository,

    dispatcher: CoroutineDispatcher
) : BaseUseCase<DemandaState, DemandaState>(dispatcher) {
    override suspend fun block(param: DemandaState): DemandaState {

        return when (param.tipoAcesso) {
            TipoAcesso.PREFEITURA -> {
                val list =
                    demandaRepository.getDemandas(
                        idRegional = "0",
                        idPrefeitura = param.filtroInput?.prefeitura?.id,
                        idAtividadeEtapa = param.filtroInput?.idAtividadeEtapa?.id,
                        idAtividadeStatus = param.filtroInput?.idAtividadeStatus?.id,
                        idConvenio = param.filtroInput?.idConvenio?.id,
                        alarme = param.filtroInput?.alarme,
                        alerta = param.filtroInput?.alerta,
                        aviso = param.filtroInput?.aviso,
                        noPrazo = param.filtroInput?.noPrazo,
                        numeroDemanda = param.filtroInput?.numeroDemanda
                    )
                return DemandaState(
                    list,
                    param.tipoAcesso,
                    param.filtroInput,
                    filtroState = param.filtroState
                )
            }

            TipoAcesso.REGIONAL -> {
                val list =
                    demandaRepository.getDemandas(
                        idRegional = param.filtroInput?.regional?.id,
                        idPrefeitura = param.filtroInput?.prefeitura?.id,
                        idAtividadeEtapa = param.filtroInput?.idAtividadeEtapa?.id,
                        idAtividadeStatus = param.filtroInput?.idAtividadeStatus?.id,
                        idConvenio = param.filtroInput?.idConvenio?.id,
                        alarme = param.filtroInput?.alarme,
                        alerta = param.filtroInput?.alerta,
                        aviso = param.filtroInput?.aviso,
                        noPrazo = param.filtroInput?.noPrazo,
                        numeroDemanda = param.filtroInput?.numeroDemanda
                    )
                return DemandaState(
                    list,
                    param.tipoAcesso,
                    param.filtroInput,
                    filtroState = param.filtroState
                )
            }

            TipoAcesso.TOTAL -> {

                val list = if (param.filtroInput?.numeroDemanda?.isNotEmpty() == true) {
                    demandaRepository.getDemandas(
                        numeroDemanda = param.filtroInput.numeroDemanda
                    )
                } else {
                    demandaRepository.getDemandas(
                        idRegional = param.filtroInput?.regional?.id,
                        idPrefeitura = param.filtroInput?.prefeitura?.id,
                        idAtividadeEtapa = param.filtroInput?.idAtividadeEtapa?.id,
                        idAtividadeStatus = param.filtroInput?.idAtividadeStatus?.id,
                        idConvenio = param.filtroInput?.idConvenio?.id,
                        alarme = param.filtroInput?.alarme,
                        alerta = param.filtroInput?.alerta,
                        aviso = param.filtroInput?.aviso,
                        noPrazo = param.filtroInput?.noPrazo,
                        numeroDemanda = param.filtroInput?.numeroDemanda
                    )
                }


                return DemandaState(
                    list,
                    param.tipoAcesso,
                    param.filtroInput,
                    filtroState = param.filtroState
                )
            }

            else -> {
                val list = demandaRepository.getDemandas(
                    idRegional = param.filtroInput?.regional?.id,
                    idPrefeitura = param.filtroInput?.prefeitura?.id,
                    idAtividadeEtapa = param.filtroInput?.idAtividadeEtapa?.id,
                    idAtividadeStatus = param.filtroInput?.idAtividadeStatus?.id,
                    idConvenio = param.filtroInput?.idConvenio?.id,
                    alarme = param.filtroInput?.alarme,
                    alerta = param.filtroInput?.alerta,
                    aviso = param.filtroInput?.aviso,
                    noPrazo = param.filtroInput?.noPrazo,
                    numeroDemanda = param.filtroInput?.numeroDemanda
                )



                return DemandaState(
                    list,
                    param.tipoAcesso,
                    param.filtroInput,
                    filtroState = param.filtroState
                )
            }
        }


    }
}