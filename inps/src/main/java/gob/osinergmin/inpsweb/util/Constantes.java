/**
 * Resumen Objeto	: Constantes.java Descripción	: Variables del sistema que
 * mantienen un valor inmutable a lo largo de toda la vida del sistema. Fecha de
 * Creación	: PR de Creación	: OSINE_SFS-480 Autor	: Julio Piro Gonzales
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones 
 * Motivo              Fecha              Nombre                        Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_SFS-480     09/05/2016 	Mario Dioses Fernandez       Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD) 
 * OSINE_SFS-480     12/05/2016 	Luis García Reyna            Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio. 
 * OSINE_SFS-480     13/05/2016 	Hernan Torres Saenz          Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
 * OSINE_SFS-480     21/05/2016 	Julio Piro Gonzales          Insertar imágenes de medios probatorios en plantillas de Informe de Supervisión
 * OSINE_SFS-480     23/05/2016 	Giancarlo Villanueva Andrade Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
 * OSINE_SFS-480     06/06/2016 	Mario Dioses Fernandez       Construir formulario de envio a Mensajeria, consumiendo WS 
 * OSINE_SFS-480     06/06/2016 	Mario Dioses Fernandez       Envio de Datos de Mensajeria a SIGED mediante WS 
 * OSINE_SFS-480     09/06/2016 	Mario Dioses Fernandez       Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica). 
 * OSINE791          19/08/2016 	Jose Herrera                 Incluir Sigla de Division en Numero de Orden Servicio
 * OSINE791-RSIS19   06/09/2016 	Zosimo Chaupis Santur        Crear la funcionalidad para generar los resultados para las CASUISTICAS GENERALES de supervision de una orden de supervision DSR-CRITICIDAD
 * OSINE791-RSIS04   05/10/2016   	Zosimo Chaupis Santur        CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
 * OSINE_SFS-791     12/10/2016     Mario Dioses Fernandez       Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
 * OSINE_SFS-791     12/10/2016     Mario Dioses Fernandez       Crear la tarea automática que cancele el registro de hidrocarburos
 * OSINE791-RSIS42   14/10/2016     Alexander Vilca Narvaez      Adecuar la funcionalidad "CERRAR ORDEN" cuando se atiende una orden de levantamiento DSR-CRITICIDAD
 * OSINE791-RSIS34   03/11/2016     Cristopher Paucar Torre      Confirma Tipo de Asignación.
 * 
*/
package gob.osinergmin.inpsweb.util;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jpiro
 */
public class Constantes {
	
	public static String RUTA_EXPEDIENTES_TEMPORALES = "/data/INPS/expedientes_adjuntos/";
	public static String RUTA_FONT_PDF = "/data/INPS/fonts/calibri.ttf";
	public static String RUTA_LOGO_PDF = "/data/INPS/images/logoOsinergmin.jpg";
	public static String TIPO_DOCUMENTO_ASIGNACION_TRABAJO = "ASIGNACION DE TRABAJO";
	public static String NOMBRE_FORMATO_ARCHIVO_ORDEN_SERVICIO = "EXPEDIENTE_ORDEN_SERVICIO";
	
    public static String FECHA;
    public static String USUARIO;
    public static Long IDPERSONAL;
    public static Long IDPERSONALSIGED;
    public static String IDENTIFICADORROL;
    public static String TERMINAL;
    public UsuarioDTO usuarioDTO;
    public static final String CONSTANTE_ROL_RESPONSABLE = "RESPONSABLE";
    public static final String CONSTANTE_ROL_ESPECIALISTA = "ESPECIALISTA";
    public static final String CONSTANTE_ROL_ESPECIALISTA_LEGAL = "ESPECIALISTA LEGAL";
    public static final String CONSTANTE_ROL_SUPERVISOR = "SUPERVISOR";
    public static final Character CONSTANTE_DOCUMENTO_FIRMADO = 'S';
    public static final Character CONSTANTE_DOCUMENTO_NO_FIRMADO = 'N';
    // htorress - RSIS 1, 2 y 3 - Inicio
    public static final String CONSTANTE_ROL_JEFE_REGIONAL = "JEFE REGIONAL";
    public static final String CONSTANTE_ROL_JEFE_UNIDAD = "JEFE DE UNIDAD";
    public static final String CONSTANTE_ROL_SUPERVISOR_REGIONAL = "SUPERVISOR REGIONAL";
    
    //btorrejon
    public static final String CONSTANTE_NOMBRE_PROCESO_DSE= "Proceso de Supervisi\u00F3n de Rechazo Autom\u00E1tico de Carga y Generaci\u00F3n";
    
    /* victoria */
    public static final String CONSTANTE_SIGLA_DSE = "DSE";
    
    // htorress - RSIS 1, 2 y 3 - Fin
    public static final String ESTADO_ACTIVO = "1";
    public static final String ESTADO_INACTIVO = "0";
    public static final char ESTADO_SIGED_ACTIVO = 'A';
    public static final char ESTADO_SIGED_INACTIVO = 'N';
    public static final char ESTADO_SIGED_SI = 'S';
    public static final char ESTADO_SIGED_NO = 'N';
    public static final String EXPEDIENTE_FLAG_ORIGEN_SIGED = "0";
    public static final String EXPEDIENTE_FLAG_ORIGEN_INPS = "1";
    public static final Long ID_ENTIDAD_GFHL = new Long(1);
    public static final Long ID_ENTIDAD_GO = new Long(2);
    public static final String UNIDAD_ORGANICA_COD_DEP_SIGA_GFHL = "H";
    public static final String UNIDAD_ORGANICA_SIGLA_GFHL = "GH";
    public static final String UNIDAD_ORGANICA_COD_DEP_SIGA_GO = "GOPH";
    public static final String UNIDAD_ORGANICA_SIGLA_GO = "GO";
    public static final String TIPO_ACCION_ORDEN_SERVICO_GENERAR = "generar";
    public static final String TIPO_ACCION_ORDEN_SERVICO_ASIGNAR = "asignar";
    public static final String TIPO_ACCION_ORDEN_SERVICO_ANULAR = "anular";
    public static final String TIPO_ACCION_ORDEN_SERVICO_EDITAR = "editar";
    public static final String ESTADO_ORIGEN_ARCHIVO_SIGED = "FINAL";
    public static final String ESTADO_ORIGEN_ARCHIVO_INPS = "TEMPORAL";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_EXP_DERIVADO = "EXP_DERIVADO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_EXP_ASIGNADO = "EXP_ASIGNADO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_OS_REGISTRO = "OS_REGISTRO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_OS_SUPERVISADO = "OS_SUPERVISADO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_OS_REVISADO = "OS_REVISADO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_OS_APROBADO = "OS_APROBADO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_OS_OFICIADO = "OS_OFICIADO";
    public static final String IDENTIFICADOR_ESTADO_PROCESO_OS_CONCLUIDO = "OS_CONCLUIDO";
    public static final String IDENTIFICADOR_ROL_RESPONSABLE = "RESPONSABLE";
    public static final String IDENTIFICADOR_ROL_ESPECIALISTA = "ESPECIALISTA";
    public static final String IDENTIFICADOR_ROL_ESPECIALISTA_LEGAL = "ESPECIALISTA LEGAL";
    public static final String IDENTIFICADOR_ROL_SUPERVISOR = "SUPERVISOR";
    /* OSINE_SFS-1344 RSIS7 - Inicio */
    public static final String IDENTIFICADOR_ROL_GERENTE_DIVISION = "GERENTE DE DIVISION";
    /* OSINE_SFS-1344 RSIS7 - fin */
    // jorojasb 
    public static final String IDENTIFICADOR_ROL_CONSULTA = "CONSULTAS";
     // jorojasb
    
