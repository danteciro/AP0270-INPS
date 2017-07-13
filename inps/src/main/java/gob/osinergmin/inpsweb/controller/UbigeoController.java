/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.service.business.UbigeoServiceNeg;
import gob.osinergmin.mdicommon.domain.dto.DistritoDTO;
import gob.osinergmin.mdicommon.domain.dto.ProvinciaDTO;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("ubigeo")
public class UbigeoController {
    private static final Logger LOG = LoggerFactory.getLogger(UbigeoController.class);
    
    @Inject
    UbigeoServiceNeg ubigeoServiceNeg;
    
    @RequestMapping(value = "/buscarProvincias", method = RequestMethod.POST)
    public @ResponseBody
    List<ProvinciaDTO> buscarProvincias(String idDepartamento) {
        LOG.info("idDepartamento = " + idDepartamento);
        List<ProvinciaDTO> lstProvincia = ubigeoServiceNeg.obtenerProvincias(idDepartamento);
        return lstProvincia;
    }

    @RequestMapping(value = "/buscarDistritos", method = RequestMethod.POST)
    public @ResponseBody
    List<DistritoDTO> buscarDistritos(String idProvincia,String idDepartamento) {
        LOG.info("idProvincia = " + idProvincia);
        LOG.info("idDepartamento = " + idDepartamento);
        List<DistritoDTO> lstDistrito = ubigeoServiceNeg.obtenerDistritos(idDepartamento,idProvincia);
        return lstDistrito;
    }
    
}
