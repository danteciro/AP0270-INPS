/**
* Resumen		
* Objeto		: ExpedienteServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz ExpedienteServiceNeg
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Luis García Reyna               Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones
* OSINE_SFS-480     27/05/2016      Luis García Reyna               Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
* OSINE_SFS-480     01/06/2016      Luis García Reyna               Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
* OSINE791          19/08/2016      Jose Herrera                    Agregar Sigla de Division al Numero Orden Servicio
* OSINE_SFS-791     14/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos 
* OSINE791–RSIS41   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD. 
*/ 

package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
//htorress - RSIS 1 y 2 - Inicio
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
//htorress - RSIS 1 y 2 - Fin
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadObliSubTipoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.mdicommon.domain.ui.OrdenServicioFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.ProcesoFilter;
import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DireccionUnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ExpedienteServiceNeg;
import gob.osinergmin.inpsweb.service.business.HstEstadoLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.SerieServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisoraEmpresaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadObliSubTipoServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadOrganicaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
// htorress - RSIS 1 y 2 - Inicio
import gob.osinergmin.inpsweb.service.dao.PersonalAsignadoDAO;
import gob.osinergmin.inpsweb.service.dao.SupervisionDAO;
// htorress - RSIS 1 y 2 - Fin
//htorress - RSIS 18 - Inicio
import gob.osinergmin.siged.remote.rest.ro.in.DevolverExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DevolverExpedienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteAprobarInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteReenviarInRO;
import gob.osinergmin.siged.rest.util.NuevoDocumentoInvoker;
//htorress - RSIS 18 - Fin
import gob.osinergmin.inpsweb.service.exception.ExpedienteException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.CorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.HstEstadoLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import gob.osinergmin.siged.remote.enums.InvocationResult;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteActualizarTipoProcesoIn;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ClienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ConsultarMensajeriaDatosCargoOut;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteActualizarTipoProcesoOut;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ListaMensajeriaItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.ListaMensajeriaOut;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author jpiro
 */
