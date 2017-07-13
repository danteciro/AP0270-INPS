package gob.osinergmin.inpsweb.util;

import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ArchivoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteAprobarInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteRechazarInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteReenviarInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ArchivoListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ClienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ProcesoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.TipoDocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.UnidadOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.UsuarioOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.VersionArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListArchivoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListDocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListExpedienteConsultaOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListProcesoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListTipoDocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListUnidadOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.list.ListUsuarioOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.DocumentoConsultaOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.ExpedienteConsultaOutRO;
import gob.osinergmin.siged.rest.util.ArchivoInvoker;
import gob.osinergmin.siged.rest.util.DocumentoInvoker;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;
import gob.osinergmin.siged.rest.util.ProcesoInvoker;
import gob.osinergmin.siged.rest.util.TipoDocumentoInvoker;
import gob.osinergmin.siged.rest.util.UnidadInvoker;
import gob.osinergmin.siged.rest.util.UsuarioInvoker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author jpiro
 */
public class SigedRemoteTest {
    
    private static final String HOST = "http://11.160.121.132:8180/siged-rest-old";
    //private static final String HOST = "http://10.10.22.12:1600/siged-rest-old";
    private static final String HOST_NEW = "http://11.160.121.132:8180/siged-rest-new";
    //private static final String HOST_NEW = "http://10.10.22.12:1600/siged-rest-new";
    private static final String URL_EXPEDIENTE_CREAR = HOST + "/remote/expediente/crear";
    private static final String HOST_SIGED = "http://10.240.132.41:8180/siged/";
    
    
    public static void main(String[] args) throws IOException {
        //listarUnidades();
        //listarArchivosxExpediente("201500082949");
        //documentosxExpediente();
        //descargarArchivo();
        //documentosConVersiones();
        //listarUsuariosTest();
       //creacionExpediente();
        //listarExpedienteParaUsuario();//3068
        //agregarDocumento();
        //agregarArchivo();
        //listarTipoDoc();
        //listarProcesos();
    	//documentosConVersiones();
    	//reenviarExpediente();
    	//aprobarExpediente();
    	//rechazarExpediente();
    	//archivarExpediente();
    	reabrirExpediente();
    	
    }
    
