package gob.osinergmin.inpsweb.service.exception;

public class ListaEmpresasVwException extends BaseException{
	  public ListaEmpresasVwException(Exception exception) {
          super(exception);
  }

  /**
   * @param message
   * @param exception
   */
  public ListaEmpresasVwException(String message, Exception exception) {
          super(message, exception);
  }

  /**
   * @param message
   * @param exception
   */
  public ListaEmpresasVwException(String codigo, Exception exception, boolean buscarCodigo) {		
          super(codigo, exception, buscarCodigo);
  }

  /**
   * @param codigo
   * @param message
   * @param exception
   */
  public ListaEmpresasVwException(String codigo, String message, Exception exception) {		
          super(codigo, message, exception);		
  }

  /**
   * @param codigo
   */
  public ListaEmpresasVwException(String codigo) {
          super(codigo);	
  }
}
