/**
* Resumen		
* Objeto			: RegistroHidrocarburoServiceNegImpl.java
* Descripción		: NegImpl DTO RegistroHidrocarburoServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     13/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
*/ 
package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.dto.RegistroHidrocarburoExternoDTO;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.RegistroHidrocarburoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.RegistroHidrocarburoDAO;
import gob.osinergmin.inpsweb.service.dao.RegistroSuspenHabiliDAO;
import gob.osinergmin.inpsweb.service.exception.RegistroHidrocarburoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.RegistroSuspenHabiliDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.RegistroHidrocarburoFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("RegistroHidrocarburoServiceNeg")
public class RegistroHidrocarburoServiceNegImpl implements RegistroHidrocarburoServiceNeg {

    private static Logger LOG = LoggerFactory.getLogger(RegistroHidrocarburoServiceNegImpl.class);
    @Inject
    private RegistroHidrocarburoDAO registroHidrocarburoDAO;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private RegistroSuspenHabiliDAO registroSuspenHabiliDAO;

    @Override
    @Transactional(readOnly=true)
    public List<RegistroHidrocarburoDTO> getListRegistroHidrocarburo(RegistroHidrocarburoFilter filtro) throws RegistroHidrocarburoException {
        LOG.info("getRegistroHidrocarburo");
        List<RegistroHidrocarburoDTO> retorno = new ArrayList<RegistroHidrocarburoDTO>();
        try {
            retorno = registroHidrocarburoDAO.getListRegistroHidrocarburo(filtro);
        } catch (Exception ex) {
            LOG.error("Error en getRegistroHidrocarburo", ex);
            throw new RegistroHidrocarburoException(ex.getMessage(),ex);
        }
        return retorno;
    }

