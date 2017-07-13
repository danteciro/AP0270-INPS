/**
* Resumen		
* Objeto		: UnidadSupervisadaServiceNeg.java
* Descripcion		: 
* Fecha de Creacion	: 
* PR de Creacion	: 
* Autor			: 
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripcion
* ---------------------------------------------------------------------------------------------------
* OSINE_MANT_DSHL_003  27/06/2017   Claudio Chaucca Umana::ADAPTER   Agrega el parametro del idPersonal que realiza la busqueda
*/ 
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.DireccionEmpSupDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaDireccionxUnidadSupervidaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import java.util.List;

/**
 *
 * @author jpiro
 */
public interface UnidadSupervisadaServiceNeg {
    public List<UnidadSupervisadaDTO> listarUnidadSupervisada(UnidadSupervisadaFilter filtro);
    public BusquedaUnidadSupervisadaDTO cargaDataUnidadOperativa(UnidadSupervisadaFilter filtro);
	public BusquedaUnidadSupervisadaDTO obtenerUnidadSupervisada(UnidadSupervisadaFilter filtro);
	public UnidadSupervisadaDTO eliminarUnidadSupervisada(UnidadSupervisadaFilter filtro);
	public List<BusquedaDireccionxUnidadSupervidaDTO> listarDireccionUnidadSupervisada(UnidadSupervisadaDTO unidadSupervisadaDTO);
	public BusquedaDireccionxUnidadSupervidaDTO obtenerDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidadSupervisada);
	public DireccionEmpSupDTO eliminardireccionEmpresaSupervisada(DireccionEmpSupDTO direccion, UsuarioDTO usuario);
	public DireccionUnidadSupervisadaDTO guardarDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidad, UsuarioDTO usuario);
	public DireccionUnidadSupervisadaDTO actualizarDireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccionUnidad, UsuarioDTO usuario);
	public DireccionUnidadSupervisadaDTO eliminardireccionUnidadSupervisada(DireccionUnidadSupervisadaDTO direccion, UsuarioDTO usuario);
	public UnidadSupervisadaDTO guardarUnidadSupervisada(UnidadSupervisadaDTO unidad, UsuarioDTO usuario);
	public UnidadSupervisadaDTO actualizarUnidadSupervisada(UnidadSupervisadaDTO unidad, UsuarioDTO usuario);
	public List<BusquedaUnidadSupervisadaDTO> cargaDataUnidadOperativaMasiva(UnidadSupervisadaFilter filtro);
        
        public List<UnidadSupervisadaDTO> getUnidadSupervisadaDTO(UnidadSupervisadaFilter filtro);
        public DireccionUnidadSupervisadaDTO buscarDireccUnidSupInps(String codigoOsinergmin);
        /* OSINE_MANT_DSHL_003 - Inicio */
        public List<BusquedaUnidadSupervisadaDTO> cargaDataUnidadOperativaMasivaUsuario(UnidadSupervisadaFilter filtro,Long idUsuario);
		/* OSINE_MANT_DSHL_003 - Fin */
}
