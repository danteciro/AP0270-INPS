package gob.osinergmin.inpsweb.service.exception;
/**
 *
 * @author mdiosesf
 */
public class ConfFiltroEmpSupException extends BaseException {
    public static final String ERROR_CREAR_ACTIVIDAD = "ConfFiltroEmpSupException.service.crear.error";
    public static final String ERROR_EDITAR_ACTIVIDAD = "ConfFiltroEmpSupException.service.editar.error";
    public static final String ERROR_VALIDA_ACTIVIDAD = "ConfFiltroEmpSupException.service.crear.validar.error";
    public static final String ERROR_VALIDA_EDITAR_ACTIVIDAD = "ConfFiltroEmpSupException.service.editar.validar.error";
    public static final String ERROR_BUSCAR_ACTIVIDAD = "ConfFiltroEmpSupException.service.buscar.error";
    public static final String ERROR_CONTAR_ACTIVIDAD = "ConfFiltroEmpSupException.service.contar.error";
    public static final String ERROR_ELIMINAR_ACTIVIDAD= "ConfFiltroEmpSupException.service.eliminar.error";

    public ConfFiltroEmpSupException(Exception exception) {
            super(exception);
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametros message
     * y exception
     * 
     * @param message
     * @param exception
     */
    public ConfFiltroEmpSupException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametros message
     * y exception
     * 
     * @param message
     * @param exception
     */
    public ConfFiltroEmpSupException(String codigo, Exception exception, boolean buscarCodigo) {		
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
    public ConfFiltroEmpSupException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * Constructor de la clase ContratoLocadorException que recibe como parametro
     * codigoException
     * 
     * @param codigo
     */
    public ConfFiltroEmpSupException(String codigo) {
            super(codigo);	
    }
}
