/**
* Resumen		
* Objeto		: SupervisionBuilder.java
* Descripción		: Constructor de Supervision
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  22/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
* OSINE_SFS-791  |  29/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar Ejecucion Medida Supervision.
*    
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghDocumentoAdjunto;
import gob.osinergmin.inpsweb.domain.PghMotivoNoSupervision;
import gob.osinergmin.inpsweb.domain.PghOrdenServicio;
import gob.osinergmin.inpsweb.domain.PghResultadoSupervision;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteTareaDTO;
import gob.osinergmin.mdicommon.domain.dto.FlujoSigedDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.MotivoNoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;

import java.util.ArrayList;
import java.util.List;

public class SupervisionBuilder {

    public static List<SupervisionDTO> toListSupervisionDTO(List<PghSupervision> lista) {
        SupervisionDTO registroDTO;
        List<SupervisionDTO> retorno = new ArrayList<SupervisionDTO>();
        if (lista != null) {
            for (PghSupervision maestro : lista) {
                registroDTO = toSupervisionDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static SupervisionDTO toSupervisionDTO(PghSupervision registro) {
        SupervisionDTO registroDTO = new SupervisionDTO();
        if (registro != null) {
            registroDTO.setIdSupervision(registro.getIdSupervision());
            if (registro.getIdOrdenServicio() != null && registro.getIdOrdenServicio().getIdOrdenServicio() != null) {
                OrdenServicioDTO ordenServicioDTO = new OrdenServicioDTO();
                ordenServicioDTO.setIdOrdenServicio(registro.getIdOrdenServicio().getIdOrdenServicio());
                ordenServicioDTO.setNumeroOrdenServicio(registro.getIdOrdenServicio().getNumeroOrdenServicio());
                ordenServicioDTO.setFechaHoraAnalogicaCreacionOS(Utiles.DateToString(registro.getIdOrdenServicio().getFechaCreacion(), Constantes.FORMATO_FECHA_LARGE));
                ordenServicioDTO.setIdTipoAsignacion(registro.getIdOrdenServicio().getIdTipoAsignacion());
                if (registro.getIdOrdenServicio().getIdEstadoProceso() != null ) {
                    if(registro.getIdOrdenServicio().getIdEstadoProceso().getIdEstadoProceso() != null){                        
                       EstadoProcesoDTO estadoProcesoDTO = new EstadoProcesoDTO();
                       estadoProcesoDTO.setIdEstadoProceso(registro.getIdOrdenServicio().getIdEstadoProceso().getIdEstadoProceso());
                       estadoProcesoDTO.setIdentificadorEstado(registro.getIdOrdenServicio().getIdEstadoProceso().getIdentificadorEstado());
                       ordenServicioDTO.setEstadoProceso(estadoProcesoDTO);
                    }
                }
                
                if (registro.getIdOrdenServicio().getIdLocador() != null && registro.getIdOrdenServicio().getIdLocador().getIdLocador() != null) {
                    LocadorDTO locador = new LocadorDTO();
                    locador.setIdLocador(registro.getIdOrdenServicio().getIdLocador().getIdLocador());
                    ordenServicioDTO.setLocador(locador);
                }
                if (registro.getIdOrdenServicio().getIdSupervisoraEmpresa() != null) {
                    if (registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getIdSupervisoraEmpresa() != null) {
                        SupervisoraEmpresaDTO supervisoraEmpresa = new SupervisoraEmpresaDTO();
                        supervisoraEmpresa.setIdSupervisoraEmpresa(registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getIdSupervisoraEmpresa());
                        if (registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getRazonSocial()!= null) {
                            supervisoraEmpresa.setRazonSocial(registro.getIdOrdenServicio().getIdSupervisoraEmpresa().getRazonSocial());
                        }
                        ordenServicioDTO.setSupervisoraEmpresa(supervisoraEmpresa);
                    }
                }
                if (registro.getIdOrdenServicio().getIdLocador()!= null) {
                    if (registro.getIdOrdenServicio().getIdLocador().getIdLocador() != null) {
                        LocadorDTO locadorDTO = new LocadorDTO();
                        locadorDTO.setIdLocador(registro.getIdOrdenServicio().getIdLocador().getIdLocador());
                        if (registro.getIdOrdenServicio().getIdLocador().getNombreCompleto() != null) {
                            locadorDTO.setNombreCompleto(registro.getIdOrdenServicio().getIdLocador().getNombreCompleto());
                        }
                        ordenServicioDTO.setLocador(locadorDTO);
                    }
                }
                /* OSINE791 - RSIS17 - Inicio */
                if (registro.getIdOrdenServicio().getIdExpediente() != null && registro.getIdOrdenServicio().getIdExpediente().getIdExpediente() != null) {
                    ExpedienteDTO expediente = new ExpedienteDTO();
                    expediente.setNumeroExpediente(registro.getIdOrdenServicio().getIdExpediente().getNumeroExpediente());
                    expediente.setIdExpediente(registro.getIdOrdenServicio().getIdExpediente().getIdExpediente());
                    if (registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdUnidadSupervisada() != null) {
                        UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();
                        unidadSupervisadaDTO.setIdUnidadSupervisada(registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdUnidadSupervisada());
                        unidadSupervisadaDTO.setCodigoOsinergmin(registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getCodigoOsinergmin());
                        unidadSupervisadaDTO.setNombreUnidad(registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getNombreUnidad());
                        unidadSupervisadaDTO.setNroRegistroHidrocarburo(registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getNumeroRegistroHidrocarburo());
                        if (registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdActividad() != null) {
                            if (registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdActividad().getIdActividad() != null) {
                                ActividadDTO actividadDTO = new ActividadDTO();
                                actividadDTO.setIdActividad(registro.getIdOrdenServicio().getIdExpediente().getIdUnidadSupervisada().getIdActividad().getIdActividad());
                                unidadSupervisadaDTO.setActividad(actividadDTO);
                            }
                        }                        
                        expediente.setUnidadSupervisada(unidadSupervisadaDTO);
                    }
                    /* OSINE791 – RSIS19 - Inicio */
                    if(registro.getIdOrdenServicio().getIdExpediente().getIdFlujoSiged() != null){
                        if(registro.getIdOrdenServicio().getIdExpediente().getIdFlujoSiged().getIdFlujoSiged() != null){
                            FlujoSigedDTO flujo = new FlujoSigedDTO();
                            flujo.setIdFlujoSiged(registro.getIdOrdenServicio().getIdExpediente().getIdFlujoSiged().getIdFlujoSiged());
                            flujo.setNombreFlujoSiged(registro.getIdOrdenServicio().getIdExpediente().getIdFlujoSiged().getNombreFlujoSiged());
                            expediente.setFlujoSiged(flujo);
                        }
                    }
                    if(registro.getIdOrdenServicio() != null){
                        if(registro.getIdOrdenServicio().getIdOrdenServicio() != null){
                            OrdenServicioDTO orden = new OrdenServicioDTO();
                            orden.setIdOrdenServicio(registro.getIdOrdenServicio().getIdOrdenServicio());
                            orden.setNumeroOrdenServicio(registro.getIdOrdenServicio().getNumeroOrdenServicio());
                            expediente.setOrdenServicio(orden);
                        }
                    }
                    /* OSINE791 – RSIS19 - Fin */
                    if (registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea() != null) {
                        ExpedienteTareaDTO expTarea = new ExpedienteTareaDTO();
                        expTarea.setIdExpediente(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getIdExpediente());
                        expTarea.setFlagCorreoOficio(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagCorreoOficio());
                        expTarea.setFlagEstadoReghMsfh(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEstadoReghMsfh());
                        expTarea.setFlagEstadoReghInps(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEstadoReghInps());
            			expTarea.setFlagEnviarConstanciaSiged(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEnviarConstanciaSiged());
            			expTarea.setFlagEstadoSiged(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagEstadoSiged());
            			expTarea.setFlagCorreoEstadoRegh(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagCorreoEstadoRegh());
            			expTarea.setFlagRegistraDocsInps(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagRegistraDocsInps());
            			expTarea.setFlagCorreoScop(registro.getIdOrdenServicio().getIdExpediente().getPghExpedienteTarea().getFlagCorreoScop());
                        expediente.setExpedienteTareaDTO(expTarea);
                    }
                    if(registro.getIdOrdenServicio().getIdExpediente().getIdEstadoLevantamiento()!=null && registro.getIdOrdenServicio().getIdExpediente().getIdEstadoLevantamiento().getIdMaestroColumna()!=null){
                    	expediente.setEstadoLevantamiento(new MaestroColumnaDTO(registro.getIdOrdenServicio().getIdExpediente().getIdEstadoLevantamiento().getIdMaestroColumna(), 
                    			registro.getIdOrdenServicio().getIdExpediente().getIdEstadoLevantamiento().getDescripcion(), registro.getIdOrdenServicio().getIdExpediente().getIdEstadoLevantamiento().getCodigo()));
                    }
                    if (registro.getIdOrdenServicio().getIdExpediente().getIdPersonal() != null && registro.getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonal() != null) {
                        PersonalDTO personal = new PersonalDTO();
                        personal.setIdPersonal(registro.getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonal());
                        if(registro.getIdOrdenServicio().getIdExpediente().getIdPersonal().getNombreUsuarioSiged()!=null){
            				personal.setNombreUsuarioSiged(registro.getIdOrdenServicio().getIdExpediente().getIdPersonal().getNombreUsuarioSiged());
            			}
            			if(registro.getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonalSiged()!=null){
            				personal.setIdPersonalSiged(registro.getIdOrdenServicio().getIdExpediente().getIdPersonal().getIdPersonalSiged());
            			}
                        expediente.setPersonal(personal);
                    }
                    ordenServicioDTO.setExpediente(expediente);
                }
                /* OSINE791 - RSIS17 - Fin */
                ordenServicioDTO.setIteracion(registro.getIdOrdenServicio().getIteracion());
                ordenServicioDTO.setIdTipoAsignacion(registro.getIdOrdenServicio().getIdTipoAsignacion());
                registroDTO.setOrdenServicioDTO(ordenServicioDTO);
            }
            registroDTO.setFechaInicio(Utiles.DateToString(registro.getFechaInicio(), Constantes.FORMATO_FECHA_CORTA));
            registroDTO.setHoraInicio(Utiles.DateToString(registro.getFechaInicio(), Constantes.FORMATO_HORA_CORTA));
            registroDTO.setFechaFin(Utiles.DateToString(registro.getFechaFin(), Constantes.FORMATO_FECHA_CORTA));
            registroDTO.setHoraFin(Utiles.DateToString(registro.getFechaFin(), Constantes.FORMATO_HORA_CORTA));
            registroDTO.setFechaInicioPorVerificar(Utiles.DateToString(registro.getFechaInicioporVerificar(), Constantes.FORMATO_FECHA_CORTA));
            registroDTO.setHoraInicioPorVerificar(Utiles.DateToString(registro.getFechaInicioporVerificar(), Constantes.FORMATO_HORA_CORTA));
            registroDTO.setActaProbatoria(registro.getActaProbatoria());
            registroDTO.setCartaVisita(registro.getCartaVisita());
            registroDTO.setFlagSupervision(registro.getFlagSupervision());
            registroDTO.setObservacion(registro.getObservacion());
            registroDTO.setFlagIdentificaPersona(registro.getFlagIdentificaPersona());
            registroDTO.setObservacionIdentificaPers(registro.getObservacionIdentificaPers());
            if (registro.getIdMotivoNoSupervision() != null && registro.getIdMotivoNoSupervision().getIdMotivoNoSupervision() != null) {
                registroDTO.setMotivoNoSupervision(new MotivoNoSupervisionDTO(registro.getIdMotivoNoSupervision().getIdMotivoNoSupervision()));
            }
            registroDTO.setDescripcionMtvoNoSuprvsn(registro.getDescripcionMtvoNoSuprvsn());
            if (registro.getIdSupervisionAnt() != null) {
                SupervisionDTO supervisionAnt = new SupervisionDTO();
                supervisionAnt.setIdSupervision(registro.getIdSupervisionAnt());
                registroDTO.setSupervisionAnterior(supervisionAnt);
            }
            /* OSINE_SFS-791 - RSIS 15 - Inicio */
            registroDTO.setOtrosIncumplimientos(registro.getOtrosIncumplimientos());
            /* OSINE_SFS-791 - RSIS 15 - Fin */
            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            registroDTO.setFlagEjecucionMedida(registro.getFlagEjecucionMedida());
            /* OSINE_SFS-791 - RSIS 16 - Fin */
            /* OSINE_SFS-791 - RSIS 19 - Inicio */
            if (registro.getIdResultadoSupervision() != null && registro.getIdResultadoSupervision().getIdResultadoSupervision() != null) {
                ResultadoSupervisionDTO resultadoDTO = new ResultadoSupervisionDTO();
                resultadoDTO.setIdResultadosupervision(registro.getIdResultadoSupervision().getIdResultadoSupervision());
                if (registro.getIdResultadoSupervision().getCodigo() != null) {
                    resultadoDTO.setCodigo(registro.getIdResultadoSupervision().getCodigo());
                }
                registroDTO.setResultadoSupervisionDTO(resultadoDTO);
            }
            /* OSINE_SFS-791 - RSIS 19 - Fin */
            
            if (registro.getIdResultSupervInicial()!= null && registro.getIdResultSupervInicial().getIdResultadoSupervision() != null) {
                ResultadoSupervisionDTO resultadoInicialDTO = new ResultadoSupervisionDTO();
                resultadoInicialDTO.setIdResultadosupervision(registro.getIdResultSupervInicial().getIdResultadoSupervision());
                if (registro.getIdResultSupervInicial().getCodigo() != null) {
                    resultadoInicialDTO.setCodigo(registro.getIdResultSupervInicial().getCodigo());
                }
                registroDTO.setResultadoSupervisionInicialDTO(resultadoInicialDTO);
            }
             /* OSINE_SFS-791 - RSIS 16 - Inicio */
            registroDTO.setFlagCumplimientoPrevio(registro.getFlagCumplimientoPrevio());
            /* OSINE_SFS-791 - RSIS 16 - Fin */

        }
        return registroDTO;
    }

    public static PghSupervision toSupervisionDomain(SupervisionDTO registro) {
        PghSupervision registroDomain = new PghSupervision();
        if (registro.getIdSupervision() != null) {
            registroDomain.setIdSupervision(registro.getIdSupervision());
        }
        if (registro.getOrdenServicioDTO() != null && registro.getOrdenServicioDTO().getIdOrdenServicio() != null) {
            registroDomain.setIdOrdenServicio(new PghOrdenServicio(registro.getOrdenServicioDTO().getIdOrdenServicio()));
        }
        registroDomain.setFechaInicio(Utiles.stringToDate(registro.getFechaInicio(), Constantes.FORMATO_FECHA_LARGE));
        registroDomain.setFechaFin(Utiles.stringToDate(registro.getFechaFin(), Constantes.FORMATO_FECHA_LARGE));
        registroDomain.setFechaInicioporVerificar(Utiles.stringToDate(registro.getFechaInicioPorVerificar(), Constantes.FORMATO_FECHA_LARGE));
        registroDomain.setActaProbatoria(registro.getActaProbatoria());
        registroDomain.setCartaVisita(registro.getCartaVisita());
        registroDomain.setFlagSupervision(registro.getFlagSupervision());
        registroDomain.setObservacion(registro.getObservacion());
        if (registro.getMotivoNoSupervision() != null && registro.getMotivoNoSupervision().getIdMotivoNoSupervision() != null) {
            registroDomain.setIdMotivoNoSupervision(new PghMotivoNoSupervision(registro.getMotivoNoSupervision().getIdMotivoNoSupervision()));
        }
        registroDomain.setDescripcionMtvoNoSuprvsn(registro.getDescripcionMtvoNoSuprvsn());
        registroDomain.setFlagIdentificaPersona(registro.getFlagIdentificaPersona());
        registroDomain.setObservacionIdentificaPers(registro.getObservacionIdentificaPers());
        registroDomain.setEstado(registro.getEstado());
        if (registro.getSupervisionAnterior() != null && registro.getSupervisionAnterior().getIdSupervision() != null) {
            registroDomain.setIdSupervisionAnt(registro.getSupervisionAnterior().getIdSupervision());
        }
        if (registro.getResultadoSupervisionInicialDTO() != null) {
            PghResultadoSupervision resulInicialSup = new PghResultadoSupervision();
            if (registro.getResultadoSupervisionInicialDTO().getIdResultadosupervision() != null) {
                resulInicialSup.setIdResultadoSupervision(registro.getResultadoSupervisionInicialDTO().getIdResultadosupervision());
            }
            if (registro.getResultadoSupervisionInicialDTO().getCodigo() != null) {
                resulInicialSup.setCodigo(registro.getResultadoSupervisionInicialDTO().getCodigo());
            }
            registroDomain.setIdResultSupervInicial(resulInicialSup);
        }

        return registroDomain;
    }
    /* OSINE791 - RSIS17 - Inicio */

    public static DocumentoAdjuntoDTO toDocumentoAdjuntoDTO(PghDocumentoAdjunto registro) {
        DocumentoAdjuntoDTO registroDTO = new DocumentoAdjuntoDTO();
        if (registro != null) {
            registroDTO.setSupervision(new SupervisionDTO(registro.getIdSupervision().getIdSupervision()));
            registroDTO.setIdTipoDocumento(new MaestroColumnaDTO(registro.getIdTipoDocumento().getIdMaestroColumna()));
        }
        return registroDTO;
    }
    /* OSINE791 - RSIS17 - Fin */
}