    /* OSINE_SFS-1063 RSIS10 - Inicio */
    public static final String DOMINIO_PROCESO_DSE = "DSE_FLUJO_SIGED";
    public static final String DOMINIO_DOCUMENTO = "TIPO_DOC_ADJUNTO_DSE";
    public static final String DOMINIO_DEMANDA = "DEMANDA";
    public static final String CODIGO_PROCESO_DSE = "IDPROCESO";
    public static final String CODIGO_DEMANDA_MW = "DEMANDAMW";
    public static final String CODIGO_DEMANDA_MAX = "DEMANDAMAX";
    public static final String CODIGO_DEMANDA_MED = "DEMANDAMED";
    public static final String CODIGO_DEMANDA_MIN = "DEMANDAMIN";
    public static final String DOCUMENTO_INFORME = "INFORME";
    public static final String DOCUMENTO_ACTA = "ACTA";
    public static final String DOCUMENTO_OFICIO = "OFICIO";
    public static final String RUTA_PLANTILLA_ACTA_INSPECCION = "/WEB-INF/reports/ReporActaInspeccionPlantilla.jasper";
    public static final String RUTA_PLANTILLA_ACTA_INSPECCION_BD = "/WEB-INF/reports/ReporActaInspeccion.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_actainspeccionObs = "/WEB-INF/reports/ActaInspeccionObservaciones.jasper";
    public static final String RUTA_LOGO_PLANTILLA = "/images/logoPlantilla.png";
    public static final String RUTA_LOGO_UKAS_PLANTILLA = "/images/ukas.png";
    public static final String RUTA_LOGO_SGS_PLANTILLA = "/images/sgs.png";
    /* OSINE_SFS-1063 RSIS10 - Fin */
    
    
    // htorress - RSIS 1, 2 y 3 - Inicio
    public static final String IDENTIFICADOR_ROL_JEFE_REGIONAL = "JEFE REGIONAL";
    public static final String IDENTIFICADOR_ROL_JEFE_UNIDAD = "JEFE DE UNIDAD";

