/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author htorress
 */
@Entity
@Table(name = "PGH_PERSONAL_ASIGNADO")
@NamedQueries({
    @NamedQuery(name = "PghPersonalAsignado.findAll", query = "SELECT p FROM PghPersonalAsignado p"),
    @NamedQuery(name = "PghPersonalAsignado.findByIdentificadorJefe", query = "SELECT p FROM PghPersonalAsignado p where p.estado='1' and p.idPersonalJefe.idPersonal=:identificadorJefe"),
    @NamedQuery(name = "PghPersonalAsignado.findByIdentificadorSubordinado", query = "SELECT p FROM PghPersonalAsignado p where p.estado='1' and p.idPersonalSubordinado.idPersonal=:identificadorSubordinado")})
public class PghPersonalAsignado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONAL_ASIGNADO")
    private Long idPersonalAsignado;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "TERMINAL_CREACION")
    private String terminalCreacion;
    @Column(name = "USUARIO_ACTUALIZACION")
    private String usuarioActualizacion;
    @Column(name = "FECHA_ACTUALIZACION")
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;
    @Column(name = "TERMINAL_ACTUALIZACION")
    private String terminalActualizacion;
    @JoinColumn(name = "ID_PERSONAL_SUBORDINADO", referencedColumnName = "ID_PERSONAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghPersonal idPersonalSubordinado;
    @JoinColumn(name = "ID_PERSONAL_JEFE", referencedColumnName = "ID_PERSONAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghPersonal idPersonalJefe;

    public PghPersonalAsignado() {
    }

    public PghPersonalAsignado(Long idPersonalAsignado) {
        this.idPersonalAsignado = idPersonalAsignado;
    }

    public Long getIdPersonalAsignado() {
        return idPersonalAsignado;
    }

    public void setIdPersonalAsignado(Long idPersonalAsignado) {
        this.idPersonalAsignado = idPersonalAsignado;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTerminalActualizacion() {
        return terminalActualizacion;
    }

    public void setTerminalActualizacion(String terminalActualizacion) {
        this.terminalActualizacion = terminalActualizacion;
    }

    public PghPersonal getIdPersonalSubordinado() {
        return idPersonalSubordinado;
    }

    public void setIdPersonalSubordinado(PghPersonal idPersonalSubordinado) {
        this.idPersonalSubordinado = idPersonalSubordinado;
    }

    public PghPersonal getIdPersonalJefe() {
        return idPersonalJefe;
    }

    public void setIdPersonalJefe(PghPersonal idPersonalJefe) {
        this.idPersonalJefe = idPersonalJefe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonalAsignado != null ? idPersonalAsignado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghPersonalAsignado)) {
            return false;
        }
        PghPersonalAsignado other = (PghPersonalAsignado) object;
        if ((this.idPersonalAsignado == null && other.idPersonalAsignado != null) || (this.idPersonalAsignado != null && !this.idPersonalAsignado.equals(other.idPersonalAsignado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghPersonalAsignado[ idPersonalAsignado=" + idPersonalAsignado + " ]";
    }
    
}
