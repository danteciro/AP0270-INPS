/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.PghProductoSuspender;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zchaupis
 */
public class ProductoSuspenderBuilder {

     public static ProductoDsrScopDTO toProductoSuspenderDTO(PghProductoSuspender registro) {
        ProductoDsrScopDTO registroDTO = new ProductoDsrScopDTO();
        if (registro != null) {
            registroDTO.setIdProductoSuspender(registro.getIdProductoSuspender());
            if (registro.getIdDetalleSupervision() != null && registro.getIdDetalleSupervision().getIdDetalleSupervision() != null) {
                DetalleSupervisionDTO detalleSupervisionDTO = new DetalleSupervisionDTO();
                detalleSupervisionDTO.setIdDetalleSupervision(registro.getIdDetalleSupervision().getIdDetalleSupervision());
                registroDTO.setDetalleSupervision(detalleSupervisionDTO);
            }
            if (registro.getIdProducto()!= null && registro.getIdProducto().getIdProducto() != null) {
                ProductoDTO productoDTO = new ProductoDTO();
                productoDTO.setIdProducto(registro.getIdProducto().getIdProducto());
                productoDTO.setNombreLargo(registro.getIdProducto().getNombreLargo());
                registroDTO.setProductodto(productoDTO);
            }
            registroDTO.setEstadoProductoSuspendido(registro.getEstado());
        }
        return registroDTO;
    }
    public static List<ProductoDsrScopDTO> toListProductoSuspenderDto(List<PghProductoSuspender> lista) {
        ProductoDsrScopDTO registroDTO;
        List<ProductoDsrScopDTO> retorno = new ArrayList<ProductoDsrScopDTO>();
        if (lista != null) {
            for (PghProductoSuspender maestro : lista) {
                registroDTO = toProductoSuspenderDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
}
