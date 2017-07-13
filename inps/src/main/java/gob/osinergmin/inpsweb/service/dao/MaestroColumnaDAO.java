/**
* Resumen		
* Objeto		: MaestroColumnaDAOImpl.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos en MaestroColumnaDAOImpl
* Fecha de Creación	: 25/03/2015
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     20/05/2016      Hernán Torres Saenz             Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;

import java.util.List;

/**
 *
 * @author jpiro
 */
public interface MaestroColumnaDAO {
    public List<MaestroColumnaDTO> findMaestroColumna(String dominio, String aplicacion,String codigo);
    public List<MaestroColumnaDTO> findMaestroColumna(String dominio, String aplicacion);
    /* OSINE_SFS-480 - RSIS 41 - Inicio */
    public List<MaestroColumnaDTO> findByIdMaestroColumna(Long idMaestroColumna);
    /* OSINE_SFS-480 - RSIS 41 - Fin */
    public List<MaestroColumnaDTO> findMaestroColumnaByCodigo(String dominio,String aplicacion, String codigo);
    public List<MaestroColumnaDTO> findMaestroColumnaByDominioAplicDesc(String dominio,String aplicacion, String descripcion);
    public List<MaestroColumnaDTO> findMaestroColumnaByDescripcion(String dominio,String aplicacion, String descripcion);
}
