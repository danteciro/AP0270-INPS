/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import java.util.Date;

/**
 *
 * @author zchaupis
 */
public class RegistroHidrocarburoExternoDTO {

    private Long idUnidadActividadExterna;
    private Long idUnidadSupervisadaExterno;
    private Long idActividadExterno;
    private String nombreUsuario;
    private String fechaActualizacion;
    private String estadoRegistroHidrocarburo;
    private Date fechaSuspension;

    /**
     * @return the idUnidadActividadExterna
     */
    public Long getIdUnidadActividadExterna() {
        return idUnidadActividadExterna;
    }

    /**
     * @param idUnidadActividadExterna the idUnidadActividadExterna to set
     */
    public void setIdUnidadActividadExterna(Long idUnidadActividadExterna) {
        this.idUnidadActividadExterna = idUnidadActividadExterna;
    }

    /**
     * @return the idUnidadSupervisadaExterno
     */
    public Long getIdUnidadSupervisadaExterno() {
        return idUnidadSupervisadaExterno;
    }

    /**
     * @param idUnidadSupervisadaExterno the idUnidadSupervisadaExterno to set
     */
    public void setIdUnidadSupervisadaExterno(Long idUnidadSupervisadaExterno) {
        this.idUnidadSupervisadaExterno = idUnidadSupervisadaExterno;
    }

    /**
     * @return the idActividadExterno
     */
    public Long getIdActividadExterno() {
        return idActividadExterno;
    }

    /**
     * @param idActividadExterno the idActividadExterno to set
     */
    public void setIdActividadExterno(Long idActividadExterno) {
        this.idActividadExterno = idActividadExterno;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the fechaActualizacion
     */
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the estadoRegistroHidrocarburo
     */
    public String getEstadoRegistroHidrocarburo() {
        return estadoRegistroHidrocarburo;
    }

    /**
     * @param estadoRegistroHidrocarburo the estadoRegistroHidrocarburo to set
     */
    public void setEstadoRegistroHidrocarburo(String estadoRegistroHidrocarburo) {
        this.estadoRegistroHidrocarburo = estadoRegistroHidrocarburo;
    }

    /**
     * @return the fechaSuspension
     */
    public Date getFechaSuspension() {
        return fechaSuspension;
    }

    /**
     * @param fechaSuspension the fechaSuspension to set
     */
    public void setFechaSuspension(Date fechaSuspension) {
        this.fechaSuspension = fechaSuspension;
    }
    
  
}