    public static final String IDENTIFICADOR_ROL_SUPERVISOR_REGIONAL = "SUPERVISOR REGIONAL";
    // htorress - RSIS 1, 2 y 3 - Fin
    // htorress - RSIS 9 - Inicio
    public static final String GESTION_ARCHIVO_CARGAR_ARCHIVO = "cargar";
    public static final String GESTION_ARCHIVO_VERSIONAR_ARCHIVO = "versionar";
    // htorress - RSIS 9 - Fin
    // htorress - RSIS 15 - Inicio
    public static final String FLAG_ANULADO_SI = "1";
    public static final String FLAG_ENUMERADO_SI = "1";
    public static final String FLAG_ENUMERADO_FIRMADO_SI = "1";
    /* OSINE_SFS-480 - RSIS 04 - Inicio */
    public static final String ENUMERADO_SI_SIGED = "S";
    /* OSINE_SFS-480 - RSIS 04 - Fin */
    /* OSINE_SFS-791 - RSIS 07 - Inicio */
    public static final Long CODIGO_RESULTADO_INICIAL = new Long(0);  //ANTES 1
    /* OSINE_SFS-791 - RSIS 07 - Fin */
    /* OSINE_SFS-791 - RSIS 12 - Inicio */
   // public static final int CODIGO_RESULTADO_PORVERIFICAR = 4; //antes 5
    public static final Long CODIGO_RESULTADO_PORVERIFICAR = new Long(4);  //antes 5
    public static final Long CODIGO_RESULTADO_OBSTACULIZADO = new Long(5); // antes 6
    public static final Long CODIGO_RESULTADO_CUMPLE = new Long(1);
    public static final Long CODIGO_RESULTADO_INCUMPLE = new Long(2);
    public static final String CODIGO_PLAZO_INICIAL_POR_VERIFICAR_OBLIGACION = "P2";
    public static final String CODIGO_PLAZO_INICIAL_MEDIDA_SEGURIDAD = "P3";
    public static final String CODIGO_PLAZO_INICIAL_POR_VERIFICAR_SUPERVISION_INICIAL = "P1";
    public static final Long ID_RESULTADO_ANTERIOR_DEFAULT = new Long(-1);
    public static final Long CODIGO_RESULTADO_ANTERIOR_DEFAULT = new Long(-1);
    
    
    public static final Long CODIGO_RESULTADO_SUPERVISION_CUMPLE = new Long(6);
    public static final Long CODIGO_RESULTADO_SUPERVISION_CUMPLE_OBSTACULIZADO = new Long(7);
    public static final Long CODIGO_RESULTADO_SUPERVISION_NOCUMPLE = new Long(8);
    public static final Long CODIGO_RESULTADO_SUPERVISION_NOCUMPLE_OBSTACULIZADO = new Long(9);
    /* OSINE_SFS-791 - RSIS 12 Fin */
    public static final Long CODIGO_RESULTADO_SUPERVISION_INICIAL_SI = new Long(10);
    public static final Long CODIGO_RESULTADO_SUPERVISION_INICIAL_NO = new Long(11);
    public static final Long CODIGO_RESULTADO_SUPERVISION_INICIAL_PORVERIFICAR = new Long(12);
    public static final Long CODIGO_RESULTADO_SUPERVISION_INICIAL_OBSTACULIZADO = new Long(13);
    /* OSINE_SFS-791 - RSIS 04 - Inicio */
    public static final Long CODIGO_RESULTADO_SUPERVISION_OBSTACULIZADA = new Long(14);
    public static final Long CODIGO_RESULTADO_SUPERVISION_NO = new Long(15);
    /* OSINE_SFS-791 - RSIS 03 - Fin */
    /* OSINE_SFS-791 - RSIS 42 - Inicio */
    public static final Long CODIGO_RESULTADO_SUPERVISION_SUBSANADO_SI = new Long(16);
    public static final Long CODIGO_RESULTADO_SUPERVISION_SUBSANADO_NO = new Long(17);
    //public static final String CODIGO_CARTA_VISITA = "CAR_VIS";
    //public static final String CODIGO_ACTA_LEVANTAMIENTO = "ACTA_LEVANT";
    //public static final String CODIGO_CONSTANCIA_HABILITACION = "CONS_HAB";
    /* OSINE_SFS-791 - RSIS 42 - Fin */
    /* OSINE_SFS-480 - RSIS 04 - Inicio */
    public static final String FLAG_VALIDAR_SI = "1";
    /* OSINE_SFS-480 - RSIS 03 - Fin */
    public static final String IDENTIFICADOR_OPCION_RESP_RECEPCIONADO = "IDENTIFICADOR_RECEPCIONADO";
    public static final String IDENTIFICADOR_OPCION_ESPE_DERIVADO = "IDENTIFICADOR_DERIVADO";
    public static final String IDENTIFICADOR_OPCION_ESPE_ASIGNADO = "IDENTIFICADOR_ASIGNADO";
    public static final String IDENTIFICADOR_OPCION_ESPE_EVALUADO = "IDENTIFICADOR_EVALUADO";
    public static final String IDENTIFICADOR_OPCION_ESPE_NOTIFICADO = "IDENTIFICADOR_NOTIFICADO";
    public static final String IDENTIFICADOR_OPCION_SUPE_SUPERVISADO = "IDENTIFICADOR_SUPERVISADO";
    public static final Long IDENTIFICADOR_TIPO_ESTADO_PROCESO_EXP = 1005L; //mdiosesf
    public static final Long IDENTIFICADOR_TIPO_ESTADO_PROCESO_OS = 1006L; //mdiosesf
    public static final Long IDENTIFICADOR_TIPO_ESTADO_PROCESO_ORDEN_SERVICIO = 985L; //mdiosesf
    public static final String APLICACION_INPS = "INPS";
    public static final String APLICACION_SGLSS = "SGLSS";
    public static final String APLICACION_MYC = "MYC";
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
    public static final String APLICACION_SIGUO = "SIGUO";
    public static final String DOMINIO_ESTADO_RHO = "ESTADO_RHO";
    //public static final String DOMINIO_ESTADO_RHO_CODIGO_ACTIVO = "1"; //segun SIGUO (MZENA) cambio a RV (REGISTRO VIGENTE)
    public static final String DOMINIO_ESTADO_RHO_CODIGO_ACTIVO = "RV";
    public static final String DOMINIO_TIPO_PETICION = "TIPO_PETICION";
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public static final String CODIGO_TIPO_PETICION_MOTIVO_EDITAR = "MOTIVO_EDITAR";
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public static final String DOMINIO_MOTIVO_EDITAR = "MOTIVO_EDITAR";
    public static final String CODIGO_MOTIVO_EDITAR_CAMBIAR_ASIGNACION = "M3";
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    public static final String DOMINIO_TIPO_DIRECCION = "TIPO_DIRECCION";
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
    public static final String DOMINIO_TIPO_COMPONENTE = "TIPO_COMPONENTE";
    public static final String CODIGO_TIPO_COMPONENTE_EXPEDIENTE = "1";
    public static final String CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO = "2";
    public static final String DOMINIO_TIPO_ASIGNACION = "TIPO_ASIGNACION";
    public static final String CODIGO_TIPO_ASIGNACION_CON_VISITA = "CV";
    public static final String DOMINIO_TIPO_SUPERVISOR = "TIPO_SUPERVISOR";
    public static final String DOMINIO_TIPO_DOC_ADJUNTO = "TIPO_DOC_ADJUNTO";
    public static final String DOMINIO_TIPO_DOCUMENTO = "TIPO_DOCUMENTO";
    public static final String CODIGO_TIPO_DOCUMENTO_DNI = "1";
    public static final String DOMINIO_TIPO_CONTACTO = "TIPO_CONTACTO";
    public static final String DOMINIO_CARGOS = "CARGOS_SUPERVISION";
    public static final String DOMINIO_MOTIVO_NO_SUPRVSN = "MOTIVO_NO_SUPRVSN";
    public static final String DOMINIO_ENTIDAD = "ENTIDAD";
    public static final String DOMINIO_VIGENCIA = "VIGENCIA";
    public static final String CODIGO_VIGENCIA_VIGENTE = "VIGE";
    public static final String DOMINIO_TIPO_AGENCIA = "TIPO_AGENCIA";
    public static final String DOMINIO_NIVEL_TIPIFICACION = "NIVEL_TIPIFICACION";
    public static final String DOMINIO_TIPO_ADJ_SUPERVISION = "TIPO_ADJ_SUPERVISION";
    public static final String CODIGO_TIPO_AGENCIA_FISCAL = "FISC";
    public static final String CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL = "1";
    public static final String CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA = "2";
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public static final String DOMINIO_ARCHIVO_PLAZO_DESC = "ARCHIVO_PLAZO_DESC";
    public static final String DOMINIO_DIRE_INPS_UO_DL = "DIRE_INPS_UO_DL";
    public static final String DOMINIO_DIRE_INPS_UO = "DIRE_INPS_UO";
    public static final String DOMINIO_TIPO_VIA = "TIPO_VIA";
    public static final String DOMINIO_TIPO_SEL_ORD_SER = "TIPO_SEL_ORD_SER";
    /* OSINE_SFS-480 - RSIS 17 - Fin*/
    
