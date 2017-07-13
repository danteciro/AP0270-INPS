package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.UbigeoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.UbigeoDAO;
import gob.osinergmin.mdicommon.domain.base.BaseConstantesOutBean;
import gob.osinergmin.mdicommon.domain.dto.DepartamentoDTO;
import gob.osinergmin.mdicommon.domain.dto.DistritoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProvinciaDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.in.ObtenerDistritosInRO;
import gob.osinergmin.mdicommon.domain.in.ObtenerProvinciasInRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerDepartamentosOutRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerDistritosOutRO;
import gob.osinergmin.mdicommon.domain.out.ObtenerProvinciasOutRO;
import gob.osinergmin.mdicommon.domain.ui.UbigeoFilter;
import gob.osinergmin.mdicommon.remote.UbigeoEndpoint;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("UbigeoService")
public class UbigeoServiceNegImpl implements UbigeoServiceNeg {	
	private static final Logger LOG = LoggerFactory.getLogger(UbigeoServiceNegImpl.class);
	
	@Inject
	private UbigeoEndpoint ubigeoEndPoint;
        @Inject
        private UbigeoDAO ubigeoDAO;

	public List<DepartamentoDTO> obtenerDepartamentos() {

		List<DepartamentoDTO> listaDepartamentoDTO = new ArrayList<DepartamentoDTO>();
		ObtenerDepartamentosOutRO response = ubigeoEndPoint.obtenerDepartamentos();

		if (BaseConstantesOutBean.SUCCESS.equals(response.getRespuesta().getResultado())) {
			listaDepartamentoDTO = response.getListaDepartamentos();			 
		}
		return listaDepartamentoDTO;
	}

	@Override
	public List<ProvinciaDTO> obtenerProvincias(String idDepartamento) {
		LOG.info("ID Departamento:---> "+idDepartamento);
		List<ProvinciaDTO> listaProvinciaDTO = new ArrayList<ProvinciaDTO>();
		ObtenerProvinciasInRO peticion = new ObtenerProvinciasInRO();
		peticion.setIdDepartamento(idDepartamento);
		ObtenerProvinciasOutRO response = ubigeoEndPoint.obtenerProvincias(peticion);
		LOG.info("respuesta : ---> "+response.getRespuesta().getResultado());
		if (BaseConstantesOutBean.SUCCESS.equals(response.getRespuesta().getResultado())) {
			listaProvinciaDTO = response.getListaProvincias();			 
		}
		return listaProvinciaDTO;
	}

	@Override
	public List<DistritoDTO> obtenerDistritos(String idDepartamento,String idProvincia) {
		LOG.info("ID Departamento:---> "+idDepartamento);
		LOG.info("ID Provincia:---> "+idProvincia);
		List<DistritoDTO> listaDistritoDTO = new ArrayList<DistritoDTO>();
		ObtenerDistritosInRO peticion = new ObtenerDistritosInRO();
		peticion.setIdDepartamento(idDepartamento);
		peticion.setIdProvincia(idProvincia);
		ObtenerDistritosOutRO response = ubigeoEndPoint.obtenerDistritos(peticion);
		LOG.info("respuesta : ---> "+response.getRespuesta().getResultado());
		if (BaseConstantesOutBean.SUCCESS.equals(response.getRespuesta().getResultado())) {
			listaDistritoDTO = response.getListaDistritos();	
		}
		return listaDistritoDTO;
	}

    @Override
    public List<UbigeoDTO> obtenerUbigeo(UbigeoFilter ubigeoFilter) {
      
        LOG.info("obtenerUbigeo");
        List<UbigeoDTO> retorno=null;        
        try{
             retorno=ubigeoDAO.obtenerUbigeo(ubigeoFilter);
            
        }catch(Exception e){
            LOG.error("Error en obtenerUbigeo",e);
        }
        return retorno;}
	
}