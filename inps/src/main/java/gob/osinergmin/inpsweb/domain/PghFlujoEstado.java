/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_FLUJO_ESTADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghFlujoEstado.findAll", query = "SELECT p FROM PghFlujoEstado p")
})
public class PghFlujoEstado extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FLUJO_ESTADO")
    private Long idFlujoEstado;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idFlujoEstado", fetch = FetchType.LAZY)
    private List<PghProcesoObligacionTipo> pghProcesoObligacionTipoList;
    @OneToMany(mappedBy = "idFlujoEstado", fetch = FetchType.LAZY)
    private List<PghFlujoEstEstadoProc> pghFlujoEstEstadoProcList;

    public PghFlujoEstado() {
    }

    public PghFlujoEstado(Long idFlujoEstado) {
        this.idFlujoEstado = idFlujoEstado;
    }

    public PghFlujoEstado(Long idFlujoEstado, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idFlujoEstado = idFlujoEstado;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdFlujoEstado() {
        return idFlujoEstado;
    }

    public void setIdFlujoEstado(Long idFlujoEstado) {
        this.idFlujoEstado = idFlujoEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PghProcesoObligacionTipo> getPghProcesoObligacionTipoList() {
        return pghProcesoObligacionTipoList;
    }

    public void setPghProcesoObligacionTipoList(List<PghProcesoObligacionTipo> pghProcesoObligacionTipoList) {
        this.pghProcesoObligacionTipoList = pghProcesoObligacionTipoList;
    }

    @XmlTransient
    public List<PghFlujoEstEstadoProc> getPghFlujoEstEstadoProcList() {
        return pghFlujoEstEstadoProcList;
    }

    public void setPghFlujoEstEstadoProcList(List<PghFlujoEstEstadoProc> pghFlujoEstEstadoProcList) {
        this.pghFlujoEstEstadoProcList = pghFlujoEstEstadoProcList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFlujoEstado != null ? idFlujoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghFlujoEstado)) {
            return false;
        }
        PghFlujoEstado other = (PghFlujoEstado) object;
        if ((this.idFlujoEstado == null && other.idFlujoEstado != null) || (this.idFlujoEstado != null && !this.idFlujoEstado.equals(other.idFlujoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghFlujoEstado[ idFlujoEstado=" + idFlujoEstado + " ]";
    }
    
}
