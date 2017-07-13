/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author jpiro
 */
@MappedSuperclass
public class Auditoria implements Serializable{
    private static final long serialVersionUID = -7570308418621556060L;

    @Basic(optional = false)
    @Size(min = 1, max = 38)
    @Column(name = "USUARIO_CREACION", updatable = false)
    protected String usuarioCreacion;

    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaCreacion;

    @Basic(optional = false)
    @Size(min = 1, max = 38)
    @Column(name = "TERMINAL_CREACION", updatable = false)
    protected String terminalCreacion;

    @Size(max = 38)
    @Column(name = "USUARIO_ACTUALIZACION", insertable = false)
    protected String usuarioActualizacion;

    @Column(name = "FECHA_ACTUALIZACION", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaActualizacion;

    @Size(max = 38)
    @Column(name = "TERMINAL_ACTUALIZACION", insertable = false)
    protected String terminalActualizacion;

    public Date getFechaCreacion() {
            return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
            return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
            return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
            this.usuarioActualizacion = usuarioActualizacion;
    }

    public String getUsuarioCreacion() {
            return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
            this.usuarioCreacion = usuarioCreacion;
    }

    public String getTerminalActualizacion() {
            return terminalActualizacion;
    }

    public void setTerminalActualizacion(String terminalActualizacion) {
            this.terminalActualizacion = terminalActualizacion;
    }

    public String getTerminalCreacion() {
            return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
            this.terminalCreacion = terminalCreacion;
    }

    @PrePersist
    void createdAt() {		
            this.fechaCreacion = new Date();
    }

    @PreUpdate
    void updatedAt() {
            this.fechaActualizacion = new Date();
    }

    /**
     * Establece los datos de auditoria
     * @param usuarioDTO
     */
    public void setDatosAuditoria(UsuarioDTO usuarioDTO){
        if(usuarioDTO!=null){
           this.usuarioActualizacion=usuarioDTO.getCodigo();
           this.terminalActualizacion=usuarioDTO.getTerminal();    	   
           this.usuarioCreacion=usuarioDTO.getCodigo();
           this.terminalCreacion=usuarioDTO.getTerminal();       
        }     
    }
    
    /**
     * Obtiene los datos de auditoria
     * @param usuarioDTO
     */
    public UsuarioDTO getDatosAuditoria(){
        UsuarioDTO usuarioDTO=new UsuarioDTO();
        usuarioDTO.setCodigo(this.usuarioActualizacion!=null?this.usuarioActualizacion:this.usuarioCreacion);   
        usuarioDTO.setTerminal(this.terminalActualizacion!=null?this.terminalActualizacion:this.terminalCreacion);
        return usuarioDTO;
    }
}
