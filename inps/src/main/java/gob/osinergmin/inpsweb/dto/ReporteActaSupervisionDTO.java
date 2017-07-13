/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

/**
 *
 * @author zchaupis
 */
public class ReporteActaSupervisionDTO {
    private String indice;
    private String infraccion;
    private String comentario;
    private String baseLegal;

    /**
     * @return the indice
     */
    public String getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(String indice) {
        this.indice = indice;
    }

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
}
