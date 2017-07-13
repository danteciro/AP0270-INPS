/**
* Resumen		
* Objeto			: EmpresasZonaServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación 
* 					  de los métodos declarados en la clase interfaz EmpresasZonaServiceNeg
* Fecha de Creación	: 15/09/216.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.dse_common.domain.dto.EtapaDTO;
import gob.osinergmin.dse_common.domain.dto.ReleDTO;
import gob.osinergmin.dse_common.domain.dto.SubEstacionDTO;
import gob.osinergmin.dse_common.domain.dto.ZonaDTO;
import gob.osinergmin.dse_common.domain.in.ObtenerEtapasInRO;
import gob.osinergmin.dse_common.domain.in.ObtenerRelesInRO;
import gob.osinergmin.dse_common.domain.in.ObtenerSubEstacionesInRO;
import gob.osinergmin.dse_common.domain.in.ObtenerZonasInRO;
import gob.osinergmin.dse_common.domain.out.ObtenerEtapasOutRO;
import gob.osinergmin.dse_common.domain.out.ObtenerRelesOutRO;
import gob.osinergmin.dse_common.domain.out.ObtenerSubEstacionesOutRO;
import gob.osinergmin.dse_common.domain.out.ObtenerZonasOutRO;
import gob.osinergmin.dse_common.domain.ui.EtapaFilter;
import gob.osinergmin.dse_common.domain.ui.ReleFilter;
import gob.osinergmin.dse_common.domain.ui.SubEstacionFilter;
import gob.osinergmin.dse_common.domain.ui.ZonaFilter;
import gob.osinergmin.dse_common.remote.EtapaEndpoint;
import gob.osinergmin.dse_common.remote.ReleEndpoint;
import gob.osinergmin.dse_common.remote.SubEstacionEndpoint;
import gob.osinergmin.dse_common.remote.ZonaEndpoint;
import gob.osinergmin.inpsweb.domain.builder.DetaConfRelesBuilder;
import gob.osinergmin.inpsweb.service.business.EmpresasZonaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EmpresasZonaDAO;
import gob.osinergmin.inpsweb.service.exception.DetaSupeCampRechCargaException;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.service.exception.SupeCampRechCargaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.ConfiguracionRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DepartamentoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaRepDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfiguracionRelesFilter;
import gob.osinergmin.mdicommon.domain.ui.DetaSupeCampRechCargaFilter;
import gob.osinergmin.mdicommon.domain.ui.EmpresasZonaVwFilter;
import gob.osinergmin.mdicommon.domain.ui.SupeCampRechCargaFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

@Service("EmpresasZonaServiceNeg")
public class EmpresasZonaServiceNegImpl implements EmpresasZonaServiceNeg {	
    private static final Logger LOG = LoggerFactory.getLogger(EmpresasZonaServiceNegImpl.class);
    @Inject
    private EmpresasZonaDAO empresasZonaDAO;
    
    @Autowired
    private ZonaEndpoint zonaEndpoint;
    
    @Autowired
    private SubEstacionEndpoint subEstacionEndpoint;
    
    @Autowired
    private ReleEndpoint releEndpoint;
    
    @Autowired
    private EtapaEndpoint etapaEndpoint;
    
    @Value("${alfresco.host}")
    private String HOST;

    @Override
    public List<EmpresasZonaVwDTO> listarEmpresasZona(EmpresasZonaVwFilter filtro) {
        LOG.info("Neg listarEmpresasZona");
        List<EmpresasZonaVwDTO> retorno = null;
        try {
            retorno = empresasZonaDAO.listarEmpresasZona(filtro);

        } catch (Exception ex) {
            LOG.error("error listarEmpresasZona", ex);
        }
        return retorno;
    }
    
	@Override
	public List<ZonaDTO> listarZonasXAnio(String anio) {
		LOG.info("Neg listarZonasXAnio");
		
    	ObtenerZonasInRO response = new ObtenerZonasInRO(); 
    	ZonaFilter zonaFilter = new ZonaFilter();
    	zonaFilter.setAnio(Integer.valueOf(anio));
    	response.setFiltroZona(zonaFilter);
    	ObtenerZonasOutRO listZonasOutRO = zonaEndpoint.buscarZonas(response);
    	
        if (listZonasOutRO.getRespuesta().getResultado().toString().equals(InvocationResult.SUCCESS.toString())){
            return listZonasOutRO.getListaZona();
        }else{
        	return null;
        }
	}

	@Override
	public List<SubEstacionDTO> listarSubEstaciones(String idEmpresa, String anio, String idZona) {
		LOG.info("Neg listarSubEstaciones");
		
    	ObtenerSubEstacionesInRO response = new ObtenerSubEstacionesInRO();
    	SubEstacionFilter subEstacionFilter = new SubEstacionFilter();
    	subEstacionFilter.setAnio(Integer.valueOf(anio));
    	subEstacionFilter.setIdEmpresa(idEmpresa);
    	subEstacionFilter.setIdZona(Integer.valueOf(idZona));
    	response.setFiltroSubEstacion(subEstacionFilter);
    	ObtenerSubEstacionesOutRO listSubEstacionOutRO = subEstacionEndpoint.buscarSubEstaciones(response);
    	
        if (listSubEstacionOutRO.getRespuesta().getResultado().toString().equals(InvocationResult.SUCCESS.toString())){
            return listSubEstacionOutRO.getListaSubEstacion();
        }else{
        	return null;
        }
	}
	
	@Override
	public List<ReleDTO> listarReles(String idSubEstacion, String idEmpresa, String anio, String etapas) {
		LOG.info("Neg listarReles");
		
    	ObtenerRelesInRO response = new ObtenerRelesInRO();
    	ReleFilter releFilter = new ReleFilter();
    	releFilter.setAnio(Integer.valueOf(anio));
    	releFilter.setIdEmpresa(idEmpresa);
    	releFilter.setIdSubEstacion(idSubEstacion);
    	releFilter.setIdEtapas(etapas);
    	response.setFiltroRele(releFilter);
    	ObtenerRelesOutRO listRelesOutRO = releEndpoint.buscarReles(response);
    	
        if (listRelesOutRO.getRespuesta().getResultado().toString().equals(InvocationResult.SUCCESS.toString())){
            return listRelesOutRO.getListaRele();
        }else{
        	return null;
        }
	}
	
	@Override
	@Transactional
	public List<SupeCampRechCargaDTO> listarSupeCampRechCarga(SupeCampRechCargaFilter filtro) {
		LOG.info("Neg listarSupeCampRechCarga");
        List<SupeCampRechCargaDTO> retorno = null;
        try {
            retorno = empresasZonaDAO.listarSupeCampRechCarga(filtro);

        } catch (Exception ex) {
            LOG.error("error listarSupeCampRechCarga", ex);
        }
        return retorno;
	}
	
	@Override
	@Transactional
	public SupeCampRechCargaDTO insertarSupeCampRechCarga(String idEmpresa, String anio,SupeCampRechCargaDTO supeCampRechCargaDTO, 
			UsuarioDTO usuarioDTO) throws SupeCampRechCargaException{
		LOG.info("insertarSupeCampRechCarga");
		
		SupeCampRechCargaDTO retorno=new SupeCampRechCargaDTO();
		DetaSupeCampRechCargaDTO respuestaDeta=null;
        try{
            retorno=empresasZonaDAO.insertarSupeCampRechCarga(supeCampRechCargaDTO, usuarioDTO);
            
            // Si tuviera Reles en Tabla NPS_DETA_SUPE_CAMP_RECH_CARGA
            if(retorno!=null){
            	DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO=new DetaSupeCampRechCargaDTO();
        		detaSupeCampRechCargaDTO.setIdSupeCampRechCarga(retorno);
        		detaSupeCampRechCargaDTO.setEstado(Constantes.ESTADO_ACTIVO);
        		        		
            	ConfiguracionRelesFilter filtro=new ConfiguracionRelesFilter();
            	filtro.setEmpCodemp(idEmpresa);
            	filtro.setAnio(anio);
            	filtro.setIdZona(Long.valueOf(supeCampRechCargaDTO.getIdZona()));
            	filtro.setIdSubestacion(0L);
            	List<ConfiguracionRelesDTO> listaConfiguracionSE = listarConfiguracionReles(filtro);
            	
            	List<ReleDTO> listaReles = new ArrayList<ReleDTO>();
            	
            	if(listaConfiguracionSE!=null && listaConfiguracionSE.size()>0){
            		
            		for(ConfiguracionRelesDTO configuracion : listaConfiguracionSE){
            			String etapasSubEstacion="";
            			if(configuracion.getDetaConfRelesList()!=null && configuracion.getDetaConfRelesList().size()>0){
		        			int contador=0;
			        		for(DetaConfRelesDTO detalleConf : configuracion.getDetaConfRelesList()){
			        			if(contador==0){
			        				etapasSubEstacion=etapasSubEstacion+detalleConf.getIdEtapa();
				    			}else{
				    				etapasSubEstacion=etapasSubEstacion+","+detalleConf.getIdEtapa();
				    			}
				    			contador++;
			            	}
		        		}
		        		List<ReleDTO> listaRelesWS = listarReles(String.valueOf(configuracion.getIdSubestacion()), idEmpresa, anio, etapasSubEstacion);
		        		listaReles.addAll(listaRelesWS);
		        	}
            		            		
            		if(listaReles!=null && listaReles.size()>0){
            			respuestaDeta = insertarDetaSupeCampRechCarga(detaSupeCampRechCargaDTO, listaReles, usuarioDTO);
            		}
            	}
            }
            
        }catch(Exception e){
            LOG.error("Error insertarSupeCampRechCarga",e);
            throw new SupeCampRechCargaException(e.getMessage(),e);
        }
        return retorno;
	}
	
	@Override
	@Transactional
	public SupeCampRechCargaDTO actualizarSupeCampRechCarga(SupeCampRechCargaDTO supeCampRechCargaDto, 
			UsuarioDTO usuarioDTO, String pantalla) throws SupeCampRechCargaException{
		LOG.info("Neg actualizarSupeCampRechCarga");
		SupeCampRechCargaDTO retorno = null;
        try {
            retorno = empresasZonaDAO.actualizarSupeCampRechCarga(supeCampRechCargaDto, usuarioDTO, pantalla);

        } catch (Exception ex) {
            LOG.error("error actualizarSupeCampRechCarga", ex);
            throw new SupeCampRechCargaException(ex.getMessage(),ex);
        }
        return retorno;
	}
	
	@Override
	@Transactional
	public SupeCampRechCargaDTO inactivarSupeCampYDetaSupeCamp(SupeCampRechCargaDTO supeCampRechCargaDto, 
			UsuarioDTO usuarioDTO, String pantalla) throws SupeCampRechCargaException{
		LOG.info("Neg inactivarSupeCampYDetaSupeCamp");
		SupeCampRechCargaDTO retorno = null;
        try {
            retorno = empresasZonaDAO.actualizarSupeCampRechCarga(supeCampRechCargaDto, usuarioDTO, pantalla);
            if(retorno!=null){
            	DetaSupeCampRechCargaFilter filtroDeta = new DetaSupeCampRechCargaFilter();
            	SupeCampRechCargaFilter supeFilter = new SupeCampRechCargaFilter();
            	supeFilter.setIdSupeCampRechCarga(retorno.getIdSupeCampRechCarga());
            	filtroDeta.setIdSupeCampRechCarga(supeFilter);
            	filtroDeta.setEstado(Constantes.ESTADO_ACTIVO);
            	List<DetaSupeCampRechCargaDTO> listaDetaSupe = empresasZonaDAO.listarDetaSupeCampRechCarga(filtroDeta);
            	if(listaDetaSupe!=null && listaDetaSupe.size()>0){
            		DetaSupeCampRechCargaDTO detalleActualizar;
            		for(DetaSupeCampRechCargaDTO detalle : listaDetaSupe){
            			detalleActualizar = new DetaSupeCampRechCargaDTO();
            			detalleActualizar.setIdDetaSupeCampRechCarga(detalle.getIdDetaSupeCampRechCarga());;
            			detalleActualizar.setEstado(Constantes.ESTADO_INACTIVO);
            			DetaSupeCampRechCargaDTO retornoDetaSupe = empresasZonaDAO.actualizarDetaSupeCampRechCarga(detalleActualizar, usuarioDTO);
            		}
            	}
            }

        } catch (Exception ex) {
            LOG.error("error inactivarSupeCampYDetaSupeCamp", ex);
            throw new SupeCampRechCargaException(ex.getMessage(),ex);
        }
        return retorno;
	}
	
	@Override
	@Transactional
	public List<DetaSupeCampRechCargaDTO> listarDetaSupeCampRechCarga(DetaSupeCampRechCargaFilter filtro) {
		LOG.info("Neg listarDetaSupeCampRechCarga");
        List<DetaSupeCampRechCargaDTO> retorno = null;
        try {
            retorno = empresasZonaDAO.listarDetaSupeCampRechCarga(filtro);

        } catch (Exception ex) {
            LOG.error("error listarDetaSupeCampRechCarga", ex);
        }
        return retorno;
	}
	
	@Override
	@Transactional
	public DetaSupeCampRechCargaDTO insertarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO, List<ReleDTO> listaReles,
			UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException{
		LOG.info("insertarDetaSupeCampRechCarga");
		
		DetaSupeCampRechCargaDTO retorno=new DetaSupeCampRechCargaDTO();
        
        try{
        	for(ReleDTO rele : listaReles){
        		detaSupeCampRechCargaDTO.setIdRele(rele.getIdRele());
        		retorno=empresasZonaDAO.insertarDetaSupeCampRechCarga(detaSupeCampRechCargaDTO, usuarioDTO);
        	}
            
            
        }catch(Exception e){
            LOG.error("Error insertarDetaSupeCampRechCarga",e);
            throw new DetaSupeCampRechCargaException(e.getMessage(),e);
        }
        return retorno;
	}
	
	@Override
	@Transactional
	public DetaSupeCampRechCargaDTO actualizarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDto, 
			UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException{
		LOG.info("Neg actualizarDetaSupeCampRechCarga");
		DetaSupeCampRechCargaDTO retorno = null;
        try {
            retorno = empresasZonaDAO.actualizarDetaSupeCampRechCarga(detaSupeCampRechCargaDto, usuarioDTO);

        } catch (Exception ex) {
            LOG.error("error actualizarDetaSupeCampRechCarga", ex);
            throw new DetaSupeCampRechCargaException(ex.getMessage(),ex);
        }
        return retorno;
	}
	
	@Override
    @Transactional
    public DocumentoAdjuntoDTO enviarActaSiged(DocumentoAdjuntoDTO doc, ExpedienteDTO expedienteDTO, Long idPersonalSiged, String descripcionEmpresa, String ruc) throws DocumentoAdjuntoException {
        LOG.info("enviarActaSiged");
        DocumentoAdjuntoDTO docEnviarSiged = null;
        try {
            if (expedienteDTO.getEmpresaSupervisada() == null && expedienteDTO.getEmpresaSupervisada().getIdEmpresaSupervisada() == null) {
                throw new ExpedienteException("No existe Identificador de Empresa Supervisada.", null);
            }

            byte[] mapByte = doc.getArchivoAdjunto();

            if (mapByte == null) {
                throw new ExpedienteException("No existe Archivo fisico para el documento: " + doc.getNombreArchivo() + ".", null);
            }
            LOG.info("mapByte-->" + mapByte);
            ExpedienteInRO expedienteInRO = new ExpedienteInRO();

            expedienteInRO.setNroExpediente(expedienteDTO.getNumeroExpediente());
            DocumentoInRO documentoInRO = new DocumentoInRO();

            documentoInRO.setCodTipoDocumento(Integer.valueOf(doc.getIdTipoDocumento().getCodigo()));
            documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            documentoInRO.setAppNameInvokes("");
            documentoInRO.setPublico(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_NO);
            documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setFirmado(Constantes.ESTADO_SIGED_NO);
            documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setNroFolios(1);
            documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_NO);
            documentoInRO.setUsuarioCreador(idPersonalSiged.intValue());

            //CLIENTE
            ClienteInRO cliente1 = new ClienteInRO();
            cliente1.setCodigoTipoIdentificacion(1); // codigoTipoIdentificacion
            cliente1.setNroIdentificacion(ruc);// RUC
            cliente1.setTipoCliente(3);
            
            cliente1.setRazonSocial(descripcionEmpresa);// Descripcion Empresa

            DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
            direccion1.setDireccion(" ");
            direccion1.setDireccionPrincipal(true);
            direccion1.setTelefono(" ");
            direccion1.setUbigeo(150104);
            direccion1.setEstado(Constantes.ESTADO_SIGED_ACTIVO);

            List<DireccionxClienteInRO> listaDirCliente1 = new ArrayList<DireccionxClienteInRO>();
            listaDirCliente1.add(direccion1);
            DireccionxClienteListInRO direccionesCliente1 = new DireccionxClienteListInRO();
            direccionesCliente1.setDireccion(listaDirCliente1);
            cliente1.setDirecciones(direccionesCliente1);

            List<ClienteInRO> listaClientes = new ArrayList<ClienteInRO>();
            listaClientes.add(cliente1);
            ClienteListInRO clientes = new ClienteListInRO();
            clientes.setCliente(listaClientes);
            documentoInRO.setClientes(clientes);

            expedienteInRO.setDocumento(documentoInRO);

            //file
            File someFile = new File(doc.getNombreArchivo());
            InputStream is = null;
            if (mapByte != null) {
                FileOutputStream fos = new FileOutputStream(someFile);
                fos.write(mapByte);
                fos.flush();
                fos.close();
                is = FileUtils.openInputStream(someFile);
            }

            List<File> files = new ArrayList();
            files.add(someFile);

            DocumentoOutRO documentoOutRO = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRO, files, false);
            
            docEnviarSiged = new DocumentoAdjuntoDTO();
            docEnviarSiged.setEstado(String.valueOf(documentoOutRO.getResultCode()));
            
            if (!(documentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {
                docEnviarSiged.setComentario(documentoOutRO.getMessage());
                
                String mensaje="";
            	if(documentoOutRO.getMessage().equals("Ocurrio un error en la invocacion.")){
            		mensaje="No se pudo adjuntar el Acta.";
            	}else{
            		mensaje=documentoOutRO.getMessage();
            	}
                
                LOG.info("Error: " + documentoOutRO.getResultCode() + "-Ocurrio un error: " + documentoOutRO.getMessage());
                throw new DocumentoAdjuntoException("Error en Servicio SIGED: " + mensaje, null);
            }else{
            	docEnviarSiged.setIdDocumento(Long.valueOf(documentoOutRO.getCodigoDocumento()));
            }
        } catch (Exception e) {
            LOG.error("error en enviarActaSiged", e);
            throw new DocumentoAdjuntoException(e.getMessage(), null);
        }
        return docEnviarSiged;
    }
	
	@Override
	@Transactional
	public int gestionarRelesDetSupe(List<DetaSupeCampRechCargaDTO> listaRelesInsertar,List<DetaSupeCampRechCargaDTO> listaRelesActualizar, boolean inactivarCabecera,
			UsuarioDTO usuario) throws DetaSupeCampRechCargaException {
		int retorno = 1;
		DetaSupeCampRechCargaDTO resultadoInsertar=null;
		DetaSupeCampRechCargaDTO resultadoActualizar=null;
		try{
			if(listaRelesInsertar!=null && listaRelesInsertar.size()>0){
				for(DetaSupeCampRechCargaDTO detalleInsertar : listaRelesInsertar ){
					resultadoInsertar=empresasZonaDAO.insertarDetaSupeCampRechCarga(detalleInsertar, usuario);
					if(!(resultadoInsertar!=null && resultadoInsertar.getIdDetaSupeCampRechCarga()!=null)){
						retorno=0;
						break;
					}
				}
			}
			if(retorno!=0){
				if(listaRelesActualizar!=null && listaRelesActualizar.size()>0){
					for(DetaSupeCampRechCargaDTO detallectualizar : listaRelesActualizar ){
						resultadoActualizar=empresasZonaDAO.actualizarDetaSupeCampRechCarga(detallectualizar, usuario);
						if(!(resultadoActualizar!=null && resultadoActualizar.getIdDetaSupeCampRechCarga()!=null)){
							retorno=0;
							break;
						}
		        	}
				}
			}
			
			if(inactivarCabecera){
				Long idSupe=0L;
				if(listaRelesInsertar!=null && listaRelesInsertar.size()>0){
					idSupe=listaRelesInsertar.get(0).getIdSupeCampRechCarga().getIdSupeCampRechCarga();
				}else if(listaRelesActualizar!=null && listaRelesActualizar.size()>0){
					idSupe=listaRelesActualizar.get(0).getIdSupeCampRechCarga().getIdSupeCampRechCarga();
				}
				
				SupeCampRechCargaDTO supeActualizar = new SupeCampRechCargaDTO();
				supeActualizar.setIdSupeCampRechCarga(idSupe);
				empresasZonaDAO.actualizarSupeCampRechCarga(supeActualizar, usuario, Constantes.TIPO_PANTALLA_CAMBIAR_ESTADO);
			}
			
        }catch(Exception ex){
        	retorno=0;
            LOG.error(" error gestionarRelesDetSupe",ex);
            throw new DetaSupeCampRechCargaException(ex.getMessage(),ex);
        }
		return retorno;
	}
	
	@Override
	@Transactional
    public List<ConfiguracionRelesDTO> listarConfiguracionReles(ConfiguracionRelesFilter filtro) {
        LOG.info("Neg listarConfiguracionReles");
        List<ConfiguracionRelesDTO> retorno = null;
        try {
            retorno = empresasZonaDAO.listarConfiguracionReles(filtro);

        } catch (Exception ex) {
            LOG.error("error listarConfiguracionReles", ex);
        }
        return retorno;
    }
	
	/**Reporte Lista Reles***/
	@Override
    @Transactional
	   public ReleDTO  ReporteActaInspeccion(List<ReleDTO> lista, HttpServletRequest request, HttpServletResponse response, 
			   HttpSession session, boolean esPlantilla, SupeCampRechCargaDTO objSupeCampRechCargaDTO, List<DetaSupeCampRechCargaDTO> lDetaSupeCampRechCargaDTO) throws DocumentoAdjuntoException{
		   
		   String rutaReportePrincipal="", rutaReportePrincipal2="";
		   List<ReleDTO> lReleDTO = new ArrayList<ReleDTO>();
		   lReleDTO = lista;
		   
		   InputStream reportJasper = null;
		   InputStream reportJasper2 = null;
		   InputStream reportStreamImage = null, reportStreamImage1 = null;
		   InputStream reportStreamImageUkas = null, reportStreamImageUkas1=null;
		   InputStream reportStreamImageSGS = null, reportStreamImageSGS1=null;
		   Map<String, Object> reportParams = null, reportParams2 = null;
		   ReleDTO registroDocumentoEjecucionMedida = null;
		   String rutaImagen = Constantes.RUTA_LOGO_PLANTILLA;
		   String rutaImagenUkas = Constantes.RUTA_LOGO_UKAS_PLANTILLA;
		   String rutaImagenSGS = Constantes.RUTA_LOGO_SGS_PLANTILLA;
		   LOG.info("abrir en ReporteActaInspeccion");
	        try {
	        	ServletContext context = session.getServletContext();
	        	ServletContext context2 = session.getServletContext();
	        	ServletContext context3 = session.getServletContext();
	        	ServletContext context4 = session.getServletContext();
	        	ServletContext context5 = session.getServletContext();
	        	ServletContext context6 = session.getServletContext();
	        	 LOG.info("INICIANDO LA CONSTRUCCION DEL DOCUMENTO ACTA DE INSPECCION");
	        	 
	        	 reportStreamImage = context.getResourceAsStream(rutaImagen);
	        	 reportStreamImageUkas = context2.getResourceAsStream(rutaImagenUkas);
	        	 reportStreamImageSGS = context3.getResourceAsStream(rutaImagenSGS);
	        	 reportStreamImage1 = context4.getResourceAsStream(rutaImagen);
	        	 reportStreamImageUkas1 = context5.getResourceAsStream(rutaImagenUkas);
	        	 reportStreamImageSGS1 = context6.getResourceAsStream(rutaImagenSGS);
	        	 
	        	 if(!esPlantilla){
	        		 rutaReportePrincipal = Constantes.RUTA_PLANTILLA_ACTA_INSPECCION;
	        	 }else{
	        		 rutaReportePrincipal = Constantes.RUTA_PLANTILLA_ACTA_INSPECCION_BD;
	        	 }
//	        	 rutaReportePrincipal = Constantes.RUTA_PLANTILLA_ACTA_INSPECCION_BD;
	        	 
	        	 reportJasper = context.getResourceAsStream(rutaReportePrincipal);
	        	 
	        	 rutaReportePrincipal2 = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_actainspeccionObs;
	        	 reportJasper2 = context.getResourceAsStream(rutaReportePrincipal2);
	        	 
	        	 reportParams = setearInformacionActaInspeccion(reportStreamImage, reportStreamImageUkas, reportStreamImageSGS, lReleDTO, esPlantilla, objSupeCampRechCargaDTO);
	        	 reportParams2 = setearObservacionesActaInspeccion(reportStreamImage1, reportStreamImageUkas1, reportStreamImageSGS1, esPlantilla, objSupeCampRechCargaDTO);
	        	 
	             byte[] x = listarDocumentoActaInspeccionBDDsr(reportJasper, reportJasper2,reportParams, reportParams2, lReleDTO, esPlantilla, lDetaSupeCampRechCargaDTO);

	             descargarReporteRele(x, response, esPlantilla);
	             LOG.info("SE REGISTRO EL DOCUMENTO DE ACTA DE INSPECCION");

	      
	        }catch (Exception ex) {
	            registroDocumentoEjecucionMedida = null;
	            LOG.error("OCURRIO UN ERROR AL GENERAR EL DOCUMENTO DE ACTA DE INSPECCION",ex);
	            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
	        }
	        return registroDocumentoEjecucionMedida;
	    }
	   
	
    public void descargarReporteRele( byte[] f, HttpServletResponse response, boolean esPlantilla) {
        
        try {        	
        	String nombre="";
        	
        	if(esPlantilla)
        		nombre="Plantilla Acta Inspeccion.pdf";
        	else
        		nombre="Acta Inspeccion.pdf";
        	
        	File someFile = new File(nombre);
            
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(f);
            fos.flush();
            fos.close();
            InputStream is = FileUtils.openInputStream(someFile);
    	
            if(is==null){
    	        response.getWriter().write("Error al Descargar Archivo.");
                return;
            }       	
            //String nombreFichero = filtro.getNombreArchivo();        	
            response.setHeader("Content-Disposition", "attachment; filename=\""
                        + nombre+ "\"");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        }catch (Exception ex) {
             LOG.info("error imprimirReporteRele--->"+ex.getMessage());
            //LOG.info("Error writing file to output stream. Filename was '" + filtro.getNombreArchivo()+ "'");
            throw new RuntimeException("exportar"+ex.getMessage());
        }
        }        
	   
	    
		 public Map<String, Object> setearInformacionActaInspeccion(InputStream reportStreamImage, InputStream reportStreamImageUkas, InputStream reportStreamImageSGS, List<ReleDTO> lista, 
				 boolean esPlantilla, SupeCampRechCargaDTO objSupeCampRechCargaDTO) {
			  
			 Map<String, Object> reportParams = new HashMap<String, Object>();
			 
			 reportParams.put("rutaImagen", reportStreamImage);
			 reportParams.put("rutaImagenUkas", reportStreamImageUkas);
			 reportParams.put("rutaImagenSGS", reportStreamImageSGS);
			 /*
			 SupeCampRechCargaDTO objSupeCamp = new SupeCampRechCargaDTO();
			 List<DetaSupeCampRechCargaDTO> detaSupeCampRechCargaList = new ArrayList<DetaSupeCampRechCargaDTO>();
			 DetaSupeCampRechCargaDTO detaSupeCamp = new DetaSupeCampRechCargaDTO();
			 ReleServicioDTO rele = new ReleServicioDTO();
			 detaSupeCamp.setReleServicio(rele);
			 */
			 
			 
			  //CABECERA DEL REPORTE
			   if(!esPlantilla){
			   	reportParams.put("fechaHoraI", objSupeCampRechCargaDTO.getFechaInicio() + " " + objSupeCampRechCargaDTO.getHoraInicio() + ":" + objSupeCampRechCargaDTO.getMinutoInicio()); 
			   	reportParams.put("fechaHoraF", objSupeCampRechCargaDTO.getFechaFin() + " " + objSupeCampRechCargaDTO.getHoraFin() + ":" + objSupeCampRechCargaDTO.getMinutoFin());
				reportParams.put("empresa", objSupeCampRechCargaDTO.getNombrEmpresa());
				reportParams.put("departamento", objSupeCampRechCargaDTO.getDescripcionDepartamento());
				reportParams.put("provincia", objSupeCampRechCargaDTO.getDescripcionProvincia());
				reportParams.put("distrito", objSupeCampRechCargaDTO.getDescripcionDistrito());
			
				/*for(ReleDTO listaRele :lista ){
				reportParams.put("alimentador",listaRele.getAlimentador());   
				reportParams.put("subEstacion", listaRele.getSubEstacion());
		            reportParams.put("demandaMax",listaRele.getDemandaMax());
		            reportParams.put("demandaMed", listaRele.getDemandaMed());
		            reportParams.put("demandaMin", listaRele.getDemandaMin());
		            reportParams.put("potR", listaRele.getPotR());
		            reportParams.put("releUmbralHz", listaRele.getReleUmbralHz());
		            reportParams.put("releUmbralS", listaRele.getReleUmbralS());
		            reportParams.put("releDerivadaHZ", listaRele.getReleDerivadaHZ());
		            reportParams.put("releDerivadaHZS", listaRele.getReleDerivadaHZS());
		            reportParams.put("releDerivadaS", listaRele.getReleDerivadaS());
		          //  reportParams.put("idRele", (releDTO.getIdRele()));
		            reportParams.put("marca", listaRele.getMarca());
		            reportParams.put("modelo", listaRele.getModelo());
		            reportParams.put("serie", listaRele.getSerie());
		            reportParams.put("subEstacion", listaRele.getSubEstacion());
		            reportParams.put("kV", listaRele.getkV());
		            reportParams.put("codInterrupcion", listaRele.getCodInterrupcion());
		            reportParams.put("fechaImplementacion", listaRele.getFechaImplementacion());
		            reportParams.put("etapa", listaRele.getEtapa());
		            //retorno.add(releDTO);
				}*/
			   }else{
				   
				    reportParams.put("fechaHoraI", "");
				   	reportParams.put("fechaHoraF", "");
					//reportParams.put("empresa", "");
				   	reportParams.put("empresa", objSupeCampRechCargaDTO.getNombrEmpresa());
					reportParams.put("departamento", "");
					reportParams.put("provincia", "");
					reportParams.put("distrito", "");
					
				   /*if(esPlantilla){
						 if (lista.size() == 0) {
							  for(int i=0; i<lista.size(); i++ ){
							  //releDTO = new ReleDTO();
						    reportParams.put("alimentador", " ");
				            reportParams.put("subEstacion", " ");
				            reportParams.put("demandaMax", " ");
				            reportParams.put("demandaMed", " ");
				            reportParams.put("demandaMin", " ");
				            reportParams.put("potR", " ");
				            reportParams.put("releUmbralHz", " ");
				            reportParams.put("releUmbralS", " ");
				            reportParams.put("releDerivadaHZ", " ");
				            reportParams.put("releDerivadaHZS", " ");
				            reportParams.put("releDerivadaS", " ");
				          //  reportParams.put("idRele", (releDTO.getIdRele()));
				            reportParams.put("marca", " ");
				            reportParams.put("modelo", " ");
				            reportParams.put("serie", " ");
				            reportParams.put("subEstacion", " ");
				            reportParams.put("kV", " ");
				            reportParams.put("codInterrupcion", " ");
				            reportParams.put("fechaImplementacion", " ");
				            reportParams.put("etapa", " ");
				            //retorno.add(releDTO);
				          }
				        }*/
				   
		 		}
			   
		 
		        return reportParams;
		    } 
		  
		 public Map<String, Object> setearObservacionesActaInspeccion(InputStream reportStreamImage, InputStream reportStreamImageUkas, InputStream reportStreamImageSGS,
				 boolean esPlantilla, SupeCampRechCargaDTO objSupeCampRechCargaDTO) {
			  Map<String, Object> reportParams = new HashMap<String, Object>();
			 
			  		  
			  reportParams.put("rutaImagen", reportStreamImage);
			  reportParams.put("rutaImagenUkas", reportStreamImageUkas);
			  reportParams.put("rutaImagenSGS", reportStreamImageSGS);
				 
			  if(esPlantilla){
				
				 reportParams.put("obsAjustesRele", " ");
				 reportParams.put("obsHabilitacionRele", " ");
				 reportParams.put("obsReporteRele", " ");
				 reportParams.put("obsOtrasObs", " ");
				 reportParams.put("obsProtocoloRele", " ");
				 reportParams.put("obsHabilitacionRele", " ");
				 reportParams.put("obsNotasEmpresa", "");
				 reportParams.put("cargoTrabajador", "");
				 reportParams.put("nombreTrabajador", "");
				 reportParams.put("cargoOsi", "");
				 reportParams.put("nombreTrabajadorOsi", "");
				 reportParams.put("fechaHoraFirma", "");
			  }
			  else{
					 reportParams.put("obsAjustesRele", objSupeCampRechCargaDTO.getAjusteRele());
					 reportParams.put("obsHabilitacionRele", objSupeCampRechCargaDTO.getHabilitacionRele());
					 reportParams.put("obsReporteRele", objSupeCampRechCargaDTO.getReporteRele());
					 reportParams.put("obsProtocoloRele", objSupeCampRechCargaDTO.getProtocoloRele());
					 reportParams.put("obsOtrasObs", objSupeCampRechCargaDTO.getOtrasObservaciones());
					 reportParams.put("obsHabilitacionRele",objSupeCampRechCargaDTO.getHabilitacionRele());
					 reportParams.put("obsNotasEmpresa", objSupeCampRechCargaDTO.getNotasEmpresa());
					 reportParams.put("cargoTrabajador", objSupeCampRechCargaDTO.getCargoSupervisorEmpresa());
					 reportParams.put("nombreTrabajador", objSupeCampRechCargaDTO.getNombreSupervisorEmpresa());
					 reportParams.put("cargoOsi",objSupeCampRechCargaDTO.getCargoSupervisorOsinergmin());
					 reportParams.put("nombreTrabajadorOsi", objSupeCampRechCargaDTO.getNombreSupervisorOsinergmin());
					 reportParams.put("fechaHoraFirma", objSupeCampRechCargaDTO.getFechaFin() + " - " + objSupeCampRechCargaDTO.getHoraFin() + ":" + objSupeCampRechCargaDTO.getMinutoFin() + " horas");				  
			  }
			
		        return reportParams;
		    } 

		 
		 public byte[] listarDocumentoActaInspeccionBDDsr(InputStream reportJasper, InputStream reportJasper2 ,Map<String, Object> reportParams, Map<String, Object> reportParams2, List<ReleDTO> ltaDetalle,
				 boolean esPlantilla, List<DetaSupeCampRechCargaDTO> lDetaSupeCampRechCargaDTO) throws DocumentoAdjuntoException{
		        byte[] reporteBytes = null;
		        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
		        try{
		        	    
		          
		        	JRDataSource listaDS;
		        	
		        	if(esPlantilla)
		        		listaDS = new JRBeanCollectionDataSource(ltaDetalle);
		        	else
		        		listaDS = new JRBeanCollectionDataSource(lDetaSupeCampRechCargaDTO);
		        	
		            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper , reportParams, listaDS);
		            JasperPrint jasperPrint2 = JasperFillManager.fillReport(reportJasper2, reportParams2, new JREmptyDataSource());
		            jasperPrints.add(jasperPrint);
		            jasperPrints.add(jasperPrint2);
		           

		            if (jasperPrint != null) {  
		            	  //reporteBytes = JasperExportManager.exportReportToPdf(jasperPrints);   
		                JRPdfExporter exporter = new JRPdfExporter();
		                ByteArrayOutputStream out = new ByteArrayOutputStream();
		                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
		                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		                exporter.exportReport();
		                reporteBytes = out.toByteArray();

		           }
		            
		        } catch (Exception ex) {
		            LOG.error("Error listarDocumentoActaInspeccionBDDsr", ex);
		            throw new DocumentoAdjuntoException(ex.getMessage(),ex);
		        }
		        return reporteBytes;
		    }

		@Override
		public SupeCampRechCargaDTO obtenerRegistroEmpresa(
				Long idSupeCampRechCarga) throws SupeCampRechCargaException {
			  LOG.info("Neg listarEmpresasZona");
			  SupeCampRechCargaDTO retorno = null;
		        try {
		            retorno = empresasZonaDAO.obtenerRegistroEmpresa(idSupeCampRechCarga);

		        } catch (Exception ex) {
		            LOG.error("error listarEmpresasZona", ex);
		        }
		        return retorno;
		}
		
		@Override
		public List<EtapaDTO> listarEtapas(String idEmpresa, String anio, String idSubEstacion) {
			LOG.info("Neg listarEtapas");
			
			ObtenerEtapasInRO response = new ObtenerEtapasInRO();
	    	EtapaFilter etapaFilter = new EtapaFilter();
	    	etapaFilter.setAnio(Integer.parseInt(anio));
	    	etapaFilter.setIdEmpresa(idEmpresa);
	    	etapaFilter.setIdSubEstacion(Integer.parseInt(idSubEstacion));
	    	response.setFiltroEtapa(etapaFilter);
	    	ObtenerEtapasOutRO listEtapaOutRO = etapaEndpoint.buscarEtapas(response);
 
	        if (listEtapaOutRO.getRespuesta().getResultado().toString().equals(InvocationResult.SUCCESS.toString())){
	            return listEtapaOutRO.getListaEtapa();
	        	
	        }else{
	        	return null;
	        	 
	        }
			
		}
			//detalleConfiguracionReles
			
		@Override
		@Transactional
		public ConfiguracionRelesDTO registrarConfiguracionReles(ConfiguracionRelesDTO configuracionRele, UsuarioDTO usuarioDTO) throws SupeCampRechCargaException{
			ConfiguracionRelesDTO retorno = null;  
			try{
				retorno= empresasZonaDAO.registrarConfiguracionReles(configuracionRele, usuarioDTO);     
	      	   } catch(Exception e){
	          LOG.error("Error SupeCampRechCargaException: " + e);
	          throw new SupeCampRechCargaException(e);
	      }
			return retorno;
		}
		
		
		@Override
		@Transactional
		public DetaConfRelesDTO detalleConfiguracionReles(DetaConfRelesDTO detaConfigRel , UsuarioDTO usuarioDTO) throws SupeCampRechCargaException{
			DetaConfRelesDTO retorno = null; 
			try{
			retorno= empresasZonaDAO.detalleConfiguracionReles(detaConfigRel,usuarioDTO);
	      	   } catch(Exception e){
	          LOG.error("Error SupeCampRechCargaException: " + e);
	          throw new SupeCampRechCargaException(e);
	      }
			return retorno;
		}
		
		@Override
		@Transactional
		public Long existeConfiguracionRele(ConfiguracionRelesDTO configuracionReleDTO){
			Long retorno = 0L;  
			try{
				retorno= empresasZonaDAO.existeConfiguracionRele(configuracionReleDTO);     
	      	   } catch(Exception e){
	          LOG.error("Error existeConfiguracionRele: " + e);
	          //throw new SupeCampRechCargaException(e);
	      }
			return retorno;
		}
		
		@Override
		@Transactional(readOnly=false)
		public boolean borrarDetalleConfiguracionReles(ConfiguracionRelesDTO configuracionReleDTO)
		{
			List<DetaConfRelesDTO> listaDetalle=null;
			try{
				if(configuracionReleDTO.getIdConfiguracionReles()!=null){
					listaDetalle = empresasZonaDAO.findDetaConfReles(configuracionReleDTO);
					if(listaDetalle!=null){
						for(DetaConfRelesDTO detalle:listaDetalle){
							empresasZonaDAO.deleteDetaRele(detalle);
						}
					}
				}
					

				return true;
			}
			catch(Exception e){
				LOG.error("Error borrarDetalleConfiguracionReles: " + e);
				return false;
			}
		}
		
		@Override
		@Transactional
		public String validaConfiguracion(String idEmpresa, String anio)
		{
			List<SubEstacionDTO> lws = listarSubEstaciones(idEmpresa, anio, "0");
			
			List<ConfiguracionRelesDTO> lconf = empresasZonaDAO.listarSubEstacionConfiguracion(idEmpresa, anio);
			
			List<ConfiguracionRelesDTO> laux = new ArrayList<ConfiguracionRelesDTO>();
			ConfiguracionRelesDTO c2 = new ConfiguracionRelesDTO();
			String  X="";
			
			//estaciones.
			/*for(int i = 0; i<estaciones.size(); i++){
				c2.setIdSubestacion(new Long(estaciones.get(i).getIdSubEstacion()));
				
				lestacionWS.add(c2);
			}*/
			for(int i=0; i<lws.size(); i++){
				for(int j=0; j<lconf.size(); j++){
				
					if(lws.get(i).getIdSubEstacion() == lconf.get(j).getIdSubestacion()){
						c2.setIdSubestacion(new Long(lws.get(i).getIdSubEstacion()));
						laux.add(c2);
					}				
				}
			}
			
			if(lws.size() == laux.size())
				X=Constantes.ESTADO_ACTIVO; // Configurado
			else
				X=Constantes.ESTADO_INACTIVO; // No Configurado.
			
			return X;
		}
		@Override
		public void ReporteActaInspeccionRep(List<ReleDTO> lista,
				HttpServletRequest request, HttpServletResponse response,
				HttpSession session, boolean esPlantilla,
				SupeCampRechCargaDTO objSupeCampRechCargaDTO,
				List<DetaSupeCampRechCargaRepDTO> lDetaSupeCampRechCargaRepDTO) {
			// TODO Auto-generated method stub
			String rutaReportePrincipal="", rutaReportePrincipal2="";
			   List<ReleDTO> lReleDTO = new ArrayList<ReleDTO>();
			   lReleDTO = lista;
			   
			   InputStream reportJasper = null;
			   InputStream reportJasper2 = null;
			   InputStream reportStreamImage = null, reportStreamImage1 = null;
			   InputStream reportStreamImageUkas = null, reportStreamImageUkas1=null;
			   InputStream reportStreamImageSGS = null, reportStreamImageSGS1=null;
			   Map<String, Object> reportParams = null, reportParams2 = null;
			   ReleDTO registroDocumentoEjecucionMedida = null;
			   String rutaImagen = Constantes.RUTA_LOGO_PLANTILLA;
			   String rutaImagenUkas = Constantes.RUTA_LOGO_UKAS_PLANTILLA;
			   String rutaImagenSGS = Constantes.RUTA_LOGO_SGS_PLANTILLA;
			   LOG.info("abrir en ReporteActaInspeccion");
		        try {
		        	ServletContext context = session.getServletContext();
		        	ServletContext context2 = session.getServletContext();
		        	ServletContext context3 = session.getServletContext();
		        	ServletContext context4 = session.getServletContext();
		        	ServletContext context5 = session.getServletContext();
		        	ServletContext context6 = session.getServletContext();
		        	 LOG.info("INICIANDO LA CONSTRUCCION DEL DOCUMENTO ACTA DE INSPECCION");
		        	 
		        	 reportStreamImage = context.getResourceAsStream(rutaImagen);
		        	 reportStreamImageUkas = context2.getResourceAsStream(rutaImagenUkas);
		        	 reportStreamImageSGS = context3.getResourceAsStream(rutaImagenSGS);
		        	 reportStreamImage1 = context4.getResourceAsStream(rutaImagen);
		        	 reportStreamImageUkas1 = context5.getResourceAsStream(rutaImagenUkas);
		        	 reportStreamImageSGS1 = context6.getResourceAsStream(rutaImagenSGS);
		        	 
		        	 if(!esPlantilla){
		        		 rutaReportePrincipal = Constantes.RUTA_PLANTILLA_ACTA_INSPECCION;
		        	 }else{
		        		 rutaReportePrincipal = Constantes.RUTA_PLANTILLA_ACTA_INSPECCION_BD;
		        	 }
//		        	 rutaReportePrincipal = Constantes.RUTA_PLANTILLA_ACTA_INSPECCION_BD;
		        	 
		        	 reportJasper = context.getResourceAsStream(rutaReportePrincipal);
		        	 
		        	 rutaReportePrincipal2 = Constantes.RUTA_PLANTILLA_GENERAR_RESULTADOS_actainspeccionObs;
		        	 reportJasper2 = context.getResourceAsStream(rutaReportePrincipal2);
		        	 
		        	 reportParams = setearInformacionActaInspeccion(reportStreamImage, reportStreamImageUkas, reportStreamImageSGS, lReleDTO, esPlantilla, objSupeCampRechCargaDTO);
		        	 reportParams2 = setearObservacionesActaInspeccion(reportStreamImage1, reportStreamImageUkas1, reportStreamImageSGS1, esPlantilla, objSupeCampRechCargaDTO);
		        	 
		             byte[] x = listarDocumentoActaInspeccionBDDsrRep(reportJasper, reportJasper2,reportParams, reportParams2, lReleDTO, esPlantilla, lDetaSupeCampRechCargaRepDTO);

		             descargarReporteRele(x, response, esPlantilla);
		             LOG.info("SE REGISTRO EL DOCUMENTO DE ACTA DE INSPECCION");

		      
		        }catch (Exception ex) {
		            registroDocumentoEjecucionMedida = null;
		            LOG.error("OCURRIO UN ERROR AL GENERAR EL DOCUMENTO DE ACTA DE INSPECCION",ex);
		        }

			
		}

		private byte[] listarDocumentoActaInspeccionBDDsrRep(
				InputStream reportJasper, InputStream reportJasper2,
				Map<String, Object> reportParams,
				Map<String, Object> reportParams2, List<ReleDTO> ltaDetalle,
				boolean esPlantilla,
				List<DetaSupeCampRechCargaRepDTO> lDetaSupeCampRechCargaRepDTO) {
			byte[] reporteBytes = null;
	        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
	        try{
	        	    
	          
	        	JRDataSource listaDS;
	        	
	        	if(esPlantilla)
	        		listaDS = new JRBeanCollectionDataSource(ltaDetalle);
	        	else
	        		listaDS = new JRBeanCollectionDataSource(lDetaSupeCampRechCargaRepDTO);
	        	
	            JasperPrint jasperPrint = JasperFillManager.fillReport(reportJasper , reportParams, listaDS);
	            JasperPrint jasperPrint2 = JasperFillManager.fillReport(reportJasper2, reportParams2, new JREmptyDataSource());
	            jasperPrints.add(jasperPrint);
	            jasperPrints.add(jasperPrint2);
	           

	            if (jasperPrint != null) {  
	            	  //reporteBytes = JasperExportManager.exportReportToPdf(jasperPrints);   
	                JRPdfExporter exporter = new JRPdfExporter();
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
	                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
	                exporter.exportReport();
	                reporteBytes = out.toByteArray();

	           }
	            
	        } catch (Exception ex) {
	            LOG.error("Error listarDocumentoActaInspeccionBDDsr", ex);
	        }
	        return reporteBytes;
		}	    
		
		@Override
		@Transactional
		public List<EtapaDTO> listarEtapasConfiguradas(String idEmpresa, String anio, String idSubEstacion) {
			LOG.info("Neg listarEtapas configuradas");
			List<EtapaDTO> retorno = null;  
			try{
				
				ConfiguracionRelesDTO configuracion= empresasZonaDAO.findConfiguracionRele(idEmpresa, anio, idSubEstacion); 
				List<DetaConfRelesDTO> detalleConfiguracion = empresasZonaDAO.findDetaConfReles(configuracion);
				retorno = DetaConfRelesBuilder.toEtapaDto(detalleConfiguracion);				
	      	   } catch(Exception e){
	          LOG.error("Error SupeCampRechCargaException: " + e);
	      }
			return retorno;

			
		}

		@Override
		// Test
		//public List<DetaConfRelesDTO> findAllEtapaByEmpresaByAnio(String idEmpresa,String anio) {
		public List<DetaConfRelesDTO> findAllEtapaByEmpresaByAnio(String idEmpresa,String anio, Long idZona) {
		// Test
			LOG.info("(Capa Servicio) Lista todas las etapas configuradas por |empresa| -> "+idEmpresa+" |anio| -> "+anio );
			List<DetaConfRelesDTO> retorno = null;  
			try{
				List<DetaConfRelesDTO> detaConfTotal = new ArrayList<DetaConfRelesDTO>();
				// Test
				//List<ConfiguracionRelesDTO> configuracion= empresasZonaDAO.findAllCnfReles(idEmpresa, anio); 
				List<ConfiguracionRelesDTO> configuracion= empresasZonaDAO.findAllCnfReles(idEmpresa, anio, idZona);
				// Test
				if(configuracion!=null && configuracion.size()>0){
					for(ConfiguracionRelesDTO cnf:configuracion){
						List<DetaConfRelesDTO> detalleConfiguracion = empresasZonaDAO.findDetaConfReles(cnf);
						if(detalleConfiguracion!=null && detalleConfiguracion.size()>0){
							for(DetaConfRelesDTO detaByEach:detalleConfiguracion){
								detaByEach.setIdSubEstacion(cnf.getIdSubestacion());
								detaConfTotal.add(detaByEach);
							}
						}
						
					}
				}	
//				retorno = DetaConfRelesBuilder.toEtapaDto(detaConfTotal);	
				retorno = detaConfTotal;
	      	   } catch(Exception e){
	          LOG.error("Error Listar todas las etapas configuradas por empresa y anio: " + e);
	      }
			return retorno;
		}
		
		public static String concatenaListadoActividades(List<ConfiguracionRelesDTO> actividad) {
			String retorno="";
	        if(actividad!=null && actividad.size()>0){
	            String[] s = new String[actividad.size()];
	            int cont=0;
	            for(ConfiguracionRelesDTO maestra : actividad){s[cont]=maestra.getIdSubestacion().toString();cont++;}
	            retorno = StringUtils.join(s, ",");
	        }        
	        return retorno;
		}

		@Override
		@Transactional(readOnly=false)
		public boolean borrarConfiguracionReles(ConfiguracionRelesDTO configuracionRele) {
			try{
				if(configuracionRele.getIdConfiguracionReles()!=null){
					empresasZonaDAO.deleteCabeRele(configuracionRele);
				}
				return true;
			}
			catch(Exception e){
				LOG.error("Error borrar Cab Configuracion: " + e);
				return false;
			}
		}

		@Override
		@Transactional(readOnly=false)
		public boolean deleteConfig(ConfiguracionRelesDTO configuracionRele) {
			try {
				boolean exitoDeta = borrarDetalleConfiguracionReles(configuracionRele);
	       		//Se borra cabecera
	       		if(exitoDeta){
	       			boolean exitoCab = borrarConfiguracionReles(configuracionRele);
	       		}
	       		
	       	} catch (Exception e) {
	       		LOG.error("Error borrar Cab Configuracion: " + e);
				return false;
			}
       		
			return true;
		}		
 
}