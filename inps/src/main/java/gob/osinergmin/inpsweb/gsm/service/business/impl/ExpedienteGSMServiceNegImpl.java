/**
* Resumen		
* Objeto			: ExpedienteGSMServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz ExpedienteServiceNeg
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
*  
*/ 

package gob.osinergmin.inpsweb.gsm.service.business.impl;
import gob.osinergmin.inpsweb.gsm.service.business.ExpedienteGSMServiceNeg;
import gob.osinergmin.inpsweb.gsm.service.dao.ExpedienteGSMDAO;
import gob.osinergmin.inpsweb.service.business.EmpresaSupervisadaServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.EstadoProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteGSMDTO;
//htorress - RSIS 1 y 2 - Inicio
import gob.osinergmin.mdicommon.domain.dto.PersonalAsignadoDTO;
//htorress - RSIS 1 y 2 - Fin
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.EstadoProcesoFilter;
import gob.osinergmin.mdicommon.domain.ui.ExpedienteFilter;
import gob.osinergmin.inpsweb.service.business.HstEstadoLevantamientoServiceNeg;
import gob.osinergmin.inpsweb.service.business.OrdenServicioServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.dao.EstadoProcesoDAO;
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
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.HstEstadoLevantamientoDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxEmpSupervisada;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service("expedienteGSMServiceNeg")
public class ExpedienteGSMServiceNegImpl implements ExpedienteGSMServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteGSMServiceNegImpl.class);

    @Inject
    private ExpedienteGSMDAO expedienteDAO;
    @Inject
    private EstadoProcesoDAO estadoProcesoDAO;
    @Inject
    private EmpresaSupervisadaServiceNeg empresaSupervisadaServiceNeg;
    // htorress - RSIS 1 y 2 - Inicio
    @Inject
    private PersonalAsignadoDAO personalAsignadoDAO;
    // htorress - RSIS 1 y 2 - Fin
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    @Inject
    private SupervisionDAO supervisionDAO;
    @Inject
    private HstEstadoLevantamientoServiceNeg hstEstadoLevantamientoServiceNeg;
    @Inject
    private OrdenServicioServiceNeg ordenServicioServiceNeg;
    // htorress - RSIS 18 - Inicio
    @Value("${siged.host.ws}")
    private String HOST_SIGED;
    // htorress - RSIS 18 - Fin
    @Value("${siged.host}")
    private String HOST;
    @Value("${ruta.plantilla.nuevo.expediente}")
    private String RUTA_PLANTILLA_NUEVO_EXPEDIENTE;

    @Override
    @Transactional(readOnly = true)
    public List<ExpedienteGSMDTO> listarExpediente(ExpedienteFilter filtro) {
        LOG.info("listarExpediente");
        List<ExpedienteGSMDTO> retorno = null;
        try {
            EstadoProcesoDTO estadoProcesoDto = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)).get(0);
            filtro.setIdEstadoProcesoDerivado(estadoProcesoDto.getIdEstadoProceso());
            // htorress - RSIS 1 y 2 - Inicio
            if (filtro.getIdentificadorRol() != null && !filtro.getIdentificadorRol().equals("")) {

                if ((filtro.getIdentificadorRol().equals(Constantes.IDENTIFICADOR_ROL_GERENTE_DIVISION))) {
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
    public PersonalDTO derivar(List<ExpedienteGSMDTO> expedientes, Long idPersonalOri, Long idPersonalDest, String motivoReasignacion, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.info("derivar");
        PersonalDTO retorno = new PersonalDTO();
        retorno.setIdPersonal(idPersonalDest);
        try {
            EstadoProcesoDTO estadoProcesoDto = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO)).get(0);
            List<ExpedienteGSMDTO> regExpedientes = new ArrayList<ExpedienteGSMDTO>();
            for (ExpedienteGSMDTO expediente : expedientes) {
            	ExpedienteGSMDTO expedienteDTO = expedienteDAO.cambiarEstadoProceso(expediente.getIdExpediente(), idPersonalDest, idPersonalOri, idPersonalDest, estadoProcesoDto.getIdEstadoProceso(), motivoReasignacion, usuarioDTO);
                regExpedientes.add(expedienteDTO);

                //Invocar a Servicio SIGED - Reenviar
                // ExpedienteDTO expedienteSiged= reenviarExpedienteSIGED(expedienteDTO, idPersonalDest, motivoReasignacion,false);
            }
//            retorno.setExpedientes(regExpedientes);
        } catch (Exception e) {
            LOG.error("Error derivar", e);
            throw new ExpedienteException(e.getMessage(), e);
        }
        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpedienteGSMDTO> listarDerivadosByIdPersonal(ExpedienteFilter filtro) throws ExpedienteException {
        return expedienteDAO.findDerivadosByIdPersonal(filtro);
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteGSMDTO asignarUnidadSupervisada(ExpedienteGSMDTO expedienteDto, UsuarioDTO usuarioDTO) throws ExpedienteException {
        return expedienteDAO.asignarUnidadSupervisada(expedienteDto, usuarioDTO);
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteGSMDTO asignarOrdenServicio(ExpedienteGSMDTO expedienteDto, String codigoTipoSupervisor, UsuarioDTO usuarioDTO, String sigla) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();
        try {
            EstadoProcesoDTO estadoProcesoExp = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_EXP_ASIGNADO)).get(0);
            expedienteDto.setEstadoProceso(estadoProcesoExp);

            EstadoProcesoDTO estadoProcesoOS = estadoProcesoDAO.find(new EstadoProcesoFilter(Constantes.IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO)).get(0);
            expedienteDto.getOrdenServicio().setEstadoProceso(estadoProcesoOS);
            /* OSINE791 - RSIS1 - Inicio */
            retorno = expedienteDAO.asignarOrdenServicio(expedienteDto, codigoTipoSupervisor, usuarioDTO, sigla);
            /* OSINE791 - RSIS1 - Fin */

            //Invocar a Servicio SIGED - Devolver
            //ExpedienteDTO expedienteSiged= rechazarExpedienteSIGED(expedienteDto);
        } catch (Exception e) {
            LOG.error("error en asignarOrdenServicio", e);
            throw new ExpedienteException(e.getMessage(), e);
        }

        return retorno;
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteGSMDTO generarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDto, String codigoTipoSupervisor, UsuarioDTO usuarioDTO, String sigla) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();
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
    public ExpedienteGSMDTO generarExpedienteSiged(ExpedienteGSMDTO expedienteDTO, Long idPersonalSiged) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();
        try {
            ExpedienteInRO expedienteInRO = new ExpedienteInRO();
            expedienteInRO.setProceso(expedienteDTO.getFlujoSiged().getCodigoSiged().intValue());//Obligatorio, si es expediente nuevo test 257
            expedienteInRO.setHistorico(Constantes.ESTADO_SIGED_NO);
            expedienteInRO.setExpedienteFisico(Constantes.ESTADO_SIGED_NO);//Flag que indica si el expediente es físico (ingresado por MP) o si es virtual.
            expedienteInRO.setDestinatario(idPersonalSiged.intValue());//En el excel dice que no es obligatorio //2582 avalos ruiz armando alexander
            //expedienteInRO.setNotificacion(1);

            //OFICIO
            DocumentoInRO documentoInRO = new DocumentoInRO();
            documentoInRO.setCodTipoDocumento(3);//3 oficio
            documentoInRO.setAsunto(expedienteDTO.getAsuntoSiged());
            documentoInRO.setAppNameInvokes("");
            documentoInRO.setPublico(Constantes.ESTADO_SIGED_SI);
            // htorress - RSIS 18 - Inicio
            //documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setEnumerado(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 18 - Fin
            documentoInRO.setEstaEnFlujo(Constantes.ESTADO_SIGED_SI);
            // htorress - RSIS 18 - Inicio
            //documentoInRO.setFirmado(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setFirmado(Constantes.ESTADO_SIGED_NO);
            // htorress - RSIS 18 - Fin
            documentoInRO.setDelExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setNroFolios(1);
            documentoInRO.setCreaExpediente(Constantes.ESTADO_SIGED_SI);
            documentoInRO.setUsuarioCreador(idPersonalSiged.intValue());//2582 avalos ruiz armando alexander
            if (expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada() == null && expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada() == null) {
                throw new ExpedienteException("No existe Identificador de Empresa Supervisada.", null);
            }
            EmpresaSupDTO emprSupe = empresaSupervisadaServiceNeg.obtenerEmpresaSupervisada(new EmpresaSupDTO(expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada()));
            List<BusquedaDireccionxEmpSupervisada> listarDireccionEmpresaSupervisada = empresaSupervisadaServiceNeg.listarDireccionEmpresaSupervisada(expedienteDTO.getUnidadSupervisada().getEmpresaSupervisada().getIdEmpresaSupervisada());
            BusquedaDireccionxEmpSupervisada direEmprSupe = listarDireccionEmpresaSupervisada != null && listarDireccionEmpresaSupervisada.size() > 0 ? listarDireccionEmpresaSupervisada.get(0) : null;

            if (direEmprSupe == null) {
                throw new ExpedienteException("No existe Direcci&oacute;n para la Empresa Supervisada.", null);
            }
            //CLIENTE
            ClienteInRO cliente1 = new ClienteInRO();
            int codiTipoIdent = 3;
            if (emprSupe.getRuc() != null) {
                codiTipoIdent = 1;
            } else if (emprSupe.getTipoDocumentoIdentidad() != null && emprSupe.getTipoDocumentoIdentidad().getCodigo().equals(Constantes.CODIGO_TIPO_DOCUMENTO_DNI)) {
                codiTipoIdent = 2;
            }
            cliente1.setCodigoTipoIdentificacion(codiTipoIdent);
            cliente1.setNroIdentificacion((codiTipoIdent == 1) ? emprSupe.getRuc() : emprSupe.getNroIdentificacion());
            cliente1.setTipoCliente(3);
            if (codiTipoIdent == 1) {
                cliente1.setRazonSocial(emprSupe.getRazonSocial());//Obligatorio si codigoTipoIdentificacion es 1.
            } else {
                cliente1.setNombre(emprSupe.getNombre());//No es obligatorio si codigoTipoIdentificacion es 1.
                cliente1.setApellidoPaterno(emprSupe.getApellidoPaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
                cliente1.setApellidoMaterno(emprSupe.getApellidoMaterno());//No es obligatorio si codigoTipoIdentificacion es 1.
            }

            DireccionxClienteInRO direccion1 = new DireccionxClienteInRO();
            direccion1.setDireccion(direEmprSupe.getDireccionCompleta());
            direccion1.setDireccionPrincipal(true);
            direccion1.setTelefono(emprSupe.getTelefono());
            direccion1.setUbigeo(Integer.parseInt(
                    Utiles.padLeft(direEmprSupe.getIdDepartamento().toString(), 2, '0')
                    + Utiles.padLeft(direEmprSupe.getIdProvincia().toString(), 2, '0')
                    + Utiles.padLeft(direEmprSupe.getIdDistrito().toString(), 2, '0')
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

            List<File> files = new ArrayList<File>();
            files.add(new File(RUTA_PLANTILLA_NUEVO_EXPEDIENTE + "nuevo_expediente.txt"));
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

    // htorress - RSIS 18 - Inicio
    @Override
    public ExpedienteGSMDTO reenviarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO, Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();

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
    public ExpedienteGSMDTO aprobarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO, Long destinatario, String contenido, boolean aprobacion) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();

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
    public ExpedienteGSMDTO archivarExpedienteSIGED(String nroExpediente, String contenido) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();

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
    public ExpedienteGSMDTO reabrirExpedienteSIGED(String nroExpediente) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();

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
    public ExpedienteGSMDTO rechazarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO, String motivo) throws ExpedienteException {
    	ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();

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
    public ExpedienteGSMDTO editarExpedienteOrdenServicio(ExpedienteGSMDTO expedienteDTO, String codigoTipoSupervisor, PersonalDTO personalDest, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.info("editarExpediente");
        ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();
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
    public ExpedienteActualizarTipoProcesoOut editarExpedienteSIGED(ExpedienteGSMDTO expedienteDTO) throws ExpedienteException {
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
            if (expedienteDTO.getEmpresaSupervisada() != null && expedienteDTO.getEmpresaSupervisada().getRuc() != null) {
                expediente.setNumeroIdentificacion(expedienteDTO.getEmpresaSupervisada().getRuc());
            } else if (expedienteDTO.getEmpresaSupervisada() != null && expedienteDTO.getEmpresaSupervisada().getRuc() != null) {
                expediente.setNumeroIdentificacion(expedienteDTO.getEmpresaSupervisada().getNroIdentificacion().toString());
            }
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
        	ExpedienteGSMDTO expediente = new ExpedienteGSMDTO();
            expediente.setIdExpediente(idExpediente);
            expediente.setAsuntoSiged(asuntoSiged);
            expediente.setNumeroExpediente(numeroExpediente);

            PersonalDTO destinatario = (PersonalDTO) personalServiceNeg.obtenerUsuarioOrigen(idOrdenServicio);

            ExpedienteGSMDTO expedienteAprobar = aprobarExpedienteSIGED(expediente, destinatario.getIdPersonalSiged(), "Aprobar.", true);
            //evalua archivar
            List<SupervisionDTO> listaSupervision = supervisionDAO.find(new SupervisionFilter(null, idOrdenServicio));
            if (!CollectionUtils.isEmpty(listaSupervision)) {
                SupervisionDTO supervisionRegistro = listaSupervision.get(0);
                if (supervisionRegistro.getResultadoSupervisionDTO() != null && supervisionRegistro.getResultadoSupervisionDTO().getIdResultadosupervision() != null) {
                    if (supervisionRegistro.getResultadoSupervisionDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE)
                            || supervisionRegistro.getResultadoSupervisionDTO().getCodigo().equals(Constantes.CODIGO_RESULTADO_SUPERVISION_CUMPLE_OBSTACULIZADO)) {
                    	ExpedienteGSMDTO expedienteArchivar = archivarExpedienteSIGED(String.valueOf(numeroExpediente), "Archivar.");
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
    public List<ExpedienteGSMDTO> findByFilter(ExpedienteFilter filtro) throws ExpedienteException {
        LOG.info("findByFilter");
        List<ExpedienteGSMDTO> retorno = null;
        try {
            retorno = expedienteDAO.findByFilter(filtro);
        } catch (Exception e) {
            LOG.error("Error findByFilter", e);
        }
        return retorno;
    }

    @Override
    @Transactional(rollbackFor = ExpedienteException.class)
    public ExpedienteGSMDTO veriActuFlgTramFinalizadoDsr(Long idExpediente, Long idOrdenServicio, Long idResultadoSupervision, UsuarioDTO usuario) throws ExpedienteException {
        LOG.info("veriActuFlgTramFinalizadoDsr");
        ExpedienteGSMDTO retorno = null;
        try {
            retorno = expedienteDAO.veriActuFlgTramFinalizadoDsr(idExpediente, idOrdenServicio, idResultadoSupervision, usuario);
        } catch (Exception e) {
            LOG.error("Error veriActuFlgTramFinalizadoDsr", e);
        }
        return retorno;
	}
    
    @Override
	@Transactional(rollbackFor=ExpedienteException.class)
	public ExpedienteGSMDTO actualizar(ExpedienteGSMDTO expediente, UsuarioDTO usuario) throws ExpedienteException {
    	LOG.info("actualizar");
    	ExpedienteGSMDTO retorno=null;
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
    public ExpedienteGSMDTO actualizarExpedienteHabModuloLevantamiento(ExpedienteGSMDTO expedienteDTO, UsuarioDTO usuarioDTO) throws ExpedienteException {
        LOG.info("actualizarExpedienteHabModuloLevantamiento");
        ExpedienteGSMDTO retorno = new ExpedienteGSMDTO();
        try {
            //Actualizando el Expediente para el modulo de levantamientos
            retorno = expedienteDAO.actualizarExpediente(expedienteDTO, usuarioDTO);
            if (retorno != null) {
                //se registrara el historial del estado levantamiento
                HstEstadoLevantamientoDTO hstEstadoLevantamientoDTO = new HstEstadoLevantamientoDTO();
                hstEstadoLevantamientoDTO.setEstado(Constantes.ESTADO_ACTIVO);
//                hstEstadoLevantamientoDTO.setExpedienteDTO(retorno);
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
}