    @Override
    @Transactional(rollbackFor=RegistroHidrocarburoException.class)
    public RegistroHidrocarburoDTO SuspenderRegistroHidrocarburoExterno(UnidadSupervisadaDTO unidadSupervisadaDTO,String nombreUsuario,UsuarioDTO usuarioDTO)  throws RegistroHidrocarburoException{
      LOG.info("SuspenderRegistroHidrocarburoExterno");
        Long retorno1 = null;
        RegistroHidrocarburoExternoDTO registroHidrocarburoExternoDTO = null;
        RegistroHidrocarburoDTO registroHidrocarburoDTO = null;
        RegistroHidrocarburoDTO registroHidrocarburofinalDTO = null;
        try {
            retorno1 = registroHidrocarburoDAO.veridUnidadOperativaBDExterna(unidadSupervisadaDTO);
            if(retorno1!= null){
                LOG.info("el ID De la Unidad Supervisada en la BD Externa es "+retorno1);
                registroHidrocarburoExternoDTO = registroHidrocarburoDAO.veridActividadBDExterna(unidadSupervisadaDTO,retorno1);
                if(registroHidrocarburoExternoDTO != null && registroHidrocarburoExternoDTO.getIdActividadExterno() != null && registroHidrocarburoExternoDTO.getIdUnidadSupervisadaExterno() != null && registroHidrocarburoExternoDTO.getIdUnidadActividadExterna()!= null){
                    LOG.info("el ID De la UnidadActividad en la BD Externa es "+registroHidrocarburoExternoDTO.getIdUnidadActividadExterna()+"| y unidad |"+registroHidrocarburoExternoDTO.getIdUnidadSupervisadaExterno()+"| y la actividad es : |"+registroHidrocarburoExternoDTO.getIdActividadExterno()+"|");
                    registroHidrocarburoExternoDTO.setNombreUsuario(nombreUsuario);
                    registroHidrocarburoExternoDTO.setEstadoRegistroHidrocarburo(Constantes.CODIGO_ESTADO_SUSPENDER_REGISTRO_HIDROCARBURO);
                    Date fecha = new Date();
                    registroHidrocarburoExternoDTO.setFechaSuspension(fecha);
//                    int resultado = 0;
                    int resultado = registroHidrocarburoDAO.actualizarRegistroHidrocarburoExterno(registroHidrocarburoExternoDTO);
                    LOG.info("retorno de update |"+resultado+"|");
                    //if(resultado == 0){
                    if(resultado > 0){
                        LOG.info("se Actualizo el RH en SFH");
                        List<RegistroHidrocarburoDTO> ltaauxDTO = registroHidrocarburoDAO.getListRegistroHidrocarburo(new RegistroHidrocarburoFilter(unidadSupervisadaDTO.getIdUnidadSupervisada()));
                        RegistroHidrocarburoDTO registroHidroDTO = new RegistroHidrocarburoDTO();
                        System.out.println("vino cant lita |"+ltaauxDTO.size()+"|");
                        if(ltaauxDTO.size() == Constantes.LISTA_UNICO_VALIR){
                            registroHidroDTO = ltaauxDTO.get(Constantes.PRIMERO_EN_LISTA);
                        }
                        registroHidrocarburoDTO = new RegistroHidrocarburoDTO();
                        LOG.info("enviando id del RH |"+registroHidroDTO.getIdRegistroHidrocarburo()+"|");
                        registroHidrocarburoDTO.setIdRegistroHidrocarburo(registroHidroDTO.getIdRegistroHidrocarburo());
                        registroHidrocarburoDTO.setNumeroRegistroHidrocarburo(unidadSupervisadaDTO.getCodigoOsinergmin());
                        registroHidrocarburoDTO.setFechaInicioSuspencion(fecha);
                        registroHidrocarburoDTO.setIdUnidadSupervisada(unidadSupervisadaDTO.getIdUnidadSupervisada());
                        List<MaestroColumnaDTO> ltamaestroEstadoProcesoSuspenderDTO = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_RHO,Constantes.APLICACION_SIGUO,Constantes.CODIGO_ESTADO_SUSPENDER_REGISTRO_HIDROCARBURO);
                        MaestroColumnaDTO maestroEstadoProcesoSuspenderDTO = new MaestroColumnaDTO();
                        if(ltamaestroEstadoProcesoSuspenderDTO.size() ==  Constantes.LISTA_UNICO_VALIR){
                            maestroEstadoProcesoSuspenderDTO = ltamaestroEstadoProcesoSuspenderDTO.get(Constantes.PRIMERO_EN_LISTA);
                        }
                        registroHidrocarburoDTO.setEstadoProceso(maestroEstadoProcesoSuspenderDTO);
                        registroHidrocarburoDTO = registroHidrocarburoDAO.ActualizarRegistroHidrocarburo(registroHidrocarburoDTO, usuarioDTO);
                        if(registroHidrocarburoDTO != null){
                            LOG.info("se Actualizo el RH en INPS");
                            RegistroSuspenHabiliDTO registroSuspenHabiliDTO = new RegistroSuspenHabiliDTO();
                            registroSuspenHabiliDTO.setIdRegistroHidrocarburo(registroHidrocarburoDTO);
                            registroSuspenHabiliDTO.setFechaInicioSuspension(fecha);
                            registroSuspenHabiliDTO.setProceso(Constantes.DESCRIPCION_REG_SUS_HAB_OPERATIVO);
                            RegistroSuspenHabiliDTO registroSuspenHabiliAuxDTO = new RegistroSuspenHabiliDTO();
                            //registroSuspenHabiliAuxDTO = registroSuspenHabiliDAO.RegistrarSuspensionRegistroHidrocarburo(registroSuspenHabiliDTO, usuarioDTO);
                            registroSuspenHabiliAuxDTO = registroSuspenHabiliDTO;
                            //return registroSuspenHabiliAuxDTO
                            
                            if(registroSuspenHabiliAuxDTO != null){
                                LOG.info("se Registro la Suspension de RH");
                                registroHidrocarburofinalDTO = registroHidrocarburoDTO;
                            }else{
                                LOG.error("Ocurrio un Error al Registrar la Suspension de RH");
                                registroHidrocarburofinalDTO = null;
                                throw new RegistroHidrocarburoException("Ocurrio un Error al Registrar la Suspension de RH",null);
                            }
                            
                        }else{
                            LOG.error("Ocurrio un Error al Actualizar el RH en INPS");
                            throw new RegistroHidrocarburoException("Ocurrio un Error al Actualizar el RH en INPS",null);
                        }
                    }else{
                        LOG.error("Ocurrio un error al Actualizar en la BD Externa");   
                        throw new RegistroHidrocarburoException("Ocurrio un error al Actualizar en la BD Externa",null);
                    }
                }else{
                    LOG.error("No se Encontro a la Actividad en la BD Externa");
                    throw new RegistroHidrocarburoException("No se Encontro a la Actividad en la BD Externa",null);
                }
            }else{
                LOG.error("No se Encontro a la Unidad Supervisada en la BD Externa");
                throw new RegistroHidrocarburoException("No se Encontro a la Unidad Supervisada en la BD Externa",null);
            }
        } catch (Exception ex) {
            LOG.error("Error en SuspenderRegistroHidrocarburoExterno", ex);
            throw new RegistroHidrocarburoException(ex.getMessage(),ex);
        }
        return registroHidrocarburofinalDTO;
    }

    @Override
    public RegistroHidrocarburoDTO ActualizarRegistroHidrocarburo(RegistroHidrocarburoDTO registroHidrocarburoDTO,UsuarioDTO usuarioDTO) throws RegistroHidrocarburoException {
      LOG.info("ActualizarRegistroHidrocarburo");
        RegistroHidrocarburoDTO retorno=new RegistroHidrocarburoDTO();
        try {
            retorno=registroHidrocarburoDAO.ActualizarRegistroHidrocarburo(registroHidrocarburoDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en ActualizarRegistroHidrocarburo", ex);
            throw new RegistroHidrocarburoException(ex.getMessage(), ex);
        }
        return retorno;
    }

    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    @Override
    @Transactional(rollbackFor=RegistroHidrocarburoException.class)
    public RegistroHidrocarburoDTO actualizarEstadoRHExterno(UnidadSupervisadaDTO unidadSupervisadaDTO, UsuarioDTO usuarioDTO, String estadoRH)  throws RegistroHidrocarburoException{
    	//Metodo que Actualiza el RH Externo y en INP generico para Habilitar, Suspender y Cancelar. >> estadoRH
    	LOG.info("procesando actualizarestadoRHExterno");
        Long retorno = null;
        RegistroHidrocarburoExternoDTO registroHidrocarburoExternoDTO = null;
        RegistroHidrocarburoDTO registroHidrocarburoDTO = null;
        RegistroHidrocarburoDTO registroHidrocarburofinalDTO = null;
        try {
        	retorno = registroHidrocarburoDAO.veridUnidadOperativaBDExterna(unidadSupervisadaDTO);
            if(retorno!= null){
                LOG.info("el ID De la Unidad Supervisada en la BD Externa es "+retorno);
                registroHidrocarburoExternoDTO = registroHidrocarburoDAO.veridActividadBDExterna(unidadSupervisadaDTO, retorno);
                if(registroHidrocarburoExternoDTO != null && registroHidrocarburoExternoDTO.getIdActividadExterno() != null && registroHidrocarburoExternoDTO.getIdUnidadSupervisadaExterno() != null && registroHidrocarburoExternoDTO.getIdUnidadActividadExterna()!= null){
                    LOG.info("el ID De la UnidadActividad en la BD Externa es "+registroHidrocarburoExternoDTO.getIdUnidadActividadExterna()+"| y unidad |"+registroHidrocarburoExternoDTO.getIdUnidadSupervisadaExterno()+"| y la actividad es : |"+registroHidrocarburoExternoDTO.getIdActividadExterno()+"|");
                    registroHidrocarburoExternoDTO.setNombreUsuario(usuarioDTO.getCodigo());
                    registroHidrocarburoExternoDTO.setEstadoRegistroHidrocarburo(estadoRH);
                    registroHidrocarburoExternoDTO.setFechaSuspension(new Date());
                    int resultado = registroHidrocarburoDAO.actualizarRegistroHidrocarburoExterno(registroHidrocarburoExternoDTO);
                    LOG.info("retorno de update |"+resultado+"|");
                    if(resultado > 0){
                        LOG.info("se Actualizo el RH en SFH");
                        List<RegistroHidrocarburoDTO> ltaauxDTO = registroHidrocarburoDAO.getListRegistroHidrocarburo(new RegistroHidrocarburoFilter(unidadSupervisadaDTO.getIdUnidadSupervisada()));
                        if(ltaauxDTO!=null && ltaauxDTO.size() == Constantes.LISTA_UNICO_VALIR){
                        	RegistroHidrocarburoDTO registroHidroDTO = ltaauxDTO.get(Constantes.PRIMERO_EN_LISTA);                        
	                        registroHidrocarburoDTO = new RegistroHidrocarburoDTO();
	                        LOG.info("enviando id del RH |"+registroHidroDTO.getIdRegistroHidrocarburo()+"|");
	                        registroHidrocarburoDTO.setIdRegistroHidrocarburo(registroHidroDTO.getIdRegistroHidrocarburo());
	                        registroHidrocarburoDTO.setNumeroRegistroHidrocarburo(unidadSupervisadaDTO.getCodigoOsinergmin());
	                        
	                        if(!estadoRH.equals(Constantes.CODIGO_ESTADO_CANCELAR_REGISTRO_HIDROCARBURO)){
	                        	if(estadoRH.equals(Constantes.CODIGO_ESTADO_SUSPENDER_REGISTRO_HIDROCARBURO)){
	                        		registroHidrocarburoDTO.setFechaInicioSuspencion(new Date());
	                        	} else if(estadoRH.equals(Constantes.CODIGO_ESTADO_HABILITAR_REGISTRO_HIDROCARBURO)){
	                        		registroHidrocarburoDTO.setFechaFinSuspencion(new Date());
	                        	}
	                        }
	                        
	                        registroHidrocarburoDTO.setIdUnidadSupervisada(unidadSupervisadaDTO.getIdUnidadSupervisada());
	                        List<MaestroColumnaDTO> ltamaestroEstadoProcesoSuspenderDTO = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_RHO,Constantes.APLICACION_SIGUO,estadoRH);
	                        if(ltamaestroEstadoProcesoSuspenderDTO.size() ==  Constantes.LISTA_UNICO_VALIR){
	                        	MaestroColumnaDTO maestroEstadoProcesoSuspenderDTO = new MaestroColumnaDTO();
	                            maestroEstadoProcesoSuspenderDTO = ltamaestroEstadoProcesoSuspenderDTO.get(Constantes.PRIMERO_EN_LISTA);
	                            registroHidrocarburoDTO.setEstadoProceso(maestroEstadoProcesoSuspenderDTO);
	                        }
	                        
	                        registroHidrocarburoDTO = registroHidrocarburoDAO.ActualizarRegistroHidrocarburo(registroHidrocarburoDTO, usuarioDTO);	                        
	                        if(registroHidrocarburoDTO == null){	                            
	                            LOG.error("Ocurrio un Error al Actualizar el RH en INPS");
	                            throw new RegistroHidrocarburoException("Ocurrio un Error al Actualizar el RH en INPS", null);
	                        } else {
                                    LOG.info("ACTUALIZACION DEL RH COMPLETADO");
	                        	registroHidrocarburofinalDTO = registroHidrocarburoDTO;
	                        }
                        }else{
                        	LOG.error("No se Encontro a la Registro Hidrocarburo Asociado en INPS");
                            throw new RegistroHidrocarburoException("No se Encontro a la Registro Hidrocarburo Asociado en INPS", null);
                        }
                    }else{
                       LOG.error("Ocurrio un error al Actualizar en la BD Externa");   
                        throw new RegistroHidrocarburoException("Ocurrio un error al Actualizar en la BD Externa", null);
                    }
                }else{
                    LOG.error("No se Encontro a la Actividad en la BD Externa");
                    throw new RegistroHidrocarburoException("No se Encontro a la Actividad en la BD Externa", null);
                }
            }else{
                LOG.error("No se Encontro a la Unidad Supervisada en la BD Externa");
                throw new RegistroHidrocarburoException("No se Encontro a la Unidad Supervisada en la BD Externa", null);
            }
        } catch (Exception ex) {
            LOG.error("Error en actualizarestadoRHExterno", ex);
            throw new RegistroHidrocarburoException("Error en actualizarestadoRHExterno", ex);
        }
        return registroHidrocarburofinalDTO;
    }
    /* OSINE_SFS-791 - RSIS 47 - Fin */ 
}
