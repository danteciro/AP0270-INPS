/**
 * Resumen Objeto	: CorreoServiceNegImpl.java Descripción	: Clase de la capa de
 * negocio que contiene la implementación de los métodos declarados en la clase
 * interfaz CorreoServiceNeg Fecha de Creación	: 11/05/2016 PR de Creación	:
 * OSINE_SFS-480 Autor	: Luis García Reyna
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones  
 *      Motivo          Fecha           Nombre                Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_SFS-480      11/05/2016   Luis García Reyna        Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio.
 * OSINE_791-RSIS19   06/09/2016   Zosimo Chaupis Santur    Correo de Notificacion al Supervisor Regional para supervisión de una orden de supervisión DSR-CRITICIDAD
 * OSINE_SFS-791      10/10/2016   Mario Dioses Fernandez   Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
 * OSINE_SFS-791      11/10/2016   Mario Dioses Fernandez   Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
 * OSINE_SFS-791      11/10/2016   Mario Dioses Fernandez   Crear la tarea automática que cancele el registro de hidrocarburos
 * OSINE791-RSIS34    03/11/2016   Cristopher Paucar Torre  Confirma Tipo de Asignación.
*/
package gob.osinergmin.inpsweb.service.business.impl;
import gob.osinergmin.inpsweb.service.business.CorreoServiceNeg;
import gob.osinergmin.inpsweb.service.business.PersonalServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadOrganicaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.CorreoDAO;
import gob.osinergmin.inpsweb.service.exception.CorreoException;
import gob.osinergmin.inpsweb.service.exception.CustomException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.CorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.PersonalDTO;
import gob.osinergmin.mdicommon.domain.dto.RegistroHidrocarburoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.CorreoFilter;
import gob.osinergmin.mdicommon.domain.ui.PersonalFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadOrganicaFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;
import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("correoServiceNeg")
public class CorreoServiceNegImpl implements CorreoServiceNeg {

    private static final Logger LOG = LoggerFactory.getLogger(CorreoServiceNegImpl.class);
    @Inject
    private CorreoDAO correoDAO;
    @Inject
    private PersonalServiceNeg personalServiceNeg;
    @Inject
    private UnidadOrganicaServiceNeg unidadOrganicaServiceNeg;
    @Value("${param.mail.wl.lookup}")
    public String MAIL_WL;
    @Value("${mail.remitente.nombre}")
    public String MAIL_REMITENTE_NOMBRE;
    @Value("${mail.remitente.correo}")
    public String MAIL_REMITENTE_CORREO;

    @Override
    public List<CorreoDTO> findByFilter(CorreoFilter filter) {
    	List<CorreoDTO> correo = null;
    	try {
			correo = correoDAO.findByFilter(filter);
		} catch (CorreoException e) {
			return null;
		}
    	return correo;
    }
    
