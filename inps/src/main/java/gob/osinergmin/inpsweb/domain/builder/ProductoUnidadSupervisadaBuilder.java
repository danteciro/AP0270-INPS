/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghProductoXUnidadSup;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class ProductoUnidadSupervisadaBuilder {
  public static List<ProductoUnidadSupervisadaDTO> toListProductoUnidadSupervisadaDTO(List<PghProductoXUnidadSup> lista) {
        ProductoUnidadSupervisadaDTO registroDTO;
        List<ProductoUnidadSupervisadaDTO> retorno = new ArrayList<ProductoUnidadSupervisadaDTO>();
        if (lista != null) {
            for (PghProductoXUnidadSup maestro : lista) {
                registroDTO = toProductoUnidadSupervisadaDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }

    public static ProductoUnidadSupervisadaDTO toProductoUnidadSupervisadaDTO(PghProductoXUnidadSup registro) {
        ProductoUnidadSupervisadaDTO registroDTO = new ProductoUnidadSupervisadaDTO();
        
        if (registro.getPghProductoXUnidadSupPK() != null) {
            //producto
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setIdProducto(registro.getPghProductoXUnidadSupPK().getIdProducto());
            MaestroColumnaDTO tipoproductoDTO = new MaestroColumnaDTO();
            tipoproductoDTO.setIdMaestroColumna(registro.getPghProductoXUnidadSupPK().getIdTipoProducto());
            productoDTO.setTipoProductoDTO(tipoproductoDTO);
            
            //unidad supervisada
            UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();
            unidadSupervisadaDTO.setIdUnidadSupervisada(registro.getPghProductoXUnidadSupPK().getIdUnidadSupervisada());
            
            registroDTO.setProductoDTO(productoDTO);
            registroDTO.setUnidadSupervisadaDTO(unidadSupervisadaDTO);
        }
        return registroDTO;
    }
}
