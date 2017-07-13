/**
* Resumen		
* Objeto		: UnidadOrganicaBuilder.java
* Descripción		: UnidadOrganicaBuilder
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE791          19/08/2016      Jose Herrera                Incluir Sigla de Division en Numero de Orden Servicio
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiUnidadOrganica;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;

import java.util.ArrayList;
import java.util.List;

/**
*
* @author l_garcia
*/

public class UnidadOrganicaBuilder {
    
    public static List<UnidadOrganicaDTO> toListUnidadOrganicaDto(List<MdiUnidadOrganica> lista) {
      UnidadOrganicaDTO unidadOrganicaDTO;
        List<UnidadOrganicaDTO> listaUnidadOrganicaDTO = new ArrayList<UnidadOrganicaDTO>();
        if (lista != null) {
            for (MdiUnidadOrganica maestro : lista) {
                unidadOrganicaDTO = toUnidadOrganicaDto(maestro); 
                listaUnidadOrganicaDTO.add(unidadOrganicaDTO);
            }
        }
        return listaUnidadOrganicaDTO;
    }
    
    public static UnidadOrganicaDTO toUnidadOrganicaDto(MdiUnidadOrganica unidadOrganica) {
        UnidadOrganicaDTO unidadOrganicaDTO = new UnidadOrganicaDTO();
        unidadOrganicaDTO.setIdUnidadOrganica(unidadOrganica.getIdUnidadOrganica());
        unidadOrganicaDTO.setIdUnidadOrganicaSuperior(unidadOrganica.getIdUnidadOrganicaSuperior());
        unidadOrganicaDTO.setDescripcion(unidadOrganica.getDescripcion());
        unidadOrganicaDTO.setEstado(unidadOrganica.getEstado());
        /* OSINE791 - RSIS1 Inicio */
        unidadOrganicaDTO.setSigla(unidadOrganica.getSigla());
        /* OSINE791 - RSIS1 Fin */        
        return  unidadOrganicaDTO;
    }
       
}
