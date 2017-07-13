/**
*
* Resumen		
* Objeto		: DetalleSupervisionServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de métodos declarados en la clase interfaz en DetalleSupervisionServiceNeg
* Fecha de Creación	: 01/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  29/08/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Detalle Obligaciones Incumplidas.
* OSINE_SFS-791  |  07/09/2016   |   Luis García Reyna          |     Registro Comentario Escenario Incumplido
* OSINE_SFS-791  |  07/10/2016   |   Mario Dioses Fernandez     |     Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*                |               |                              |
* 
*/
package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.DetalleSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.business.EscenarioIncumplidoServiceNeg;
import gob.osinergmin.inpsweb.service.business.InfraccionServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ResultadoSupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.dao.DetalleSupervisionDAO;
import gob.osinergmin.inpsweb.service.dao.ResultadoSupervisionDAO;
import gob.osinergmin.inpsweb.service.exception.DetalleSupervisionException;
import gob.osinergmin.inpsweb.service.exception.InfraccionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.EscenarioIncumplidoDTO;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.EscenarioIncumplidoFilter;
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import gob.osinergmin.mdicommon.domain.ui.ResultadoSupervisionFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service("DetalleSupervisionServiceNeg")
public class DetalleSupervisionServiceNegImpl implements DetalleSupervisionServiceNeg {
    private static Logger LOG = LoggerFactory.getLogger(DetalleEvaluacionServiceNegImpl.class);
    
    @Inject
    private DetalleSupervisionDAO detalleSupervisionDAO;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private InfraccionServiceNeg infraccionServiceNeg;
    @Inject
    private ResultadoSupervisionDAO resultadoSupervisionDAO;
    @Inject
    private EscenarioIncumplidoServiceNeg escenarioIncumplidoServiceNeg;
    @Inject
    private ResultadoSupervisionServiceNeg resultadoSupervisionServiceNeg;
    
    @Override
    @Transactional(readOnly = true)
    public List<DetalleSupervisionDTO> listarDetalleSupervision(DetalleSupervisionFilter filtro) throws DetalleSupervisionException {
        LOG.info("listarDetalleSupervision");
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        try{        
            retorno=detalleSupervisionDAO.find(filtro);
        }catch(Exception ex){
            LOG.error("Error en listarDetalleSupervision",ex);
            throw new DetalleSupervisionException(ex.getMessage(),ex);
        }
        return retorno;
    }
    
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    @Override
    @Transactional(readOnly = true)
    public DetalleSupervisionDTO getDetalleSupervision(DetalleSupervisionFilter filtro) {
        LOG.info("getDetalleSupervision");
        DetalleSupervisionDTO retorno=null;        
        try{
            List<DetalleSupervisionDTO> lst=detalleSupervisionDAO.find(filtro);
            if(!CollectionUtils.isEmpty(lst)){
                retorno=lst.get(0);
            }
        }catch(Exception e){
            LOG.error("Error en getEscenarioIncumplimiento",e);
        }
        return retorno;
    }
    
