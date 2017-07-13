/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

/**
 *
 * @author zchaupis
 */
public class ReporteProductoDTO {
    private String nroExpediente;
    private String razonSocial;
    private String registroRH;
    private String nroActa;
    private String fechaActa;
    private String descripcionProducto;

    /**
     * @return the nroExpediente
     */
    public String getNroExpediente() {
        return nroExpediente;
    }

    /**
     * @param nroExpediente the nroExpediente to set
     */
    public void setNroExpediente(String nroExpediente) {
        this.nroExpediente = nroExpediente;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the registroRH
     */
    public String getRegistroRH() {
        return registroRH;
    }

    /**
     * @param registroRH the registroRH to set
     */
    public void setRegistroRH(String registroRH) {
        this.registroRH = registroRH;
    }

    /**
     * @return the nroActa
     */
    public String getNroActa() {
        return nroActa;
    }

    /**
     * @param nroActa the nroActa to set
     */
    public void setNroActa(String nroActa) {
        this.nroActa = nroActa;
    }

    /**
     * @return the fechaActa
     */
    public String getFechaActa() {
        return fechaActa;
    }

    /**
     * @param fechaActa the fechaActa to set
     */
    public void setFechaActa(String fechaActa) {
        this.fechaActa = fechaActa;
    }

    /**
     * @return the descripcionProducto
     */
    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    /**
     * @param descripcionProducto the descripcionProducto to set
     */
    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }
    
}