    /* OSINE_SFS-Ajustes - mdiosesf - Inicio*/
    public static final String DOMINIO_TIP_DOC_NUE_EXP_SIG = "TIP_DOC_NUE_EXP_SIG";
    public static final String DOMINIO_TIP_DOC_ASIG_EXP_SIG = "TIP_DOC_ASIG_EXP_SIG";
    public static final String DOMINIO_COM_REEN_EXP_SIGED = "COM_REEN_EXP_SIGED";
    public static final String CODIGO_COM_REEN_EXP_SIGED_GEN_EXP_OS = "GEN_EXP_OS";   
    public static final String CODIGO_COM_REEN_EXP_SIGED_ASIG_OS = "ASIG_OS";
    public static final String CODIGO_COM_REEN_EXP_SIGED_EDIT_EXP_OS = "EDIT_EXP_OS";
    public static final String CODIGO_COM_REEN_EXP_SIGED_ATE_OS_DEFAULT = "ATE_OS_DEFAULT";
    public static final String CODIGO_COM_REEN_EXP_SIGED_ATE_OS_DSR = "ATE_OS_DSR";
    public static final String CODIGO_COM_REEN_EXP_SIGED_REV_OS = "REV_OS";
    public static final String CODIGO_COM_REEN_EXP_SIGED_APR_OS_DEFAULT = "APR_OS_DEFAULT";  
    public static final String CODIGO_COM_REEN_EXP_SIGED_APR_OS_DSR = "APR_OS_DSR";
    public static final String VARIABLE_TEXT_REEN_EXP_SIG = "$nro_expediente";
    public static final String VARIABLE_TEXT_REEN_ORD_SIG = "$nro_orden_servicio";
    public static final String STYLE_VARIABLE_COMENTARIO_COMPLEMENTO = "color: #444444;";
    /* OSINE_SFS-Ajustes - mdiosesf - Fin*/
    