@Service("expedienteServiceNeg")
public class ExpedienteServiceNegImpl implements ExpedienteServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteServiceNegImpl.class);

    @Inject
    private ExpedienteDAO expedienteDAO;
    @Inject
    private EstadoProcesoDAO estadoProcesoDAO;
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    @Inject
    private ArchivoServiceNeg archivoServiceNeg;
    // htorress - RSIS 1 y 2 - Inicio
    @Inject
    private PersonalAsignadoDAO personalAsignadoDAO;
    // htorress - RSIS 1 y 2 - Fin
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    @Inject
    private SupervisionDAO supervisionDAO;
    @Inject
    private SerieServiceNeg serieServiceNeg;
    @Inject
    private UnidadOrganicaServiceNeg unidadOrganicaServiceNeg;
    @Inject
    private HstEstadoLevantamientoServiceNeg hstEstadoLevantamientoServiceNeg;
    @Inject
    private OrdenServicioServiceNeg ordenServicioServiceNeg;
    @Inject
    private SupervisoraEmpresaServiceNeg supervisoraEmpresaServiceNeg;
    // htorress - RSIS 18 - Inicio
    @Value("${siged.host.ws}")
    private String HOST_SIGED;
    // htorress - RSIS 18 - Fin
    @Value("${siged.host}")
    private String HOST;
    @Value("${ruta.plantilla.nuevo.expediente}")
    private String RUTA_PLANTILLA_NUEVO_EXPEDIENTE;
    @Inject
    MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
    @Inject
    private CorreoServiceNeg correoServiceNeg;
    @Inject
    private UnidadObliSubTipoServiceNeg unidadSeleccionMuestral;
    /* OSINE_SFS-Ajustes - mdiosesf - Fin */
    @Inject
    private DireccionUnidadSupervisadaServiceNeg direccionUnidadSupervisadaServiceNeg;
    
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;

    @Override
    @Transactional(readOnly = true)
    public List<ExpedienteDTO> listarExpediente(ExpedienteFilter filtro) {
        LOG.info("listarExpediente");
        List<ExpedienteDTO> retorno = null;
        try {
            EstadoProcesoDTO estadoProcesoDto = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)).get(0);
            filtro.setIdEstadoProcesoDerivado(estadoProcesoDto.getIdEstadoProceso());
            // htorress - RSIS 1 y 2 - Inicio
            if (filtro.getIdentificadorRol() != null && !filtro.getIdentificadorRol().equals("")) {

                if ((filtro.getIdentificadorRol().equals(Constantes.CONSTANTE_ROL_JEFE_UNIDAD)) || (filtro.getIdentificadorRol().equals(Constantes.CONSTANTE_ROL_JEFE_REGIONAL))) {
                    PersonalAsignadoDTO personal = new PersonalAsignadoDTO();
                    personal.setIdPersonalJefe(new PersonalDTO());
                    personal.getIdPersonalJefe().setIdPersonal(filtro.getIdPersonal());
                    List<PersonalAsignadoDTO> personalAsignado = null;
                    personalAsignado = personalAsignadoDAO.find(personal);
                    filtro.setIdsPersonalAsignado(new ArrayList<PersonalAsignadoDTO>());
                    filtro.getIdsPersonalAsignado().addAll(personalAsignado);
                }
            }
            // htorress - RSIS 1 y 2 - Fin
            retorno = expedienteDAO.findForGrid(filtro);
        } catch (Exception ex) {
            LOG.error("Error en listarExpediente", ex);
        }
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public PersonalDTO derivar(List<ExpedienteDTO> expedientes, Long idPersonalOri, Long idPersonalDest, String motivoReasignacion, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.info("derivar");
        PersonalDTO retorno = new PersonalDTO();
        retorno.setIdPersonal(idPersonalDest);
        try {
            EstadoProcesoDTO estadoProcesoDto = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)).get(0);
            List<ExpedienteDTO> regExpedientes = new ArrayList<ExpedienteDTO>();
            for (ExpedienteDTO expediente : expedientes) {
                ExpedienteDTO expedienteDTO = expedienteDAO.cambiarEstadoProceso(expediente.getIdExpediente(), idPersonalDest, idPersonalOri, idPersonalDest, estadoProcesoDto.getIdEstadoProceso(), motivoReasignacion, usuarioDTO);
                regExpedientes.add(expedienteDTO);

                //Invocar a Servicio SIGED - Reenviar
                // ExpedienteDTO expedienteSiged= reenviarExpedienteSIGED(expedienteDTO, idPersonalDest, motivoReasignacion,false);
            }
            retorno.setExpedientes(regExpedientes);
        } catch (Exception e) {
            LOG.error("Error derivar", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpedienteDTO> listarDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException {
        return expedienteDAO.findDerivadosByIdPersonal(filtro);
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteDTO asignarUnidadSupervisada(ExpedienteDTO expedienteDto, UsuarioDTO usuarioDTO) throws ExpedienteException {
        return expedienteDAO.asignarUnidadSupervisada(expedienteDto, usuarioDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpedienteDTO asignarOrdenServicio(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, UsuarioDTO usuarioDTO, String sigla) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();
        try {
            EstadoProcesoDTO estadoProcesoExp = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_ASIGNADO)).get(0);
            expedienteDto.setEstadoProceso(estadoProcesoExp);

            EstadoProcesoDTO estadoProcesoOS = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            expedienteDto.getOrdenServicio().setEstadoProceso(estadoProcesoOS);
            /* OSINE791 - RSIS1 - Inicio */
            retorno = expedienteDAO.asignarOrdenServicio(expedienteDto, codigoTipoSupervisor, usuarioDTO, sigla);
            /* OSINE791 - RSIS1 - Fin */
        } catch (Exception e) {
            LOG.error("error en asignarOrdenServicio", e);
            throw new ExpedienteException(e.getMessage(), e);
        }

        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public ExpedienteDTO generarExpedienteOrdenServicio(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, UsuarioDTO usuarioDTO, String sigla) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();
        try {
            EstadoProcesoDTO estadoProcesoExp = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_ASIGNADO)).get(0);
            expedienteDto.setEstadoProceso(estadoProcesoExp);

            EstadoProcesoDTO estadoProcesoOS = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            expedienteDto.getOrdenServicio().setEstadoProceso(estadoProcesoOS);

            retorno = expedienteDAO.generarExpedienteOrdenServicio(expedienteDto, codigoTipoSupervisor, usuarioDTO, sigla);
        } catch (Exception e) {
            LOG.error("error en generarExpedienteOrdenServicio", e);
            throw new ExpedienteException(e.getMessage(), e);
        }

        return retorno;
    }

    @Override
    public ExpedienteDTO generarExpedienteSiged(ExpedienteDTO expedienteDTO, Long idDirccionUnidadSuprvisada, Long idPersonalSiged) throws ExpedienteException {
        LOG.info("generarExpedienteSiged");
        ExpedienteDTO retorno = new ExpedienteDTO();
        try {
            ExpedienteInRO expedienteInRO = new ExpedienteInRO();
            expedienteInRO.setProceso(expedienteDTO.getFlujoSiged().getCodigoSiged().intValue());//Obligatorio, si es expediente nuevo test 257
            expedienteInRO.setHistorico(Constantes.ESTADO_SIGED_NO);
            expedienteInRO.setExpedienteFisico(Constantes.ESTADO_SIGED_NO);//Flag que indica si el expediente es físico (ingresado por MP) o si es virtual.
            expedienteInRO.setDestinatario(idPersonalSiged.intValue());//En el excel dice que no es obligatorio //2582 avalos ruiz armando alexander
            
            //OFICIO
            //Obtener el tipo documento con dominio TIP_DOC_NUE_EXP_SIG
            //List<MaestroColumnaDTO> listTipoDoc=maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIP_DOC_NUE_EXP_SIG, Constantes.APLICACION_INPS);
            List<MaestroColumnaDTO> listTipoDoc=maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_TIP_DOC_ASIG_EXP_SIG, Constantes.APLICACION_INPS);
	        Integer codTipoDoc=(!CollectionUtils.isEmpty(listTipoDoc)) ? new Integer(listTipoDoc.get(0).getCodigo()) : null;
            if(codTipoDoc==null) {
            	throw new ExpedienteException("No existe Tipo Documento para Expediente Siged.", null);
            }
            DocumentoInRO documentoInRO = new DocumentoInRO();
            documentoInRO.setCodTipoDocumento(codTipoDoc);            
            documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            documentoInRO.setAppNameInvokes("");
            documentoInRO.setPublico(Constantes.ESTADO_SIGED_SI);
            // htorress - RSIS 18 - Inicio
            documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 18 - Fin
            documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
            // htorress - RSIS 18 - Inicio
            documentoInRO.setFirmado(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 18 - Fin
            documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setNroFolios(1);
            documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setUsuarioCreador(idPersonalSiged.intValue());
            
//            if (expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada() == null && expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada() == null) {
//                throw new ExpedienteException("No existe Identificador de Empresa Supervisada.", null);
//            }
//            EmpresaSupDTO emprSupe = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada()));
//            List<BusquedaDireccionxEmpSupervisada> listarDireccionEmpresaSupervisada = empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada());
//            BusquedaDireccionxEmpSupervisada direEmprSupe = listarDireccionEmpresaSupervisada != null && listarDireccionEmpresaSupervisada.size() > 0 ? listarDireccionEmpresaSupervisada.get(0) : null;
//
//            if (direEmprSupe == null) {
//                throw new ExpedienteException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
//            }
            
            DireccionUnidadSupervisadaDTO direUnid=null;
            if(idDirccionUnidadSuprvisada!=null){
                LOG.info("buscando direccion de unidad supervisada="+idDirccionUnidadSuprvisada);
                direUnid=direccionUnidadSupervisadaServiceNeg.findById(idDirccionUnidadSuprvisada);
            }
            if(direUnid==null){
                throw new ExpedienteException("No existe Direcci&oacute;n para la Unidad Supervisada.", null);
            }
                
            //CLIENTE
            ClienteInRO cliente1 = new ClienteInRO();
//            int codiTipoIdent = 3;
//            if (emprSupe.getRuc() != null) {
//                codiTipoIdent = 1;
//            } else if (emprSupe.getTipoDocumentoIdentidad() != null && emprSupe.getTipoDocumentoIdentidad().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI)) {
//                codiTipoIdent = 2;
//            }
            if(StringUtil.isEmpty(expedienteDTO.getUnidadSupervisada().getRuc())){
                throw new ExpedienteException("Umpresa Supervisada no tiene RUC.", null);
            }
            
            int codiTipoIdent=1;
            cliente1.setCodigoTipoIdentificacion(codiTipoIdent);
//            cliente1.setNroIdentificacion((codiTipoIdent == 1) ? emprSupe.getRuc() : emprSupe.getNroIdentificacion());
            cliente1.setNroIdentificacion(expedienteDTO.getUnidadSupervisada().getRuc());
            cliente1.setTipoCliente(3);
//            if (codiTipoIdent == 1) {
//                cliente1.setRazonSocial(emprSupe.getRazonSocial());//Obligatorio si codigoTipoIdentificacion es 1.
//            } else {
//                cliente1.setNombre(emprSupe.getNombre());//No es obligatorio si codigoTipoIdentificacion es 1.
//                cliente1.setApellidoPaterno(emprSupe.getApellidoPaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
//                cliente1.setApellidoMaterno(emprSupe.getApellidoMaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
//            }
            cliente1.setRazonSocial(expedienteDTO.getUnidadSupervisada().getNombreUnidad());
            
            DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
//            direccion1.setDireccion(direEmprSupe.getDireccionCompleta());
            direccion1.setDireccion(direUnid.getDireccionCompleta());
            direccion1.setDireccionPrincipal(true);
//            direccion1.setTelefono(emprSupe.getTelefono());
//            direccion1.setUbigeo(Integer.parseInt(
//                    Utiles.padLeft(direEmprSupe.getIdDepartamento().toString(), 2, '0')
//                    + Utiles.padLeft(direEmprSupe.getIdProvincia().toString(), 2, '0')
//                    + Utiles.padLeft(direEmprSupe.getIdDistrito().toString(), 2, '0')
//            ));
            direccion1.setTelefono(direUnid.getTelefono1());
            direccion1.setUbigeo(Integer.parseInt(
                    Utiles.padLeft(direUnid.getDepartamento().getIdDepartamento().toString(), 2, '0')
                    + Utiles.padLeft(direUnid.getProvincia().getIdProvincia().toString(), 2, '0')
                    + Utiles.padLeft(direUnid.getDistrito().getIdDistrito().toString(), 2, '0')
            ));
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

	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	        UsuarioDTO usuario = Constantes.getUsuarioDTO(request);
            
            //generando pdf
            PersonalDTO personal = null;
            List<UnidadOrganicaDTO> unidadUO = null;
            
            List<PersonalDTO> listPersona = personalServiceNeg.findPersonal(new PersonalFilter(usuario.getCodigo(), null));
            if (listPersona != null && listPersona.size() > 0) {
            	personal = listPersona.get(0);
            }
            PersonalFilter filtro = new PersonalFilter();
            filtro.setIdPersonal(personal.getIdPersonal());
            filtro.setFlagDefault(Constantes.ESTADO_ACTIVO);
            List<PersonalDTO> personalUnidOrgDefault = personalServiceNeg.findPersonal(filtro);
            if (personalUnidOrgDefault != null && personalUnidOrgDefault.size() > 0 && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault() != null && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica() != null) {
            	unidadUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica().getIdUnidadOrganica(), null));
            }
            
            String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
            
            List<BusquedaUnidadSupervisadaDTO> registro = new ArrayList<BusquedaUnidadSupervisadaDTO>();
            List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
            UnidadSupervisadaFilter filtroDatosUnidadOperativa = new UnidadSupervisadaFilter();
            filtroDatosUnidadOperativa.setCadCodigoOsinerg(expedienteDTO.getUnidadSupervisada().getCodigoOsinergmin());
            if(listaDireUoDl!=null && listaDireUoDl.size()!=0) { filtroDatosUnidadOperativa.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo()); }
            
            registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtroDatosUnidadOperativa);
            String razonSocial = "";
            String codigoOsinergmin = "";
            String actividad = "";
            String codigoPersonalSupervisoraEmpresa = "";
            String descripcionSupervisoraEmpresa = "";
            String proceso = "";
            
            if(registro != null && registro.size() != 0){
            	BusquedaUnidadSupervisadaDTO unidadSupervisada = new BusquedaUnidadSupervisadaDTO();
            	unidadSupervisada = registro.get(0);
            	razonSocial = unidadSupervisada.getNombreUnidad() == null ? "" : unidadSupervisada.getNombreUnidad();
            	codigoOsinergmin = unidadSupervisada.getCodigoOsinergmin() == null ? "" : unidadSupervisada.getCodigoOsinergmin();
            	actividad = unidadSupervisada.getActividad() == null ? "" : unidadSupervisada.getActividad();
            }
            
            PersonalFilter filterSupervisoraEmpresa = new PersonalFilter();
            try{
            	filterSupervisoraEmpresa.setIdSupervisoraEmpresa(expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            }catch(Exception ex){
            	filterSupervisoraEmpresa.setIdSupervisoraEmpresa(Long.parseLong("0"));
            }
            List<PersonalDTO> listPersonalUnidadSupervisora = personalServiceNeg.findPersonal(filterSupervisoraEmpresa);
            if(listPersonalUnidadSupervisora != null && listPersonalUnidadSupervisora.size() != 0){
            	codigoPersonalSupervisoraEmpresa = "(CODIGO " + listPersonalUnidadSupervisora.get(0).getNombreUsuarioSiged() + ")";
            }
            
            try{
            	SupervisoraEmpresaDTO asignadoSupeEmp = supervisoraEmpresaServiceNeg.getById(expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
            	descripcionSupervisoraEmpresa = asignadoSupeEmp.getRazonSocial() == null ? asignadoSupeEmp.getNombreConsorcio() : asignadoSupeEmp.getRazonSocial();
            }catch(Exception ex){
            	descripcionSupervisoraEmpresa = "";
            }
            
            ProcesoFilter procesoFilter = new ProcesoFilter();
            procesoFilter.setIdentificador(Constantes.PROCESO_PREOPERATIVO);
            List<ProcesoDTO> listaProceso =  serieServiceNeg.listarProceso(procesoFilter);
            try{
            	for (ProcesoDTO procesoDTO : listaProceso) {
            		if(procesoDTO.getIdProceso() == expedienteDTO.getProceso().getIdProceso()){
            			proceso = procesoDTO.getDescripcion();
            		}
            	}
            }catch(Exception ex){
            	proceso = "";
            }
            
            String rutaDocumento = Constantes.RUTA_EXPEDIENTES_TEMPORALES + Constantes.NOMBRE_FORMATO_ARCHIVO_ORDEN_SERVICIO + ".pdf";
            generarDocumentoOrdenServicio(rutaDocumento,usuario,expedienteDTO);
            
            //fin creacion de documento
            
            List<File> files = new ArrayList<File>();
            //files.add(new File(RUTA_PLANTILLA_NUEVO_EXPEDIENTE + "nuevo_expediente.txt"));
            files.add(new File(rutaDocumento));
            System.out.println("File: " + files.get(0).getAbsoluteFile());

            ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.create(HOST + "/remote/expediente/crear", expedienteInRO, files);
            if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                LOG.info("Expediente: " + expedienteOutRO.getCodigoExpediente());
                LOG.info("Documento: " + expedienteOutRO.getIdDocumento());
                for (ClienteOutRO cliente : expedienteOutRO.getClientes().getCliente()) {
                    LOG.info("getCodigoCliente:" + cliente.getCodigoCliente());
                    LOG.info("getCodigoTipoIdentificacion" + cliente.getCodigoTipoIdentificacion());
                    LOG.info("getNumeroIdentificacion:" + cliente.getNumeroIdentificacion());
                }
                retorno.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
                //volviendo a versionar el archivo con el numero de expediente
                List<DocumentoAdjuntoDTO> listado=archivoServiceNeg.listarDocumentosSiged(expedienteOutRO.getCodigoExpediente());
                for (DocumentoAdjuntoDTO documentoAdjuntoDTO : listado) {
					if(documentoAdjuntoDTO.getIdDocumento() == Long.parseLong(expedienteOutRO.getIdDocumento().toString())){
						DocumentoAdjuntoDTO archivoEnviado= new DocumentoAdjuntoDTO();
						DocumentoAdjuntoDTO archivo = new DocumentoAdjuntoDTO();
			            archivo.setTipoDocumentoCarga(new MaestroColumnaDTO());
			            
			            expedienteDTO.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
			            generarDocumentoOrdenServicio(rutaDocumento, usuario, expedienteDTO);
			            
			            //obteniendo bytes
			            File archivoGenerado = new File(rutaDocumento);
			            int size = (int) archivoGenerado.length();
			            byte[] bytes = new byte[size];
			            try {
			                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(archivoGenerado));
			                buf.read(bytes, 0, bytes.length);
			                buf.close();
			            } catch (FileNotFoundException e) {
			                System.out.println("Error archivo no encontrado");
			            } catch (IOException e) {
			            	System.out.println("Error al transformar archivo");
			            }
			            
			            archivo.setArchivoAdjunto(bytes);
			            archivo.setNombreArchivo(Constantes.NOMBRE_FORMATO_ARCHIVO_ORDEN_SERVICIO + "_1.pdf");
			            archivo.setIdDocumento(documentoAdjuntoDTO.getIdDocumento());
			            archivo.getTipoDocumentoCarga().setCodigo(Constantes.TIPO_DOCUMENTO_ASIGNACION_TRABAJO);
			            archivo.setIdArchivo(documentoAdjuntoDTO.getIdArchivo());
			            boolean tipoCarga =true;
		            	archivoEnviado=archivoServiceNeg.agregarArchivoSiged(archivo, Constantes.getIDPERSONALSIGED(request), tipoCarga);
		                if(archivoEnviado.getEstado().equals(Constantes.FLAG_REGISTRADO_SI)){
		                	System.out.println("Se versiono el archivo.");
		                }else{
		                	System.out.println("Error al versionar el archivo.");
		                }
					}
				}
                
            } else {
                LOG.error("Ocurrio un error: " + expedienteOutRO.getMessage());
                retorno.setMensajeServicio("Error en Servicio SIGED: " + expedienteOutRO.getMessage());
                //throw new ExpedienteException("Error en Servicio SIGED: " + expedienteOutRO.getMessage(), null);
            }

        } catch (Exception e) {
            LOG.error("error en generarExpedienteSiged", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }
    
    public void generarDocumentoOrdenServicio(String rutaDocumento,UsuarioDTO usuario, ExpedienteDTO expedienteDTO) throws DocumentException, IOException{
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	//Creando documento para enviarlo al web service y generar el expediente
        Date date = new Date();
        DateFormat dateFormatFecha = new SimpleDateFormat("dd/MM/yyyy");
        BaseColor myColorCabeceraPadre = new BaseColor(153, 204, 255);
        
        Font fontTitle = null;
        Font fontContenido = null;
        Font fontContenidoResaltado = null;
        try{
        	String rutaFont = Constantes.RUTA_FONT_PDF;
        	BaseFont base = BaseFont.createFont(rutaFont, BaseFont.WINANSI,true);
        	fontTitle = new Font(base, 12,Font.BOLD);
        	fontContenido = new Font(base, 9,Font.NORMAL);
        	fontContenidoResaltado = new Font(base, 9,Font.BOLD);
        }catch(Exception ex){
        	fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
        	fontContenido = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.NORMAL);
        	fontContenidoResaltado = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD);
        }
        
    	Document documento = new Document(PageSize.A4,10,10,10,10);
        //String rutaDocumento = Constantes.RUTA_EXPEDIENTES_TEMPORALES + "expedienteOrdenServicio_" +  Calendar.getInstance().get(Calendar.HOUR) + Calendar.getInstance().get(Calendar.MINUTE) + Calendar.getInstance().get(Calendar.SECOND) + Calendar.getInstance().get(Calendar.MILLISECOND) + ".pdf";
        PdfWriter.getInstance(documento, new FileOutputStream(rutaDocumento));
        documento.open();
        
        PdfPTable tableCabecera = new PdfPTable(2);
        tableCabecera.setTotalWidth(new float[]{180,395});
        tableCabecera.setLockedWidth(true);
        tableCabecera.setSpacingBefore(20);

        PdfPCell cellLogo = createImageCell(Constantes.RUTA_LOGO_PDF);
        cellLogo.setBorder(Rectangle.NO_BORDER);
        PdfPCell cellEspacioVacio = new PdfPCell(new Phrase("" , fontTitle));
        cellEspacioVacio.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellTitulo = new PdfPCell(new Phrase("ORDEN DE SERVICIO" , fontTitle));
        cellTitulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellTitulo.setBorder(Rectangle.NO_BORDER);
        cellTitulo.setColspan(2);
        
        tableCabecera.addCell(cellLogo);
        tableCabecera.addCell(cellEspacioVacio);
        tableCabecera.addCell(cellTitulo);
        
        //obteniendo datos de cabecera
        PersonalDTO personal = null;
        List<UnidadOrganicaDTO> unidadUO = null;
        
        List<PersonalDTO> listPersona = personalServiceNeg.findPersonal(new PersonalFilter(usuario.getCodigo(), null));
        if (listPersona != null && listPersona.size() > 0) {
        	personal = listPersona.get(0);
        	PersonalFilter filtro = new PersonalFilter();
            filtro.setIdPersonal(personal.getIdPersonal());
            filtro.setFlagDefault(Constantes.ESTADO_ACTIVO);
            List<PersonalDTO> personalUnidOrgDefault = personalServiceNeg.findPersonal(filtro);
            if (personalUnidOrgDefault != null && personalUnidOrgDefault.size() > 0 && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault() != null && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica() != null) {
            	unidadUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica().getIdUnidadOrganica(), null));
            }
        }
        
        
        String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
        String numeroOrdenServicioPorGenerar = generarNumeroOrdenServicio(sigla);
        //fin de obtener datos de cabecera
        
        PdfPTable tableDatosServicio = new PdfPTable(4);
        tableDatosServicio.setTotalWidth(new float[]{100,275,100,100});
        tableDatosServicio.setLockedWidth(true);
        tableDatosServicio.setSpacingBefore(20);
        
        PdfPCell cellDS11= new PdfPCell(new Phrase("ASIGNADO POR:" , fontContenidoResaltado));
        cellDS11.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS11.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS12= new PdfPCell(new Phrase(personal.getNombreCompleto() == null ? "-" : personal.getNombreCompleto() , fontContenido));
        cellDS12.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS12.setBorder(Rectangle.NO_BORDER);
        
        
        PdfPCell cellDS13= new PdfPCell(new Phrase("FECHA:" , fontContenidoResaltado));
        cellDS13.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS13.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS14= new PdfPCell(new Phrase( dateFormatFecha.format(date) , fontContenido));
        cellDS14.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS14.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS21= new PdfPCell(new Phrase("ÁREA:" , fontContenidoResaltado));
        cellDS21.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS21.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS22= new PdfPCell(new Phrase( unidadUO == null ? "-" : unidadUO.get(0).getDescripcion() , fontContenido));
        cellDS22.setColspan(3);
        cellDS22.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS22.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS31= new PdfPCell(new Phrase("Nº EXPEDIENTE:" , fontContenidoResaltado));
        cellDS31.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS31.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS32= new PdfPCell(new Phrase(expedienteDTO.getNumeroExpediente() == null ? "-" : expedienteDTO.getNumeroExpediente(), fontContenido));
        cellDS32.setColspan(3);
        cellDS32.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS32.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS41= new PdfPCell(new Phrase("Nº ORDEN SERVICIO:" , fontContenidoResaltado));
        cellDS41.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS41.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS42= new PdfPCell(new Phrase(numeroOrdenServicioPorGenerar == null ? "-" : numeroOrdenServicioPorGenerar , fontContenido));
        cellDS42.setColspan(3);
        cellDS42.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS42.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS51= new PdfPCell(new Phrase("ASUNTO:" , fontContenidoResaltado));
        cellDS51.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS51.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDS52= new PdfPCell(new Phrase(expedienteDTO.getAsuntoSiged() == null ? "-" : expedienteDTO.getAsuntoSiged().toUpperCase() , fontContenido));
        cellDS52.setColspan(3);
        cellDS52.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellDS52.setBorder(Rectangle.NO_BORDER);
        
        List<BusquedaUnidadSupervisadaDTO> registro = new ArrayList<BusquedaUnidadSupervisadaDTO>();
        List<MaestroColumnaDTO> listaDireUoDl = maestroColumnaServiceNeg.findByDominioAplicacion(Constantes.DOMINIO_DIRE_INPS_UO, Constantes.APLICACION_INPS);
        UnidadSupervisadaFilter filtroDatosUnidadOperativa = new UnidadSupervisadaFilter();
        filtroDatosUnidadOperativa.setCadCodigoOsinerg(expedienteDTO.getUnidadSupervisada().getCodigoOsinergmin());
        if(listaDireUoDl!=null && listaDireUoDl.size()!=0) { filtroDatosUnidadOperativa.setCadCodigosTipoDireccion(listaDireUoDl.get(0).getCodigo()); }
        
        registro=unidadSupervisadaServiceNeg.cargaDataUnidadOperativaMasiva(filtroDatosUnidadOperativa);
        String razonSocial = "";
        String codigoOsinergmin = "";
        String actividad = "";
        String codigoPersonalSupervisoraEmpresa = "";
        String descripcionSupervisoraEmpresa = "";
        String proceso = "";
        
        if(registro != null && registro.size() != 0){
        	BusquedaUnidadSupervisadaDTO unidadSupervisada = new BusquedaUnidadSupervisadaDTO();
        	unidadSupervisada = registro.get(0);
        	razonSocial = unidadSupervisada.getNombreUnidad() == null ? "" : unidadSupervisada.getNombreUnidad();
        	codigoOsinergmin = unidadSupervisada.getCodigoOsinergmin() == null ? "" : unidadSupervisada.getCodigoOsinergmin();
        	actividad = unidadSupervisada.getActividad() == null ? "" : unidadSupervisada.getActividad();
        }
        
        PersonalFilter filterSupervisoraEmpresa = new PersonalFilter();
        try{
        	filterSupervisoraEmpresa.setIdSupervisoraEmpresa(expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
        }catch(Exception ex){
        	filterSupervisoraEmpresa.setIdSupervisoraEmpresa(Long.parseLong("0"));
        }
        List<PersonalDTO> listPersonalUnidadSupervisora = personalServiceNeg.findPersonal(filterSupervisoraEmpresa);
        if(listPersonalUnidadSupervisora != null && listPersonalUnidadSupervisora.size() != 0){
        	codigoPersonalSupervisoraEmpresa = "(CODIGO " + listPersonalUnidadSupervisora.get(0).getNombreUsuarioSiged() + ")";
        }
        
        try{
        	SupervisoraEmpresaDTO asignadoSupeEmp = supervisoraEmpresaServiceNeg.getById(expedienteDTO.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa());
        	descripcionSupervisoraEmpresa = asignadoSupeEmp.getRazonSocial() == null ? asignadoSupeEmp.getNombreConsorcio() : asignadoSupeEmp.getRazonSocial();
        }catch(Exception ex){
        	descripcionSupervisoraEmpresa = "";
        }
        
        ProcesoFilter procesoFilter = new ProcesoFilter();
        procesoFilter.setIdentificador(Constantes.PROCESO_PREOPERATIVO);
        List<ProcesoDTO> listaProceso =  serieServiceNeg.listarProceso(procesoFilter);
        try{
        	for (ProcesoDTO procesoDTO : listaProceso) {
        		if(procesoDTO.getIdProceso() == expedienteDTO.getProceso().getIdProceso()){
        			proceso = procesoDTO.getDescripcion();
        		}
        	}
        }catch(Exception ex){
        	proceso = "";
        }
        
        tableDatosServicio.addCell(cellDS11);
        tableDatosServicio.addCell(cellDS12);
        tableDatosServicio.addCell(cellDS13);
        tableDatosServicio.addCell(cellDS14);
        tableDatosServicio.addCell(cellDS21);
        tableDatosServicio.addCell(cellDS22);
        tableDatosServicio.addCell(cellDS31);
        tableDatosServicio.addCell(cellDS32);
        tableDatosServicio.addCell(cellDS41);
        tableDatosServicio.addCell(cellDS42);
        tableDatosServicio.addCell(cellDS51);
        tableDatosServicio.addCell(cellDS52);
        
        PdfPCell cellAsunto= new PdfPCell();
        cellAsunto.setColspan(4);
        cellAsunto.setBorder(Rectangle.NO_BORDER);
        
        PdfPTable tableAsunto = new PdfPTable(5);
        tableAsunto.setTotalWidth(new float[]{155,65,120,155,80});
        tableAsunto.setLockedWidth(true);
        tableAsunto.setSpacingBefore(20);
        tableAsunto.setSpacingAfter(20);
        
        PdfPCell cellA11= new PdfPCell(new Phrase("RAZÓN SOCIAL" , fontContenidoResaltado));
        cellA11.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA11.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cellA11.setBackgroundColor(myColorCabeceraPadre);
        
        PdfPCell cellA12= new PdfPCell(new Phrase("CÓDIGO \n OSINERGMIN" , fontContenidoResaltado));
        cellA12.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA12.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cellA12.setBackgroundColor(myColorCabeceraPadre);
        
        PdfPCell cellA13= new PdfPCell(new Phrase("ACTIVIDAD" , fontContenidoResaltado));
        cellA13.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA13.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cellA13.setBackgroundColor(myColorCabeceraPadre);
        
        PdfPCell cellA14= new PdfPCell(new Phrase("EMPRESA SUPERVISORA" , fontContenidoResaltado));
        cellA14.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA14.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cellA14.setBackgroundColor(myColorCabeceraPadre);
        
        PdfPCell cellA15= new PdfPCell(new Phrase("TIPO SUPERVISIÓN" , fontContenidoResaltado));
        cellA15.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA15.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cellA15.setBackgroundColor(myColorCabeceraPadre);
        
        PdfPCell cellA21= new PdfPCell(new Phrase( razonSocial , fontContenido));
        cellA21.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA21.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        
        PdfPCell cellA22= new PdfPCell(new Phrase(codigoOsinergmin , fontContenido));
        cellA22.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA22.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        
        PdfPCell cellA23= new PdfPCell(new Phrase(actividad , fontContenido));
        cellA23.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA23.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        
        PdfPCell cellA24= new PdfPCell(new Phrase(descripcionSupervisoraEmpresa + codigoPersonalSupervisoraEmpresa , fontContenido));
        cellA24.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA24.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        
        PdfPCell cellA25= new PdfPCell(new Phrase(proceso , fontContenido));
        cellA25.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cellA25.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        
        tableAsunto.addCell(cellA11);
        tableAsunto.addCell(cellA12);
        tableAsunto.addCell(cellA13);
        tableAsunto.addCell(cellA14);
        tableAsunto.addCell(cellA15);
        tableAsunto.addCell(cellA21);
        tableAsunto.addCell(cellA22);
        tableAsunto.addCell(cellA23);
        tableAsunto.addCell(cellA24);
        tableAsunto.addCell(cellA25);
        
        cellAsunto.addElement(tableAsunto);
        
        PdfPCell cellComentarios= new PdfPCell(new Phrase("COMENTARIOS:" , fontContenidoResaltado));
        cellComentarios.setColspan(4);
        cellComentarios.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cellComentarios.setBorder(Rectangle.NO_BORDER);
        
        PdfPCell cellDescripcionComentarios= new PdfPCell(new Phrase(expedienteDTO.getComentarios() == null ? "" : expedienteDTO.getComentarios().toUpperCase() , fontContenido));
        cellDescripcionComentarios.setColspan(4);
        cellDescripcionComentarios.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        
        tableDatosServicio.addCell(cellAsunto);
        tableDatosServicio.addCell(cellComentarios);
        tableDatosServicio.addCell(cellDescripcionComentarios);

        documento.add(tableCabecera);
        documento.add(tableDatosServicio);
        
        documento.close();
    }
    
    @Override
    public String generarNumeroOrdenServicio(String sigla) {
    	// TODO Auto-generated method stub
    	return expedienteDAO.generarNumeroOrdenServicio(sigla);
    }
    
    public  PdfPCell createImageCell(String path) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
        try{
        	Image img = Image.getInstance(path);
            cell = new PdfPCell(img, true);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        }catch(Exception ex){
        	LOG.error("Error al obtener la imagen de cabecera", ex);
        }
        return cell;
    }

    // htorress - RSIS 18 - Inicio
    @Override
    public ExpedienteDTO reenviarExpedienteSIGED(ExpedienteDTO expedienteDTO, Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();

        try {
            ExpedienteReenviarInRO expedienteRInRO = new ExpedienteReenviarInRO();

            expedienteRInRO.setNumeroExpediente(expedienteDTO.getNumeroExpediente());
            expedienteRInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            expedienteRInRO.setDestinatario(destinatario.intValue());
            expedienteRInRO.setContenido(contenido);

            ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.reenviar(HOST + "/remote/expediente/reenviar", expedienteRInRO, aprobacion);

            if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                System.out.println(expedienteOutRO.getResultCode());
                System.out.println(expedienteOutRO.getMessage());
                retorno.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
            } else {
                System.out.println("Error: " + expedienteOutRO.getResultCode() + "-Ocurrio un error: " + expedienteOutRO.getMessage());
                retorno.setMensajeServicio(expedienteOutRO.getMessage());
            }
        } catch (Exception e) {
            LOG.error("error en reenviarExpedienteSIGED", e);
            throw new ExpedienteException(e.getMessage(), null);
        }

        return retorno;
    }

    @Override
    public ExpedienteDTO aprobarExpedienteSIGED(ExpedienteDTO expedienteDTO, Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();

        try {
            ExpedienteAprobarInRO expedienteRInRO = new ExpedienteAprobarInRO();

            expedienteRInRO.setNumeroExpediente(expedienteDTO.getNumeroExpediente());
            expedienteRInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            expedienteRInRO.setDestinatario(destinatario.intValue());
            expedienteRInRO.setContenido(contenido);

            ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.aprobar(HOST + "/remote/expediente/aprobar", expedienteRInRO, aprobacion);

            if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                System.out.println(expedienteOutRO.getResultCode());
                System.out.println(expedienteOutRO.getMessage());
                retorno.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
            } else {
                System.out.println("Error: " + expedienteOutRO.getResultCode() + "-Ocurrio un error: " + expedienteOutRO.getMessage());
                retorno.setMensajeServicio(expedienteOutRO.getMessage());
            }
        } catch (Exception e) {
            LOG.error("error en aprobarExpedienteSIGED", e);
            throw new ExpedienteException(e.getMessage(), null);
        }

        return retorno;

    }

    @Override
    public ExpedienteDTO archivarExpedienteSIGED(String nroExpediente, String contenido) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();

        try {
            ExpedienteInRO expedienteRInRO = new ExpedienteInRO();

            expedienteRInRO.setObservacionArchivar(contenido);
            expedienteRInRO.setNroExpediente(nroExpediente);

            ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.archivar(HOST + "/remote/expediente/archivar", expedienteRInRO);

            if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                LOG.info("getResultCode=" + expedienteOutRO.getResultCode());
                LOG.info("getResultCode=" + expedienteOutRO.getMessage());
                retorno.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
            } else {
                LOG.info("getResultCode=" + expedienteOutRO.getResultCode() + "-Ocurrio un error: " + expedienteOutRO.getMessage());
                retorno.setMensajeServicio(expedienteOutRO.getMessage());
            }
        } catch (Exception e) {
            LOG.error("error en aprobarExpedienteSIGED", e);
            throw new ExpedienteException(e.getMessage(), null);
        }
        return retorno;
    }

    @Override
    public ExpedienteDTO reabrirExpedienteSIGED(String nroExpediente) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();

        try {

            ExpedienteOutRO expedienteOutRO = ExpedienteInvoker.reabrir(HOST + "/remote/expediente/reabrir", nroExpediente);;

            if (expedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                System.out.println(expedienteOutRO.getResultCode());
                System.out.println(expedienteOutRO.getMessage());
                retorno.setNumeroExpediente(expedienteOutRO.getCodigoExpediente());
            } else {
                System.out.println("Error: " + expedienteOutRO.getResultCode() + "-Ocurrio un error: " + expedienteOutRO.getMessage());
                retorno.setMensajeServicio(expedienteOutRO.getMessage());
            }
        } catch (Exception e) {
            LOG.error("error en reabrirExpedienteSIGED", e);
            throw new ExpedienteException(e.getMessage(), null);
        }

        return retorno;

    }

    @Override
    public ExpedienteDTO rechazarExpedienteSIGED(ExpedienteDTO expedienteDTO, String motivo) throws ExpedienteException {
        ExpedienteDTO retorno = new ExpedienteDTO();

        try {
            DevolverExpedienteInRO devolverExpedienteInRO = new DevolverExpedienteInRO();
            devolverExpedienteInRO.setNroExpediente(expedienteDTO.getNumeroExpediente());
            devolverExpedienteInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            devolverExpedienteInRO.setMotivo(motivo);
            /* OSINE_SFS-480 - RSIS 40 - Inicio */
            devolverExpedienteInRO.setIdUsuario(expedienteDTO.getPersonal().getIdPersonalSiged().intValue());
            /* OSINE_SFS-480 - RSIS 40 - Fin */
            DevolverExpedienteOutRO devolverExpedienteOutRO = NuevoDocumentoInvoker.devolverExpediente(HOST_SIGED + "/remote/nuevodocumento/devolverExpediente", devolverExpedienteInRO);

            if (devolverExpedienteOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                System.out.println(devolverExpedienteOutRO.getResultCode());
                System.out.println(devolverExpedienteOutRO.getMessage());
                retorno.setEstado(String.valueOf(devolverExpedienteOutRO.getResultCode()));
            } else {
                System.out.println("Error: " + devolverExpedienteOutRO.getResultCode() + "-Ocurrio un error: " + devolverExpedienteOutRO.getMessage());
                retorno.setEstado(String.valueOf(devolverExpedienteOutRO.getResultCode()));
                retorno.setMensajeServicio(devolverExpedienteOutRO.getMessage());
            }
        } catch (Exception e) {
            LOG.error("error en aprobarExpedienteSIGED", e);
            throw new ExpedienteException(e.getMessage(), null);
        }

        return retorno;

    }

    // htorress - RSIS 18 - Fin
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteDTO editarExpedienteOrdenServicio(ExpedienteDTO expedienteDTO, String codigoTipoSupervisor, PersonalDTO personalDest, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.info("editarExpediente");
        ExpedienteDTO retorno = new ExpedienteDTO();
        try {
            //editarExpediente
            retorno = expedienteDAO.editarExpedienteOrdenServicio(expedienteDTO, codigoTipoSupervisor, personalDest, usuarioDTO);
        } catch (Exception e) {
            LOG.error("error en editarExpedienteOrdenServicio", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }

    @Override
    public ExpedienteActualizarTipoProcesoOut editarExpedienteSIGED(ExpedienteDTO expedienteDTO) throws ExpedienteException {
        LOG.info("editarExpedienteSIGED");
        ExpedienteActualizarTipoProcesoOut retorno = null;
        try {
            ExpedienteActualizarTipoProcesoIn expediente = new ExpedienteActualizarTipoProcesoIn();
            expediente.setAsuntoSiged(expedienteDTO.getAsuntoSiged());
            expediente.setNroExpediente(expedienteDTO.getNumeroExpediente());
            if (expedienteDTO.getFlujoSiged() != null && expedienteDTO.getFlujoSiged().getCodigoSiged() != null) {
                expediente.setTipoProceso(expedienteDTO.getFlujoSiged().getCodigoSiged().toString());
            }
            expediente.setTipoIdentificacion(null);
//            if (expedienteDTO.getEmpresaSupervisada() != null && expedienteDTO.getEmpresaSupervisada().getRuc() != null) {
//                expediente.setNumeroIdentificacion(expedienteDTO.getEmpresaSupervisada().getRuc());
//            } else if (expedienteDTO.getEmpresaSupervisada() != null && expedienteDTO.getEmpresaSupervisada().getRuc() != null) {
//                expediente.setNumeroIdentificacion(expedienteDTO.getEmpresaSupervisada().getNroIdentificacion().toString());
//            }
            expediente.setNumeroIdentificacion(expedienteDTO.getUnidadSupervisada().getRuc());
            retorno = NuevoDocumentoInvoker.actualizarExpedenteSiged(HOST_SIGED + "/remote/nuevodocumento/actualizarExpedenteSiged", expediente);
            if (retorno.getResultCode() != null && retorno.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                LOG.info("resultCode: " + retorno.getResultCode());
            } else {
                LOG.info("Mensaje " + retorno.getMessage());
                LOG.info("Codigo error: " + retorno.getErrorCode());
            }
        } catch (Exception e) {
            LOG.error("Error editarExpedienteSIGED", e);
        }
        return retorno;
    }

    /* OSINE_SFS-480 - RSIS 47 - Inicio */
 /* OSINE_SFS-480 - RSIS 06 - Inicio */
    @Override
    public List<ListaMensajeriaItemOut> listarMensajeria(String idUsuarioSiged) {

        LOG.info("listarMensajeria");
        List<ListaMensajeriaItemOut> listaRetorno = new ArrayList<ListaMensajeriaItemOut>();
        try {
            ListaMensajeriaOut listaMensajeriaOutRO = NuevoDocumentoInvoker.listarMensajeria(HOST_SIGED + "/remote/nuevodocumento/listarMensajeria", idUsuarioSiged);

            LOG.info("listaMensajeriaOutRO.getResultCode()-->" + listaMensajeriaOutRO.getResultCode());
            if (listaMensajeriaOutRO.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                listaRetorno.addAll(listaMensajeriaOutRO.getListaMensajeriaItemOut());
            }
        } catch (Exception e) {
            LOG.error("Error listarMensajeria", e);
        }
        return listaRetorno;
    }

    @Override
    public ConsultarMensajeriaDatosCargoOut cargaDatosCargo(String idMensajeria) {

        LOG.info("cargaDatosCargo");
        ConsultarMensajeriaDatosCargoOut retorno = new ConsultarMensajeriaDatosCargoOut();
        try {
            ConsultarMensajeriaDatosCargoOut consultarMensajeriaDatosCargoOut = NuevoDocumentoInvoker.consultaMensajeriaDatosCargo(HOST_SIGED + "/remote/nuevodocumento/consultaMensajeriaDatosCargo", idMensajeria);

            LOG.info("cargaDatosCargo.getResultCode()-->" + consultarMensajeriaDatosCargoOut.getResultCode());
            if (consultarMensajeriaDatosCargoOut.getResultCode().equals(InvocationResult.SUCCESS.getCode())) {
                retorno = (ConsultarMensajeriaDatosCargoOut) consultarMensajeriaDatosCargoOut;
            }
        } catch (Exception e) {
            LOG.error("Error cargaDatosCargo", e);
        }
        return retorno;
    }

    /* OSINE_SFS-480 - RSIS 06 - Fin */
    @Override
    @Transactional(rollbackFor=ExpedienteException.class)  
    public Map<String,Object> aprobarOrdenServicioDsrCri(Long idExpediente,Long idOrdenServicio,Long idPersonalOri, String numeroExpediente, String asuntoSiged,HttpServletRequest request) throws ExpedienteException{
        Map<String,Object> retorno=new HashMap<String,Object>();
        try{
            ExpedienteDTO expediente = new ExpedienteDTO();
            expediente.setIdExpediente(idExpediente);
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);
            
            /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
            //PersonalDTO destinatario = (PersonalDTO) personalServiceNeg.obtenerUsuarioOrigen(idOrdenServicio);
            PersonalDTO destinatarioRev = personalServiceNeg.findPersonal(new PersonalFilter(idPersonalOri)).get(0);

            List<OrdenServicioDTO> listOrdenServicio=ordenServicioServiceNeg.findByFilter(new OrdenServicioFilter(idOrdenServicio));
            
            //Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
        	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_APR_OS_DSR);
        	String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expediente.getNumeroExpediente()) : "--";
	        comRenExpSiged=(!CollectionUtils.isEmpty(listOrdenServicio)) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, listOrdenServicio.get(0).getNumeroOrdenServicio()) : comRenExpSiged;
            
	        ExpedienteDTO expedienteAprobar = aprobarExpedienteSIGED(expediente, destinatarioRev.getIdPersonalSiged(), comRenExpSiged, false);	        
	        //ExpedienteDTO expedienteAprobar = aprobarExpedienteSIGED(expediente, destinatario.getIdPersonalSiged(), "Aprobar.", true);
            /* OSINE_SFS-Ajustes - mdiosesf - Fin */
	        
            //evalua archivar
            List<SupervisionDTO> listaSupervision = supervisionDAO.find(new SupervisionFilter(null, idOrdenServicio));
            if (!CollectionUtils.isEmpty(listaSupervision)) {
                SupervisionDTO supervisionRegistro = listaSupervision.get(0);
                if (supervisionRegistro.getResultadoSupervisionDTO() != null && supervisionRegistro.getResultadoSupervisionDTO().getIdResultadosupervision() != null) {
                    if (supervisionRegistro.getResultadoSupervisionDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE)
                            || supervisionRegistro.getResultadoSupervisionDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE_OBSTACULIZADO)) {
                        ExpedienteDTO expedienteArchivar = archivarExpedienteSIGED(String.valueOf(numeroExpediente), "Archivar.");
                        LOG.info("expedienteArchivar-->" + expedienteArchivar);
                    }
                } else {
                    throw new ExpedienteException("La Supervisi&oacute no cuenta con Resultados Generados", null);
                }
            } else {
                throw new ExpedienteException("La Orden de Servicio no tiene Supervisi&oacute;n relacionada", null);
            }

            if (expedienteAprobar.getNumeroExpediente() != null) {
                OrdenServicioDTO respuesta = ordenServicioServiceNeg.aprobarCriticidad(idExpediente, idOrdenServicio, idPersonalOri, Constantes.getUsuarioDTO(request));

                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
                retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados");
                retorno.put("expediente", respuesta);
            } else {
                LOG.error("Error en aprobar");
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, expedienteAprobar.getMensajeServicio());
            }
        } catch (Exception e) {
            LOG.error("Error en aprobarOrdenServicioDefault", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }

    /* OSINE_SFS-791 - RSIS 47 - Inicio */
    @Override
    @Transactional(readOnly = true)
    public List<ExpedienteDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException {
        LOG.info("findByFilter");
        List<ExpedienteDTO> retorno = null;
        try {
            retorno = expedienteDAO.findByFilter(filtro);
        } catch (Exception e) {
            LOG.error("Error findByFilter", e);
        }
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteDTO veriActuFlgTramFinalizadoDsr(Long idExpediente, Long idOrdenServicio, Long idResultadoSupervision, UsuarioDTO usuario) throws ExpedienteException {
        LOG.info("veriActuFlgTramFinalizadoDsr");
        ExpedienteDTO retorno = null;
        try {
            retorno = expedienteDAO.veriActuFlgTramFinalizadoDsr(idExpediente, idOrdenServicio, idResultadoSupervision, usuario);
        } catch (Exception e) {
            LOG.error("Error veriActuFlgTramFinalizadoDsr", e);
        }
        return retorno;
	}
    
    @Override
	@Transactional(rollbackFor=ExpedienteException.class)
	public ExpedienteDTO actualizar(ExpedienteDTO expediente, UsuarioDTO usuario) throws ExpedienteException {
    	LOG.info("actualizar");
    	ExpedienteDTO retorno=null;
        try{
        	retorno=expedienteDAO.actualizar(expediente, usuario);
        }catch(Exception e){
            LOG.error("Error actualizar", e);
        }
        return retorno;
	}
	/* OSINE_SFS-791 - RSIS 47 - Fin */	
        /* OSINE_SFS-791 - RSIS 41 - Inicio */
    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteDTO actualizarExpedienteHabModuloLevantamiento(ExpedienteDTO expedienteDTO, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.info("actualizarExpedienteHabModuloLevantamiento");
        ExpedienteDTO retorno = new ExpedienteDTO();
        try {
            //Actualizando el Expediente para el modulo de levantamientos
            retorno = expedienteDAO.actualizarExpediente(expedienteDTO, usuarioDTO);
            if (retorno != null) {
                //se registrara el historial del estado levantamiento
                HstEstadoLevantamientoDTO hstEstadoLevantamientoDTO = new HstEstadoLevantamientoDTO();
                hstEstadoLevantamientoDTO.setEstado(Constantes.ESTADO_ACTIVO);
                hstEstadoLevantamientoDTO.setExpedienteDTO(retorno);
                Date fechaactual = new Date();
                String fecha = Utiles.DateToString(fechaactual, Constantes.FORMATO_FECHA_CORTA);
                String hora = Utiles.DateToString(fechaactual, Constantes.FORMATO_HORA_CORTA);
                hstEstadoLevantamientoDTO.setFechaEstado(fecha + " " + hora);
                hstEstadoLevantamientoDTO.setIdEstado(expedienteDTO.getEstadoLevantamiento().getIdMaestroColumna());
                hstEstadoLevantamientoDTO = hstEstadoLevantamientoServiceNeg.registrarHstEstadoLevantamiento(hstEstadoLevantamientoDTO, usuarioDTO);
                if (hstEstadoLevantamientoDTO == null) {
                    retorno = null;
                    LOG.error("error en Registrar el historico del estado del levantamiento");
                    throw new ExpedienteException("error en actualizarExpedienteHabModuloLevantamiento");
                }
            } else {
                retorno = null;
                LOG.error("error en actualizarExpedienteHabModuloLevantamiento");
                throw new ExpedienteException("error en actualizarExpedienteHabModuloLevantamiento");
            }
        } catch (Exception e) {
            LOG.error("error en actualizarExpedienteHabModuloLevantamiento", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 41 - Fin */
    
    /* OSINE_SFS-Ajustes - mdiosesf - Inicio */
    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public Map<String, Object> asignarOrdenServicioPrincipal(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, UsuarioDTO usuarioDTO, String sigla, HttpServletRequest request) throws ExpedienteException {
    	LOG.info("procesar asignarOrdenServicioPrincipal");
    	Map<String, Object> retorno = new HashMap<String, Object>();
        try {            
        	List<PersonalDTO> destinatario = null;
            if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
                destinatario = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDto.getOrdenServicio().getLocador().getIdLocador(), null));
            } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
                destinatario = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
            }

            if (!CollectionUtils.isEmpty(destinatario)) {            	
            	ExpedienteDTO expediente = asignarOrdenServicio(expedienteDto, codigoTipoSupervisor, Constantes.getUsuarioDTO(request),"DSR");
            	
            	//Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
            	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_ASIG_OS);
    	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expediente.getNumeroExpediente()) : "--";
    	        comRenExpSiged=(expediente.getOrdenServicio()!=null && expediente.getOrdenServicio().getNumeroOrdenServicio()!=null) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, expediente.getOrdenServicio().getNumeroOrdenServicio()) : comRenExpSiged;
            	
                String msjeCorreo = "";
                List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                expedienteDto.getOrdenServicio().setNumeroOrdenServicio(expediente.getOrdenServicio().getNumeroOrdenServicio());
                boolean rptaCorreo = correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), destinatario.get(0), expedienteDto);
                if (!rptaCorreo) {
                    msjeCorreo = ". No se pudo enviar correo a Empresa Supervisora.";
                    expediente.getOrdenServicio().setNumeroOrdenServicio(expediente.getOrdenServicio().getNumeroOrdenServicio() + msjeCorreo);
                }                    	
            	    
                ExpedienteDTO expedienteReenviarSiged = reenviarExpedienteSIGED(expedienteDto, destinatario.get(0).getIdPersonalSiged(), comRenExpSiged, false);
            	if (expedienteReenviarSiged.getNumeroExpediente() != null) {
            		retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            		retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados" + msjeCorreo);
            		retorno.put("expediente", expediente);
            	}
            	else {            		
            		throw new ExpedienteException("El servicio reenvío SIGED Asignar orden no disponible.", null);
            	}
            } else {
            	throw new ExpedienteException("Error en la obtención del id del Personal Siged del destinatario.", null);
            }
        } catch (Exception e) {
            LOG.error("error en asignarOrdenServicioPrincipal", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }
    
//    @Override
//    @Transactional(rollbackFor = ExpedienteException.class)
//    public Map<String, Object> generarExpedienteOrdenServicioPrincipal(ExpedienteDTO expedienteDto, String codigoTipoSupervisor, HttpServletRequest request)  throws ExpedienteException {
//    	LOG.info("procesar asignarOrdenServicioPrincipal");
//    	Map<String, Object> retorno = new HashMap<String, Object>();
//        try {            
//        	ExpedienteDTO expedienteSiged = generarExpedienteSiged(expedienteDto, Constantes.getIDPERSONALSIGED(request));
//        	if (expedienteSiged == null || expedienteSiged.getNumeroExpediente() == null) {
//        		retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
//        		retorno.put(ConstantesWeb.VV_MENSAJE, "El servicio SIGED no se encuentra disponible.");
//        	} else {
//	              expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());
//	              List<PersonalDTO> destinatario = null;
//	              if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
//	                  destinatario = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDto.getOrdenServicio().getLocador().getIdLocador(), null));
//	              } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
//	                  destinatario = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
//	              }
//	              
//                  expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());//viene del WS
//                  expedienteDto.setFechaCreacionSiged(new Date());
//                  expedienteDto.setFlagOrigen(Constantes.EXPEDIENTE_FLAG_ORIGEN_INPS);
//
//                  String sigla=(String) request.getSession().getAttribute(Constantes.PERSONAL_UNIDAD_ORGANICA_SIGLA);
//                  if(!StringUtils.isEmpty(sigla)){
//                	  if (!CollectionUtils.isEmpty(destinatario)) {  
//	                        ExpedienteDTO expedienteBD=generarExpedienteOrdenServicio(expedienteDto,codigoTipoSupervisor,Constantes.getUsuarioDTO(request),sigla);
//	
//	                        //Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
//	    	              	List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_GEN_EXP_OS);
//	    	      	        String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expedienteBD.getNumeroExpediente()) : "--";
//	    	      	        comRenExpSiged=(expedienteBD.getOrdenServicio()!=null && expedienteBD.getOrdenServicio().getNumeroOrdenServicio()!=null) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, expedienteBD.getOrdenServicio().getNumeroOrdenServicio()) : comRenExpSiged;
//	    	      	      
//	                        String msjeCorreo = "";
//	                        List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
//	                        LOG.info("remitente---->" + remitente);
//	                        expedienteDto.getOrdenServicio().setNumeroOrdenServicio(expedienteBD.getOrdenServicio().getNumeroOrdenServicio());
//	                        boolean rptaCorreo = correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), destinatario.get(0), expedienteDto);
//	                        if (!rptaCorreo) {
//	                            msjeCorreo = " Pero no se pudo enviar correo a Empresa Supervisora.";
//	                        }
//
//	    	      	        ExpedienteDTO expedienteReenviarSiged = reenviarExpedienteSIGED(expedienteDto, destinatario.get(0).getIdPersonalSiged(), comRenExpSiged, false);
//	    	                if (expedienteReenviarSiged.getNumeroExpediente() != null) {
//	    	                	retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
//		                        retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados." + msjeCorreo);
//		                        retorno.put("expediente", expedienteBD);
//	    	                } else {
//	    	                	throw new ExpedienteException("El servicio reenvío SIGED Asignar orden no disponible.", null);
//	    	                }	
//	                  } else {
//		                  retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
//		                  retorno.put(ConstantesWeb.VV_MENSAJE, "Error en la obtenci&oacute;n del id del Personal Siged del destinatario.");
//		              }
//                  }   else {
//                	  retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
//	                  retorno.put(ConstantesWeb.VV_MENSAJE, "No se encontro Sigla de Unidad Org&aacute;nica.");
//	              }
//        	}
//        } catch (Exception e) {
//            LOG.error("error en asignarOrdenServicioPrincipal", e);
//            throw new ExpedienteException(e.getMessage(), e);
//        }
//        return retorno;
//    }
    
    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public Map<String, Object> generarExpedienteOrdenServicioMasivoPrincipal(List<UnidadSupervisadaDTO> unidades, ExpedienteDTO expedienteDto, String codigoTipoSupervisor, String sigla, HttpServletRequest request)  throws ExpedienteException {
    	LOG.info("procesar asignarOrdenServicioPrincipal");
    	Map<String, Object> retorno = new HashMap<String, Object>();
    	List<ExpedienteDTO> listaOrdSerCreada = new ArrayList<ExpedienteDTO>();
        List<UnidadSupervisadaDTO> listaOrdSerNoGenExpSiged = new ArrayList<UnidadSupervisadaDTO>();
        List<UnidadSupervisadaDTO> listaOrdSerNoReeSiged = new ArrayList<UnidadSupervisadaDTO>();
        List<UnidadSupervisadaDTO> listaOrdSerNoIdPerSiged = new ArrayList<UnidadSupervisadaDTO>();
        String ordSerNoReeSigedMsg = null;
        String ordSerNoIdPerSigedMsg = null;
        String ordSerNoGenExpSigedMsg = null;
        MaestroColumnaDTO periodo = maestroColumnaServiceNeg.buscarByDominioByAplicacionByCodigo(Constantes.DOMINIO_SUPERV_MUEST_PERIODO, Constantes.APLICACION_INPS, Constantes.CODIGO_PERIODO).get(0);
        Long cantPeriodos = new Long(periodo.getDescripcion());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        Long per = obtenerPeriodoActual(cantPeriodos, mes);
        String perUnidad = year + "" + per;
        try {            
        	for (UnidadSupervisadaDTO unidad : unidades) {
                expedienteDto.setUnidadSupervisada(unidad);
                
                ExpedienteDTO expedienteSiged = generarExpedienteSiged(expedienteDto,unidad.getListaDirecciones().get(0).getIdDirccionUnidadSuprvisada(), Constantes.getIDPERSONALSIGED(request));
                //momentaneo
                //expedienteSiged.setNumeroExpediente("222222222220");
                if (expedienteSiged == null || expedienteSiged.getNumeroExpediente() == null) {                    
                    expedienteDto.setMensajeServicio("<b>" + expedienteSiged.getMensajeServicio() + "</b><br>");
                    ordSerNoGenExpSigedMsg = expedienteDto.getMensajeServicio();
                    listaOrdSerNoGenExpSiged.add(unidad);	                
                } else {
                    expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());
                    List<PersonalDTO> destinatario = null;
                    if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)) {
                        destinatario = personalServiceNeg.findPersonal(new PersonalFilter(expedienteDto.getOrdenServicio().getLocador().getIdLocador(), null));
                    } else if (codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)) {
                        destinatario = personalServiceNeg.findPersonal(new PersonalFilter(null, expedienteDto.getOrdenServicio().getSupervisoraEmpresa().getIdSupervisoraEmpresa()));
                    }
                    if (!CollectionUtils.isEmpty(destinatario)) {              	
                    	
                    	expedienteDto.setNumeroExpediente(expedienteSiged.getNumeroExpediente());//viene del WS
                        expedienteDto.setFechaCreacionSiged(new Date());
                        expedienteDto.setFlagOrigen(Constantes.EXPEDIENTE_FLAG_ORIGEN_INPS);                    
//                        expedienteDto.setEmpresaSupervisada(new EmpresaSupDTO(expedienteDto.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada()));
                        expedienteDto.setFlagMuestral(expedienteDto.getUnidadSupervisada().getFlagMuestral());
                        
                        ExpedienteDTO expedienteBD=generarExpedienteOrdenServicio(expedienteDto,codigoTipoSupervisor,Constantes.getUsuarioDTO(request),sigla);
                       
                        //Ajuste al reenviar expediente SIGED >> dominio COM_REEN_EXP_SIGED.
                        //List<MaestroColumnaDTO> listComRenExpSiged=maestroColumnaServiceNeg.findByDominioAplicacionCodigo(Constantes.DOMINIO_COM_REEN_EXP_SIGED, Constantes.APLICACION_INPS, Constantes.CODIGO_COM_REEN_EXP_SIGED_GEN_EXP_OS);
                        //String comRenExpSiged=(!CollectionUtils.isEmpty(listComRenExpSiged)) ? listComRenExpSiged.get(0).getDescripcion().replace(Constantes.VARIABLE_TEXT_REEN_EXP_SIG, expedienteDto.getNumeroExpediente()) : "--";
                        //comRenExpSiged=(expedienteBD.getOrdenServicio()!=null && expedienteBD.getOrdenServicio().getNumeroOrdenServicio()!=null) ? comRenExpSiged.replace(Constantes.VARIABLE_TEXT_REEN_ORD_SIG, expedienteBD.getOrdenServicio().getNumeroOrdenServicio()) : comRenExpSiged;
                        
            	        
                        if (expedienteBD != null && expedienteBD.getOrdenServicio() != null
                                && expedienteBD.getOrdenServicio().getNumeroOrdenServicio() != null
                                && unidad.getFlagMuestral().equals(Constantes.ESTADO_ACTIVO)) {
                            UnidadObliSubTipoDTO unidadMuestraltoUpdate = new UnidadObliSubTipoDTO();
                            UnidadSupervisadaDTO unidadMuestra = new UnidadSupervisadaDTO();
                            unidadMuestra.setIdUnidadSupervisada(expedienteDto.getUnidadSupervisada().getIdUnidadSupervisada());
                            unidadMuestraltoUpdate.setIdUnidadSupervisada(unidadMuestra);
                            unidadMuestraltoUpdate.setFlagSupOrdenServicio(Constantes.ESTADO_ACTIVO);
                            unidadMuestraltoUpdate.setPeriodo(perUnidad);
                            unidadMuestraltoUpdate.setEstado(Constantes.ESTADO_ACTIVO);
                            UnidadObliSubTipoDTO unidadMuestral = unidadSeleccionMuestral.updateUnidadMuestral(unidadMuestraltoUpdate, Constantes.getUsuarioDTO(request));
                            LOG.info("unidadMuestral update=" + unidadMuestral);
                        }
                        List<PersonalDTO> remitente = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                        LOG.info("remitente---->" + remitente);
                        String msjCorreo = "";
                        expedienteDto.getOrdenServicio().setNumeroOrdenServicio(expedienteBD.getOrdenServicio().getNumeroOrdenServicio());
                        boolean rptaCorreo = correoServiceNeg.enviarCorreoAsignarOS(remitente.get(0), destinatario.get(0), expedienteDto);
                        if (!rptaCorreo) {
                            LOG.info("No se pudo enviar correo a Empresa Supervisora.");
                            msjCorreo = "<br>No se pudo enviar correo a Empresa Supervisora.<br>";
                        }
                        
                        String mensajeFinal = "";
                        mensajeFinal = mensajeFinal + expedienteBD.getOrdenServicio().getNumeroOrdenServicio() + "<br>";
                        if (expedienteSiged != null || expedienteSiged.getNumeroExpediente() != null) {  
                        	mensajeFinal = mensajeFinal + "<b>Número de expediente generado:</b><br>";
                        	mensajeFinal = mensajeFinal + expedienteSiged.getNumeroExpediente() + "<br>";
                        }

                        expedienteBD.setMensajeServicio("<b>Orden(es) de Servicio creada(s):</b><br>");
                        expedienteBD.getOrdenServicio().setNumeroOrdenServicio(mensajeFinal + msjCorreo);
                        listaOrdSerCreada.add(expedienteBD);          
                        
                        String comRenExpSiged = "";
                        List<CorreoDTO> correo = correoServiceNeg.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF019));
                        if (correo != null && correo.size() > 0) {
                        	List<PersonalDTO> remitenteCorreo = personalServiceNeg.findPersonal(new PersonalFilter(Constantes.getIDPERSONAL(request)));
                        	List<UnidadOrganicaDTO> unidadUO = null;
                            PersonalFilter filtroPersonal = new PersonalFilter(); 
                            filtroPersonal.setIdPersonal(expedienteDto.getPersonal().getIdPersonal());
                            filtroPersonal.setFlagDefault(Constantes.ESTADO_ACTIVO);
                            List<PersonalDTO> personalUnidOrgDefault = personalServiceNeg.findPersonal(filtroPersonal);
                            if (personalUnidOrgDefault != null && personalUnidOrgDefault.size() > 0 && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault() != null && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica() != null) {
                            	unidadUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica().getIdUnidadOrganica(), null));
                            }
                            
                        	Map<String, String> parametros = new HashMap<String, String>();
                            parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                            parametros.put("{fecha_actual}", Utiles.padLeft(Calendar.getInstance().get(Calendar.DATE) + "/", 3, '0') + Utiles.padLeft(Calendar.getInstance().get(Calendar.MONTH) + "/", 3, '0') + Calendar.getInstance().get(Calendar.YEAR));
                            parametros.put("{usuario_remitente}", remitenteCorreo.get(0) != null ? remitenteCorreo.get(0).getNombreCompleto() : "");
                            parametros.put("{destinatario}", destinatario.get(0) != null ? destinatario.get(0).getNombreCompleto() : "");
                            parametros.put("{orden_servicio}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                            parametros.put("{unidad_usuario}", (unidadUO != null && unidadUO.size() != 0) ? unidadUO.get(0).getDescripcion() : "");
                            parametros.put("{asunto}", expedienteDto.getAsuntoSiged());
                        	comRenExpSiged = armarMensajeParametros(correo.get(0).getMensaje(), parametros);
                        }
                    	
                        ExpedienteDTO expedienteReenviarSiged = reenviarExpedienteSIGED(expedienteDto, destinatario.get(0).getIdPersonalSiged(), comRenExpSiged, false);
                        if (expedienteReenviarSiged.getNumeroExpediente() == null) {                          
                            expedienteDto.setMensajeServicio("<b>El servicio reenvío SIGED Asignar orden no disponible.</b><br>");
                            ordSerNoReeSigedMsg = expedienteDto.getMensajeServicio();
                            listaOrdSerNoReeSiged.add(unidad);
                        }
                    } else {
                        expedienteDto.setMensajeServicio("<b>Error en la obtención del id del Personal Siged del destinatario.</b><br>");
                        ordSerNoIdPerSigedMsg = expedienteDto.getMensajeServicio();
                        listaOrdSerNoIdPerSiged.add(unidad);
                    }
                }
            }
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
            retorno.put(ConstantesWeb.VV_MENSAJE, "Cambios realizados.");
            String ordSerCreada = concatenaOrdSerCreada(listaOrdSerCreada);
            String ordSerNoGenExpSiged = concatenaOrdSerNoGenExpSiged(listaOrdSerNoGenExpSiged);
            String ordSerNoReeSiged = concatenaOrdSerNoReeSiged(listaOrdSerNoReeSiged);
            String ordSerNoIdPerSiged = concatenaOrdSerNoIdPerSiged(listaOrdSerNoIdPerSiged);

            if (ordSerCreada != null && !ordSerCreada.equals("")) {
                retorno.put("ordSerCreadaConcatenada", ordSerCreada + "<br>");
            } else {
                retorno.put("ordSerCreadaConcatenada", "");
            }
            if (ordSerNoGenExpSiged != null && !ordSerNoGenExpSiged.equals("")) {
                retorno.put("ordSerNoGenExpSigedConcatenada", ordSerNoGenExpSiged + "<br>");
            } else {
                retorno.put("ordSerNoGenExpSigedConcatenada", "");
            }
            if (ordSerNoReeSiged != null && !ordSerNoReeSiged.equals("")) {
                retorno.put("ordSerNoReeSigedConcatenada", ordSerNoReeSiged + "<br>");
            } else {
                retorno.put("ordSerNoReeSigedConcatenada", "");
            }
            if (ordSerNoIdPerSiged != null && !ordSerNoIdPerSiged.equals("")) {
                retorno.put("ordSerNoIdPerSigedConcatenada", ordSerNoIdPerSiged + "<br>");
            } else {
                retorno.put("ordSerNoIdPerSigedConcatenada", "");
            }

            if (listaOrdSerCreada != null && listaOrdSerCreada.size() > 0) {
                retorno.put("msgOrdSerCreada", listaOrdSerCreada.get(0).getMensajeServicio());
            } else {
                retorno.put("msgOrdSerCreada", "");
            }
            if ((listaOrdSerNoReeSiged != null && listaOrdSerNoReeSiged.size() > 0) || (listaOrdSerNoGenExpSiged != null && listaOrdSerNoGenExpSiged.size() > 0) || (listaOrdSerNoIdPerSiged != null && listaOrdSerNoIdPerSiged.size() > 0)) {
                retorno.put("msgOrdSerNoCreada", "<b>ORDENES DE SERVICIO NO CREADAS:</b><br>");
            } else {
                retorno.put("msgOrdSerNoCreada", "");
            }
            if (listaOrdSerNoGenExpSiged != null && listaOrdSerNoGenExpSiged.size() > 0) {
                retorno.put("msgNoGenExpSiged", ordSerNoGenExpSigedMsg);
            } else {
                retorno.put("msgNoGenExpSiged", "");
            }
            if (listaOrdSerNoReeSiged != null && listaOrdSerNoReeSiged.size() > 0) {
                retorno.put("msgNoReeSiged", ordSerNoReeSigedMsg);
            } else {
                retorno.put("msgNoReeSiged", "");
            }
            if (listaOrdSerNoIdPerSiged != null && listaOrdSerNoIdPerSiged.size() > 0) {
                retorno.put("msgNoIdPerSiged", ordSerNoIdPerSigedMsg);
            } else {
                retorno.put("msgNoIdPerSiged", "");
            }
        } catch (Exception e) {
            LOG.error("error en asignarOrdenServicioPrincipal", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }
    
    private String armarMensajeParametros(String mensaje, Map<String, String> parametros) {
        LOG.info("armarMensajeParametros");
        try {
            Set<Map.Entry<String, String>> rawParameters = parametros.entrySet();
            for (Map.Entry<String, String> entry : rawParameters) {
            	String entrada = entry.getValue();
                mensaje = mensaje.replace(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        } catch (Exception e) {
            LOG.error("Error armarMensajeParametros", e);
        }
        return mensaje;
    }
    
    private String concatenaOrdSerCreada(List<ExpedienteDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (ExpedienteDTO maestra : filtros) {
                s[cont] = maestra.getOrdenServicio().getNumeroOrdenServicio().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    private String concatenaOrdSerNoGenExpSiged(List<UnidadSupervisadaDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (UnidadSupervisadaDTO maestra : filtros) {
                s[cont] = maestra.getCodigoOsinergmin().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    private String concatenaOrdSerNoReeSiged(List<UnidadSupervisadaDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (UnidadSupervisadaDTO maestra : filtros) {
                s[cont] = maestra.getCodigoOsinergmin().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }

    private String concatenaOrdSerNoIdPerSiged(List<UnidadSupervisadaDTO> filtros) {
        String retorno = "";
        if (filtros != null && filtros.size() > 0) {
            String[] s = new String[filtros.size()];
            int cont = 0;
            for (UnidadSupervisadaDTO maestra : filtros) {
                s[cont] = maestra.getCodigoOsinergmin().toString();
                cont++;
            }
            retorno = StringUtils.join(s, ",");
        }
        return retorno;
    }
    
    private Long obtenerPeriodoActual(Long cantPeriodos, int mes) {
        Long per = new Long(0);
        Long cantMeses = 12 / cantPeriodos;
        Long contPeriodo = new Long(0);
        Long longPeriodo = new Long(0);
        ArrayList<Long> dominio = new ArrayList<Long>();
        for (int i = 0; i < cantPeriodos + 1; i++) {
            longPeriodo = cantMeses * contPeriodo;
            contPeriodo++;
            dominio.add(longPeriodo);
        }
        contPeriodo = new Long(0);
        for (int i = 0; i < cantPeriodos; i++) {
            contPeriodo++;
            if (mes > dominio.get(i) && (mes < dominio.get(i + 1) || mes == dominio.get(i + 1))) {
                per = contPeriodo;
            }
        }
        LOG.info("Array Periodos: " + dominio);
        LOG.info("Periodo: " + per);
        return per;
    }
    /* OSINE_SFS-Ajustes - mdiosesf - Fin */
}