    public static void reenviarExpediente(){
    	
    	ExpedienteReenviarInRO  expedienteRInRO = new ExpedienteReenviarInRO ();
    	  
    	expedienteRInRO.setNumeroExpediente("201600019563");
    	  
    	expedienteRInRO.setAsunto("Se reenviar Expediente prueba");
    	expedienteRInRO.setDestinatario(2582);
    	expedienteRInRO.setContenido("carga de informacion");
    	
    	ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.reenviar(HOST+"/remote/expediente/reenviar", expedienteRInRO, true);
    	
    	if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())){
    		System.out.println(expedienteOutRO.getResultCode());
    	    System.out.println(expedienteOutRO.getMessage());
    	}else{
    	    System.out.println("Error: "+expedienteOutRO.getResultCode()+"-Ocurrio un error: " + expedienteOutRO.getMessage());
    	}
    	     
    }
    
    public static void rechazarExpediente(){
    
	    ExpedienteRechazarInRO expedienteRInRO= new ExpedienteRechazarInRO();
	    expedienteRInRO.setNumeroExpediente("201600019563");
		expedienteRInRO.setAsunto("Se reenviar Expediente prueba");
		expedienteRInRO.setAccion(7);
		expedienteRInRO.setContenido("carga de informacion");
		
		ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.rechazar(HOST+"/remote/expediente/rechazar", expedienteRInRO);
		
		 if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))
		      System.out.println(expedienteOutRO.getMessage());
		    else
		      System.out.println("Error: "+expedienteOutRO.getResultCode()+"-Ocurrio un error: " + expedienteOutRO.getMessage());	
	
    
    }
    
    
    public static void aprobarExpediente(){
    	
    	ExpedienteAprobarInRO expedienteRInRO =new ExpedienteAprobarInRO();
    	
    	expedienteRInRO.setNumeroExpediente("201600034642");  	  
    	expedienteRInRO.setAsunto("Se para la aprobacion del Expediente prueba");
    	expedienteRInRO.setDestinatario(3600);
    	expedienteRInRO.setContenido("carga de informacion");
    	
    	ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.aprobar(HOST+"/remote/expediente/aprobar", expedienteRInRO,true);
		
		 if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))
		      System.out.println(expedienteOutRO.getMessage());
		    else
		      System.out.println("Error: "+expedienteOutRO.getResultCode()+"-Ocurrio un error: " + expedienteOutRO.getMessage());
	
	
    }
    
    public static void archivarExpediente(){
        
    	ExpedienteInRO expedienteRInRO= new ExpedienteInRO();
		expedienteRInRO.setObservacionArchivar("Se reenviar Expediente prueba");
		expedienteRInRO.setNroExpediente("201600013655");  
		
		ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.archivar(HOST+"/remote/expediente/archivar", expedienteRInRO);
		
		 if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))
		      System.out.println(expedienteOutRO.getMessage());
		    else
		      System.out.println("Error: "+expedienteOutRO.getResultCode()+"-Ocurrio un error: " + expedienteOutRO.getMessage());	
	
    
    }
    public static void reabrirExpediente(){
        
   
		
		ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.reabrir(HOST+"/remote/expediente/reabrir", "201600013655");
		
		 if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))
		      System.out.println(expedienteOutRO.getMessage());
		    else
		      System.out.println("Error: "+expedienteOutRO.getResultCode()+"-Ocurrio un error: " + expedienteOutRO.getMessage());	
	
    
    }
    
    public static void agregarDocumento(){
    ExpedienteInRO expedienteInRO = new ExpedienteInRO();

    expedienteInRO.setNroExpediente("201600034585");
    DocumentoInRO documentoInRO = new DocumentoInRO();

    documentoInRO.setCodTipoDocumento(Integer.valueOf(3));
    documentoInRO.setAsunto("Documento de principal");
    documentoInRO.setAppNameInvokes("");

    documentoInRO.setPublico(Character.valueOf('S'));
    documentoInRO.setEnumerado(Character.valueOf('N'));
    documentoInRO.setEstaEnFlujo(Character.valueOf('S'));
    documentoInRO.setFirmado(Character.valueOf('N'));
    documentoInRO.setDelExpediente(Character.valueOf('S'));
    documentoInRO.setNroFolios(Integer.valueOf(1));
    documentoInRO.setCreaExpediente(Character.valueOf('S'));

    //documentoInRO.setNumeroDocumento("55kk-os-1225");

    ClienteInRO cliente2 = new ClienteInRO();
    cliente2.setCodigoTipoIdentificacion(Integer.valueOf(3));
    cliente2.setNroIdentificacion("43427830");
    cliente2.setApellidoMaterno("LA CRUZ");
    cliente2.setApellidoPaterno("FLORIAN");
    cliente2.setNombre("CRISTHIAN");
    cliente2.setTipoCliente(Integer.valueOf(3));

    DireccionxClienteInRO direccion3 = new DireccionxClienteInRO();
    direccion3.setDireccion("direccion de prueba 3");
    direccion3.setDireccionPrincipal(true);
    direccion3.setTelefono("1234567");
    direccion3.setUbigeo(Integer.valueOf(150104));
    direccion3.setEstado(Character.valueOf('A'));

    List listaDirCliente2 = new ArrayList();
    listaDirCliente2.add(direccion3);
    DireccionxClienteListInRO direccionesCliente2 = new DireccionxClienteListInRO();
    direccionesCliente2.setDireccion(listaDirCliente2);

    cliente2.setDirecciones(direccionesCliente2);

    List listaClientes = new ArrayList();
    listaClientes.add(cliente2);

    ClienteListInRO clientes = new ClienteListInRO();
    clientes.setCliente(listaClientes);

    documentoInRO.setClientes(clientes);
    documentoInRO.setUsuarioCreador(Integer.valueOf(3600));
    expedienteInRO.setDocumento(documentoInRO);

    List files = new ArrayList();
   files.add(new File("C:/data/plantillas/GFHL/nuevo_expediente.txt"));
    //files.add(new File("C:/data/plantillas/GFHL/F1-GTH-PE-03 Solicitud de Vacaciones.doc"));

    DocumentoOutRO documentoOutRO = ExpedienteInvoker.addDocument(HOST+"/remote/expediente/agregarDocumento", expedienteInRO, files, false);
    if (documentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))
      System.out.println(documentoOutRO.getCodigoDocumento());
    else
      System.out.println("Error: "+documentoOutRO.getResultCode()+"-Ocurrio un error: " + documentoOutRO.getMessage());
  }    
    public static void listarTipoDoc(){
        ListTipoDocumentoOutRO listTipoDocumentoOutRO = null;
        listTipoDocumentoOutRO = TipoDocumentoInvoker.list(HOST+"/remote/tipodocumento/list");
            if (listTipoDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
              for (TipoDocumentoOutRO tipoDocumento : listTipoDocumentoOutRO.getTipoDocumento())
                System.out.println(tipoDocumento.getNombreTipoDocumento()+"-"+tipoDocumento.getIdTipoDocumento());
            }
            else{
              System.out.println("Ocurrio un error: " + listTipoDocumentoOutRO.getMessage());
          }
    }    
    public static void agregarArchivo(){
        DocumentoInRO documentoInRO = new DocumentoInRO();

        ArchivoInRO archivo1 = new ArchivoInRO();
        archivo1.setIdArchivo("file1");
        archivo1.setDescripcion("Descripcion del error encontrado");

        ArchivoInRO archivo2 = new ArchivoInRO();
        archivo2.setIdArchivo("file2");
        archivo2.setDescripcion("Otro archivo de error encontrado");

        List desc = new ArrayList(0);
        desc.add(archivo1);
        desc.add(archivo2);

        ArchivoListInRO archivosDesc = new ArchivoListInRO();
        archivosDesc.setArchivo(desc);
        documentoInRO.setArchivos(archivosDesc);
        documentoInRO.setIdDocumento(Integer.valueOf(2321592));
        documentoInRO.setUsuarioCreador(Integer.valueOf(3600));

        documentoInRO.setPublico(Character.valueOf('N'));
        documentoInRO.setEnumerado(Character.valueOf('S'));
        documentoInRO.setEstaEnFlujo(Character.valueOf('S'));
        documentoInRO.setFirmado(Character.valueOf('S'));
        documentoInRO.setDelExpediente(Character.valueOf('S'));
        documentoInRO.setNroFolios(Integer.valueOf(1));
        documentoInRO.setCreaExpediente(Character.valueOf('S'));

        List files = new ArrayList();
        files.add(new File("C:/data/plantillas/GFHL/nuevo_expediente2.txt"));

        boolean versionable = true;

        ListArchivoOutRO listaResultado = DocumentoInvoker.addArchivo(HOST+"/remote/documento/agregarArchivo", documentoInRO, files, versionable);
        if (listaResultado.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
          for (ArchivoOutRO archivo : listaResultado.getArchivo()) {
            System.out.println("Id Archivo: " + archivo.getIdArchivo());
            System.out.println("Nombre: " + archivo.getNombre());
          }
        } else {
          System.out.println("Mensaje " + listaResultado.getMessage());
          System.out.println("Codigo error: " + listaResultado.getErrorCode());
          for (ArchivoOutRO archivo : listaResultado.getArchivo()) {
            System.out.println("Id Archivo: " + archivo.getIdArchivo());
            System.out.println("Nombre: " + archivo.getNombre());
          }
        }
    }    
    public static void listarProcesos() {
    ListProcesoOutRO listProcesoOutRO = null;
    listProcesoOutRO = ProcesoInvoker.list(HOST+"/remote/proceso/list");
    if (listProcesoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
      for (ProcesoOutRO proceso : listProcesoOutRO.getProceso())
        System.out.println(proceso.getNombreProceso() +"-"+ proceso.getCodigoProceso());
    }
    else
      System.out.println("Ocurrio un error: " + listProcesoOutRO.getMessage());
    }
    public static void listarUnidades() {
        ListUnidadOutRO listUnidadOutRO = null;
        listUnidadOutRO = UnidadInvoker.list(HOST+"/siged-rest-old/remote/unidad/list");
        if (listUnidadOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
          for (UnidadOutRO unidad : listUnidadOutRO.getUnidad())
            System.out.println(unidad.getNombreUnidad() + " " + unidad.getCantidadProcesos());
        }
        else
          System.out.println("Ocurrio un error: " + listUnidadOutRO.getMessage());
      }    
    public static void listarProcesosPorArea(Integer idArea){
        ListProcesoOutRO listProcesoOutRO = null;
        listProcesoOutRO = ProcesoInvoker.listByArea(HOST+"/remote/proceso/listByArea", idArea);
        if (listProcesoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
          for (ProcesoOutRO proceso : listProcesoOutRO.getProceso())
            System.out.println(proceso.getNombreProceso());
        }
        else
          System.out.println("Ocurrio un error: " + listProcesoOutRO.getMessage());
      }
    public static void descargarArchivo(){
    Integer idArchivo = Integer.valueOf(3130275);
    try {
      File download = new File("C:/201500082993_24062015_MP.pdf");
      download = ArchivoInvoker.download(HOST+"/remote/archivo/descarga", idArchivo, download);
      System.out.println("download: " + download);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
    public static void documentosxExpediente(){
        boolean incluirArchivos = true;
        ListDocumentoOutRO listDocumentoOutRO = ExpedienteInvoker.documentos(HOST+"/remote/expediente/documentos", "201500082949", incluirArchivos);
        if (listDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
            List<DocumentoConsultaOutRO> listaDocumentos = listDocumentoOutRO.getDocumento();
            if ((listaDocumentos != null) && (!listaDocumentos.isEmpty()))
                for (DocumentoConsultaOutRO documento : listaDocumentos) {
                    System.out.println("Asunto: " + documento.getAsunto());
                    System.out.println("Autor: " + documento.getAutor());
                    System.out.println("NÃºmero: " + documento.getNroDocumento());
                    System.out.println("Propietario: " + documento.getNombrePropietario());
                    System.out.println("idTipoDocumento: " + documento.getIdTipoDocumento());
                    System.out.println("nombreTipoDocumento: " + documento.getNombreTipoDocumento());                    
                    if (incluirArchivos) {
                        ListArchivoOutRO archivosList = documento.getArchivos();
                        if (archivosList != null) {
                            List<ArchivoOutRO> archivos = archivosList.getArchivo();
                            if ((archivos != null) && (!archivos.isEmpty())) {
                                System.out.println("Archivos: ");
                                for (ArchivoOutRO archivo : archivos) {
                                    System.out.println("IdArchivo: " + archivo.getIdArchivo());
                                    System.out.println("Archivo: " + archivo.getNombre());
                                    if ((archivo.getDescripcion() != null) && (!archivo.getDescripcion().isEmpty()))
                                        System.out.println("Descripcion: " + archivo.getDescripcion());
                                }
                            }
                        }
                    }
                }
        }else{
            System.out.println("Ocurrio un error: " + listDocumentoOutRO.getMessage());
        }
    }
    public static void listarArchivosxExpediente(String nroExpediente){
        //String nroExpediente = "201100098323";
        ListArchivoOutRO listArchivoOutRO = ArchivoInvoker.listByNroExpediente(HOST+"/remote/archivo/listExp", nroExpediente);
        if (listArchivoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode()))
          for (ArchivoOutRO archivo : listArchivoOutRO.getArchivo()) {
            System.out.println("Id Archivo: " + archivo.getIdArchivo());
            System.out.println("Nombre: " + archivo.getNombre());
            System.out.println("Descripcion: " + archivo.getDescripcion());
            System.out.println("estadoDigitalizacion: " + archivo.getEstadoDigitalizacion());
            System.out.println("flagBloqueo: " + archivo.getFlagBloqueo());
            System.out.println("usuarioBloqueando: " + archivo.getUsuarioBloqueando());
            System.out.println("nombreUsuarioBloqueando: " + archivo.getNombreUsuarioBloqueando());
            System.out.println("rutaAlfresco: " + archivo.getRutaAlfresco());
            System.out.println("fechaBloqueo: " + archivo.getFechaBloqueo());
            System.out.println("msFechaBloqueo: " + archivo.getMsFechaBloqueo());
            System.out.println("fechaCreacion: " + archivo.getFechaCreacion());
            System.out.println("msFechaCreacion: " + archivo.getFechaCreacion());
            System.out.println("===============================================");
          }
        else
          System.out.println("Error: " + listArchivoOutRO.getMessage());
    }
    public static void documentosConVersiones(){
      ListDocumentoOutRO listDocumentoOutRO=null;
      
    listDocumentoOutRO = ExpedienteInvoker.documentosConVersiones(HOST+"/remote/expediente/documentosConVersiones", "201600034585");
    System.out.println("--->"+listDocumentoOutRO.getResultCode()+"--->"+listDocumentoOutRO.getMessage());
      
    if (listDocumentoOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
      //List listaDocumentos = listDocumentoOutRO.getDocumento();
      List<DocumentoConsultaOutRO> listaDocumentos= listDocumentoOutRO.getDocumento();
      if ((listaDocumentos != null) && (!listaDocumentos.isEmpty()))
        for (DocumentoConsultaOutRO documento : listaDocumentos) {
          System.out.println("Asunto: " + documento.getAsunto());
          System.out.println("Autor: " + documento.getAutor());
          System.out.println("NÃºmero: " + documento.getNroDocumento());
          System.out.println("Propietario: " + documento.getNombrePropietario());
 

          ListArchivoOutRO archivosList = documento.getArchivos();
          if (archivosList != null) {
            //List archivos = archivosList.getArchivo();
            List<ArchivoOutRO>archivos = archivosList.getArchivo();
            if ((archivos != null) && (!archivos.isEmpty())) {
              System.out.println("Archivos: ");
              for (ArchivoOutRO archivo : archivos) {
                System.out.println("IdArchivo: " + archivo.getIdArchivo());
                System.out.println("Archivo: " + archivo.getNombre());
                if ((archivo.getDescripcion() != null) && (!archivo.getDescripcion().isEmpty())) {
                  System.out.println("Descripcion: " + archivo.getDescripcion());
                }
                if ((archivo.getVersiones() != null) && (archivo.getVersiones().getVersion() != null))
                  for (VersionArchivoOutRO versionArchivoOutRO : archivo.getVersiones().getVersion()) {
                    System.out.println("Version: " + versionArchivoOutRO.getLabel());
                    System.out.println("Author: " + versionArchivoOutRO.getAutor());
                    System.out.println("Fecha: " + versionArchivoOutRO.getFechaCreacion());
                  }
              }
            }
          }
        }
        }
        else{
          System.out.println("Ocurrio un error: " + listDocumentoOutRO.getMessage());
        }
    }
    public static void listarUsuariosTest() {
        ListUsuarioOutRO listaUsuarios = UsuarioInvoker.listarTodos(HOST+"/remote/usuario/list");
        if (listaUsuarios.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
          List<UsuarioOutRO> usuarios = listaUsuarios.getUsuario();
          if ((usuarios != null) && (!usuarios.isEmpty()))
            for (UsuarioOutRO usu : usuarios)
              System.out.println(usu.getIdUsuario() + " " + usu.getNombres()+" - "+ usu.getIdUsuario());
        }
        else{
          System.out.println("Ocurrio un error en la invocacion");
        }
        System.out.println("--------------------------------");
    }
    public static void listarExpedienteParaUsuario() {
        Integer estado = Integer.valueOf(3);
        ListExpedienteConsultaOutRO listExpedienteConsulta = ExpedienteInvoker.listExpByUser(HOST+"/remote/expediente/listByUser", Integer.valueOf(2582), estado);
        System.out.println("--->"+listExpedienteConsulta.getResultCode());
        if (listExpedienteConsulta.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
          List<ExpedienteConsultaOutRO> listExpedientes = listExpedienteConsulta.getExpediente();
          if ((listExpedientes != null) && (!listExpedientes.isEmpty()))
            for (ExpedienteConsultaOutRO expediente : listExpedientes)
                System.out.println("Nro. Exp: " + expediente.getNroExpediente());
        }
        else
        {
          System.out.println("Ocurrio un error: " + listExpedienteConsulta.getMessage());
        }
    }
    public static void creacionExpediente() {

        ExpedienteInRO expedienteInRO = new ExpedienteInRO();

        //ZZZ Pruebas //40
        expedienteInRO.setProceso(42);//Obligatorio, si es expediente nuevo
        //expedienteInRO.setHistorico('N');
        //expedienteInRO.setExpedienteFisico('S');
        expedienteInRO.setDestinatario(3600);//En el excel dice que no es obligatorio //2582 avalos ruiz armando alexander
        //expedienteInRO.setNotificacion(1);

        //OFICIO
        DocumentoInRO documentoInRO = new DocumentoInRO();
        documentoInRO.setCodTipoDocumento(3);
        documentoInRO.setAsunto("ASUNTO DE PRUEBA GMD");
        documentoInRO.setAppNameInvokes("");
        //documentoInRO.setPublico('S');
        documentoInRO.setEnumerado('N');
       // documentoInRO.setEstaEnFlujo('S'); 
        documentoInRO.setFirmado('N');
        //documentoInRO.setDelExpediente('S');
        documentoInRO.setNroFolios(1);
        documentoInRO.setCreaExpediente('S');
        documentoInRO.setUsuarioCreador(3600);//2582 avalos ruiz armando alexander

        //ARCHIVO (NO ES OBLIGATORIO)
//        ArchivoInRO archivo1 = new ArchivoInRO();
//        archivo1.setIdArchivo("idArchivo1");
//        archivo1.setDescripcion("Descripcion del error encontrado");
//
//        ArchivoInRO archivo2 = new ArchivoInRO();
//        archivo2.setIdArchivo("idArchivo2");
//        archivo2.setDescripcion("Otro archivo de error encontrado");
//
//        List<ArchivoInRO> lstArchivoInRo = new ArrayList<ArchivoInRO>(0);
//        lstArchivoInRo.add(archivo1);
//        lstArchivoInRo.add(archivo2);
//
//        ArchivoListInRO archivosDesc = new ArchivoListInRO();
//        archivosDesc.setArchivo(lstArchivoInRo);
//        documentoInRO.setArchivos(archivosDesc);

        //CLIENTE
        ClienteInRO cliente1 = new ClienteInRO();
        cliente1.setCodigoTipoIdentificacion(3);
        cliente1.setNroIdentificacion("43427830");
        cliente1.setTipoCliente(3);//1: RECLAMANTE, 2: CONCESIONARIA, 3: OTROS	
        cliente1.setNombre("CRISTHIAN");//No es obligatorio si codigoTipoIdentificacion es 1.
        cliente1.setApellidoPaterno("FLORIAN");//No es obligatorio si codigoTipoIdentificacion es 1.
        cliente1.setApellidoMaterno("LA CRUZ");//No es obligatorio si codigoTipoIdentificacion es 1.
        //cliente1.setRazonSocial("RAZON DE PRUEBA");//Obligatorio si codigoTipoIdentificacion es 1.

        DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
        direccion1.setDireccion("PASAJE TENIENTE PAIVA 1");
        direccion1.setDireccionPrincipal(true);
        direccion1.setTelefono("4246750");
        direccion1.setUbigeo(150104);
        direccion1.setEstado('A');

//        DireccionxClienteInRO direccion2 = new DireccionxClienteInRO();
//        direccion2.setDireccion("PASAJE TENIENTE PAIVA 2");
//        direccion2.setDireccionPrincipal(false);
//        direccion2.setTelefono("4250703");
//        direccion2.setUbigeo(150105);
//        direccion2.setEstado('A');

        List<DireccionxClienteInRO> listaDirCliente1 = new ArrayList<DireccionxClienteInRO>();
        listaDirCliente1.add(direccion1);
//        listaDirCliente1.add(direccion2);
        DireccionxClienteListInRO direccionesCliente1 = new DireccionxClienteListInRO();
        direccionesCliente1.setDireccion(listaDirCliente1);
        cliente1.setDirecciones(direccionesCliente1);

        List<ClienteInRO> listaClientes = new ArrayList<ClienteInRO>();
        listaClientes.add(cliente1);
        ClienteListInRO clientes = new ClienteListInRO();
        clientes.setCliente(listaClientes);
        documentoInRO.setClientes(clientes);

        expedienteInRO.setDocumento(documentoInRO);

        List<File> files = new ArrayList<File>();
        files.add(new File("C:/data/plantillas/GFHL/F1-GTH-PE-03 Solicitud de Vacaciones.doc"));
        System.out.println("File: " + files.get(0).getAbsoluteFile());
        //files.add(new File("C:/text1.txt"));

        ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.create(URL_EXPEDIENTE_CREAR, expedienteInRO, files);
        if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
            System.out.println("Expediente: " + expedienteOutRO.getCodigoExpediente());
            System.out.println("Documento: " + expedienteOutRO.getIdDocumento());
            for (ClienteOutRO cliente : expedienteOutRO.getClientes().getCliente()) {
                System.out.println(cliente.getCodigoCliente());
                System.out.println(cliente.getCodigoTipoIdentificacion());
                System.out.println(cliente.getNumeroIdentificacion());
            }
        } else {
            System.out.println("Ocurrio un error: " + expedienteOutRO.getMessage());
        }
    }


}
