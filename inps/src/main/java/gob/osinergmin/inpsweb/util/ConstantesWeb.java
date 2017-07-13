/**
* Resumen		
* Objeto		: ConstantesWeb.java
* Descripción		: Variables del sistema que mantienen un valor inmutable a lo largo de toda la vida del sistema.
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     18/05/2016      Luis García Reyna               Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones - Transportar rubro (Actividad) asociado por expediente
* OSINE_SFS-480     19/05/2016      Giancarlo Villanueva Andrade    Transportar lista unidades supervisadas asociadas al expediente
* OSINE_SFS-480     20/05/2016      Hernán Torres Saenz             Crear la interfaz "Anular orden de servicio" de acuerdo a especificaciones.
* OSINE_SFS-480     27/05/2016      Luis García Reyna               Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
* OSINE_SFS-480     06/06/2016      Mario Dioses Fernandez          Construir formulario de envio a Mensajeria, consumiendo WS
* OSINE_791-RSIS8   31/08/2016      Cristopher Paucar Torre         Registrar Medio Probatorio.
* OSINE791-RSIS25   08/09/2016     	Alexander Vilca Narvaez 	    Crear la interfaz para consultar el comentario de ejecución de medida
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*/

package gob.osinergmin.inpsweb.util;
/**
 *
 * @author jpiro
 */
public class ConstantesWeb {
    public static final int VV_EXITO = 0;
    public static final int VV_ERROR = 1;
    public static final int VV_ADVERTENCIA = 2;
    
    public static final String VV_RESULTADO = "resultado";
    public static final String VV_MENSAJE = "mensaje";
    /* OSINE_SFS-1063 - RSIS 05 - Inicio */
    public static final String SUCCESS = "SUCCESS";
    public static final String RESTRICT="RESTRICT";
    public static final String RESTRICT_INF_ADICIONAL="RESTRICT_INF_ADICIONAL";
    /* OSINE_SFS-1063 - RSIS 05 - Fin */
    private ConstantesWeb() {
    }
    
    public static class Navegacion{
        public static final String PAGE_INPS_VISTA_DESAUTORIZADA="common/unauthorized";
        
        public static final String PAGE_INPS_ORDEN_SERVICIO="common/ordenServicio/ordenServicio";
        /* OSINE_SFS-791 - RSIS 33 - Inicio */
        public static final String PAGE_INPS_ORDEN_SERVICIO_LEVANTAMIENTO="common/ordenServicio/ordenServicioLevantamiento";
        /* OSINE_SFS-791 - RSIS 33 - Fin */
        public static final String PAGE_INPS_ORDEN_SERVICIO_ASIGNAR="common/ordenServicio/ordenServicioAsignar";
        public static final String PAGE_INPS_ORDEN_SERVICIO_ATENDER="common/ordenServicio/ordenServicioAtender";
        public static final String PAGE_INPS_ORDEN_SERVICIO_CONSULTAR="common/ordenServicio/ordenServicioConsultar";
        public static final String PAGE_INPS_ORDEN_SERVICIO_REVISAR="common/ordenServicio/ordenServicioRevisar";
        public static final String PAGE_INPS_ORDEN_SERVICIO_APROBAR="common/ordenServicio/ordenServicioAprobar";
        