    @Override
    @Transactional(rollbackFor = DetalleSupervisionException.class)
    public DetalleSupervisionDTO registroComentarioDetSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException {
        LOG.info("registroComentarioDetSupervision");
        DetalleSupervisionDTO retorno=new DetalleSupervisionDTO();
        try {
            retorno=detalleSupervisionDAO.registroComentarioDetSupervision(detalleSupervisionDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en registroComentarioDetSupervision", ex);
            throw new DetalleSupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 16 - Fin */

    @Override
    @Transactional(rollbackFor = DetalleSupervisionException.class)
    public DetalleSupervisionDTO actualizarComentarioDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException{
        LOG.info("actualizarComentarioDetalleSupervision");
        DetalleSupervisionDTO retorno=new DetalleSupervisionDTO();
        try {
            retorno=detalleSupervisionDAO.registroComentarioDetSupervision(detalleSupervisionDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en actualizarComentarioDetalleSupervision", ex);
            throw new DetalleSupervisionException(ex.getMessage(), ex);
        }
        return retorno;
    }

    @Override
    public long cantidadDetalleSupervision(DetalleSupervisionFilter filtro) throws DetalleSupervisionException {
      LOG.info("cantidadDetalleSupervision");
        Long retorno = new Long(0);
        try{        
            retorno=detalleSupervisionDAO.cantidadDetalleSupervision(filtro);
           
        }catch(Exception ex){
            LOG.error("Error en cantidadDetalleSupervision",ex);
        }
        return retorno;
    }

    @Override
    @Transactional
    public Boolean VerCierreTotalSupervision(DetalleSupervisionFilter filter, UsuarioDTO usuarioDTO,Long idUnidadSupervisada) throws DetalleSupervisionException {
        LOG.info("VerCierreTotalSupervision");
        boolean validar = false;
        try {
            //ID_MAESTRO_COLU MEDIDA_SEGURIDAD CIERRE_TOTAL CODIGO=GG2
            Long idMaesColCierreTotal;
            List<MaestroColumnaDTO> ltacierreTotalDTO = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_MEDIDA_SEGURIDAD, Constantes.APLICACION_MYC, Constantes.CODIGO_CIERRE_TOTAL);
            if(!CollectionUtils.isEmpty(ltacierreTotalDTO)){
                idMaesColCierreTotal=ltacierreTotalDTO.get(Constantes.PRIMERO_EN_LISTA).getIdMaestroColumna();
            }else{
                throw new DetalleSupervisionException("No se encontro MaestroColumna de Cierre Total",null);
            }
            //ID_RESULTADOSUPERVISION PARA INCUMPLE
            ResultadoSupervisionFilter filtro = new ResultadoSupervisionFilter();
            filtro.setCodigo(Constantes.CODIGO_RESULTADO_INCUMPLE);
            ResultadoSupervisionDTO resultadoSupervisionDTO = new ResultadoSupervisionDTO();
            resultadoSupervisionDTO = resultadoSupervisionDAO.getResultadoSupervision(filtro);
            Long idResultSupIncumple=resultadoSupervisionDTO.getIdResultadosupervision();

            List<DetalleSupervisionDTO> lstDetSup = detalleSupervisionDAO.find(filter);
            for (DetalleSupervisionDTO detalleDTO : lstDetSup) {
                detalleDTO = ActualizarDetalleSupervisionbyInfraccion(detalleDTO, usuarioDTO, idUnidadSupervisada);
                if (detalleDTO.getResultadoSupervision().getIdResultadosupervision().equals(idResultSupIncumple)) {
                    if (detalleDTO.getIdMedidaSeguridad().equals(idMaesColCierreTotal)) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en listarDetalleSupervision", ex);
            throw new DetalleSupervisionException(ex.getMessage(),ex);
        }
        return validar;
    }
    
    public DetalleSupervisionDTO ActualizarDetalleSupervisionbyInfraccion(DetalleSupervisionDTO detalleDTO, UsuarioDTO usuarioDTO,Long idUnidadSupervisada) throws InfraccionException {
        ResultadoSupervisionFilter filtro = new ResultadoSupervisionFilter();
        filtro.setCodigo(Constantes.CODIGO_RESULTADO_INCUMPLE);
        ResultadoSupervisionDTO resultadoSupervisionDTO = new ResultadoSupervisionDTO();
        resultadoSupervisionDTO = resultadoSupervisionDAO.getResultadoSupervision(filtro);
        Long idResultSupIncumple=resultadoSupervisionDTO.getIdResultadosupervision();
                
        //if (detalleDTO.getResultadoSupervision().getIdResultadosupervision().equals(resultadoSupervisionDTO.getIdResultadosupervision())) {
        if (detalleDTO.getResultadoSupervision().getIdResultadosupervision().equals(idResultSupIncumple)) {
            InfraccionDTO infraccionDTO = infraccionServiceNeg.ComprobarMedidaSeguridadInfraccion(new InfraccionFilter(detalleDTO.getObligacion().getIdObligacion(),idUnidadSupervisada));
            if (!detalleDTO.getIdMedidaSeguridad().equals(infraccionDTO.getIdmedidaSeguridad())) {
                detalleDTO.setIdMedidaSeguridad(infraccionDTO.getIdmedidaSeguridad());
                try {
                    detalleDTO = ActualizarMedidaSeguridadDetalleSupervision(detalleDTO, usuarioDTO);
                } catch (DetalleSupervisionException ex) {
                    LOG.error("Error en ActualizarDetalleSupervisionbyInfraccion", ex);
                }
            }
        }
        return detalleDTO;
    }

    @Override
    @Transactional(rollbackFor = DetalleSupervisionException.class)
    public DetalleSupervisionDTO ActualizarMedidaSeguridadDetalleSupervision(DetalleSupervisionDTO detalleSupervisionDTO, UsuarioDTO usuarioDTO) throws DetalleSupervisionException {
      LOG.info("ActualizarMedidaSeguridadDetalleSupervision");
        DetalleSupervisionDTO retorno=new DetalleSupervisionDTO();
        try {
            retorno=detalleSupervisionDAO.ActualizarMedidaSeguridadDetalleSupervision(detalleSupervisionDTO,usuarioDTO);  
        } catch (Exception ex) {
            LOG.error("Error en ActualizarMedidaSeguridadDetalleSupervision", ex);
            throw new DetalleSupervisionException(ex.getMessage(), ex);
        }
        return retorno; 
    }
            
    @Override
    @Transactional(readOnly = true)
    public List<DetalleSupervisionDTO> validarComentarioEjecucionMedida(DetalleSupervisionFilter filtro) throws DetalleSupervisionException {
        LOG.info("validarComentarioEjecucionMedida");
        List<DetalleSupervisionDTO> retorno = new ArrayList<DetalleSupervisionDTO>();
        try{        
            retorno=detalleSupervisionDAO.find(filtro);
            if(!CollectionUtils.isEmpty(retorno)){
                for(DetalleSupervisionDTO reg : retorno){
                    if(reg.getCountEscIncumplido()>0){
                        LOG.info("--getCountEscIncumplido--->"+reg.getCountEscIncumplido()+"--idDetSup--"+reg.getIdDetalleSupervision());
                        List<EscenarioIncumplidoDTO> lstEscIndo = escenarioIncumplidoServiceNeg.findEscenarioIncumplido(new EscenarioIncumplidoFilter(null, reg.getIdDetalleSupervision(), null));
                        LOG.info("----->"+(!CollectionUtils.isEmpty(lstEscIndo)?lstEscIndo.size():"vacio"));
                        for(EscenarioIncumplidoDTO regEscIndo : lstEscIndo){
                            if(StringUtil.isEmpty(regEscIndo.getComentarioEjecucion())){
                                throw new DetalleSupervisionException("Falta Registrar Comentario Ejecuci&oacute;n en Escenario Incumplido.",null);
                            }
                        } 
                    }else{
                        if(StringUtil.isEmpty(reg.getComentario())){
                            throw new DetalleSupervisionException("Falta Registrar Comentario Ejecuci&oacute;n en Infraccion.",null);
                        }
                    }                    
                }
            }
        }catch(Exception ex){
            LOG.error("Error en validarComentarioEjecucionMedida",ex);
            throw new DetalleSupervisionException(ex.getMessage(),ex);
        }
        return retorno;
    }
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    @Override
    @Transactional(readOnly = true)
    public List<DetalleSupervisionDTO> listarDetSupInfLevantamiento(DetalleSupervisionFilter filtro) throws DetalleSupervisionException {
      LOG.info("listarDetSupInfLevantamiento");
      List<DetalleSupervisionDTO> detalleSupervisionList=null;
        try {
        	detalleSupervisionList=detalleSupervisionDAO.listarDetSupInfLevantamiento(filtro);  
        } catch (Exception ex) {
            LOG.error("Error en listarDetSupInfLevantamiento", ex);
            throw new DetalleSupervisionException(ex);
        }
        return detalleSupervisionList; 
    }
    /* OSINE_SFS-791 - RSIS 33 - Fin */

    /* OSINE_SFS-791 - RSIS 41 - Inicio */
    @Override
    @Transactional(rollbackFor=DetalleSupervisionException.class)
    public List<DetalleSupervisionDTO> VerificaActualizaDetalleSupervisionSubsanados(DetalleSupervisionFilter filtro, UsuarioDTO usuarioDTO) throws DetalleSupervisionException {
      LOG.info("VerificaActualizaDetalleSupervisionSubsanados");
      //int cantidadDetalleActualizado = 0;
      List<DetalleSupervisionDTO> detalleSupervisionlistaretorno= new ArrayList<DetalleSupervisionDTO>();
      List<DetalleSupervisionDTO> detalleSupervisionList=null;
        try {
            detalleSupervisionList = detalleSupervisionDAO.find(filtro);
            LOG.info("en la lista de esta supervisiones hay : |"+detalleSupervisionList.size()+"|");
            if (detalleSupervisionList.size() > Constantes.LISTA_VACIA) {
                LOG.info("entro dentro del primer if");
                LOG.info("BUSCANDO EL ID RESULTADO SUP DE LOS SUBSANADOS SI");
                ResultadoSupervisionFilter filtroIdRS = new ResultadoSupervisionFilter();
                filtroIdRS.setCodigo(Constantes.CODIGO_RESULTADO_SUPERVISION_SUBSANADO_SI);
                ResultadoSupervisionDTO resultadoDTO = resultadoSupervisionServiceNeg.getResultadoSupervision(filtroIdRS);
                LOG.info("antes del for");
                for (DetalleSupervisionDTO detalleSupervisionDTO : detalleSupervisionList) {
                    if (detalleSupervisionDTO.getResultadoSupervision().getIdResultadosupervision().equals(resultadoDTO.getIdResultadosupervision())) {
                        DetalleSupervisionDTO detalleSupervisionDTOaux = new DetalleSupervisionDTO();
                        if (detalleSupervisionDTO.getIdDetalleSupervisionAnt() != null) {
                            detalleSupervisionDTOaux.setIdDetalleSupervision(detalleSupervisionDTO.getIdDetalleSupervisionAnt());
                            detalleSupervisionDTOaux.setIdDetalleSupervisionSubsana(detalleSupervisionDTO.getIdDetalleSupervision());
                            DetalleSupervisionDTO detalleSupervisionDTOaux2 = new DetalleSupervisionDTO();
                            detalleSupervisionDTOaux2 = detalleSupervisionDAO.ActualizarDetalleSupervisionSubsanado(detalleSupervisionDTOaux, usuarioDTO);
                            if (detalleSupervisionDTOaux2 != null) {
                                detalleSupervisionlistaretorno.add(detalleSupervisionDTOaux2);
                            } else {
                                detalleSupervisionlistaretorno = null;
                                LOG.error("Ocurrio un Error al Actualizar el Detalle Supervision");
                                throw new DetalleSupervisionException("Ocurrio un Error al Actualizar el DetalleSupervision", null);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            detalleSupervisionlistaretorno = null;
            LOG.error("Error en VerificaActualizaDetalleSupervisionSubsanados", ex);
            throw new DetalleSupervisionException(ex);
        }
        return detalleSupervisionlistaretorno;
    }
    /* OSINE_SFS-791 - RSIS 41 - Fin */
}
