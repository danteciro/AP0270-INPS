/**
* Resumen		
* Objeto		: DestinatarioCorreoNegImpl.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_791-RSIS19 | 06/09/2016 | Zosimo Chaupis Santur | Correo de Notificacion al Supervisor Regional para supervisión de una orden de supervisión DSR-CRITICIDAD
 
*/

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.service.business.DestinatarioCorreoServiceNeg;
import gob.osinergmin.inpsweb.service.dao.DestinatarioCorreoDAO;
import gob.osinergmin.inpsweb.service.exception.DestinatarioCorreoException;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DestinatarioCorreoServiceNeg")
public class DestinatarioCorreoServiceNegImpl implements DestinatarioCorreoServiceNeg {

    private static Logger LOG = LoggerFactory.getLogger(DestinatarioCorreoServiceNegImpl.class);
    @Inject
    private DestinatarioCorreoDAO destinatarioCorreoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<DestinatarioCorreoDTO> getDestinatarioCorreobyUbigeo(DestinatarioCorreoFilter filtro) throws DestinatarioCorreoException {
        LOG.info("getDestinatarioCorreobyUbigeo");
        List<DestinatarioCorreoDTO> retorno = new ArrayList<DestinatarioCorreoDTO>();
        try {
            retorno = destinatarioCorreoDAO.getDestinatarioCorreobyUbigeo(filtro);
        } catch (Exception ex) {
            LOG.error("Error en getDestinatarioCorreobyUbigeo", ex);
            throw new DestinatarioCorreoException(ex.getMessage(),ex);
        }
        return retorno;
    }
}