        public static final String PAGE_INPS_ORDEN_SERVICIO_APROBAR_GSM="GSM/common/ordenServicio/ordenServicioAprobar";
        public static final String PAGE_INPS_ORDEN_SERVICIO_NOTIFICAR="common/ordenServicio/ordenServicioNotificar";
        public static final String PAGE_INPS_ORDEN_SERVICIO_CONCLUIR="common/ordenServicio/ordenServicioConcluir";
        public static final String PAGE_INPS_ORDEN_SERVICIO_CONFIRMAR_DESCARGO="common/ordenServicio/ordenServicioConfDes";
        /* OSINE_SFS-480 - RSIS 27 - Inicio */
        public static final String PAGE_INPS_ORDEN_SERVICIO_MASIVO="common/ordenServicio/ordenServicioMasivo";
        /* OSINE_SFS-480 - RSIS 27 - Fin */
        /* OSINE_SFS-480 - RSIS 03 - Inicio */
        public static final String PAGE_INPS_ORDEN_SERVICIO_ENVIA_MENSAJERIA="common/ordenServicio/ordenServicioEnviaMensajeria"; 
        /* OSINE_SFS-480 - RSIS 03 - Fin */
        public static final String PAGE_INPS_BUSQUEDA_EXPEDIENTE="common/expediente/busquedaExpediente";
        public static final String PAGE_INPS_MANTENIMIENTO_UNIDAD_OPERATIVA="common/unidadOperativa/mantUnidadOperativa";
        public static final String PAGE_INPS_UNIDAD_OPERATIVA="common/unidadOperativa/unidadOperativa";
        public static final String PAGE_INPS_SUBIR_DOCUMENTO="common/expediente/subirDocumento";
        public static final String PAGE_INPS_ORDEN_SERVICIO_SUPERVISOR="common/ordenServicio/ordenServicioSupervisor";
        /* OSINE_SFS-480 - RSIS 38 - Inicio */
        public static final String PAGE_INPS_ORDEN_SERVICIO_SUPERVISOR_DEVOLVER="common/ordenServicio/ordenServicioSupervisorDevolver";
        /* OSINE_SFS-480 - RSIS 38 - Fin */
        public static final String PAGE_INPS_REASIGNAR_EXPEDIENTE="common/expediente/reasignarExpediente";
        public static final String PAGE_INPS_DOCUMENTO_EXPEDIENTE="common/expediente/documentoExpediente";
        public static final String PAGE_INPS_TRAZABILIDAD_ORDEN_SERVICIO="common/ordenServicio/trazabilidadOrdenServicio";       
        /* OSINE_SFS-480 - RSIS 06 - Inicio */ 
         public static final String PAGE_INPS_CONSULTA_MENSAJERIA_EXPEDIENTE="common/expediente/consultaMensajeriaExpediente";     
        /* OSINE_SFS-480 - RSIS 06 - Fin */ 
         //vubaldo inicio
        public static final String PAGE_INPS_ESPECIALISTA_DSE="dse/especialista/bandejaEspecialista";
        public static final String PAGE_INPS_GENERAR_OFICIO="dse/especialista/rechazoCarga/generarOficio";
        public static final String PAGE_INPS_ESPECIALISTA="especialista/especialista";
        public static final String PAGE_INPS_ESPECIALISTA_LEGAL="especialistaLegal/especialistaLegal";
        public static final String PAGE_INPS_ACTA_EXPEDIENTES_ZONA="dse/especialista/rechazoCarga/actasExpedientesZona";
      public static final String PAGE_INPS_GENERAR_ACTA_INSPECCION="dse/especialista/actaInspeccion/generarActaInspeccion";
        // vubaldo fin
        //jorojasb inicio
        public static final String PAGE_INPS_CONSULTA_DSE="dse/consulta/bandejaConsulta";
        public static final String PAGE_INPS_SUPERVISOR_DSE="dse/supervisor/bandejaSupervisor";
        //jorojasb fin
        
        // htorress - RSIS 1, 2 y 3 - Inicio
        public static final String PAGE_INPS_JEFE_REGIONAL="jefe/jefe";
        public static final String PAGE_INPS_JEFE_UNIDAD="jefe/jefe";
        public static final String PAGE_INPS_SUPERVISOR_REGIONAL="supervisorRegional/supervisorRegional";
        // htorress - RSIS 1, 2 y 3 - Fin
        // htorress - RSIS 9 - Inicio
        public static final String PAGE_INPS_SUPERVISION_GEST_ARCHIVO="common/supervision/gestionArchivo";
        // htorress - RSIS 9 - Fin
        
        
        // jorojasb RSIS10 - inicio
        public static final String PAGE_INPS_ESPECIALITA_VER_DOC = "dse/especialista/informeSupervision/verDocumento";
        public static final String PAGE_INPS_ESPECIALITA_ADJUNTAR_INFORME = "dse/especialista/informeSupervision/adjuntarInforme";
        public static final String PAGE_INPS_ESPECIALITA_ACTA_INSPECCION = "dse/especialista/informeSupervision/verActaInspeccion";
        
        // jorojasb RSIS10 - fin
        public static final String PAGE_INPS_SUPERVISOR="supervisor/supervisor";
        
        public static final String PAGE_INPS_RESPONSABLE="responsable/responsable";
        public static final String PAGE_INPS_RESPONSABLE_DERIVADOS="responsable/recepcion/popUpDerivados";

        public static final String PAGE_INPS_FRM_SELECT_ACTIVIDADES="common/popupSeleccActividad";
        public static final String PAGE_INPS_SUPERVISION="common/supervision/supervision";
        /* OSINE791 - RSIS2 - Inicio */
        public static final String PAGE_INPS_SUPERVISION_DSR="common/supervision/dsr/supervision";
        public static final String PAGE_INPS_SUPERVISION_DSR_DETALLE_OBLIGACION="common/supervision/dsr/obligacion/detalleObligacionDsr";
        
