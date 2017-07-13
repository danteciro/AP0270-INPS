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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PGH_PROC_TRAM_ACTIVIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProcTramActividad.findAll", query = "SELECT p FROM PghProcTramActividad p"),
    @NamedQuery(name = "PghProcTramActividad.findByIdProcTramActi", query = "SELECT p FROM PghProcTramActividad p WHERE p.idProcTramActi = :idProcTramActi"),
    @NamedQuery(name = "PghProcTramActividad.findByEstado", query = "SELECT p FROM PghProcTramActividad p WHERE p.estado = :estado"),
    @NamedQuery(name = "PghProcTramActividad.findByUsuarioCreacion", query = "SELECT p FROM PghProcTramActividad p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "PghProcTramActividad.findByFechaCreacion", query = "SELECT p FROM PghProcTramActividad p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PghProcTramActividad.findByTerminalCreacion", query = "SELECT p FROM PghProcTramActividad p WHERE p.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "PghProcTramActividad.findByUsuarioActualizacion", query = "SELECT p FROM PghProcTramActividad p WHERE p.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "PghProcTramActividad.findByFechaActualizacion", query = "SELECT p FROM PghProcTramActividad p WHERE p.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "PghProcTramActividad.findByTerminalActualizacion", query = "SELECT p FROM PghProcTramActividad p WHERE p.terminalActualizacion = :terminalActualizacion")})
public class PghProcTramActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROC_TRAM_ACTI")
    private Long idProcTramActi;
    @Column(name = "ESTADO")
    private Character estado;
    @Size(max = 38)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 38)
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
    @OneToMany(mappedBy = "idProcTramActi", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;
    @JoinColumn(name = "ID_PROCEDIMIENTO_TRAMITE", referencedColumnName = "ID_PROCEDIMIENTO_TRAMITE")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghProcedimientoTramite idProcedimientoTramite;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiActividad idActividad;

    public PghProcTramActividad() {
    }

    public PghProcTramActividad(Long idProcTramActi) {
        this.idProcTramActi = idProcTramActi;
    }

    public Long getIdProcTramActi() {
        return idProcTramActi;
    }

    public void setIdProcTramActi(Long idProcTramActi) {
        this.idProcTramActi = idProcTramActi;
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
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

    public PghProcedimientoTramite getIdProcedimientoTramite() {
        return idProcedimientoTramite;
    }

    public void setIdProcedimientoTramite(PghProcedimientoTramite idProcedimientoTramite) {
        this.idProcedimientoTramite = idProcedimientoTramite;
    }

    public MdiActividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(MdiActividad idActividad) {
        this.idActividad = idActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcTramActi != null ? idProcTramActi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProcTramActividad)) {
            return false;
        }
        PghProcTramActividad other = (PghProcTramActividad) object;
        if ((this.idProcTramActi == null && other.idProcTramActi != null) || (this.idProcTramActi != null && !this.idProcTramActi.equals(other.idProcTramActi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghProcTramActividad[ idProcTramActi=" + idProcTramActi + " ]";
    }
    
}
