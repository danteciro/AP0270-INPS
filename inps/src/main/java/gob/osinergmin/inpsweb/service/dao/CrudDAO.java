/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author jpiro
 */
public interface CrudDAO {
    EntityManager getEm();

	<T> T create(T t);
        
	<T> T find(Object id, Class<T> type);

	<T> T update(T t);
        
        /**
        * Actualiza un registro, creando previamente un registro nuevo con los datos anteriores del registro
        * agrega dato del campo idPadreTrazabilidad = el id del registro original
        * agrega dato del campo claveUnicaTrazabilidad
        * @param t, objeto a actualizar
        * @return el registro actualizado
        * @throws Exception si la tabla no tiene un atributo @Id
        */
        void delete(Object t);
	void deleteAll( Object t );

	<T> T deleteCompuesto(Class<T> type, Object o);

	List findByNamedQuery(String queryName);

	List findByNamedQuery(String queryName, int resultLimit);

	List findByNamedQuery(String namedQueryName, Map<String, Object> parameters);

	List findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit);

	List findByNativeQuery(String nativeQuery, Map<String, Object> parameters);

	List<Object> findByNativeQuery(String nativeQuery,
			Map<String, Object> parameters, int resultLimit);

	Integer actualizarByNamedQuery(String namedQueryName,
			Map<String, Object> parameters);
        
        Long findSequence(String sequence);
}
