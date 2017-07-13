/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.InfraccionServiceNeg;
import gob.osinergmin.inpsweb.service.business.ProductoUnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.InfraccionDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.inpsweb.service.dao.ProductoUnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.service.exception.InfraccionException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.InfraccionDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.InfraccionFilter;
import gob.osinergmin.mdicommon.domain.ui.ProductoUnidadSupervisadaFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zchaupis
 */
@Service("InfraccionServiceNeg")
public class InfraccionServiceNegImpl implements InfraccionServiceNeg {

    private static Logger LOG = LoggerFactory.getLogger(InfraccionServiceNegImpl.class);
    @Inject
    private InfraccionDAO infraccionDAO;
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private ProductoUnidadSupervisadaDAO productoUnidadSupervisadaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<InfraccionDTO> getListInfraccion(InfraccionFilter filtro) throws InfraccionException {
        LOG.info("getListInfraccion");
        List<InfraccionDTO> retorno = new ArrayList<InfraccionDTO>();
        try {
            retorno = infraccionDAO.getListInfraccion(filtro);
        } catch (Exception ex) {
            LOG.error("Error en getListInfraccion", ex);
        }
        return retorno;
    }

    @Override
    public InfraccionDTO ComprobarMedidaSeguridadInfraccion(InfraccionFilter filtro) throws InfraccionException {
        LOG.info("ComprobarMedidaSeguridadInfraccion");
        
        List<InfraccionDTO> ltainfraccionDTO = infraccionDAO.getListInfraccion(filtro);
        InfraccionDTO retorno = new InfraccionDTO();
        try {
            if (ltainfraccionDTO.size() == Constantes.LISTA_UNICO_VALIR) {
                retorno = ltainfraccionDTO.get(Constantes.PRIMERO_EN_LISTA);
            }
            List<MaestroColumnaDTO> ltanroInfraccionCierreTotal = maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_INFRACCION_MEDI_SEG_CIERRE_TOTAL, Constantes.APLICACION_INPS);
            MaestroColumnaDTO nroInfraccionCierreTotal = new MaestroColumnaDTO();
            if (ltanroInfraccionCierreTotal.size() == Constantes.LISTA_UNICO_VALIR) {
                nroInfraccionCierreTotal = ltanroInfraccionCierreTotal.get(Constantes.PRIMERO_EN_LISTA);
            } 
            if (retorno.getCodigo().equals(nroInfraccionCierreTotal.getCodigo())) {
                LOG.info("la Unidad Posee la Infraccion de Cierre total");
                //comprobando que todos sus productos son combustibles liquidos
                List<MaestroColumnaDTO> ltaTipoProductoCombustibleLiquido = maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_PRODUCTO, Constantes.APLICACION_INPS, Constantes.CODIGO_COMBUSTIBLE_LIQUIDO);
                MaestroColumnaDTO TipoProductoCombustibleLiquidoDTO = new MaestroColumnaDTO();
                if (ltaTipoProductoCombustibleLiquido.size() == Constantes.LISTA_UNICO_VALIR) {
                    TipoProductoCombustibleLiquidoDTO = ltaTipoProductoCombustibleLiquido.get(Constantes.PRIMERO_EN_LISTA);
                }
                ProductoUnidadSupervisadaFilter filtroprounisup = new ProductoUnidadSupervisadaFilter();
                filtroprounisup.setIdUnidadSupervisada(filtro.getIdUnidadSupervisada());
                List<ProductoUnidadSupervisadaDTO> ltapro = productoUnidadSupervisadaDAO.getProductobyUnidadSupervisada(filtroprounisup);
                int ContadorTipoCombusLiquido = 0;
                int CantidadTotalProductos = ltapro.size();
                for (ProductoUnidadSupervisadaDTO objeto : ltapro) {
                    if (objeto.getProductoDTO().getTipoProductoDTO().getIdMaestroColumna().equals(TipoProductoCombustibleLiquidoDTO.getIdMaestroColumna())) {
                        ContadorTipoCombusLiquido++;
                    }
                }
                if (ContadorTipoCombusLiquido == CantidadTotalProductos) {
                    LOG.info("Todos los Productos de la Unidad Supervisada son de tipo Combustible Liquido");
                    List<MaestroColumnaDTO> ltamedidaSeguridadCierreTotal = maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_MEDIDA_SEGURIDAD, Constantes.APLICACION_MYC, Constantes.CODIGO_CIERRE_TOTAL);
                    MaestroColumnaDTO medidaSeguridadCierreTotalDTO = new MaestroColumnaDTO();
                    if (ltamedidaSeguridadCierreTotal.size() == Constantes.LISTA_UNICO_VALIR) {
                        medidaSeguridadCierreTotalDTO = ltamedidaSeguridadCierreTotal.get(Constantes.PRIMERO_EN_LISTA);
                    }
                    retorno.setIdmedidaSeguridad(medidaSeguridadCierreTotalDTO.getIdMaestroColumna());
                } else {
                    LOG.info("No Todos los Productos de la Unidad Supervisada son de tipo Combustible Liquido");
                }
            }
        } catch (Exception ex) {
            LOG.error("Error en ComprobarMedidaSeguridadInfraccion", ex);
        }

        return retorno;
    }
}
