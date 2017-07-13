package gob.osinergmin.inpsweb.service.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.rpc.processor.model.Model;

import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.ListaEmpresasVwServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ListaEmpresasVwDAO;
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.service.exception.ListaEmpresasVwException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ClienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListArchivoOutRO;
import gob.osinergmin.siged.rest.util.ArchivoInvoker;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

@Service("listaEmpresasVwServiceNeg")
public class ListaEmpresasVwServiceNegImpl implements ListaEmpresasVwServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(ListaEmpresasVwServiceNegImpl.class);
    @Inject
    private ExpedienteServiceNeg expedienteServiceNeg;
    @Inject
	private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private ListaEmpresasVwDAO empresaVwDAO;
    @Value("${siged.host.ws}")
    private String HOST_SIGED;
    @Value("${siged.host}")
    private String HOST;
    @Value("${ruta.plantilla.nuevo.expediente}")
    private String RUTA_PLANTILLA_NUEVO_EXPEDIENTE;
    @Override
	@Transactional(readOnly=true)
	public List<EmpresaViewDTO> findEmpresasVw(EmpresaViewFilter filtro) throws ListaEmpresasVwException {
		LOG.info("inicio findTanqueAspersor");
		List<EmpresaViewDTO> listado=null;
        try{
        	   listado = empresaVwDAO.findEmpresasVw(filtro);       
        	   } catch(Exception e){
            LOG.error("Error findTanqueAspersor: " + e);
            throw new ListaEmpresasVwException(e);
        }
		return listado;
	}

	@Override
	public ExpedienteDTO generarExpedienteRechazoCarga(DocumentoAdjuntoDTO archivoDTO ,String ruc,
			ExpedienteDTO expedienteDTO, Long idPersonalSiged)
			throws ListaEmpresasVwException {
		  ExpedienteDTO retorno = new ExpedienteDTO();
			try{     

				List<MaestroColumnaDTO> procesoList = maestroColumnaServiceNeg.buscarByDominioByAplicacionByCodigo(Constantes.DOMINIO_PROCESO_DSE,Constantes.APLICACION_INPS, Constantes.CODIGO_PROCESO_DSE);
		        int idProceso = 0;
		        if(procesoList!=null && procesoList.size()>0){
		        	idProceso = Integer.parseInt(procesoList.get(0).getDescripcion());
		        }
				
	        	ExpedienteInRO expedienteInRO = new ExpedienteInRO();
	            expedienteInRO.setProceso(idProceso);
	            expedienteInRO.setHistorico(Constantes.ESTADO_SIGED_NO);
	            expedienteInRO.setExpedienteFisico(Constantes.ESTADO_SIGED_NO);//Flag que indica si el expediente es físico (ingresado por MP) o si es virtual.
	            expedienteInRO.setDestinatario(idPersonalSiged.intValue());//En el excel dice que no es obligatorio //2582 avalos ruiz armando alexander
	            
	            List<MaestroColumnaDTO> tipoDocumentoList = maestroColumnaServiceNeg.findByDominioAplicacionDescripcion(Constantes.DOMINIO_DOCUMENTO,Constantes.APLICACION_INPS, Constantes.DOCUMENTO_OFICIO);
		        int tipoDocumento = 0;
		        if(tipoDocumentoList!=null && tipoDocumentoList.size()>0){
		        	tipoDocumento = Integer.parseInt(tipoDocumentoList.get(0).getCodigo());
		        }
	            
	            //OFICIO
	            DocumentoInRO documentoInRO = new DocumentoInRO();
	            documentoInRO.setCodTipoDocumento(tipoDocumento);//3 oficio
	            documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
	            documentoInRO.setAppNameInvokes("");
	            documentoInRO.setPublico(Constantes.ESTADO_SIGED_SI);
	            documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_NO);
	            documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
	            documentoInRO.setFirmado(Constantes.ESTADO_SIGED_NO);
	            documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
	            documentoInRO.setNroFolios(1);
	            documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_SI);
	            documentoInRO.setUsuarioCreador(idPersonalSiged.intValue());
	         
	            EmpresaViewDTO empresa = new EmpresaViewDTO();
	            String direEmprSupe=" ";
	                if(direEmprSupe==null) throw new ListaEmpresasVwException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
	              //CLIENTE
	                ClienteInRO cliente1 = new ClienteInRO();
	                int codiTipoIdent=1;
	                 
	                cliente1.setCodigoTipoIdentificacion(codiTipoIdent);
	                cliente1.setNroIdentificacion(ruc);
	                cliente1.setTipoCliente(3);
	                

	                DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
	                direccion1.setDireccion(" ");
	                direccion1.setDireccionPrincipal(true);
	                direccion1.setTelefono(" ");
	                direccion1.setUbigeo(Integer.parseInt("150104"));
	                direccion1.setEstado(Constantes.ESTADO_SIGED_ACTIVO);//A: Activo, N: No Activo	

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
	                String nombre = archivoDTO.getNombreArchivo();
	                List<File> files = new ArrayList<File>();
	                File someFile = new File(nombre);
	                
	                FileOutputStream fos = new FileOutputStream(someFile);
	                fos.write(archivoDTO.getRutaAlfrescoTmp());
	                fos.flush();
	                fos.close();
	                files.add(someFile);
	                //System.out.println("File: " + files.get(0).getAbsoluteFile());
	                Integer idArchivo;
	                ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.create(HOST+"/remote/expediente/crear", expedienteInRO, files);
	                if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
	                    LOG.info("Expediente: " + expedienteOutRO.getCodigoExpediente());
	                    LOG.info("Documento: " + expedienteOutRO.getIdDocumento());
	                    idArchivo=obtenerArchivoxExpediente(expedienteOutRO.getCodigoExpediente());

	                    for (ClienteOutRO cliente : expedienteOutRO.getClientes().getCliente()) {
	                        LOG.info("getCodigoCliente:"+cliente.getCodigoCliente());
	                        LOG.info("getCodigoTipoIdentificacion"+cliente.getCodigoTipoIdentificacion());
	                        LOG.info("getNumeroIdentificacion:"+cliente.getNumeroIdentificacion());
	                       
	                    }
	                    retorno.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
	                     retorno.setIdExpediente(Long.valueOf(idArchivo)); // idArchivo
	                } else {
	                    LOG.error("Ocurrio un error: " + expedienteOutRO.getMessage());
	                    retorno.setMensajeServicio("Error en Servicio SIGED: " + expedienteOutRO.getMessage());
	                }
	        }catch(Exception e){
	            LOG.error("error en generarExpedienteRechazoCarga",e);
	            throw new ListaEmpresasVwException(e.getMessage(),e);
	        }
	        return retorno;
	}

	@Override
	@Transactional
	public SupervisionRechCargaDTO registrarSupervisionRechazoCarga(
			SupervisionRechCargaDTO supervisionRechCargaDTO,
			UsuarioDTO usuarioDTO) throws ListaEmpresasVwException {
		 try{
      	    empresaVwDAO.registrarSupervisionRechazoCarga(supervisionRechCargaDTO, usuarioDTO);     
      	   } catch(Exception e){
          LOG.error("Error registrarSupervisionRechazoCarga: " + e);
          throw new ListaEmpresasVwException(e);
      }
		return null;
	}
	
	
	public Integer obtenerArchivoxExpediente(String nroExpediente){
		Integer idArchivo=0;
       ListArchivoOutRO listArchivoOutRO = ArchivoInvoker.listByNroExpediente(HOST+"/remote/archivo/listExp", nroExpediente);
       if (listArchivoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
         for (ArchivoOutRO archivo : listArchivoOutRO.getArchivo()) {
          idArchivo=archivo.getIdArchivo();
         }
       } else{
         System.out.println("Error: " + listArchivoOutRO.getMessage());
	}
       return idArchivo;
   }
	

}