        public static final String PAGE_INPS_COMENTARIO_INCUMPLIMIENTO="common/supervision/dsr/comentarioIncumplimiento/comentarioIncumplimiento";
        public static final String PAGE_INPS_COMENTARIO_COMPLEMENTO="common/supervision/dsr/comentarioIncumplimiento/comentarioComplemento";
        public static final String PAGE_INPS_DETALLE_LEVANTAMIENTO_INFRACCION="common/supervision/dsr/levantamiento/detalleDsrLevantamiento";
        /* OSINE791 - RSIS2 - Fin */
        public static final String PAGE_INPS_SUPERVISION_DESC_HALLAZGO="common/supervision/descripcionHallazgo";
        public static final String PAGE_INPS_SUPERVISION_EVAL_DESCARGO="common/supervision/evaluacionDescargo";
        public static final String PAGE_INPS_SUPERVISION_DOCU_ADJUNTO="common/supervision/documentoAdjunto";
        /*<!--  OSINE791 - RSIS8 - Inicio -->*/
        public static final String PAGE_INPS_SUPERVISION_REG_MEDIO_PROBATORIO="common/supervision/registrarMedioProbatorio";
        /*<!--  OSINE791 - RSIS8 - Fin -->*/          
        /* OSINE791 - RSIS25 - Inicio */     
        public static final  String PAGE_INPS_CONSULTA_COMENTARIO="common/supervision/dsr/obligacion/obligacionDsrComentarioEjecucionMedida";     
        /* OSINE791 - RSIS25 - Fin */
        /*<!--  OSINE791 - RSIS14 - Inicio -->*/
        public static final String PAGE_INPS_SUPERVISION_DSR_COMENTARIO_SUBSANACION="common/supervision/dsr/obligacion/obligacionDsrComentarioSubsanacion";
        /*<!--  OSINE791 - RSIS14 - Fin -->*/
        
        /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
        public static final String PAGE_INPS_REGISTRO_COMENTARIO_EJECUCION_MEDIDA="common/supervision/dsr/ejecucionMedida/ejecucionMedidaComentario";
        /* OSINE_SFS-791 - RSIS 16 - Fin */ 
        /* OSINE_SFS-1063 - RSIS 04 - Inicio */
        public static final String PAGE_INPS_REGISTRO_ACTA_INSPECCION="dse/especialista/actaInspeccion/registrarActaInspeccion";
        public static final String PAGE_INPS_ADJUNTAR_ACTA_INSPECCION="dse/especialista/actaInspeccion/adjuntarActaInspeccion";
        /* OSINE_SFS-1063 - RSIS 04 - Fin */
        
        /*OSINE_SFS-791 - RSIS 03 - Inicio */
        public static final String PAGE_INPS_SUPERVISION_ADJ_SUPERVISION="common/supervision/adjuntoSupervision";
        /*OSINE_SFS-791 - RSIS 03 - Fin */ 
        /*OSINE_SFS-791 - RSIS 29 - Inicio */
        public static final String PAGE_INPS_MOD_LEVANTAMIENTO="modLevantamientos/modLevantamientos";
        public static final String PAGE_405_MOD_LEVANTAMIENTO="modLevantamientos/405";
        /*OSINE_SFS-791 - RSIS 29 - Inicio */
        /* OSINE_SFS-1344 - RSIS 3 - Inicio */ 
        public static final String PAGE_INPS_ORDEN_SERVICIO_MASIVO_GSM="GSM/common/ordenServicio/ordenServicioMasivo";
        /* OSINE_SFS-1344 - RSIS 3 - Fin */ 
    }
    
    public static class mensajes{
    	public static final String MSG_OPERATION_SUCCESS_CREATE="Se registr&oacute; satisfactoriamente.";
        public static final String MSG_OPERATION_SUCCESS_UPDATE="Se actualiz&oacute; el registro satisfactoriamente.";
        public static final String MSG_OPERATION_SUCCESS_DELETE="Se elimin&oacute; el registro satisfactoriamente.";
        public static final String MSG_OPERATION_SUCCESS_INVALIDATE="Se anul&oacute; el registro satisfactoriamente.";
        // htorress - RSIS 9 - Inicio
        public static final String MSG_OPERATION_SUCCESS_CARGAR_DOCUMENTO="Se guard&oacute; satisfactoriamente el documento seleccionado.";
        public static final String MSG_OPERATION_SUCCESS_CARGAR_ARCHIVO="Se guard&oacute; satisfactoriamente el archivo seleccionado.";
        // htorress - RSIS 9 - Fin
        // htorress - RSIS 15 - Inicio
        public static final String MSG_OPERATION_SUCCESS_ANULAR="Se elimin&oacute; el archivo satisfactoriamente.";
        public static final String MSG_OPERATION_SUCCESS_ENUMERAR="Se enumer&oacute; el archivo satisfactoriamente.";
        public static final String MSG_OPERATION_SUCCESS_FIRMAR_ENUMERAR="Se firm&oacute; y enumer&oacute; el archivo satisfactoriamente.";
        // htorress - RSIS 15 - Fin
        public static final String MSG_OPERATION_ERROR_DEVALUACION="Ingrese descripci&oacute;n para continuar."; //mdioses -RSIS6
        /* OSINE_SFS-480 - RSIS 42 - Inicio */
        public static final String MSG_OPERATION_SUCCESS_ANULAR_OS="Se anul&oacute; la Orden de Servicio satisfactoriamente.";
        /* OSINE_SFS-480 - RSIS 42 - Fin */
        /* OSINE_SFS-480 - RSIS 03 - Inicio */
        public static final String MSG_OPERATION_SUCCESS_VALIDAR_DOCUMENTO_OS="El tipo de documento seleccionado como principal no puede ser enviado a Mensajer&iacute;a."; 
        /* OSINE_SFS-480 - RSIS 03 - Fin */
        
