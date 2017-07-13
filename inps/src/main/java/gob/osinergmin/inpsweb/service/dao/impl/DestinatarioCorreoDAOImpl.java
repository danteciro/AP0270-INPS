/**
* Resumen		
* Objeto		: DestinatarioCorreodaoImpl.java
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
package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.DestinatarioCorreoBuilder;
import gob.osinergmin.inpsweb.domain.builder.DetalleSupervisionBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DestinatarioCorreoDAO;
import gob.osinergmin.inpsweb.service.exception.DestinatarioCorreoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DestinatarioCorreoDTO;
import gob.osinergmin.mdicommon.domain.ui.DestinatarioCorreoFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zchaupis
 */
@Repository("destinatarioCorreoDAO")
public class DestinatarioCorreoDAOImpl implements DestinatarioCorreoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(DestinatarioCorreoDAOImpl.class);
    @Inject
    private CrudDAO crud;

    @Override
    public List<DestinatarioCorreoDTO> getDestinatarioCorreobyUbigeo(DestinatarioCorreoFilter filtro)throws DestinatarioCorreoException {
        LOG.info("getDestinatarioCorreobyUbigeo: find-inicio");
        List<DestinatarioCorreoDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new PghDestinatarioCorreo ");
            jpql.append(" ( ");
            jpql.append(" destino.idDestinatarioCorreo, ");
            jpql.append(" destino.idPersonal.idPersonal, ");
            jpql.append(" destino.idPersonal.nombre, ");
            jpql.append(" destino.idPersonal.apellidoPaterno, ");
            jpql.append(" destino.idPersonal.apellidoMaterno, ");
            jpql.append(" destino.idPersonal.nombreCompleto, ");
            jpql.append(" destino.idPersonal.correoElectronico ");
            jpql.append(" ) ");
            jpql.append(" FROM PghDestinatarioCorreo destino");
            if (filtro.getIdDepartamento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdDepartamento().trim()) && filtro.getIdProvincia() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdProvincia().trim()) && filtro.getIdDistrito() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdDistrito().trim())) {
                jpql.append("  , PghUbigeoRegion ubi ");
            }
            jpql.append(" where destino.estado = :estadoActivo ");
            jpql.append(" and destino.idPersonal.estado = :estadoActivo ");
            if (filtro.getIdDestinatarioCorreo() != null) {
                jpql.append(" AND destino.idDestinatarioCorreo=:idDestinatarioCorreo ");
            }
            if (filtro.getIdDepartamento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdDepartamento().trim()) && filtro.getIdProvincia() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdProvincia().trim()) && filtro.getIdDistrito() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdDistrito().trim())) {
                jpql.append(" AND destino.idRegion.idRegion = ubi.idRegion.idRegion ");
                jpql.append(" AND ubi.mdiUbigeo.mdiUbigeoPK.idDepartamento =:idDepartamento ");
                jpql.append(" AND ubi.mdiUbigeo.mdiUbigeoPK.idProvincia =:idProvincia ");
                jpql.append(" AND ubi.mdiUbigeo.mdiUbigeoPK.idDistrito =:idDistrito ");
                jpql.append(" AND destino.idRegion.estado = :estadoActivo ");
                jpql.append(" AND ubi.idRegion.estado = :estadoActivo ");
            }
            if (filtro.getCodigoFuncionalidadCorreo() != null) {
                jpql.append(" AND destino.idCorreo.codigoFuncionalidad =:codigoFuncionalCorreo ");
                jpql.append(" AND destino.idCorreo.estado = :estadoActivo ");
            }

            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            if (filtro.getIdDestinatarioCorreo() != null) {
                query.setParameter("idDestinatarioCorreo", filtro.getIdDestinatarioCorreo());
            }
            if (filtro.getIdDepartamento() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdDepartamento().trim()) && filtro.getIdProvincia() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdProvincia().trim()) && filtro.getIdDistrito() != null && !Constantes.CONSTANTE_VACIA.equals(filtro.getIdDistrito().trim())) {
                LOG.info("---getIdDepartamento->"+filtro.getIdDepartamento());
                query.setParameter("idDepartamento", filtro.getIdDepartamento());
                LOG.info("---getIdProvincia->"+filtro.getIdProvincia());
                query.setParameter("idProvincia", filtro.getIdProvincia());
                LOG.info("---getIdDistrito->"+filtro.getIdDistrito());
                query.setParameter("idDistrito", filtro.getIdDistrito());
            }
            if (filtro.getCodigoFuncionalidadCorreo() != null) {
                LOG.info("---getCodigoFuncionalidadCorreo->"+filtro.getCodigoFuncionalidadCorreo());
                query.setParameter("codigoFuncionalCorreo", filtro.getCodigoFuncionalidadCorreo());
            }
            retorno = DestinatarioCorreoBuilder.toListDestinatarioCorreoDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
            throw new DestinatarioCorreoException(e.getMessage(),e);
        }
        LOG.info("getDestinatarioCorreobyUbigeo: find-fin");
        return retorno;
    }
}
