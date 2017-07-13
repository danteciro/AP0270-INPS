package gob.osinergmin.inpsweb.service.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gob.osinergmin.inpsweb.service.business.EmpresaSupervisionViewServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EmpresaSupervisionViewDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.EmpresaSupervisionViewException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("EmpresaSupervisionViewService")
public class EmpresaSupervisionViewServiceImpl implements EmpresaSupervisionViewServiceNeg{

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EmpresaSupervisionViewServiceImpl.class);	
	@Inject
	private EmpresaSupervisionViewDAO empresaSupervisionViewDAO;
	@Inject
	private ExpedienteDAO expedienteDAO;
	@Inject
	private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
	@Value("${siged.host}")
    private String HOST;
    
	
	
	@Override
	@Transactional(readOnly=true)
	public List<EmpresaViewDTO> findEmpresas(EmpresaViewFilter empresaViewFilter) throws EmpresaSupervisionViewException {
		return empresaSupervisionViewDAO.findEmpresas(empresaViewFilter);
	}
	

	@Override
	@Transactional
	public DocumentoAdjuntoDTO adjuntarInformeSiged( DocumentoAdjuntoDTO documentoAdjuntoDTO, ExpedienteDTO expedienteDTO, EmpresaViewFilter empresaViewFilter ) throws EmpresaSupervisionViewException {
		DocumentoAdjuntoDTO docEnviarSiged = null;
		try {
			byte[] mapByte = documentoAdjuntoDTO.getArchivoAdjunto();
			if (mapByte == null) {
	            throw new ExpedienteException("No existe Archivo fisico para el documento: " + documentoAdjuntoDTO.getNombreArchivo() + ".", null);
	        }
			LOG.info("mapByte-->" + mapByte);
			File someFile = new File(documentoAdjuntoDTO.getNombreArchivo());
		    InputStream is = null;
	        if (mapByte != null) {
	            FileOutputStream fos = new FileOutputStream(someFile);
	            fos.write(mapByte);
	            fos.flush();
	            fos.close();
	            is = FileUtils.openInputStream(someFile);
	        }
	        List<File> files = new ArrayList<File>();
	        files.add(someFile);
	        List<MaestroColumnaDTO> tipoDocumentoList = maestroColumnaServiceNeg.findByDominioAplicacionDescripcion(Constantes.DOMINIO_DOCUMENTO,Constantes.APLICACION_INPS, Constantes.DOCUMENTO_INFORME);
	        int tipoDocumento = 0;
	        if(tipoDocumentoList!=null && tipoDocumentoList.size()>0){
	        	tipoDocumento = Integer.parseInt(tipoDocumentoList.get(0).getCodigo());
	        }
	        
	        ExpedienteInRO expedienteInRO = new ExpedienteInRO();
	        	DocumentoInRO documentoInRO = new DocumentoInRO();
            	documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            	documentoInRO.setAppNameInvokes("");
            	documentoInRO.setPublico(Character.valueOf('S'));
            	documentoInRO.setEnumerado(Character.valueOf('N'));
            	documentoInRO.setEstaEnFlujo(Character.valueOf('S'));
            	documentoInRO.setFirmado(Character.valueOf('N'));
            	documentoInRO.setDelExpediente(Character.valueOf('S'));
            	documentoInRO.setNroFolios(Integer.valueOf(1));
            	documentoInRO.setCreaExpediente(Character.valueOf('N'));
            	documentoInRO.setAppNameInvokes("");
            	documentoInRO.setCodTipoDocumento(tipoDocumento);
            expedienteInRO.setNroExpediente(expedienteDTO.getNumeroExpediente());  
            expedienteInRO.setDocumento(documentoInRO);
			ClienteInRO cliente = new ClienteInRO();
			    cliente.setCodigoTipoIdentificacion(Integer.valueOf(1));
			    cliente.setNroIdentificacion(empresaViewFilter.getRuc());
			    cliente.setTipoCliente(Integer.valueOf(3));
            DireccionxClienteInRO direccion = new DireccionxClienteInRO();
			    direccion.setDireccion(" ");
			    direccion.setDireccionPrincipal(true);
			    direccion.setTelefono(" ");
			    direccion.setUbigeo(Integer.valueOf(150104));
			    direccion.setEstado(Character.valueOf('A'));
			    List<DireccionxClienteInRO> listaDirCliente = new ArrayList<DireccionxClienteInRO>();
			    listaDirCliente.add(direccion);
			DireccionxClienteListInRO direccionesCliente = new DireccionxClienteListInRO();
			    direccionesCliente.setDireccion(listaDirCliente);
			    cliente.setDirecciones(direccionesCliente);
			    List<ClienteInRO> listaClientes = new ArrayList<ClienteInRO>();
			    listaClientes.add(cliente);
			    ClienteListInRO clientes = new ClienteListInRO();
			    clientes.setCliente(listaClientes);
			    documentoInRO.setClientes(clientes);
			    documentoInRO.setUsuarioCreador((int)expedienteDTO.getPersonal().getIdPersonalSiged().longValue());
			    
			DocumentoOutRO documentoOutRO = ExpedienteInvoker.addDocument(HOST + "/remote/expediente/agregarDocumento", expedienteInRO, files, false);
			docEnviarSiged = new DocumentoAdjuntoDTO();
			docEnviarSiged.setIdDocumento(Long.valueOf(documentoOutRO.getCodigoDocumento()));
		    docEnviarSiged.setEstado(String.valueOf(documentoOutRO.getResultCode()));
            if (!(documentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))) {
               docEnviarSiged.setComentario(documentoOutRO.getMessage());
               LOG.info("Error: " + documentoOutRO.getResultCode() + "-Ocurrio un error: " + documentoOutRO.getMessage());
               throw new DocumentoAdjuntoException("Error en Servicio SIGED: " + documentoOutRO.getMessage(), null);
            }
        } catch (Exception e) {
            LOG.error("error en enviarDocOrdenServicioSiged", e);
            throw new EmpresaSupervisionViewException( e.getMessage() , null); 
        }
        return docEnviarSiged;
	}
	
}
