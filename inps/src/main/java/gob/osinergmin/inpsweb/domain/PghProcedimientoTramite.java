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
@Table(name = "PGH_PROCEDIMIENTO_TRAMITE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProcedimientoTramite.findAll", query = "SELECT p FROM PghProcedimientoTramite p"),
    @NamedQuery(name = "PghProcedimientoTramite.findByIdProcedimientoTramite", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.idProcedimientoTramite = :idProcedimientoTramite"),
    @NamedQuery(name = "PghProcedimientoTramite.findByUsuarioCreacion", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "PghProcedimientoTramite.findByFechaCreacion", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PghProcedimientoTramite.findByTerminalCreacion", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "PghProcedimientoTramite.findByUsuarioActualizacion", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "PghProcedimientoTramite.findByFechaActualizacion", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "PghProcedimientoTramite.findByTerminalActualizacion", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "PghProcedimientoTramite.findByEstado", query = "SELECT p FROM PghProcedimientoTramite p WHERE p.estado = :estado")})
public class PghProcedimientoTramite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROCEDIMIENTO_TRAMITE")
    private Long idProcedimientoTramite;
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
    @Column(name = "ESTADO")
    private Character estado;
    @JoinColumn(name = "ID_TRAMITE", referencedColumnName = "ID_TRAMITE")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghTramite idTramite;
    @JoinColumn(name = "ID_PROCEDIMIENTO", referencedColumnName = "ID_PROCEDIMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghProcedimiento idProcedimiento;
    @OneToMany(mappedBy = "idProcedimientoTramite", fetch = FetchType.LAZY)
    private List<PghProcTramActividad> pghProcTramActividadList;

    public PghProcedimientoTramite() {
    }

    public PghProcedimientoTramite(Long idProcedimientoTramite) {
        this.idProcedimientoTramite = idProcedimientoTramite;
    }

    public Long getIdProcedimientoTramite() {
        return idProcedimientoTramite;
    }

    public void setIdProcedimientoTramite(Long idProcedimientoTramite) {
        this.idProcedimientoTramite = idProcedimientoTramite;
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

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public PghTramite getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(PghTramite idTramite) {
        this.idTramite = idTramite;
    }

    public PghProcedimiento getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(PghProcedimiento idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghProcTramActividad> getPghProcTramActividadList() {
        return pghProcTramActividadList;
    }

    public void setPghProcTramActividadList(List<PghProcTramActividad> pghProcTramActividadList) {
        this.pghProcTramActividadList = pghProcTramActividadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcedimientoTramite != null ? idProcedimientoTramite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProcedimientoTramite)) {
            return false;
        }
        PghProcedimientoTramite other = (PghProcedimientoTramite) object;
        if ((this.idProcedimientoTramite == null && other.idProcedimientoTramite != null) || (this.idProcedimientoTramite != null && !this.idProcedimientoTramite.equals(other.idProcedimientoTramite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghProcedimientoTramite[ idProcedimientoTramite=" + idProcedimientoTramite + " ]";
    }
    
}
