/**
 * Resumen Objeto		: ConstantesGSMWeb.java 
 * Descripción	        : Variables del sistema que mantienen un valor inmutable a lo largo de toda 
 * 						  la vida del sistema, gerencia GSM.
 * Fecha de Creación	: 24/10/2016.
 * PR de Creación		: OSINE_SFS-1344. 
 * Autor				: Diana Medrano.
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
 */

package gob.osinergmin.inpsweb.gsm.util;

public class ConstantesGSM {
	
    public static final int VV_EXITO = 0;
    public static final int VV_ERROR = 1;
    public static final int VV_ADVERTENCIA = 2;
    
    public static final String VV_RESULTADO = "resultado";
    public static final String VV_MENSAJE = "mensaje";
    
    private ConstantesGSM() {
    }
    public static class Navegacion{
      public static final String PAGE_INPS_VISTA_DESAUTORIZADA="common/unauthorized";
	  public static final String PAGE_INPS_GSM_ESPECIALISTA="GSM/especialista/especialista";
      public static final String PAGE_INPS_GSM_GERENTE_DIVISION="GSM/gerenteDivision/gerenteDivision";
      public static final String PAGE_INPS_GSM_SUPERVISOR="GSM/supervisor/supervisor";
      /*OSINE_SFS-1344 - Inicio */
      public static final String PAGE_INPS_GSM_ORDEN_SERVICIO_SUPERVISOR="GSM/common/ordenServicio/ordenServicioSupervisor";
      public static final String PAGE_INPS_GSM_ORDEN_SERVICIO_SUPERVISOR_DEVOLVER="GSM/common/ordenServicio/ordenServicioSupervisorDevolver";
      public static final String PAGE_INPS_GSM_ORDEN_SERVICIO_ATENDER="GSM/common/ordenServicio/ordenServicioAtender";
      /*OSINE_SFS-1344  - Fin */
    }
}