    /* OSINE_SFS-480 - RSIS 12 - Inicio */
    @Override
    public boolean enviarCorreoAsignarOS(PersonalDTO remitenteDto, PersonalDTO destinatarioDto, ExpedienteDTO expedienteDto) {
        LOG.info("enviarCorreoAsignarOS");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF019));
            if (correo != null && correo.size() > 0) {

                //datos destinatario
                String destinatario = destinatarioDto.getNombreCompleto();
                LOG.info("----------destinatario--------->" + destinatario);
                String nombreEmpresaDest = "";
                if (destinatarioDto.getLocador() != null) {
                    nombreEmpresaDest = destinatarioDto.getLocador().getNombreCompleto();
                } else if (destinatarioDto.getSupervisoraEmpresa() != null) {
                    nombreEmpresaDest = destinatarioDto.getSupervisoraEmpresa().getRazonSocial();
                }
                
                List<UnidadOrganicaDTO> unidadUO = null;
                PersonalFilter filtroPersonal = new PersonalFilter(); 
                filtroPersonal.setIdPersonal(expedienteDto.getPersonal().getIdPersonal());
                filtroPersonal.setFlagDefault(Constantes.ESTADO_ACTIVO);
                List<PersonalDTO> personalUnidOrgDefault = personalServiceNeg.findPersonal(filtroPersonal);
                if (personalUnidOrgDefault != null && personalUnidOrgDefault.size() > 0 && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault() != null && personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica() != null) {
                	unidadUO = unidadOrganicaServiceNeg.findUnidadOrganica(new UnidadOrganicaFilter(personalUnidOrgDefault.get(0).getPersonalUnidadOrganicaDefault().getUnidadOrganica().getIdUnidadOrganica(), null));
                }

                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNombreFlujoSiged-------->" + expedienteDto.getFlujoSiged().getNombreFlujoSiged());
                LOG.info("----------getNombreCompleto-------->" + remitenteDto.getNombreCompleto());
                LOG.info("----------destinatario-------->" + destinatario);
                LOG.info("----------nombreEmpresaDest-------->" + nombreEmpresaDest);
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());

                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{flujo_siged}", expedienteDto.getFlujoSiged().getNombreFlujoSiged());
                parametros.put("{fecha_actual}", Utiles.padLeft(Calendar.getInstance().get(Calendar.DATE) + "/", 3, '0') + Utiles.padLeft(Calendar.getInstance().get(Calendar.MONTH) + "/", 3, '0') + Calendar.getInstance().get(Calendar.YEAR));
                parametros.put("{usuario_remitente}", remitenteDto.getNombreCompleto());
                parametros.put("{destinatario}", destinatario);
                parametros.put("{nombre_empresa}", nombreEmpresaDest);
                parametros.put("{orden_servicio}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{unidad_usuario}", (unidadUO != null && unidadUO.size() != 0) ? unidadUO.get(0).getDescripcion() : "");
                parametros.put("{asunto}", expedienteDto.getAsuntoSiged());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);

                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);

                List<String> destTO = new ArrayList<String>();
                destTO.add(destinatarioDto.getCorreoElectronico());
                LOG.info("----------getCorreoElectronico--------->" + destinatarioDto.getCorreoElectronico());
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoAsignarOS", ex);
            retorno = false;
        }
        return retorno;
    }
    /* OSINE-791 - RSIS 34 - Inicio */
     @Override
    public boolean enviarCorreoConfirmTipoAsig(PersonalDTO remitenteDto, PersonalDTO destinatarioDto, ExpedienteDTO expedienteDto) {
        LOG.info("enviarCorreoAsignarOS");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF018));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                String destinatario = destinatarioDto.getNombreCompleto();
                LOG.info("----------destinatario--------->" + destinatario);
                String nombreEmpresaDest = "";
                if (destinatarioDto.getLocador() != null) {
                    nombreEmpresaDest = destinatarioDto.getLocador().getNombreCompleto();
                } else if (destinatarioDto.getSupervisoraEmpresa() != null) {
                    nombreEmpresaDest = destinatarioDto.getSupervisoraEmpresa().getRazonSocial();
                }

                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNombreFlujoSiged-------->" + expedienteDto.getFlujoSiged().getNombreFlujoSiged());
                LOG.info("----------getNombreCompleto-------->" + remitenteDto.getNombreCompleto());
                LOG.info("----------destinatario-------->" + destinatario);
                LOG.info("----------nombreEmpresaDest-------->" + nombreEmpresaDest);
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());

                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{orden_servicio}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);

                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);

                List<String> destTO = new ArrayList<String>();
                destTO.add(destinatarioDto.getCorreoElectronico());
                LOG.info("----------getCorreoElectronico--------->" + destinatarioDto.getCorreoElectronico());
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoAsignarOS", ex);
            retorno = false;
        }
        return retorno;
    }
    /* OSINE-791 - RSIS 34 - Fin */
    public String armarMensajeParametros(String mensaje, Map<String, String> parametros) {
        LOG.info("armarMensajeParametros");
        try {
            Set<Map.Entry<String, String>> rawParameters = parametros.entrySet();
            for (Map.Entry<String, String> entry : rawParameters) {
                mensaje = mensaje.replace(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        } catch (Exception e) {
            LOG.error("Error armarMensajeParametros", e);
        }
        return mensaje;
    }

    public boolean enviarCorreo(List<String> mailTO, List<String> mailCC, List<String> mailBCC,
        String mailFrom, String nombreFrom, String asunto, StringBuffer mensaje,DocumentoAdjuntoDTO adjunto) {
        LOG.info("enviarCorreo");
        boolean retorno = true;
        try {
            if (CollectionUtils.isEmpty(mailTO)){throw new CustomException("No existe registro de correo.");}
            
            //leera_desde_el_WebLogic
            LOG.info("leyendo desde Weblogic");
            LOG.info("-----------MAIL_WL---------->" + MAIL_WL);
            InitialContext ic = new InitialContext();
            Session sessionWebLogic = (Session) ic.lookup(MAIL_WL);

            Properties props = sessionWebLogic.getProperties();
            LOG.info("-->>mail.smtp.user: " + props.getProperty("mail.smtp.user"));

            LOG.info("pasando la linea de sessionWebLogic");
            LOG.info("Construyendo mensaje");
            // Construimos el mensaje
            MimeMessage message = new MimeMessage(sessionWebLogic);
            LOG.info("-----------mailFrom---->" + mailFrom);
            LOG.info("-----------nombreFrom---->" + nombreFrom);
            message.setFrom(new InternetAddress(mailFrom, nombreFrom));

            //destinatarios
            for (String mail : mailTO) {
                LOG.info("------>mailDestinatarioTO------>" + mail);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            }
            if (mailCC != null && mailCC.size() > 0) {
                for (String mail : mailCC) {
                    LOG.info("------>mailDestinatarioCC------>" + mail);
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(mail));
                }
            }
            if (mailBCC != null && mailBCC.size() > 0) {
                for (String mail : mailBCC) {
                    LOG.info("------>mailDestinatarioBCC------>" + mail);
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
                }
            }
            
            message.setSubject(asunto);
            message.setSentDate(new Date());
            
            // Crea un Multipar message
            Multipart multipart = new MimeMultipart();
            
            //part body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent( mensaje.toString(), "text/html; charset=utf-8" );
            // agrega texto message part
            multipart.addBodyPart(messageBodyPart);
            
            // archivo adjunto - part adjunto
            if(adjunto!=null && adjunto.getArchivoAdjunto()!=null && !StringUtil.isEmpty(adjunto.getNombreArchivo()) && !StringUtil.isEmpty(adjunto.getMimeType())){
                messageBodyPart = new MimeBodyPart();
                String fname=adjunto.getNombreArchivo();
                DataSource source = new ByteArrayDataSource(adjunto.getArchivoAdjunto(), adjunto.getMimeType());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fname);
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);

            Transport.send(message);
        } catch (CustomException e) {
            LOG.error("Error en enviarCorreo", e);
            retorno = false;
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreo", ex);
            retorno = false;
        }

        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 12 - Fin */

    /* OSINE_SFS-791 - RSIS 19 - Inicio */
    @Override
    public boolean enviarCorreoNotificacionOtrosIncumplimientos(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, SupervisionDTO supervisionDTO) {
        LOG.info("enviarCorreoNotificacionOtrosIncumplimientos");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF003));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNombreFlujoSiged-------->" + expedienteDto.getFlujoSiged().getNombreFlujoSiged());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{otros_incumplimientos}", supervisionDTO.getOtrosIncumplimientos());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionOtrosIncumplimientos", ex);
            retorno = false;
        }
        return retorno;
    }

    @Override
    public boolean enviarCorreoNotificacionNoEjecucionMedida(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto) {
        LOG.info("enviarCorreoNotificacionNoEjecucionMedida");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF004));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNombreFlujoSiged-------->" + expedienteDto.getFlujoSiged().getNombreFlujoSiged());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);

                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionNoEjecucionMedida", ex);
            retorno = false;
        }
        return retorno;
    }

    @Override
    public boolean enviarCorreoNotificacionObstaculizacionObligaciones(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, List<DetalleSupervisionDTO> listadetalleSupervisionDTO) {
      LOG.info("enviarCorreoNotificacionObstaculizacionObligaciones");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF005));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNombreFlujoSiged-------->" + expedienteDto.getFlujoSiged().getNombreFlujoSiged());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                String llaveCuerpoTabla = "{data_tabla}";

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                mensaje = armarCuerpoTablaObstaculizacionObligaciones(mensaje, listadetalleSupervisionDTO, llaveCuerpoTabla);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);

                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                } 
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionObstaculizacionObligaciones", ex);
            retorno = false;
        }
        return retorno; 
    }

    public String armarCuerpoTablaObstaculizacionObligaciones(String mensaje, List<DetalleSupervisionDTO> lista, String llave) {
        LOG.info("armarCuerpoTablaObstaculizacionObligaciones");
        try {
            String tabla = "";
            tabla += "<tr><td>Infracci&oacute;n</td> <td>Comentarios</td></tr>";//armando Cabecera de la tabla
            for (DetalleSupervisionDTO detalleSupervisionDTO : lista) {
                tabla += "<tr>";
                //tabla += "<td>" + detalleSupervisionDTO.getObligacion().getInfraccion().getDescripcionInfraccion() + "</td>";
                tabla += "<td>" + detalleSupervisionDTO.getInfraccion().getDescripcionInfraccion() + "</td>";
                tabla += "<td>" + detalleSupervisionDTO.getComentario() + "</td>";
                tabla += "</tr>";
            }
            mensaje = mensaje.replace(llave, tabla);
        } catch (Exception e) {
            LOG.error("Error armarCuerpoTablaObstaculizacionObligaciones", e);
        }
        return mensaje;
    }
    /* OSINE_SFS-791 - RSIS 19 - Fin */
    
    /* OSINE_SFS-791 - RSIS 18 - Inicio */
    
    //CORREO1
    @Override
    public boolean enviarCorreoNotificacionMedidaSeguridad(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto) {
        LOG.info("enviarCorreoNotificacionMedidaSeguridad");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF006));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionOtrosIncumplimientos", ex);
            retorno = false;
        }
        return retorno;
    }
    
    //CORREO2
    @Override
    public boolean enviarCorreoNotificacionSuspensionRH(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto) {
        LOG.info("enviarCorreoNotificacionSuspensionRH");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF007));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nro_registro}", registroHidrocarburoDto.getNumeroRegistroHidrocarburo());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionSuspensionRH", ex);
            retorno = false;
        }
        return retorno;
    }
    
    //CORREO3
    @Override
    public boolean enviarCorreoNotificacionDesactivarRolComprasScop(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto, UnidadSupervisadaDTO unidadSupervisadaDto) {
        LOG.info("enviarCorreoNotificacionDesactivarRolComprasScop");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF008));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nro_registro}", registroHidrocarburoDto.getNumeroRegistroHidrocarburo());
                parametros.put("{agente}", unidadSupervisadaDto.getNombreUnidad());
                parametros.put("{codigo_osinergmin}", unidadSupervisadaDto.getCodigoOsinergmin());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionDesactivarRolComprasScop", ex);
            retorno = false;
        }
        return retorno;
    }
    
    //CORREO4
    @Override
    public boolean enviarCorreoNotificacionDesactivarRolComprasScopProducto(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto, UnidadSupervisadaDTO unidadSupervisadaDto,byte[] reporteBytes) {
        LOG.info("enviarCorreoNotificacionDesactivarRolComprasScopProducto");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF009));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nro_registro}", registroHidrocarburoDto.getNumeroRegistroHidrocarburo());
                parametros.put("{agente}", unidadSupervisadaDto.getNombreUnidad());
                parametros.put("{codigo_osinergmin}", unidadSupervisadaDto.getCodigoOsinergmin());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
               
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                
                DocumentoAdjuntoDTO xlsAdj = null;
                if(reporteBytes!=null){
                    xlsAdj=new DocumentoAdjuntoDTO();
                    xlsAdj.setArchivoAdjunto(reporteBytes);
                    xlsAdj.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_EXCEL_PRODUCTOS_SUSPENDIDOS);
                    xlsAdj.setMimeType(Constantes.MIME_TYPE_XSL);
                }
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,xlsAdj);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionDesactivarRolComprasScopProducto", ex);
            retorno = false;
        }
        return retorno;
    }        
    /* OSINE_SFS-791 - RSIS 18 - Fin */
    
    /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
    @Override
    public boolean enviarCorreoNotificacionTipoAsignacion(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto) {
        LOG.info("enviarCorreoNotificacionTipoAsignacion");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF010));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nuevo_tipo_asignacion}", expedienteDto.getOrdenServicio().getDescTipoAsignacion());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionTipoAsignacion", ex);
            retorno = false;
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 33 - Fin */ 
    /* OSINE_SFS-791 - RSIS 4 - Inicio */
    @Override
    public boolean EnvioNotificacionesSupervisionDsrObstaculizadaInicial(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, SupervisionDTO supervisionDTO) {
      LOG.info("EnvioNotificacionesSupervisionDsrObstaculizadaInicial");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF011));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                String observacion = "";
                if(supervisionDTO.getObservacion() != null){
                    observacion = supervisionDTO.getObservacion();
                }                
                parametros.put("{comentario_obstaculizacion}", observacion);

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en EnvioNotificacionesSupervisionDsrObstaculizadaInicial", ex);
            retorno = false;
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 4 - Fin */
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
    @Override
    public boolean enviarCorreoNotificacionTareaProg(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, CorreoFilter filtro) {
        LOG.info("enviarCorreoNotificacionTareaProg");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(filtro);
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                LOG.info("----------getNroRegistroHidrocarburo-------->" + expedienteDto.getUnidadSupervisada().getNroRegistroHidrocarburo());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                //Parametros del RSIS 46 - 47
                parametros.put("{nro_expediente}", (expedienteDto.getNumeroExpediente()!=null) ? expedienteDto.getNumeroExpediente() : "--");
                parametros.put("{nro_orden}", (expedienteDto.getOrdenServicio().getNumeroOrdenServicio()!=null) ? expedienteDto.getOrdenServicio().getNumeroOrdenServicio() : "--");
                parametros.put("{nro_registro}", (expedienteDto.getUnidadSupervisada().getNroRegistroHidrocarburo()!=null) ? expedienteDto.getUnidadSupervisada().getNroRegistroHidrocarburo() : "--");

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr, null);            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionTareaProg", ex);
            retorno = false;
        }
        return retorno;
    }
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
    /* OSINE_SFS-791 - RSIS 40 - Inicio */ 
     //CORREO HABILITACION RH
    @Override
    public boolean enviarCorreoNotificacionHabilitacionRH(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto) {
        LOG.info("enviarCorreoNotificacionHabilitacionRH");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF012));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nro_registro}", registroHidrocarburoDto.getNumeroRegistroHidrocarburo());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionHabilitacionRH", ex);
            retorno = false;
        }
        return retorno;
    }
    //CORREO Habilitacion rol de compras SCOP
    @Override
    public boolean enviarCorreoNotificacionHabilitacionRolComprasScop(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto, UnidadSupervisadaDTO unidadSupervisadaDto) {
        LOG.info("enviarCorreoNotificacionHabilitacionRolComprasScop");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF013));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nro_registro}", registroHidrocarburoDto.getNumeroRegistroHidrocarburo());
                parametros.put("{agente}", unidadSupervisadaDto.getNombreUnidad());
                parametros.put("{codigo_osinergmin}", unidadSupervisadaDto.getCodigoOsinergmin());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,null);            
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionHabilitacionRolComprasScop", ex);
            retorno = false;
        }
        return retorno;
    }
    //CORREO ENVIO DE NOTIFICACION HABILITACION DE COMPRAS POR PRODUCTOS
    @Override
    public boolean enviarCorreoNotificacionHabilitarRolComprasScopProducto(List<DestinatarioCorreoDTO> listadestinatarioCorreoDTO, ExpedienteDTO expedienteDto, RegistroHidrocarburoDTO registroHidrocarburoDto, UnidadSupervisadaDTO unidadSupervisadaDto,byte[] reporteBytes) {
        LOG.info("enviarCorreoNotificacionHabilitarRolComprasScopProducto");
        boolean retorno;
        try {
            //obtener asunto y mensaje
            List<CorreoDTO> correo = correoDAO.findByFilter(new CorreoFilter(Constantes.CODIGO_FUNCIONALIDAD_TF014));
            if (correo != null && correo.size() > 0) {
                //datos destinatario
                LOG.info("----------getNumeroExpediente-------->" + expedienteDto.getNumeroExpediente());
                LOG.info("----------getNumeroOrdenServicio-------->" + expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                //armando mensaje y asunto - replace
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("{nro_expediente}", expedienteDto.getNumeroExpediente());
                parametros.put("{nro_orden}", expedienteDto.getOrdenServicio().getNumeroOrdenServicio());
                parametros.put("{nro_registro}", registroHidrocarburoDto.getNumeroRegistroHidrocarburo());
                parametros.put("{agente}", unidadSupervisadaDto.getNombreUnidad());
                parametros.put("{codigo_osinergmin}", unidadSupervisadaDto.getCodigoOsinergmin());

                String asunto = correo.get(0).getAsunto();
                String mensaje = correo.get(0).getMensaje();
                asunto = armarMensajeParametros(asunto, parametros);
                mensaje = armarMensajeParametros(mensaje, parametros);
               
                //envio correo                
                StringBuffer mensajeBfr = new StringBuffer();
                mensajeBfr.append(mensaje);
                List<String> destTO = new ArrayList<String>();
                for (DestinatarioCorreoDTO destino : listadestinatarioCorreoDTO) {
                    LOG.info("----------destinatario--------->" + destino.getPersonalDTO().getNombreCompleto());
                    LOG.info("----------getCorreoElectronico--------->" + destino.getPersonalDTO().getCorreoElectronico());
                    destTO.add(destino.getPersonalDTO().getCorreoElectronico());
                }         
                LOG.info("----------MAIL_REMITENTE_CORREO--------->" + MAIL_REMITENTE_CORREO);
                LOG.info("----------MAIL_REMITENTE_NOMBRE--------->" + MAIL_REMITENTE_NOMBRE);
                
                DocumentoAdjuntoDTO xlsAdj = null;
                if(reporteBytes!=null){
                    xlsAdj=new DocumentoAdjuntoDTO();
                    xlsAdj.setArchivoAdjunto(reporteBytes);
                    xlsAdj.setNombreArchivo(Constantes.NOMBRE_ARCHIVO_EXCEL_PRODUCTOS_SUSPENDIDOS);
                    xlsAdj.setMimeType(Constantes.MIME_TYPE_XSL);
                }
                retorno = enviarCorreo(destTO, null, null, MAIL_REMITENTE_CORREO, MAIL_REMITENTE_NOMBRE, asunto, mensajeBfr,xlsAdj);
            } else {
                retorno = false;
            }
        } catch (Exception ex) {
            LOG.error("Error en enviarCorreoNotificacionHabilitarRolComprasScopProducto", ex);
            retorno = false;
        }
        return retorno;
    }        
    /* OSINE_SFS-791 - RSIS 40 - Fin */ 
}
