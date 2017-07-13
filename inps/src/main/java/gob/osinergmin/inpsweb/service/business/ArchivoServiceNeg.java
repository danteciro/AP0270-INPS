/**
* Resumen
* Objeto		: ArchivoServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el ArchivoServiceNegImpl
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     06/06/2016      Mario Dioses Fernandez      Construir formulario de envio a Mensajeria, consumiendo WS
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
//htorress - RSIS 9 - Inicio
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
//htorress - RSIS 9 - Fin
import gob.osinergmin.siged.remote.rest.ro.in.EnviarMensajeriaIn;
import gob.osinergmin.siged.remote.rest.ro.out.ConsultaMensajeriaDocumentosItemOut;
import gob.osinergmin.siged.remote.rest.ro.out.EnviarMensajeriaOut;
import gob.osinergmin.siged.remote.rest.ro.out.ObtenerDestinatariosOut;
import gob.osinergmin.siged.remote.rest.ro.out.ObtenerOficinaRegionalOut;
import gob.osinergmin.siged.remote.rest.ro.out.ValidarDocumentoOut;
import gob.osinergmin.siged.remote.rest.ro.out.obtenerClienteDetalleOut;
import gob.osinergmin.siged.remote.rest.ro.out.query.ExpedienteConsultaOutRO;

import java.io.File;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface ArchivoServiceNeg {
    public List<DocumentoAdjuntoDTO> listarDocumentosSiged(String numeroExpediente);
    public File descargarArchivoSiged(DocumentoAdjuntoDTO archivo);
    // htorress - RSIS 9 - Inicio
    public DocumentoAdjuntoDTO agregarArchivoSiged(DocumentoAdjuntoDTO doc, Long idPersonalSiged, boolean tipoCarga) throws DocumentoAdjuntoException;
    List<DocumentoAdjuntoDTO> consultaVersionesArchivoSIGED(String nroExpediente, String idDocumento);
    // htorress - RSIS 9 - Fin
    // htorress - RSIS 15 - Inicio
    public DocumentoAdjuntoDTO enumerarDocumentoSiged(String numeroExpediente , Integer idDocumento, Integer idPersonalSiged) throws DocumentoAdjuntoException;
    public DocumentoAdjuntoDTO cambiarFirmante(String numeroExpediente, Integer idDocumento, Integer idPersonalSiged) throws DocumentoAdjuntoException;
    public DocumentoAdjuntoDTO firmarEnumerarDocumentoSiged(String numeroExpediente , Integer idDocumento, Integer idPersonalSiged) throws DocumentoAdjuntoException;
    public DocumentoAdjuntoDTO anularArchivoSiged(DocumentoAdjuntoDTO doc, String motivo, String numeroExpediente,Long idPersonalSiged) throws DocumentoAdjuntoException;
    // htorress - RSIS 15 - Fin
    /* OSINE_SFS-480 - RSIS 03 - Inicio */
    public ValidarDocumentoOut validarDocumentoSIGED(String esTipo, Long iIdDoc) throws DocumentoAdjuntoException; 
    public ObtenerOficinaRegionalOut listarOficinaRegionalSIGED(Long IdPersonalSIGED) throws DocumentoAdjuntoException; 
    public ObtenerDestinatariosOut listarDestinatarioSIGED(String IdPersonalSIGED) throws DocumentoAdjuntoException; 
    public obtenerClienteDetalleOut listarDetalleDestinatarioSIGED(Long nroIdentificacion, boolean estadoFlujo, String nroExpediente) throws DocumentoAdjuntoException;
    public ExpedienteConsultaOutRO findExpedienteSIGED(String nroExpediente) throws DocumentoAdjuntoException; 
    public EnviarMensajeriaOut registrarMensajeriaSIGED(EnviarMensajeriaIn paramMensajeria) throws DocumentoAdjuntoException;
    /* OSINE_SFS-480 - RSIS 03 - Fin */
    /* OSINE_SFS-480 - RSIS 06 - Inicio */
    public List<ConsultaMensajeriaDocumentosItemOut> listarArchivosMensajeria(String idMensajeria, Long idPersonalSiged);
    /* OSINE_SFS-480 - RSIS 06 - Fin */
}
