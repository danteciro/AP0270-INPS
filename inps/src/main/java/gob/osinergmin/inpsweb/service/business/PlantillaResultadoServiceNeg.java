/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.PlantillaResultadoException;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;

/**
 *
 * @author jpiro
 */
public interface PlantillaResultadoServiceNeg {
    public DocumentoAdjuntoDTO generaDocumentoPlantilla(Long idPlantillaResultado, Long idSupervision) throws PlantillaResultadoException;
}
