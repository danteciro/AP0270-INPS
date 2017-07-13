package gob.osinergmin.inpsweb.service.exception;
/**
 *
 * @author mdiosesf
 */
public class DireccionUnidadSupervisadaException extends BaseException {
    public static final String ERROR_CREAR_ACTIVIDAD = "DireccionUnidadSupervisadaException.service.crear.error";
    public static final String ERROR_EDITAR_ACTIVIDAD = "DireccionUnidadSupervisadaException.service.editar.error";
    public static final String ERROR_VALIDA_ACTIVIDAD = "DireccionUnidadSupervisadaException.service.crear.validar.error";
    public static final String ERROR_VALIDA_EDITAR_ACTIVIDAD = "DireccionUnidadSupervisadaException.service.editar.validar.error";
    public static final String ERROR_BUSCAR_ACTIVIDAD = "DireccionUnidadSupervisadaException.service.buscar.error";
    public static final String ERROR_CONTAR_ACTIVIDAD = "DireccionUnidadSupervisadaException.service.contar.error";
    public static final String ERROR_ELIMINAR_ACTIVIDAD= "DireccionUnidadSupervisadaException.service.eliminar.error";

    public DireccionUnidadSupervisadaException(Exception exception) {
            super(exception);
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametros message
     * y exception
     * 
     * @param message
     * @param exception
     */
    public DireccionUnidadSupervisadaException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametros message
     * y exception
     * 
     * @param message
     * @param exception
     */
    public DireccionUnidadSupervisadaException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametros
     * codigoException, message y exception
     * 
     * @param codigo
     * @param message
     * @param exception
     */
    public DireccionUnidadSupervisadaException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametro
     * codigoException
     * 
     * @param codigo
     */
    public DireccionUnidadSupervisadaException(String codigo) {
            super(codigo);	
    }
}
