/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_CARGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghCargo.findAll", query = "SELECT p FROM PghCargo p"),
    @NamedQuery(name = "PghCargo.findByIdCargo", query = "SELECT p FROM PghCargo p WHERE p.idCargo = :idCargo"),
    @NamedQuery(name = "PghCargo.findByNombreCargo", query = "SELECT p FROM PghCargo p WHERE p.nombreCargo = :nombreCargo"),
    @NamedQuery(name = "PghCargo.findByEstado", query = "SELECT p FROM PghCargo p WHERE p.estado = :estado"),
    @NamedQuery(name = "PghCargo.findByUsuarioCreacion", query = "SELECT p FROM PghCargo p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "PghCargo.findByFechaCreacion", query = "SELECT p FROM PghCargo p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PghCargo.findByTerminalCreacion", query = "SELECT p FROM PghCargo p WHERE p.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "PghCargo.findByUsuarioActualizacion", query = "SELECT p FROM PghCargo p WHERE p.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "PghCargo.findByFechaActualizacion", query = "SELECT p FROM PghCargo p WHERE p.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "PghCargo.findByTerminalActualizacion", query = "SELECT p FROM PghCargo p WHERE p.terminalActualizacion = :terminalActualizacion")})
public class PghCargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CARGO")
    private Long idCargo;
    @Size(max = 20)
    @Column(name = "NOMBRE_CARGO")
    private String nombreCargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private Character estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 38)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 38)
    @Column(name = "TERMINAL_CREACION")
    private String terminalCreacion;
    @Size(max = 38)
    @Column(name = "USUARIO_ACTUALIZACION")
    private String usuarioActualizacion;
    @Column(name = "FECHA_ACTUALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Size(max = 38)
    @Column(name = "TERMINAL_ACTUALIZACION")
    private String terminalActualizacion;
    @OneToMany(mappedBy = "idCargo", fetch = FetchType.LAZY)
    private List<PghPersonal> pghPersonalList;

    public PghCargo() {
    }

    public PghCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public PghCargo(Long idCargo, Character estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idCargo = idCargo;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
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

    @XmlTransient
    @JsonIgnore
    public List<PghPersonal> getPghPersonalList() {
        return pghPersonalList;
    }

    public void setPghPersonalList(List<PghPersonal> pghPersonalList) {
        this.pghPersonalList = pghPersonalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghCargo)) {
            return false;
        }
        PghCargo other = (PghCargo) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghCargo[ idCargo=" + idCargo + " ]";
    }
    
}
