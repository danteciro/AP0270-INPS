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
public class ReporteCierreParcialDTO {

    private String infraccion;
    private String escenario;
    private String comentario;

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
     * @return the escenario
     */
    public String getEscenario() {
        return escenario;
    }

    /**
     * @param escenario the escenario to set
     */
    public void setEscenario(String escenario) {
        this.escenario = escenario;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
