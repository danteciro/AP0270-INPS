/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

/**
 *
 * @author zchaupis
 */
public class ReporteActaLevantamientoDTO {
    private String infraccion;
    private String baseLegal;
    private String informacionLevantamientoAgente;

    /**
     * @return the infraccion
     */
    public String getInfraccion() {
        return infraccion;
    }

    /**
     * @param infraccion the infraccion to set
     */
    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    /**
     * @return the baseLegal
     */
    public String getBaseLegal() {
        return baseLegal;
    }

    /**
     * @param baseLegal the baseLegal to set
     */
    public void setBaseLegal(String baseLegal) {
        this.baseLegal = baseLegal;
    }

    /**
     * @return the informacionLevantamientoAgente
     */
    public String getInformacionLevantamientoAgente() {
        return informacionLevantamientoAgente;
    }

    /**
     * @param informacionLevantamientoAgente the informacionLevantamientoAgente to set
     */
    public void setInformacionLevantamientoAgente(String informacionLevantamientoAgente) {
        this.informacionLevantamientoAgente = informacionLevantamientoAgente;
    }
    
}