        /* OSINE_SFS-1063 RSIS10 - Inicio */
        
        public static final String MSG_OPERATION_ERROR_SIGED="El servicio SIGED no se encuentra disponible."; 
        public static final String MSG_OPERATION_SUCCESS_TERMINAR_REGISTRO_ACTA="La supervisi&oacute;n se termin&oacute; correctamente.";
        public static final String MSG_OPERATION_ERROR_TERMINAR_REGISTRO_ACTA="Error al terminar la supervisi&oacute;n.";
        
        public static final String MSG_OPERATION_SUCCESS_REABRIR_REGISTRO_ACTA="Se Reabri&oacute; correctamente la supervisi&oacute;n.";
        public static final String MSG_OPERATION_ERROR_REABRIR_REGISTRO_ACTA="Error al Reabrir la supervisi&oacute;n.";
        
        public static final String MSG_OPERATION_ERROR_LISTAR_ZONAS="Error en el Servicio Listar Zonas.";
        public static final String MSG_OPERATION_ERROR_LISTAR_SUB_ESTACIONES="Error en el Servicio Listar Sub Estaciones.";
        public static final String MSG_OPERATION_ERROR_LISTAR_RELES="Error en el Servicio Listar Rel&eacute;s.";
        
        public static final String MSG_OPERATION_ERROR_ABRIR_REGISTRO_ACTA="Error al Abrir Registro del Acta.";
        public static final String MSG_OPERATION_ERROR_NO_CONFIGURACION="La Sub Estaci&oacute;n no esta configurada.";
        
        public static final String MSG_OPERATION_ERROR_LISTAR_EMPRESAS_ZONA="Pesta&ntilde;a Registro Acta Inspeci&oacute;n - Error al Listar Empresas y Zonas.";
        
        public static final String MSG_OPERATION_ERROR_VALIDAR_RELES_SERVICIO_DETA_SUP="Error en la validaci&oacute;n de Reles Servicio y Detalle Supervisi&oacute;n.";
        public static final String MSG_OPERATION_SUCCESS_ACTUALIZAR_REGISTRO_ACTA="Se Actualiz&oacute; correctamente el Registro del Acta.";
        public static final String MSG_OPERATION_ERROR_ACTUALIZAR_REGISTRO_ACTA="Error al Actualizar el Registro del Acta.";
        public static final String MSG_OPERATION_RESTRICT_TERMINAR_REGISTRO_ACTA="¿Est&aacute; seguro que desea terminar la Supervisi&oacute;n "
        		+ "sin haber completado la informaci&oacute;n de todos los Rel&eacute;s?";
        public static final String MSG_OPERATION_RESTRICT_COMPLETAR_INFORMACION_ADICIONAL="No se puede terminar la supervisi&oacute;n, porque no se ha registrado datos "
        		+ "en la Pesta&ntilde;a 'Informaci&oacute;n Inicial' de la supervisi&oacute;n.";
        /* OSINE_SFS-1063 RSIS10  - Fin */
        
        /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
        public static final String MSG_OPERATION_ERROR_LISTAR="Error al listar datos.";
        public static final String MSG_OPERATION_ERROR_CREATE="Error al registrar el registro.";
        public static final String MSG_OPERATION_ERROR_UPDATE="Error al actualizar el registro.";
        public static final String MSG_OPERATION_ERROR_DELETE="Error al eliminar el registro.";
        /* OSINE_SFS-791 - RSIS 33 - Fin */ 
        /* OSINE_SFS-791 - RSIS 42 - Inicio */ 
        public static final String MSG_SIN_DESTINATARIO="No se encontro destinatario en la Orden de Servicio";
        /* OSINE_SFS-791 - RSIS 42 - Fin */
        /* OSINE_SFS-791 - RSIS 39 - Inicio */ 
        public static final String MSG_OPERATION_DUPLICATE_CARTA_VISITA="La carta de visita ya se encuentra registrada, corregir";
        /* OSINE_SFS-791 - RSIS 39 - Fin */
    }
}
