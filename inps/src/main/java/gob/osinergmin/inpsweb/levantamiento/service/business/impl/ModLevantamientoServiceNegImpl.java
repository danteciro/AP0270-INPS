/**
* Resumen		
* Objeto			: PghOrgaActiModuSecc.java
* Descripción		: Clase DTO PghOrgaActiModuSecc
* Fecha de Creación	: 31/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 
package gob.osinergmin.inpsweb.levantamiento.service.business.impl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import gob.osinergmin.inpsweb.levantamiento.service.business.ModLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DestinatarioCorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DetalleLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.exception.LevantamientoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.DetalleLevantamientoFilter;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;

@Service("modLevantamientoServiceNeg")
public class ModLevantamientoServiceNegImpl implements ModLevantamientoServiceNeg {
	private static final Logger LOG = LoggerFactory.getLogger(ModLevantamientoServiceNegImpl.class);
	@Inject
	private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
	@Inject
	private ExpedienteServiceNeg expedienteServiceNeg;
	@Inject
	private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
	@Inject 
	private DestinatarioCorreoServiceNeg destinatarioCorreoServiceNeg;
	@Inject 
	private CorreoServiceNeg correoServiceNeg;
	@Inject
	private DetalleLevantamientoServiceNeg detalleLevantamientoServiceNeg;
	
	@Override
	@Transactional(rollbackFor=LevantamientoException.class)
	public void enviarLevantamiento(ExpedienteDTO expediente, List<DetalleSupervisionDTO> detalleSupervisionList, SupervisionDTO supervision, UbigeoDTO ubigeo, UsuarioDTO usuario) throws LevantamientoException {
		LOG.info("enviarLevantamiento");
        DocumentoAdjuntoFilter filtroDocAdjunto = new DocumentoAdjuntoFilter();        
        try {
        	if(expediente!=null && supervision!=null && !CollectionUtils.isEmpty(detalleSupervisionList)){   
        		OrdenServicioDTO ordenServicio = supervision.getOrdenServicioDTO()!=null ? supervision.getOrdenServicioDTO() : null;
        		
        		List<MaestroColumnaDTO> lstTipoDocSup=maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_DOCUMENTO_MULTIMEDIA);
		        String codigoDocEnvioSiged=(!CollectionUtils.isEmpty(lstTipoDocSup)) ? lstTipoDocSup.get(0).getCodigo() : null;
		        
		        List<File> archivosLevList=null;
        		for(DetalleSupervisionDTO detalle : detalleSupervisionList){
        			DetalleLevantamientoFilter filtroLev=new DetalleLevantamientoFilter();
		            filtroLev.setIdDetalleSupervision(detalle.getIdDetalleSupervision());
		            filtroLev.setFlagUltimoRegistro(Constantes.ESTADO_ACTIVO);
		            List<DetalleLevantamientoDTO> detalleLevantamientoist = detalleLevantamientoServiceNeg.find(filtroLev);
		            if(!CollectionUtils.isEmpty(detalleLevantamientoist)){
		            	Long idLevantamiento=detalleLevantamientoist.get(0).getIdDetalleLevantamiento();			                
	        		
			        	//Los medios probatorios cargados para levantamiento de cada obligación incumplida deberán ser enviados al expediente SIGED 
			        	//como tipo documento SIGED MEDIOS PROBATORIOS DE LEVANTAMIENTO DE INCUMPLIMIENTOS
			        	filtroDocAdjunto = new DocumentoAdjuntoFilter();
						filtroDocAdjunto.setIdDetalleLevantamiento(idLevantamiento);
						List<DocumentoAdjuntoDTO> docAdjuntoDetLevList=documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroDocAdjunto);	
						List<File> archivosList = documentoAdjuntoServiceNeg.armarFilesEnvioPghDocAdjSiged(docAdjuntoDetLevList, usuario);
						if(!CollectionUtils.isEmpty(archivosList)){
							for(File file : archivosList){
								if(archivosLevList==null) archivosLevList=new ArrayList<File>();
								archivosLevList.add(file);
							}
						}							        	
		            }
        		}
        		if(!CollectionUtils.isEmpty(archivosLevList) && codigoDocEnvioSiged!=null){
					documentoAdjuntoServiceNeg.agregarDocExpedienteSiged(archivosLevList, expediente, usuario, codigoDocEnvioSiged);
				}	
        		
        		//El estado de LEVANTAMIENTOS del expediente cambiará a POR EVALUAR. 					 
	        	List<MaestroColumnaDTO> estadoLevantamientoList = maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_ESTADO_LEVANTAMIENTO, Constantes.APLICACION_INPS, Constantes.CODIGO_ESTADO_LEVANTAMIENTO_POREVALUAR);
	            if(!CollectionUtils.isEmpty(estadoLevantamientoList)){	            	
	            	MaestroColumnaDTO estadoLevantamiento = estadoLevantamientoList.get(0);
	            	expediente.setEstadoLevantamiento(estadoLevantamiento);
	            	expedienteServiceNeg.actualizar(expediente, usuario);
	            }
	            
	            //El expediente INPS deberá habilitarse en la bandeja DERIVADOS de INPS, con el fin que el SUPERVISOR REGIONAL 
		        //pueda generar una nueva orden de servicio (orden de levantamiento) para la atención de la Empresa Supervisora.
		        Long idPersonalOri = expediente.getPersonal().getIdPersonal();
		        Long idPersonalDest = expediente.getPersonal().getIdPersonal();
	        	String motivoReasignacion = "derivar.";
	        	List<ExpedienteDTO> expedienteList = new ArrayList<ExpedienteDTO>();	
	        	expedienteList.add(expediente);
	        	expedienteServiceNeg.derivar(expedienteList, idPersonalOri, idPersonalDest, motivoReasignacion, usuario);
        		
	            //Deberá notificarse el envío de información al Supervisor Regional.
	            //Ver contenido propuesto para el correo  en el Anexo_Solicitud_NPS_Correos.doc
	            //sección: HA CONCLUIDO EL INGRESO DE INFORMACION LEVANTAMIENTO INFRACCION.
	            envioCorreo(ordenServicio, ubigeo);	        
        	}        
        } catch (Exception ex) {
        	LOG.error("Error en enviarLevantamiento", ex);
        	throw new LevantamientoException(ex.getMessage(),ex);            
        }	
	}
	
	
	public void envioCorreo(OrdenServicioDTO ordenServicio, UbigeoDTO ubigeo){
		LOG.info("envioCorreo");
		try {
			//Deberá notificarse el envío de información al Supervisor Regional.
	        //Ver contenido propuesto para el correo  en el Anexo_Solicitud_NPS_Correos.doc
	        //sección: HA CONCLUIDO EL INGRESO DE INFORMACION LEVANTAMIENTO INFRACCION.
			DestinatarioCorreoFilter filtro = new DestinatarioCorreoFilter();
	        filtro.setIdDepartamento(ubigeo.getIdDepartamento());
	        filtro.setIdProvincia(ubigeo.getIdProvincia());
	        filtro.setIdDistrito(ubigeo.getIdDistrito());
	        filtro.setCodigoFuncionalidadCorreo(Constantes.CODIGO_FUNCIONALIDAD_TF017);
	        List<DestinatarioCorreoDTO> milistaDestinos = destinatarioCorreoServiceNeg.getDestinatarioCorreobyUbigeo(filtro);
	        if(!CollectionUtils.isEmpty(milistaDestinos)){
	            if(ordenServicio!=null && ordenServicio.getExpediente()!=null && ordenServicio.getExpediente().getUnidadSupervisada()!=null){
		            ExpedienteDTO expedienteDTO = new ExpedienteDTO();            
		            expedienteDTO.setIdExpediente(ordenServicio.getExpediente().getIdExpediente());
		            expedienteDTO.setNumeroExpediente(ordenServicio.getExpediente().getNumeroExpediente());
		            expedienteDTO.setOrdenServicio(ordenServicio);
		            expedienteDTO.setUnidadSupervisada(ordenServicio.getExpediente().getUnidadSupervisada());		            
		            correoServiceNeg.enviarCorreoNotificacionTareaProg(milistaDestinos, expedienteDTO, new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF017));
	            }
	        }
		} catch (Exception ex) {
        	LOG.error("Error en envioCorreo", ex);        	          
        }	
	}
}
