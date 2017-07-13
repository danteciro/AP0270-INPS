/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto.builder;

import gob.osinergmin.inpsweb.dto.DatoPlantillaDTO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaContactoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.TipificacionDTO;
import gob.osinergmin.mdicommon.domain.dto.TipoSancionDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;

import java.util.ArrayList;
import java.util.List;

public class DatoPlantillaBuilder {
    public static List<DatoPlantillaDTO> toListPlantillaResultadoSupervisionDto(List<Object[]> lista) {
        DatoPlantillaDTO plantillaResultadoDTO;
        List<DatoPlantillaDTO> retorno = new ArrayList<DatoPlantillaDTO>();
        if(lista!=null){
            for(Object[] maestro:lista){
                plantillaResultadoDTO = toPlantillaResultadoSupervisionDto(maestro);
                retorno.add(plantillaResultadoDTO);
            }
        }
        return retorno;
    }
    
    private static DatoPlantillaDTO toPlantillaResultadoSupervisionDto(Object[] maestro) {
        DatoPlantillaDTO retorno = new DatoPlantillaDTO();
        if(maestro!=null){
            UnidadOrganicaDTO unidadOrganica = new UnidadOrganicaDTO();
            if(maestro[5]!=null){				
                    unidadOrganica.setDescripcion(maestro[5].toString());				
            }
            retorno.setUnidadOrganica(unidadOrganica);

            SupervisionDTO supervision = new SupervisionDTO();
            if(maestro[12]!=null && maestro[13]!=null){	
            	String fechaInicial="";
            	String fechaFinal="";
        		String fechaInicio[] = maestro[12].toString().split("/");
        		String fechaFin[] = maestro[13].toString().split("/");
        		if(fechaInicio.length>0){
        			fechaInicial = fechaInicio[0]+" de "+ Utiles.obtenerMes(Integer.parseInt(fechaInicio[1]))+" de "+ fechaInicio[2];
        		}
        		if(fechaFin.length>0){
        			fechaFinal = fechaFin[0]+" de "+ Utiles.obtenerMes(Integer.parseInt(fechaFin[1]))+" de "+ fechaFin[2];
        		}
                supervision.setFechaInicio(fechaInicial);
                supervision.setFechaFin(fechaFinal);				
            }
            if(maestro[24]!=null){
                    supervision.setCartaVisita(maestro[24].toString());
            }
            retorno.setSupervision(supervision);

            ActividadDTO actividad = new ActividadDTO();
            if(maestro[6]!=null){				
                    actividad.setNombre(maestro[6].toString());				
            }
            retorno.setIdActividad(actividad);

            EmpresaSupDTO empresaSupervisada = new EmpresaSupDTO();
            if(maestro[7]!=null){
                    empresaSupervisada.setRazonSocial(maestro[7].toString());				
            }
            retorno.setEmpresaSupervisada(empresaSupervisada);

            DireccionUnidadSupervisadaDTO direccionUnidadSupervisada = new DireccionUnidadSupervisadaDTO();
            if(maestro[14]!=null){
                    direccionUnidadSupervisada.setDireccionCompleta(maestro[14].toString());				
            }
            retorno.setDirUnidadSupervisada(direccionUnidadSupervisada);

            if(maestro[15]!=null && maestro[16]!=null && maestro[17]!=null){
                    retorno.setDepartamento(maestro[15].toString());
                    retorno.setProvincia(maestro[16].toString());
                    retorno.setDistrito(maestro[17].toString());
            }

            OrdenServicioDTO ordenServicio = new OrdenServicioDTO();
            if(maestro[2]!=null){
                    ordenServicio.setNumeroOrdenServicio(maestro[2].toString());				
            }
            retorno.setOrdenServicio(ordenServicio);

            EmpresaContactoDTO empresaContacto = new EmpresaContactoDTO();
            if(maestro[18]!=null){
                    empresaContacto.setCargo(maestro[18].toString());
            }
            if(maestro[19]!=null){
                    empresaContacto.setNombreCompleto(maestro[19].toString());
            }
            retorno.setEmpresaContacto(empresaContacto);
            ExpedienteDTO expediente = new ExpedienteDTO();
            if(maestro[11]!=null){
                    expediente.setNumeroExpediente(maestro[11].toString());
            }
            retorno.setExpediente(expediente);
            UnidadSupervisadaDTO unidadSupervisada = new UnidadSupervisadaDTO();
            if(maestro[10]!=null){
                    unidadSupervisada.setCodigoOsinergmin(maestro[10].toString());
            }
            retorno.setUnidadSupervisada(unidadSupervisada);
            ProcesoDTO proceso = new ProcesoDTO();
            if(maestro[3]!=null){
                    proceso.setDescripcion(maestro[3].toString());
            }
            retorno.setIdProceso(proceso);
            SupervisoraEmpresaDTO supervisoraEmpresa = new SupervisoraEmpresaDTO();
            if(maestro[20]!=null){				
                    supervisoraEmpresa.setIdSupervisoraEmpresa(new Long(maestro[20].toString()));
            }
            if(maestro[21]!=null){				
                    supervisoraEmpresa.setRazonSocial(maestro[21].toString());
            }
            retorno.setSupervisoraEmpresa(supervisoraEmpresa);		
            LocadorDTO locador = new LocadorDTO();
            if(maestro[22]!=null){
                    locador.setIdLocador(new Long(maestro[22].toString()));
            }	
            if(maestro[23]!=null){
                    locador.setNombreCompleto(maestro[23].toString());
            }
            retorno.setLocador(locador);
            if(maestro[25]!=null){
            	retorno.setNroRegistroHidrocarburo(maestro[25].toString());
            }
            if(maestro[26]!=null){
            	retorno.setRucEmpresaSupervisada(maestro[26].toString());
            }
            if(maestro[27]!=null){
            	retorno.setCargoPersonaAtiende(maestro[27].toString());
            }
            if(maestro[28]!=null){
            	retorno.setNombrePersonaAtiende(maestro[28].toString());
            }
            if(maestro[29]!=null){
            	retorno.setMotivoNoSupervision(maestro[29].toString());
            }
        }
        return retorno;
    }
    
