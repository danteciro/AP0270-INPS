/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

/**
 *
 * @author zchaupis
 */
public class ReporteDTO {
    private String descripcionDocumento;
    private String nombreArchivo;

    /**
     * @return the descripcionDocumento
     */
    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    /**
     * @param descripcionDocumento the descripcionDocumento to set
     */
    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    
}
