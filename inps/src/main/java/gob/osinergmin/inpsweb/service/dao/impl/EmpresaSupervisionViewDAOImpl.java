package gob.osinergmin.inpsweb.service.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gob.osinergmin.inpsweb.domain.builder.EmpresaViewBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.EmpresaSupervisionViewDAO;
import gob.osinergmin.inpsweb.service.exception.EmpresaSupervisionViewException;
import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;
import gob.osinergmin.inpsweb.util.StringUtil;

@Repository("EmpresaSupervisionViewDAO")
public class EmpresaSupervisionViewDAOImpl implements EmpresaSupervisionViewDAO{
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(EmpresaSupervisionViewDAOImpl.class); 
	
	@Inject
	private CrudDAO crudDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<EmpresaViewDTO> findEmpresas(EmpresaViewFilter empresaViewFilter) throws EmpresaSupervisionViewException {
		Query query = null;
		String queryString =  new String();
		List<EmpresaViewDTO> lista = new ArrayList<EmpresaViewDTO>(); 
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select A.ID_SUPERVISION_RECH_CARGA, " +
					    "    B.ID_INFORME_SUPE_RECH_CARGA, " +
					    "    LE.tipo, " +
					    "   LE.ruc, " +
					    "   LE.empresa, " +
					    "  A.NUMERO_EXPEDIENTE, " + 
					    "   LE.idempresa, " +
					    "   LE.idtipo, " +
					    "   B.ID_INFORME_DOC, " +
					    "   B.NOMBRE_INFORME_DOC, " +
					    " A.ID_OFICIO_DOC, " +
					    " A.NOMBRE_OFICIO_DOC," +
					    " A.anio " +
					 " from Nps_Supervision_Rech_Carga A " +
 					 " inner join NPS_LISTA_EMPRESAS_VW LE on LE.idempresa = A.Emp_Codemp " +
					 " left join nps_informe_supe_rech_carga B on B.Id_Supervision_Rech_Carga=A.Id_Supervision_Rech_Carga " +
 					 " where A.estado = '1' ");
					 /*" where A.Numero_Expediente is not null " +
					 "  and A.estado = '1' " +
					 "  and Extract(year from A.Anio) = 2016 " +
					 "  and le.anio=2016 ");
				       */
			//if( empresaViewFilter.getAnio()!=null){
			if(!StringUtil.isEmpty(empresaViewFilter.getAnio())){
				sql.append(" and Extract(year from A.Anio)  = " + empresaViewFilter.getAnio());
				sql.append(" and le.anio = " + empresaViewFilter.getAnio());
			}
			
			//if(empresaViewFilter.getRuc()!=null){
			if(!StringUtil.isEmpty(empresaViewFilter.getRuc())){
				sql.append(" and  LE.ruc = '" + empresaViewFilter.getRuc() + "'");
			}
			
			//if(empresaViewFilter.getTipoEmpresa()!=null){
			if(!StringUtil.isEmpty(empresaViewFilter.getTipoEmpresa())){
				sql.append(" and LE.idtipo = '" + empresaViewFilter.getTipoEmpresa()+"'");
			}
			
			//if(empresaViewFilter.getEmpresa()!=null){
			if(!StringUtil.isEmpty(empresaViewFilter.getEmpresa())){
				sql.append(" and LE.empresa like '%" + empresaViewFilter.getEmpresa().toUpperCase() + "%'");
			}
			
			//if(empresaViewFilter.getNumeroExpediente()!=null){
			if(!StringUtil.isEmpty(empresaViewFilter.getNumeroExpediente())){
				sql.append(" and A.NUMERO_EXPEDIENTE = " + empresaViewFilter.getNumeroExpediente());
			}
			
			sql.append(" Order by  A.emp_codemp ");
			queryString = sql.toString();
			query = crudDAO.getEm().createNativeQuery(queryString);
			lista = EmpresaViewBuilder.toListObjectEmpresaDTO(query.getResultList()); 
		} catch (Exception e) {
			LOG.error("Error en findEmpresasDAO");
		}
		if(lista!=null && lista.size()>0)
			return lista;
		return lista;
		
	}

	
	
}
