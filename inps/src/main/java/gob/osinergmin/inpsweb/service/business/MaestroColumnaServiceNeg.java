/**
* Resumen
* Objeto		: MaestroColumnaServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el MaestroColumnaServiceNegImpl
* Fecha de Creación	: 25/03/2015
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     20/05/2016      Hernán Torres Saenz         Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio" 
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD. 
*/

package gob.osinergmin.inpsweb.service.business;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface MaestroColumnaServiceNeg {
    public List<MaestroColumnaDTO> findByDominioAplicacionCodigo(String dominio,String aplicacion,String codigo);
    public List<MaestroColumnaDTO> findByDominioAplicacion(String dominio,String aplicacion);
	public List<MaestroColumnaDTO> buscarTodos(String dominio);
	/* OSINE_SFS-480 - RSIS 41 - Inicio */
	public List<MaestroColumnaDTO> findByIdMaestroColumna(Long idMaestroColumna);
	/* OSINE_SFS-480 - RSIS 41 - Fin */
	public List<MaestroColumnaDTO>  buscarByDominioByAplicacionByCodigo(String dominioSupervMuestPeriodo, String aplicacionInps,String codigoPeriodo);
	/* OSINE_SFS-791 - RSIS 47 - Inicio */ 
	public List<MaestroColumnaDTO> findMaestroColumnaByDominioAplicDesc(String dominio, String aplicacion, String descripcion);
	/* OSINE_SFS-791 - RSIS 47 - Fin */ 
        /* OSINE_SFS-791 - RSIS 41 - Inicio */ 
        public List<MaestroColumnaDTO> findMaestroColumnaByCodigo(String dominio,String aplicacion, String codigo);
        /* OSINE_SFS-791 - RSIS 41 - Fin */ 
        /* OSINE_SFS-1063 - RSIS 10 - Inicio */
	public List<MaestroColumnaDTO> findByDominioAplicacionDescripcion(String dominio,String aplicacion, String descripcion);
	/* OSINE_SFS-1063 - RSIS 10 - Fin */
}
