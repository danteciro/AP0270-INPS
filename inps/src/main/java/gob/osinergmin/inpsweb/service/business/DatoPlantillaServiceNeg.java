/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.DatoPlantillaException;
import java.util.Map;

/**
 *
 * @author jpiro
 */
public interface DatoPlantillaServiceNeg {
    public Map<String, Map<String, Object>> obtenerDatosPlantillaUno(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaDos(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaTres(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaCuatro(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaCinco(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaSeis(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaSiete(Long idSupervision) throws DatoPlantillaException;
    public Map<String, Map<String, Object>> obtenerDatosPlantillaNoSupervisada(Long idSupervision) throws DatoPlantillaException; //mdiosesf
}