    public static List<DocumentoAdjuntoDTO> toListPlantillaResultadoSupervisionDocumentosDto(List<Object[]> lista) {
        List<DocumentoAdjuntoDTO> retorno = new ArrayList<DocumentoAdjuntoDTO>();
        if(lista!=null){
            for(Object[] maestro:lista){
                DocumentoAdjuntoDTO documento = new DocumentoAdjuntoDTO();
                if(maestro[0]!=null){
                    documento.setDescripcionDocumento(maestro[0].toString());
                }
                if(maestro[1]!=null){
                    documento.setNombreArchivo(maestro[1].toString());
                }
                if(maestro[2]!=null){
                    documento.setDescripcionConcatenadaDocumento(maestro[2].toString());
                }				
                retorno.add(documento);
            }
        }
        return retorno;
    }
    
    public static List<DetalleSupervisionDTO> toListPlantillaResultadoSupervisionHallazgosDto(List<Object[]> lista) {
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        if(lista!=null){
            for(Object[] maestro:lista){
                DetalleSupervisionDTO hallazgo = new DetalleSupervisionDTO();
                if(maestro[0]!=null){
                    hallazgo.setIdDetalleSupervision(new Long(maestro[0].toString()));
                }
                if(maestro[1]!=null){
                    hallazgo.setDescripcionResultado(maestro[1].toString());
                }		
                if(maestro[2]!=null){
                    hallazgo.setFlagResultado(maestro[2].toString());
                }
                if(maestro[3]!=null){
                    ObligacionDTO obligacion  = new ObligacionDTO();
                    obligacion.setIdObligacion(new Long(maestro[3].toString()));
                    obligacion.setDescripcion(maestro[4].toString());
                    hallazgo.setObligacion(obligacion);
                }
                if(maestro[5]!=null){
                    TipificacionDTO tipificacion = new TipificacionDTO();
                    tipificacion.setIdTipificacion(new Long(maestro[5].toString()));
                    tipificacion.setCodTipificacion(maestro[6].toString());
                    tipificacion.setDescripcion(maestro[7].toString());
                    if(maestro[8]!=null) { tipificacion.setSancionMonetaria(maestro[8].toString()); } else { tipificacion.setSancionMonetaria(Constantes.CONSTANTE_VACIA); } //mdiosesf - RSIS6
                    hallazgo.setTipificacion(tipificacion);
                }
                if(maestro[9]!=null){
                	DetalleSupervisionDTO detalleSuperAnt = new DetalleSupervisionDTO();
                	detalleSuperAnt.setIdDetalleSupervision(new Long(maestro[9].toString()));
                	if(maestro[10]!=null) { detalleSuperAnt.setDescripcionResultado(maestro[10].toString()); } else { detalleSuperAnt.setDescripcionResultado(Constantes.CONSTANTE_VACIA); } //mdiosesf - RSIS6
                	hallazgo.setDetalleSupervisionAnt(detalleSuperAnt);
                }
                retorno.add(hallazgo);
            }
        }
        return retorno;
    }
    
