/**
* Resumen
* Objeto		: ArchivoServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz ArchivoServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     01/05/2016      Mario Dioses Fernandez          Construir formulario de envio a Mensajeria, consumiendo WS
* 
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.domain.builder.ArchivoBuilder;
import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
// htorress - RSIS 9 - Inicio
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.siged.rest.util.DocumentoInvoker;
import gob.osinergmin.siged.remote.rest.ro.out.query.DocumentoConsultaOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.ExpedienteConsultaOutRO;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import gob.osinergmin.siged.remote.rest.ro.in.ArchivoAnularInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ArchivoAnularOutRO;
import gob.osinergmin.siged.rest.util.NuevoDocumentoInvoker;
//htorress - RSIS 9 - Fin
// htorress - RSIS 15 - Inicio
import gob.osinergmin.siged.remote.rest.ro.out.CambiarFirmanteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ConsultaMensajeriaDocumentosItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.EnumerarDocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.EnviarMensajeriaOut;
import gob.osinergmin.siged.remote.rest.ro.out.FirmarEnumerarDocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ObtenerDestinatariosOut;
import gob.osinergmin.siged.remote.rest.ro.out.ObtenerOficinaRegionalOut;
import gob.osinergmin.siged.remote.rest.ro.out.ValidarDocumentoOut;
import gob.osinergmin.siged.remote.rest.ro.out.consultaMensajeriaDocumentosOut;
import gob.osinergmin.siged.remote.rest.ro.out.obtenerClienteDetalleOut;
// htorress - RSIS 15 - Fin
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ArchivoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.EnviarMensajeriaIn;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ArchivoListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListDocumentoOutRO;
import gob.osinergmin.siged.rest.util.ArchivoInvoker;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("archivoServiceNeg")
public class ArchivoServiceNegImpl implements ArchivoServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(ArchivoServiceNegImpl.class);
    
    @Value("${siged.host}")
    private String HOST;
    // htorress - RSIS 18 - Inicio
    @Value("${siged.host.ws}")
    private String HOST_SIGED;
    // htorress - RSIS 18 - Fin
    
    //@Override
    public List<DocumentoAdjuntoDTO> enviarDocumentosSiged(ExpedienteDTO expediente,List<DocumentoAdjuntoDTO> documentos){
        LOG.info("enviarDocumentosSiged");
        List<DocumentoAdjuntoDTO> retorno=new ArrayList<DocumentoAdjuntoDTO>();
        try{
            ///////////////////////////
            //documentoInRO.setDelExpediente(Character.valueOf('S'));
            DocumentoInRO documentoInRO = new DocumentoInRO();
            documentoInRO.setCodTipoDocumento(170);
            documentoInRO.setEnumerado('S');
            documentoInRO.setEstaEnFlujo('S');
            documentoInRO.setFirmado('N');
            documentoInRO.setAsunto(expediente.getAsuntoSiged());
            documentoInRO.setCreaExpediente('N');//NO CREAMOS EXPEDIENTE
            documentoInRO.setNroFolios(1);
            documentoInRO.setPublico('N');
            documentoInRO.setUsuarioCreador(2582);//aavalosr 2582
            documentoInRO.setAppNameInvokes("");

            ClienteInRO cliente1 = new ClienteInRO();
            cliente1.setCodigoTipoIdentificacion(2);
            cliente1.setNroIdentificacion("43427830");
            cliente1.setNombre("LUIS ALFREDO");
            cliente1.setApellidoPaterno("MALDONADO");
            cliente1.setApellidoMaterno("CERVANTES");
            cliente1.setTipoCliente(3);

            DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
            direccion1.setDireccion("direccion de prueba 1");
            direccion1.setDireccionPrincipal(true);
            direccion1.setTelefono("12345");
            direccion1.setUbigeo(Integer.valueOf(150104));
            direccion1.setEstado(Character.valueOf('A'));

            DireccionxClienteInRO direccion2 = new DireccionxClienteInRO();
            direccion2.setDireccion("direccion de prueba 2");
            direccion2.setDireccionPrincipal(false);
            direccion2.setTelefono("123456");
            direccion2.setUbigeo(Integer.valueOf(150105));
            direccion2.setEstado(Character.valueOf('A'));

            List listaDirCliente1 = new ArrayList();
            listaDirCliente1.add(direccion1);
            listaDirCliente1.add(direccion2);
            DireccionxClienteListInRO direccionesCliente1 = new DireccionxClienteListInRO();
            direccionesCliente1.setDireccion(listaDirCliente1);
            cliente1.setDirecciones(direccionesCliente1);

            ClienteInRO cliente2 = new ClienteInRO();
            cliente2.setCodigoTipoIdentificacion(1);
            cliente2.setNroIdentificacion("20147980486");
            cliente2.setTipoCliente(3);

            DireccionxClienteInRO direccion3 = new DireccionxClienteInRO();
            direccion3.setDireccion("direccion de prueba 3");
            direccion3.setDireccionPrincipal(true);
            direccion3.setTelefono("1234567");
            direccion3.setUbigeo(150104);
            direccion3.setEstado('A');

//            DireccionxClienteInRO direccion4 = new DireccionxClienteInRO();
//            direccion4.setDireccion("direccion de prueba 4");
//            direccion4.setDireccionPrincipal(false);
//            direccion4.setTelefono("12345678");
//            direccion4.setUbigeo(Integer.valueOf(150105));
//            direccion4.setEstado(Character.valueOf('A'));

            List listaDirCliente2 = new ArrayList();
            listaDirCliente2.add(direccion3);
            //listaDirCliente2.add(direccion4);
            DireccionxClienteListInRO direccionesCliente2 = new DireccionxClienteListInRO();
            direccionesCliente2.setDireccion(listaDirCliente2);

            cliente2.setDirecciones(direccionesCliente2);

            List listaClientes = new ArrayList();
//            listaClientes.add(cliente1);
            listaClientes.add(cliente1);

            ClienteListInRO clientes = new ClienteListInRO();
            clientes.setCliente(listaClientes);

            documentoInRO.setClientes(clientes);

//            ArchivoInRO archivo1 = new ArchivoInRO();
//            archivo1.setIdArchivo("file1");
//            archivo1.setDescripcion("Descripcion del error encontrado");
//
//            ArchivoInRO archivo2 = new ArchivoInRO();
//            archivo2.setIdArchivo("file2");
//            archivo2.setDescripcion("Otro archivo de error encontrado");

//            List desc = new ArrayList(0);
//            desc.add(archivo1);
//            desc.add(archivo2);

//            ArchivoListInRO archivosDesc = new ArchivoListInRO();
//            archivosDesc.setArchivo(desc);

//            documentoInRO.setArchivos(archivosDesc);
            
            ExpedienteInRO expedienteInRO = new ExpedienteInRO();
            expedienteInRO.setNroExpediente(expediente.getNumeroExpediente());//nroExpediente
            expedienteInRO.setDocumento(documentoInRO);

            List files = new ArrayList();
            files.add(new File("C:/test.txt"));

            DocumentoOutRO documentoOutRO = ExpedienteInvoker.addDocument(HOST+"/remote/expediente/agregarDocumento", expedienteInRO, files, false);
            LOG.info("documentoOutRO.getResultCode()-->"+documentoOutRO.getResultCode());
            if (!documentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
                throw new Exception("Problemas al conectarse a los Servicios de SIGED");
            }
            
            //////////////////////
//            LOG.info("listDocumentoOutRO.getResultCode()-->"+listDocumentoOutRO.getResultCode());
//            if (listDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
//                retorno=ArchivoBuilder.toListDocumentoAdjuntoDto(listDocumentoOutRO.getDocumento());
//            }
        }catch(Exception e){
            LOG.error("Error enviarDocumentosSiged",e);
        }
        return retorno;
    }
    
    @Override
    public List<DocumentoAdjuntoDTO> listarDocumentosSiged(String numeroExpediente){	
	
        LOG.info("listarDocumentosSiged");
        List<DocumentoAdjuntoDTO> retorno=new ArrayList<DocumentoAdjuntoDTO>();
        try{
        	boolean incluirArchivos = true;
            ListDocumentoOutRO listDocumentoOutRO = ExpedienteInvoker.documentos(HOST+"/remote/expediente/documentos", numeroExpediente, incluirArchivos);
            
            LOG.info("listDocumentoOutRO.getResultCode()-->"+listDocumentoOutRO.getResultCode());
            if (listDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
                retorno=ArchivoBuilder.toListDocumentoAdjuntoDto(listDocumentoOutRO.getDocumento());
                // htorress - RSIS 9 - Inicio
                Collections.reverse(retorno);
                // htorress - RSIS 9 - Inicio
            }
        }catch(Exception e){
            LOG.error("Error listarDocumentosSiged",e);
        }
        return retorno;
    }
    
    @Override
    public File descargarArchivoSiged(DocumentoAdjuntoDTO archivo){
        File retorno=null;
        try{
        retorno = new File(archivo.getNombreArchivo());// File("C:/hola.txt");
        retorno=ArchivoInvoker.download(HOST+"/remote/archivo/descarga", Integer.valueOf(archivo.getIdArchivo().intValue()), retorno);
        }catch(Exception e){
            LOG.error("Error en descargarArchivoSiged",e);
        }
        return retorno;
    }
    // htorress - RSIS 13 - Inicio
    @Override
    public List<DocumentoAdjuntoDTO> consultaVersionesArchivoSIGED(String  nroExpediente, String idDocumento){
    	LOG.info("consultaVersionesArchivoSIGED-->"+idDocumento);
    	List<DocumentoAdjuntoDTO> retorno=new ArrayList<DocumentoAdjuntoDTO>();
    try{
        DocumentoConsultaOutRO documentoEncontrado=null;
        
        ListDocumentoOutRO listDocumentoOutRO = ExpedienteInvoker.documentosConVersiones(HOST+"/remote/expediente/documentosConVersiones", nroExpediente);
                  
        if (listDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {       
        	
        	for(DocumentoConsultaOutRO documento : listDocumentoOutRO.getDocumento()){
        		System.out.println("...>idDocumento="+documento.getIdDocumento());
        		if(documento.getIdDocumento().toString().equals(idDocumento)){
        			documentoEncontrado=documento;
        			break;
        		}
        	}
        	System.out.println("--->documentoEncontrado="+documentoEncontrado);
        	if(documentoEncontrado!=null){
	        	List<DocumentoConsultaOutRO> listDocuOutRO = new ArrayList<DocumentoConsultaOutRO>();
	        	listDocuOutRO.add(documentoEncontrado);
	            retorno=ArchivoBuilder.toListDocumentoAdjuntoDto(listDocuOutRO);
        	}
           
        }else{
              System.out.println("Ocurrio un error: " + listDocumentoOutRO.getMessage());
            }
    
    }catch(Exception e){
        LOG.error("Error listarDocumentosSiged",e);
    }
    return retorno;
    }
    // htorress - RSIS 13 - Fin
    // htorress - RSIS 9 - Inicio
	@Override
	@Transactional
	public DocumentoAdjuntoDTO agregarArchivoSiged(DocumentoAdjuntoDTO doc, Long idPersonalSiged, boolean tipoCarga)throws DocumentoAdjuntoException{
		LOG.info("agregarArchivoSiged");
		DocumentoAdjuntoDTO docEnviarCargaSiged=null;
		DocumentoInRO documentoInRO = new DocumentoInRO();
		try{

	        documentoInRO.setIdDocumento(Integer.valueOf((doc.getIdDocumento()).intValue()));
	        documentoInRO.setUsuarioCreador((idPersonalSiged).intValue()); // Verificar <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	        LOG.info("Id Personal Siged :-->" + idPersonalSiged);
	        
	        documentoInRO.setPublico(Constantes.ESTADO_SIGED_NO);
	        documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_SI);
	        documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
	        documentoInRO.setFirmado(Constantes.ESTADO_SIGED_SI);
	        documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
	        documentoInRO.setNroFolios(1);
	        documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_SI);
	
	        
	        byte[] mapByte=doc.getArchivoAdjunto();
	        
	        if(mapByte==null) throw new ExpedienteException("No existe Archivo fisico para el documento: "+doc.getNombreArchivo()+".", null);
	        LOG.info("mapByte-->"+mapByte);
	        //file
	        File someFile = new File(doc.getNombreArchivo());
	        InputStream is = null;
	        if (mapByte!=null) {
	            FileOutputStream fos = new FileOutputStream(someFile);
	            fos.write(mapByte);
	            fos.flush();
	            fos.close();
	            is = FileUtils.openInputStream(someFile);      
	        }   
	        
	        List<File> files =new ArrayList();     
	        files.add(someFile);
	
	        ListArchivoOutRO listaResultado = DocumentoInvoker.addArchivo(HOST+"/remote/documento/agregarArchivo", documentoInRO, files, tipoCarga);
	        docEnviarCargaSiged = new DocumentoAdjuntoDTO();
	        if (listaResultado.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
	        	docEnviarCargaSiged.setEstado(String.valueOf(listaResultado.getResultCode()));
	        	for (ArchivoOutRO archivo : listaResultado.getArchivo()) {
		        	docEnviarCargaSiged.setIdArchivo(archivo.getIdArchivo().longValue());
		        	LOG.info("getIdArchivo:"+archivo.getIdArchivo());
		        }
	        } else {
	        	LOG.info("Mensaje " + listaResultado.getMessage());
	        	LOG.info("Codigo error: " + listaResultado.getErrorCode());
	        	docEnviarCargaSiged.setComentario(listaResultado.getMessage());
	        	docEnviarCargaSiged.setEstado(String.valueOf(listaResultado.getResultCode()));
	            for (ArchivoOutRO archivo : listaResultado.getArchivo()) {
	            	LOG.info("Id Archivo: " + archivo.getIdArchivo());
	            	LOG.info("Nombre: " + archivo.getNombre());
	            }
	        }
	        
		}catch(Exception e){
            LOG.error("error en agregarArchivoSiged",e);
            throw new DocumentoAdjuntoException(e.getMessage(), null);
        }
		return docEnviarCargaSiged;
	}
	// htorress - RSIS 9 - Fin
	// htorress - RSIS 15 - Inicio
	@Override
	@Transactional
	public DocumentoAdjuntoDTO enumerarDocumentoSiged(String numeroExpediente, Integer idDocumento, Integer idPersonalSiged) throws DocumentoAdjuntoException {
		LOG.info("enumerarDocumentoSiged");
		DocumentoAdjuntoDTO resultado=new DocumentoAdjuntoDTO();
		try{

			EnumerarDocumentoOutRO firmEnumOut = NuevoDocumentoInvoker.enumerarDocumento(HOST_SIGED+"remote/nuevodocumento/enumerarDocumento", numeroExpediente, idDocumento, idPersonalSiged);        	

	        if (firmEnumOut.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
	        	resultado.setEstado(String.valueOf(firmEnumOut.getResultCode()));
		        LOG.info("resultCode:"+firmEnumOut.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + firmEnumOut.getMessage());
	        	LOG.info("Codigo error: " + firmEnumOut.getErrorCode());
	        	resultado.setComentario(firmEnumOut.getMessage());
	        	resultado.setEstado(String.valueOf(firmEnumOut.getResultCode()));
	            	//LOG.info("Id Archivo: " + archivo.getIdArchivo());
	            	//LOG.info("Nombre: " + archivo.getNombre());
	        }
        }catch(Exception e){
            LOG.error("Error enumerarDocumentoSiged",e);
        }
		return resultado;
	}
	
	@Override
	@Transactional
	public DocumentoAdjuntoDTO cambiarFirmante(String numeroExpediente, Integer idDocumento, Integer idPersonalSiged) throws DocumentoAdjuntoException {
		LOG.info("cambiarFirmante");
		DocumentoAdjuntoDTO resultado=new DocumentoAdjuntoDTO();
		try{

			CambiarFirmanteOutRO cambiarFirmanteOutRO = NuevoDocumentoInvoker.cambiarFirmante(HOST_SIGED+"remote/nuevodocumento/cambiarFirmante", numeroExpediente, idDocumento, idPersonalSiged);        	

	        if (cambiarFirmanteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
	        	resultado.setEstado(String.valueOf(cambiarFirmanteOutRO.getResultCode()));
		        LOG.info("resultCode:"+cambiarFirmanteOutRO.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + cambiarFirmanteOutRO.getMessage());
	        	LOG.info("Codigo error: " + cambiarFirmanteOutRO.getErrorCode());
	        	resultado.setComentario(cambiarFirmanteOutRO.getMessage());
	        	resultado.setEstado(String.valueOf(cambiarFirmanteOutRO.getResultCode()));
	            	//LOG.info("Id Archivo: " + archivo.getIdArchivo());
	            	//LOG.info("Nombre: " + archivo.getNombre());
	        }
        }catch(Exception e){
            LOG.error("Error cambiarFirmante",e);
        }
		return resultado;
	}
	
	@Override
	@Transactional
	public DocumentoAdjuntoDTO firmarEnumerarDocumentoSiged(String numeroExpediente, Integer idDocumento, Integer idPersonalSiged) throws DocumentoAdjuntoException {
		LOG.info("firmarEnumerarDocumentoSiged");
		DocumentoAdjuntoDTO resultado=new DocumentoAdjuntoDTO();
		try{

      	    FirmarEnumerarDocumentoOutRO firmEnumOut = NuevoDocumentoInvoker.firmarEnumerarDocumento(HOST_SIGED+"remote/nuevodocumento/firmarEnumerarDocumento", numeroExpediente, idDocumento, idPersonalSiged);        	

	        if (firmEnumOut.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
	        	resultado.setEstado(String.valueOf(firmEnumOut.getResultCode()));
		        LOG.info("resultCode:"+firmEnumOut.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + firmEnumOut.getMessage());
	        	LOG.info("Codigo error: " + firmEnumOut.getErrorCode());
	        	resultado.setComentario(firmEnumOut.getMessage());
	        	resultado.setEstado(String.valueOf(firmEnumOut.getResultCode()));
	            	//LOG.info("Id Archivo: " + archivo.getIdArchivo());
	            	//LOG.info("Nombre: " + archivo.getNombre());
	        }
        }catch(Exception e){
            LOG.error("Error firmarEnumerarDocumentoSiged",e);
        }
		return resultado;
	}
	
	@Override
	public DocumentoAdjuntoDTO anularArchivoSiged(DocumentoAdjuntoDTO doc, String motivo, String numeroExpediente, Long idPersonalSiged) throws DocumentoAdjuntoException {
		LOG.info("anularArchivoSiged");
		DocumentoAdjuntoDTO resultado=new DocumentoAdjuntoDTO();
        try{
      		ArchivoAnularInRO anularInRO = new ArchivoAnularInRO();
      	    anularInRO.setNroExpediente(numeroExpediente);
      	    anularInRO.setIdDocumento(doc.getIdDocumento().intValue());
      	    anularInRO.setIdArchivo(doc.getIdArchivo().intValue());
      	    anularInRO.setIdUsuarioAnulacion(idPersonalSiged.intValue());
      	    anularInRO.setMotivo(motivo);
      	    ArchivoAnularOutRO anularOut = NuevoDocumentoInvoker.anularArchivo(HOST_SIGED+"remote/nuevodocumento/anularArchivo", anularInRO);        	
	        if (anularOut.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
	        	resultado.setEstado(String.valueOf(anularOut.getResultCode()));
		        LOG.info("resultCode:"+anularOut.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + anularOut.getMessage());
	        	LOG.info("Codigo error: " + anularOut.getErrorCode());
	        	resultado.setComentario(anularOut.getMessage());
	        	resultado.setEstado(String.valueOf(anularOut.getResultCode()));
	        }
        }catch(Exception e){
            LOG.error("Error anularArchivoSiged",e);
        }
		return resultado;
	}
	// htorress - RSIS 15 - Fin
	/* OSINE_SFS-480 - RSIS 03 - Inicio */
	@Override
	public ValidarDocumentoOut validarDocumentoSIGED(String esTipo, Long iIdDoc) throws DocumentoAdjuntoException { 
		LOG.info("validarDocumentoSIGED");
		ValidarDocumentoOut retorno=null;
		try{
			retorno = NuevoDocumentoInvoker.validarDocumento(HOST_SIGED+"remote/nuevodocumento/validarDocumento", esTipo, iIdDoc.intValue());
			if (retorno.getResultCode()!=null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {	        	
	        	LOG.info("resultCode: " + retorno.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + retorno.getMensaje());
	        	LOG.info("Codigo error: " + retorno.getErrorCode());
	        }
		}catch(Exception e){
            LOG.error("Error validarDocumentoSIGED", e);
        }
		return retorno;
	} 
	
	@Override
	public ObtenerOficinaRegionalOut listarOficinaRegionalSIGED(Long IdPersonalSIGED) throws DocumentoAdjuntoException { 
		LOG.info("listarOficinaRegionalSIGED");
		ObtenerOficinaRegionalOut retorno=null;
		try{
			retorno = NuevoDocumentoInvoker.obtenerOficinaRegional(HOST_SIGED+"remote/nuevodocumento/obtenerOficinaRegional", IdPersonalSIGED.intValue());
			if (retorno.getResultCode()!=null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {	        	
	        	LOG.info("resultCode: " + retorno.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + retorno.getMessage());
	        	LOG.info("Codigo error: " + retorno.getErrorCode());
	        }
		}catch(Exception e){
            LOG.error("Error listarOficinaRegionalSIGED", e);
        }
		return retorno;
	}
	
	@Override
	public ObtenerDestinatariosOut listarDestinatarioSIGED(String filtro) throws DocumentoAdjuntoException { 
		LOG.info("listarDestinatarioSIGED");
		ObtenerDestinatariosOut retorno=null;
		try{
			retorno = NuevoDocumentoInvoker.obtenerDestinatariosOut(HOST_SIGED+"remote/nuevodocumento/obtenerDestinatarios", filtro);
			if (retorno.getResultCode()!=null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {	        	
	        	LOG.info("resultCode: " + retorno.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + retorno.getMessage());
	        	LOG.info("Codigo error: " + retorno.getErrorCode());
	        }
		}catch(Exception e){
            LOG.error("Error listarDestinatarioSIGED", e);
        }
		return retorno;
	}
	
	@Override
	public obtenerClienteDetalleOut listarDetalleDestinatarioSIGED(Long nroIdentificacion, boolean estadoFlujo, String nroExpediente) throws DocumentoAdjuntoException { 
		LOG.info("listarDetalleDestinatarioSIGED");
		obtenerClienteDetalleOut retorno=null;
		try{			
			retorno = NuevoDocumentoInvoker.obtenerClienteDetalle(HOST_SIGED+"remote/nuevodocumento/obtenerClienteDetalle", nroIdentificacion.toString(), estadoFlujo, nroExpediente);
			if (retorno.getResultCode()!=null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {	        	
	        	LOG.info("resultCode: " + retorno.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + retorno.getMessage());
	        	LOG.info("Codigo error: " + retorno.getErrorCode());
	        }
		}catch(Exception e){
            LOG.error("Error listarDetalleDestinatarioSIGED", e);
        }
		return retorno;
	}
	
	@Override
	public EnviarMensajeriaOut registrarMensajeriaSIGED(EnviarMensajeriaIn paramMensajeria) throws DocumentoAdjuntoException { 
		LOG.info("registrarMensajeriaSIGED");
		EnviarMensajeriaOut retorno=new EnviarMensajeriaOut();
		try{	
			retorno=NuevoDocumentoInvoker.enviarMensajeria(HOST_SIGED+"remote/nuevodocumento/enviarMensajeria", paramMensajeria);
			if (retorno.getResultCode()!=null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {	        	
	        	LOG.info("resultCode: " + retorno.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + retorno.getMessage());
	        	LOG.info("Codigo error: " + retorno.getErrorCode());
	        }
		}catch(Exception e){
            LOG.error("Error registrarMensajeriaSIGED", e);
        }
		return retorno;
	}
	
	@Override
	public ExpedienteConsultaOutRO findExpedienteSIGED(String nroExpediente) throws DocumentoAdjuntoException { 
		LOG.info("findExpedienteSIGED");
		ExpedienteConsultaOutRO retorno=null;
		try{
			retorno = ExpedienteInvoker.buscar(HOST+"/remote/expediente/find", nroExpediente);			
			if (retorno.getResultCode()!=null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {	        	
	        	LOG.info("resultCode: " + retorno.getResultCode());
	        } else {
	        	LOG.info("Mensaje " + retorno.getMessage());
	        	LOG.info("Codigo error: " + retorno.getErrorCode());
	        }
		}catch(Exception e){
            LOG.error("Error findExpedienteSIGED", e);
        }
		return retorno;
	}
	/* OSINE_SFS-480 - RSIS 03 - Fin */
	/* OSINE_SFS-480 - RSIS 06 - Inicio */
	@Override
    public List<ConsultaMensajeriaDocumentosItemOut> listarArchivosMensajeria(String idMensajeria, Long idPersonalSiged){	
	
        LOG.info("listarArchivosMensajeria");
        List<ConsultaMensajeriaDocumentosItemOut> listaRetorno=new ArrayList<ConsultaMensajeriaDocumentosItemOut>();
        try{
            consultaMensajeriaDocumentosOut listDocumentoOutRO = NuevoDocumentoInvoker.consultaMensajeriaDocumentos(HOST_SIGED+"/remote/nuevodocumento/consultaMensajeriaDocumentos", idMensajeria,idPersonalSiged.intValue());
            
            LOG.info("listarArchivosMensajeria.getResultCode()-->"+listDocumentoOutRO.getResultCode());
            if (listDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
            	listaRetorno.addAll(listDocumentoOutRO.getConsultaMensajeriaDocumentosItemOut());
            }
        }catch(Exception e){
            LOG.error("Error listarArchivosMensajeria",e);
        }
        return listaRetorno;
    }
	/* OSINE_SFS-480 - RSIS 06 - Fin */
}