    /* OSINE_SFS-1063 - RSIS 04 - Inicio */
    public static final String DOMINIO_TIPO_EMPRESA = "TIPO_EMPRESA";
    public static final String DOMINIO_FIRMA = "FIRMA";
    public static final int DOMINIO_DOCUMENTO_OFICIO =3;
    public static final String TIPO_PANTALLA_INFORMACION ="tabInformacionInicio";
    public static final String TIPO_PANTALLA_OBSERVACION ="tabObservaciones";
    public static final String TIPO_PANTALLA_ADJUNTAR ="adjuntar";
    public static final String TIPO_PANTALLA_CAMBIAR_ESTADO ="actualizar";
    public static final String TIPO_FLAG_REGISTRO ="flagRegistro";
    public static final String TIPO_ESTADO_REGISTRO_ACTA_TERMINAR="terminarRegistroActa";
    public static final String TIPO_ESTADO_REGISTRO_ACTA_REABRIR="reabrirRegistroActa";
    /* OSINE_SFS-1063 - RSIS 04 - Fin */
    /* OSINE791 - RSIS17 - Inicio */
    //public static final String DOMINIO_TIPO_DOC_SUPERVISION = "TIPO_DOC_SUPERVISION";
    //public static final String CODIGO_GEN_RESULT = "GEN_RESULT";
    public static final String DOMINIO_OBSERV_CARTA_VISITA = "OBSERV_CARTA_VISITA";
    public static final String CODIGO_OBS_CARTA_VISITA_SIN_INFRACCION = "OCVSI";
    /* OSINE791 - RSIS17 - Fin */
    /* OSINE791 - RSIS41 - Inicio */
    public static final String DOMINIO_ESTADO_LEVANTAMIENTO = "ESTD_LVNTMTO";
    public static final String CODIGO_ESTADO_LEVANTAMIENTO_PENDIENTE = "ESTA_LEV_PENDI";
    public static final String CODIGO_ESTADO_LEVANTAMIENTO_ENPROCESO = "ESTA_LEV_ENPROC";
    public static final String CODIGO_ESTADO_LEVANTAMIENTO_POREVALUAR = "ESTA_LEV_POREVA";
    /* OSINE791 - RSIS41 - Fin */
    /* OSINE_SFS-480 - RSIS 11 - Inicio*/
    public static final String CODIGO_FILTRO_EMP_SUP_PROC = "PROC";
    public static final String CODIGO_FILTRO_EMP_SUP_OBLI = "OBLI";
    public static final String CODIGO_FILTRO_EMP_SUP_RUBR = "RUBR";
    public static final String CODIGO_FILTRO_EMP_SUP_UBIG = "UBIG";
    /* OSINE_SFS-480 - RSIS 11 - Fin*/
    public static final String SESION_LISTA_DIRECCIONES_EMPRESA_SUPERVISADA = "LIST_DIR_EMP_SUP";
    public static final String SESION_LISTA_REPRESENTANTES_EMPRESA_SUPERVISADA = "LIST_REP_EMP_SUP";
    public static final String PROCESO_PREOPERATIVO = "PROCESO_PREOPERATIVO";
    public static final String PROCESO_OPERATIVO = "PROCESO_OPERATIVO";
    public static final String PROCESO_ESPECIAL = "PROCESO_ESPECIAL";
    public static final int SUPERVISION_PRIMERA_ITERACION = 1;
    public static final int SUPERVISION_SEGUNDA_ITERACION = 2;
    public static final String SUPERVISION_MODO_CONSULTA = "consulta"; //mdiosesf - RSIS6
    public static final String SUPERVISION_MODO_REGISTRO = "registro"; //mdiosesf - RSIS6
    public static final String DETALLE_SUPERVISION_CUMPLE = "1";
    public static final String DETALLE_SUPERVISION_INCUMPLE = "0";
    public static final String DETALLE_SUPERVISION_REGISTRADO = "1";
    public static final String DETALLE_SUPERVISION_NOREGISTRADO = "0";
    public static final String DETALLE_SUPERVISION_SANCIONA = "0";
    public static final String DETALLE_SUPERVISION_NOSANCIONA = "1";
    public static final String CONSTANTE_TIPOS_ARCHIVOS_ORDEN_SERVICIO = ".jpg|.pdf|.xls|.doc|.xlsx|.docx";
    /* OSINE_SFS-480 - RSIS 04 - Inicio */
    public static final String CONSTANTE_TIPOS_ARCHIVOS_MENSAJERIA = ".pdf|.doc|.docx";
    /* OSINE_SFS-480 - RSIS 04 - Fin */
    public static int VALOR_MEGA_BYTE = 1048576;
    public static final String FORMATO_FECHA_CORTA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_LARGE = "dd/MM/yyyy h:mm a";
    public static final String FORMATO_FECHA_HORA_LARGE = "yyyy-MM-dd HH:mm";
    public static final String FORMATO_HORA_CORTA = "hh:mm a";
    /* OSINE_SFS-1063 - RSIS 04 - Inicio */
    public static final String FORMATO_HORA = "hh:mm";
    public static final String CONSTANTE_TIPOS_ARCHIVOS_ACTA_INSPECCION = ".pdf";
    public static final String CONSTANTE_TIPOS_ARCHIVOS_INFORME_SUPERVISION = ".pdf";
    /* OSINE_SFS-1063 - RSIS 04 - Fin */
    public static final int CON_VISITA = 1;
    public static final String CONSTANTE_VACIA = "";
    public static final String RESPALDO_DETALLE_SUPERVISION = "respaldo";
    public static final String APPLICACION_SPACE_REQUISITOS = "REQUISITOS";
    public static final String APPLICACION_SPACE_LOCADOR = "LOCADOR";
    public static final String APPLICACION_SPACE_OBLIGACIONES = "OBLIGACIONES";
    public static final String APPLICACION_SPACE_GENERALES = "GENERALES";
    public static final int PRIMERO_EN_LISTA = 0;
    public static final String FLAG_ULTIMO_PERSONA_GENERAL = "1";
    public static final String FLAG_NO_ULTIMO_PERSONA_GENERAL = "0";
    public static final String FLAG_IDENTIFICA_PERSONA_SI = "1";
    public static final String FLAG_IDENTIFICA_PERSONA_NO = "0";
    public static final String FLAG_REGISTRADO_SI = "1";
    public static final String FLAG_REGISTRADO_NO = "0";
    public static final int FLAG_REALIZA_SUPERVISION = 1;
    public static final String FLAG_SUPERVISION = "1";
    public static final String FLAG_NO_SUPERVISION = "0";
    public static final String DOMINIO_OBLIG_CLASIF = "PGH_OBLIG_CLASIF";
    public static final String DOMINIO_OBLIG_CRITIC = "PGH_OBLIG_CRITIC";
    public static final String APLICACION_OBLIGACIONES = "OBLIGACIONES";
    public static int YEAR = Calendar.getInstance().get(Calendar.YEAR);
    public static int MONTH = Calendar.getInstance().get(Calendar.MONTH);
    public static int DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public static Calendar MCALENDAR = Calendar.getInstance();
    public static String NAME_MONTH = MCALENDAR.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "ES"));
    public static String DATE_NOW = DAY + " de " + NAME_MONTH + " de " + YEAR;
    public static String DISTRITO_OSINERGMIN = "Magdalena del Mar";
    /* OSINE_SFS-480 - RSIS 12 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF001 = "TF001";
    /* OSINE_SFS-480 - RSIS 12 - Fin */
     /* OSINE_SFS-791 - RSIS 19 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF003 = "TF003";//codigo del cuerpo del correo para : OTROS INCUMPLIMIENTOS
    public static final String CODIGO_FUNCIONALIDAD_TF004 = "TF004";//codigo del cuerpo del correo para : NO EJECUCION MEDIDA
    public static final String CODIGO_FUNCIONALIDAD_TF005 = "TF005";//codigo del cuerpo del correo para : OBLIGACIONES OBSTACULIZADAS 
    /* OSINE_SFS-791 - RSIS 19 - Fin */
    /* OSINE_SFS-791 - RSIS 18 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF006 = "TF006";//codigo del cuerpo del correo para : MEDIDAS DE SEGURIDAD EN SUPERVISION 
    public static final String CODIGO_FUNCIONALIDAD_TF007 = "TF007";//codigo del cuerpo del correo para : SUSPENSION DEL REGISTRO
    public static final String CODIGO_FUNCIONALIDAD_TF008 = "TF008";//codigo del cuerpo del correo para : DESACTIVAR ROL DE COMPRAS SCOP - EXPEDIENTE
    public static final String CODIGO_FUNCIONALIDAD_TF009 = "TF009";//codigo del cuerpo del correo para : DESACTIVAR ROL DE COMPRAS SCOP POR PRODUCTO - EXPEDIENTE
    /* OSINE_SFS-791 - RSIS 18 - Inicio */
    /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
    public static final String CODIGO_FUNCIONALIDAD_TF010 = "TF010";//codigo del cuerpo del correo para : SOLICITA CAMBIAR TIPO DE ASIGNACION
    /* OSINE_SFS-791 - RSIS 33 - Fin */ 
    /* OSINE_SFS-791 - RSIS 4 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF011 = "TF011";//codigo del cuerpo del correo para : SUPERVISION INICIAL OBSTACULIZADA
    /* OSINE_SFS-791 - RSIS 4 - Fin */
    /* OSINE_SFS-791 - RSIS 40 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF012 = "TF012";//codigo del cuerpo del correo para : HABILITAR RH
    public static final String CODIGO_FUNCIONALIDAD_TF013 = "TF013";//codigo del cuerpo del correo para : ACTIVAR ROL DE COMPRAS SCOP
    public static final String CODIGO_FUNCIONALIDAD_TF014 = "TF014";//codigo del cuerpo del correo para : ACTIVAR ROL DE COMPRAS SCOP POR PRODUCTO
    /* OSINE_SFS-791 - RSIS 40 - Fin */
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF015 = "TF015";//codigo del cuerpo del correo para : EMITIR OFICIO LEVANTAMIENTO INFRACCIONES PENDIENTE
    public static final String CODIGO_FUNCIONALIDAD_TF016 = "TF016";//codigo del cuerpo del correo para : CANCELACION DEL REGISTRO
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */
    /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF017 = "TF017";//codigo del cuerpo del correo para : HA CONCLUIDO EL INGRESO DE INFORMACION LEVANTAMIENTO INFRACCION  
    /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */
    /* OSINE-791 - RSIS 34 - Inicio */
    public static final String CODIGO_FUNCIONALIDAD_TF018 = "TF018";
    public static final String CODIGO_FUNCIONALIDAD_TF019 = "TF019";
    /* OSINE-791 - RSIS 34 - Fin */
    
    //PLANTILLAS
    public static final String IDENT_PLANTILLA_UNO = "PLANTILLA_IS_OS1";
    public static final Object IDENT_PLANTILLA_DOS = "PLANTILLA_IS_OS2";
    public static final Object IDENT_PLANTILLA_TRES = "PLANTILLA_IFPAS";
    public static final Object IDENT_PLANTILLA_CUATRO = "PLANTILLA_IIPAS";
    public static final String IDENT_PLANTILLA_CINCO = "PLANTILLA_AIP_OPERATIVOS";
    public static final Object IDENT_PLANTILLA_SEIS = "PLANTILLA_OFICIO_IPAS";
    public static final Object IDENT_PLANTILLA_SIETE = "PLANTILLA_RESOLUCION_PAS";
    public static final Object IDENT_PLANTILLA_OCHO = "PLANTILLA_INFORME_SUPERVISION_SUSPENSION"; //mdiosesf
    public static final Object IDENT_PLANTILLA_NUEVE = "PLANTILLA_INFORME_MEDIDA_ADMINISTRATIVA";
    public static final Object IDENT_PLANTILLA_DIEZ = "PLANTILLA_SUPERVISION_CANCELACION";
    public static final Object IDENT_PLANTILLA_ONCE = "PLANTILLA_MEDIDA_CORRECTIVA";
    public static final Object IDENT_PLANTILLA_DOCE = "PLANTILLA_SUPERVISION_OBSTACULIZAR";
    public static final Object IDENT_PLANTILLA_TRECE = "PLANTILLA_INICIO_PAS";
    public static final Object IDENT_PLANTILLA_CATORCE = "PLANTILLA_SUPERVISION_CASO_FURTUITO";
    /* OSINE_SFS-480 - RSIS 01 - Inicio */
    public static final String TIPO_ARCHIVO_JPG = "JPG";
    public static final String TIPO_ARCHIVO_JPEG = "JPEG";
    public static final String TIPO_ARCHIVO_PNG = "PNG";
    public static final String TIPO_ARCHIVO_GIF = "GIF";
    /* OSINE_SFS-480 - RSIS 01 - Fin */
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    public static final String IDENTIFICADOR_SELECCION_MUESTRAL = "S";
    public static final String DOMINIO_DIRE_INPS_SM = "DIRE_INPS_SM";
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    public static final String DOMINIO_SUPERV_MUEST_PERIODO = "SUPERV_MUEST_PERIODO";
    public static final String CODIGO_PERIODO = "PERIODO";
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    public static final String CODIGO_SUPERVISION_MUESTRAL_CODIGO_CONTIGENCIA = "TPOS03";
    public static final String CODIGO_SUPERVISION_MUESTRAL_CODIGO_MUESTRAL = "TPOS02";
    public static final String CODIGO_SUPERVISION_MUESTRAL_CODIGO_ASIGNACION = "TPOS01";
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    public static final String PERSONAL_UNIDAD_ORGANICA_DIVISION = "PERSONAL_UNIDAD_ORGANICA_DIVISION";
    /* OSINE791 - RSIS1 Inicio */
    public static final String PERSONAL_UNIDAD_ORGANICA_SIGLA = "PERSONAL_UNIDAD_ORGANICA_SIGLA";    
    /* OSINE791 - RSIS1 Fin */
    public static final String ORDEN_SERVICIO_ASIGNAR = "asignar";
    public static final String ORDEN_SERVICIO_ATENDER = "atender";
    public static final String ORDEN_SERVICIO_CONSULTAR = "consultar";
    public static final String ORDEN_SERVICIO_REVISAR = "revisar";
    public static final String ORDEN_SERVICIO_APROBAR = "aprobar";
    public static final String ORDEN_SERVICIO_NOTIFICAR = "notificar";
    public static final String ORDEN_SERVICIO_CONCLUIR = "concluir";
    public static final String ORDEN_SERVICIO_CONFIRMAR_DESCARGO = "confirmardescargo";
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    public static final String ORDEN_SERVICIO_LEVANTAMIENTO = "levantamiento";
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    /* OSINE_SFS-791 - RSIS 16 - Inicio */
    public static final String FLAG_OBLIGACIONES_SI_INCUMPLIDAS = "1";
    public static final String FLAG_OBLIGACIONES_NO_INCUMPLIDAS = "0";
    /* OSINE_SFS-791 - RSIS 16 - Fin */
    /* OSINE_SFS-791 - RSIS 17 - Inicio */
    public static final String RUTA_IMG_CABECERA_GENERAR_RESULTADOS = "/images/cabecera.png";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_CARTA_VISITA = "/WEB-INF/reports/cartaVisita.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_ConstanciaRH = "/WEB-INF/reports/ConstanciaRH.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_actaSupervision01 = "/WEB-INF/reports/actaSupervision01.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_actaSupervision02 = "/WEB-INF/reports/actaSupervision02.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_actaSupervision03 = "/WEB-INF/reports/actaSupervision03.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_EXCEL_PRODUCTOS = "/WEB-INF/reports/reportProducto.jasper";
    public static final String NOMBRE_ARCHIVO_EXCEL_PRODUCTOS_SUSPENDIDOS = "productos_suspendidos.xls";
    
        /* OSINE_SFS-791 - RSIS 40 - Inicio */
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_ACTA_LEVANTAMIENTO_01 = "/WEB-INF/reports/ActaLevantamiento01.jasper";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_ACTA_LEVANTAMIENTO_02 = "/WEB-INF/reports/ActaLevantamiento02.jasper";
    public static final String NOMBRE_ARCHIVO_ACTA_LEVANTAMIENTO = "ActadeLevantamiento";
    public static final String DESCRIPCION_DOCUMENTO_ACTA_LEVANTAMIENTO = "ACTA DE LEVANTAMIENTO";
    public static final String EXTENSION_DOCUMENTO_ACTA_LEVANTAMIENTO = ".PDF";
    public static final String RUTA_PLANTILLA_GENERAR_RESULTADOS_Constancia_HabilitacionRH = "/WEB-INF/reports/constanciaHabilitacionRH.jasper";
    public static final String NOMBRE_ARCHIVO_CONSTANCIA_HABILITARH = "ConstanciaHabilitacionRH";
    public static final String DESCRIPCION_DOCUMENTO_CONSTANCIA_HABILITARH = "CONSTANCIA DE HABILITACION RH";
    public static final String EXTENSION_DOCUMENTO_CONSTANCIA_HABILITARH = ".PDF";
    
     /* OSINE_SFS-791 - RSIS 41 - Fin */
    public static final int NRO_INFRACCIONES_CARTA_VISITA = 0;
    /* OSINE_SFS-791 - RSIS 41 - Inicio */
    public static final int NRO_INFRACCIONES_CARTA_VISITA_TERMINO_SUPERVISION = 0;
    /* OSINE_SFS-791 - RSIS 41 - Fin */
    public static final int NRO_INFRACCIONES_PART1_ACTA_SUPERVISION = 16;
    public static final int LISTA_VACIA = 0;
    public static final int LISTA_UNICO_VALIR = 1;
    public static final int VALOR_VACIO = 0;
    
    
    public static final String DEFAULT_DEPARTAMENTO = "00";
    public static final String DEFAULT_PROVINCIA = "00";
    public static final String DEFAULT_DISTRITO = "00";
    public static final String CODIGO_CIERRE_PARCIAL = "AA1";
    public static final String CODIGO_CIERRE_TOTAL = "AA2";
    public static final String DOMINIO_MEDIDA_SEGURIDAD = "MEDIDA_SEGURIDAD";
    public static final String DOMINIO_INFRACCION_MEDI_SEG_CIERRE_TOTAL = "INFRA_MEDI_SEG_TOTAL";
    public static final String DOMINIO_TIPO_PRODUCTO = "TIPO_PRODUCTO";
    public static final String CODIGO_COMBUSTIBLE_LIQUIDO = "COMB_LIQU";
    public static final String CODIGO_ESTADO_SUSPENDER_REGISTRO_HIDROCARBURO = "SU";
    public static final String CODIGO_ESTADO_HABILITAR_REGISTRO_HIDROCARBURO = "RV";
    public static final String CODIGO_ESTADO_CANCELAR_REGISTRO_HIDROCARBURO = "CA";
    public static final String NOMBRE_ARCHIVO_CARTA_VISITA = "CartaVisita";
    public static final String DESCRIPCION_DOCUMENTO_CARTA_VISITA = "CARTA DE VISITA";
    public static final String EXTENSION_DOCUMENTO_CARTA_VISITA = ".PDF";
    public static final String NOMBRE_ARCHIVO_CONSTANCIA_RH = "ConstanciaSuspensionRH";
    public static final String DESCRIPCION_DOCUMENTO_CONSTANCIA_RH = "CONSTANCIA DE SUSPENSION RH";
    /* OSINE_SFS-791 - RSIS 47 - Inicio */ 
    public static final String NOMBRE_ARCHIVO_CONSTANCIA_RH_CANCELACION = "ConstanciaCancelacionRH";
    public static final String DESCRIPCION_DOCUMENTO_CONSTANCIA_RH_CANCELACION = "CONSTANCIA DE CANCELACION RH";
    /* OSINE_SFS-791 - RSIS 47 - Fin */ 
    public static final String EXTENSION_DOCUMENTO_CONSTANCIA_RH = ".PDF";
    public static final String DESCRIPCION_REG_SUS_HAB_OPERATIVO = "OPERATIVO";
    public static final String NOMBRE_ARCHIVO_ACTA_SUPERVISION = "ActaSupervision";
    public static final String DESCRIPCION_DOCUMENTO_ACTA_SUPERVISION = "ACTA DE SUPERVISION";
    public static final String EXTENSION_DOCUMENTO_ACTA_SUPERVISION = ".PDF";
    /* OSINE_SFS-791 - RSIS 17 - Fin */
    /* OSINE_SFS-791 - RSIS 4 - Inicio */
    public static final String IDENTIFICADOR_FLAG_SUPERVISION_OBSTACULIZADO = "SUPERVISIONOBSTACULIZADO";
    public static final String IDENTIFICADOR_FLAG_SUPERVISION_NO = "SUPERVISIONNO";
    /* OSINE_SFS-791 - RSIS 4 - Fin */
    public static final String DOMINIO_ACTIVA_DSR_PROD_SCOP = "ACTIVA_DSR_PROD_SCOP";
    /* OSINE_SFS-791 - RSIS 9 - Inicio */
    public static final String CODIGO_COMPLEMENTO_TIPO_NUM="NUM";
    public static final String CODIGO_COMPLEMENTO_TIPO_SELEC="SELEC";
    public static final String CODIGO_COMPLEMENTO_TIPO_PARR="PARR";
    public static final String CODIGO_COMPLEMENTO_TIPO_CHK="CHK";
    public static final String CODIGO_COMPLEMENTO_DATOS_MAEST_COLU="MAEST_COLU";
    public static final String CODIGO_COMPLEMENTO_DATOS_VIEW="VIEW";
    /* OSINE_SFS-791 - RSIS 9 - Fin */

     /* OSINE791 RSIS20 - Inicio */
    public static final String METODO_CERRAR_ATENCION_DSR = "cerrarAtencionDSR";
    public static final Long CODIGO_RESULTADO_SUPERVISION_VACIO = new Long(0);
    public static final String CODIGO_TRAMITE_CONCLUIDO = "1";
    public static final String CODIGO_TRAMITE_NO_CONCLUIDO = "0";
    public static final String DOMINIO_TIP_DOC_SUP_SIGED = "TIP_DOC_SUP_SIGED";
    
    
    public static final String MIME_TYPE_XSL = "application/vnd.ms-excel";
     /* OSINE791 RSIS20 - Fin */
    
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
    public static final Long PLAZO_NOTIFICACION_OFICIO_OBLIGACIONES_INCUMPLIDAS = new Long(151);
    public static final Long PLAZO_CANCELAR_RH = new Long(181); 
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
     /* OSINE_SFS-791 - RSIS 40 - Inicio */ 
    public static final String DESCRIPCION_CIERRE_NINGUNO = "NINGUNO";
    public static final String DESCRIPCION_CIERRE_TOTAL = "CIERRE_TOTAL";
    public static final String DESCRIPCION_CIERRE_PARCIAL = "CIERRE_PARCIAL";
    /* OSINE_SFS-791 - RSIS 40 - Fin */
     /* OSINE_SFS-791 - RSIS 40 - Inicio */ 
    public static final String FLAG_AUTOMATICO_EJECUCION_TAREA_SI = "0";
    public static final String FLAG_AUTOMATICO_EJECUCION_TAREA_NO = "1";
     /* OSINE_SFS-791 - RSIS 40 - Inicio */ 
    
    /*OSINE_SFS-791 RSIS 1*/
    public static final String CODIGO_FLUJO_SUPERV_INPS_DSR_CRI = "DSR_CRI";
    
    /* OSINE_SFS-1344 RSIS 1 Inicio */
    public static final String CONSTANTE_CODIGO_BANDEJA_GSM = "B_GSM";
    public static final String CONSTANTE_CODIGO_BANDEJA_DSE = "B_DSE";
    /* OSINE_SFS-1344 RSIS 1 Fin */
    /* OSINE_SFS-1344 RSIS 19 Inicio */
     public static final String DESCRIPCION_EJECUCION_MEDIDA_SI = "siEjecMed";
      public static final String DESCRIPCION_EJECUCION_MEDIDA_NO = "NoEjecMed";
    /* OSINE-1344 RSIS 19 Fin */
    /* OSINE-791 RSIS 40-46-47 Inicio */
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_CANCELARRH = "298";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_HABILITARRH = "297";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_MP_LEVANTAMIENTO_INCUMPLIMIENTO_OS = "3";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_DOCUMENTO_MULTIMEDIA = "293";

      //public static final String CODIGO_TIP_DOC_SUP_SIGED_CARTA_VISITA = "169";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD = "296";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_ACTA_EJECUCION_MEDIDA_SEGURIDAD = "294";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_CONSTANCIA_EJECUCION_MEDIDA_REGISTRO_HIDROCARBUROS = "295";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGISTRO_HIDROCARBUROS = " 297";
      //public static final String CODIGO_TIP_DOC_SUP_SIGED_CONSTANCIA_SUSPENSION_CANCELACION_REGISTRO_HIDROCARBUROS = "298";
      
      /* OSINE791 RSIS42 - Inicio */
    //public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_LEVANTAMIENTO_INCUMPLIMIENTO_OS = "LEVANTAMIENTO_INCUMPLIMIENTO_OS";
    //public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_MEDIO_PROBATORIO_LEVANTAMIENTO_INCUMPLIMIENTO_OS = "MEDIO_PROBATORIO_LEVANTAMIENTO_INCUMPLIMIENTO_OS";
    //public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_MEDIO_PROBATORIO_OS = "MEDIO_PROBATORIO_OS";
    //public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_RESULTADO_OS = "RESULTADO_OS";
    
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA = "CARTA_VISITA";
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_DOCUMENTO_MULTIMEDIA = "DOCUMENTO_MULTIMEDIA";
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD= "ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD";
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_EJECUCION_MEDIDA_SEGURIDAD= "ACTA_EJECUCION_MEDIDA_SEGURIDAD";
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_EJECUCION_MEDIDA_REGIS_HIDRO= "CONSTANCIA_EJECUCION_MEDIDA_REGIS_HIDRO";
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO= "CONSTANCIA_HABILITACION_REGIS_HIDRO";
    public static final String DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_SUSPENSION_CANCELACION_REGIS_HIDRO= "CONSTANCIA_SUSPENSION_CANCELACION_REGIS_HIDRO";
    
    
    public static final String TIPO_DOCUMENTO_INFORME_SUPERVISION = "18";
    /* OSINE791 RSIS42 - fIN */
    
    /* OSINE-791 - RSIS 40-46-47 - Fin */
    /* OSINE-791 - RSIS 66 - Fin */
    public static final String CON_INCUMPLIMIENTO = "1";
    public static final String SIN_INCUMPLIMIENTO = "0";
    /* OSINE-791 - RSIS 66 - Fin */
    public static String getFECHA() {
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        String prefix = DATE_FORMAT.format(today);
        Constantes.FECHA = prefix;
        return Constantes.FECHA;
    }

    public static String getUSUARIO(HttpServletRequest request) {
        String usuario = ((String) request.getSession().getAttribute("j_username"));
        Constantes.USUARIO = usuario;
        return Constantes.USUARIO;
    }

    public static Long getIDPERSONAL(HttpServletRequest request) {
        Long idPersonal = (Long) request.getSession().getAttribute("idPersonal");
        Constantes.IDPERSONAL = idPersonal;
        return Constantes.IDPERSONAL;
    }

    public static String getIDENTIFICADORROL(HttpServletRequest request) {
        String identificadorRol = (String) request.getSession().getAttribute("identificadorRol");
        Constantes.IDENTIFICADORROL = identificadorRol;
        return Constantes.IDENTIFICADORROL;
    }

    public static Long getIDPERSONALSIGED(HttpServletRequest request) {
        Constantes.IDPERSONALSIGED = (Long) request.getSession().getAttribute("idPersonalSiged");
        return Constantes.IDPERSONALSIGED;
    }

    public static String getTERMINAL() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }

    public static UsuarioDTO getUsuarioDTO(HttpServletRequest request) throws UnknownHostException {
        UsuarioDTO retorno = new UsuarioDTO();
        retorno.setCodigo(getUSUARIO(request));
        retorno.setTerminal(getTERMINAL());
        return retorno;
    }
}