    public static List<DetalleSupervisionDTO> toListPlantillaResultadoSupervisionHallazgosBaseLegalDto(List<Object[]> lista) {
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        if(lista!=null){
            for(Object[] maestro:lista){
                DetalleSupervisionDTO hallazgo = new DetalleSupervisionDTO();
                if(maestro[0]!=null){
                    hallazgo.setIdDetalleSupervision(new Long(maestro[0].toString()));
                }
                if(maestro[1]!=null){
                    hallazgo.setFlagResultado(maestro[1].toString());
                }
                if(maestro[2]!=null){
                    hallazgo.setIdBaseLegal(new Long(maestro[2].toString()));
                }		
                if(maestro[3]!=null){
                    hallazgo.setDescripcionBaseLegal(maestro[3].toString());
                }
                retorno.add(hallazgo);
            }
        }
        return retorno;
    }
    
    public static List<TipoSancionDTO> toListPlantillaResultadoTipoSancionDto(List<Object[]> lista) {
        List<TipoSancionDTO> retorno = null;
        if(lista!=null){
            retorno = new ArrayList<TipoSancionDTO>();
            for(Object[] maestro:lista){
                TipoSancionDTO sancion = new TipoSancionDTO();
                if(maestro[0]!=null && maestro[1]!=null){
                    sancion.setIdTipoSancion(new Long(maestro[0].toString()));
                    sancion.setAbreviatura(maestro[1].toString());
                }
                retorno.add(sancion);
            }
        }
        return retorno;
    }
    
    public static List<DatoPlantillaDTO> toListPlantillaResultadoFechasVisitaDto(List<Object[]> lista) {
        List<DatoPlantillaDTO> retorno = null;
        if(lista!=null){
                retorno = new ArrayList<DatoPlantillaDTO>();
                for(Object[] maestro:lista){
                        DatoPlantillaDTO plantilla = new DatoPlantillaDTO();
                        if(maestro[0]!=null){
                                plantilla.setFechasVisitas(maestro[0].toString());
                        }
                        if(maestro[1]!=null){
                                plantilla.setFechaSupervisionActual(maestro[1].toString());
                        }
                        if(maestro[2]!=null){
                                plantilla.setFechaSupervisionAnterior(maestro[2].toString());
                        }
                        if(maestro[3]!=null){
                        	plantilla.setCartasVisitas(maestro[3].toString());
                        }
                        if(maestro[4]!=null){
                        	plantilla.setCartaVisitaActual(maestro[4].toString());
                        }
                        if(maestro[5]!=null){
                        	plantilla.setCartaVisitaAnterior(maestro[5].toString());
                        }
                        retorno.add(plantilla);
                }
        }
        return retorno;
    }
}
