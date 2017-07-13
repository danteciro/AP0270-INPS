/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_FLUJO_EST_ESTADO_PROC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghFlujoEstEstadoProc.findAll", query = "SELECT p FROM PghFlujoEstEstadoProc p")
})
public class PghFlujoEstEstadoProc extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FLUJO_EST_ESTADO_PROC")
    private Long idFlujoEstEstadoProc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_FLUJO_ESTADO", referencedColumnName = "ID_FLUJO_ESTADO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghFlujoEstado idFlujoEstado;
    @JoinColumn(name = "ID_ESTADO_PROCESO_SGT", referencedColumnName = "ID_ESTADO_PROCESO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEstadoProceso idEstadoProcesoSgt;
    @JoinColumn(name = "ID_ESTADO_PROCESO", referencedColumnName = "ID_ESTADO_PROCESO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEstadoProceso idEstadoProceso;

    public PghFlujoEstEstadoProc() {
    }

    public PghFlujoEstEstadoProc(Long idFlujoEstEstadoProc) {
        this.idFlujoEstEstadoProc = idFlujoEstEstadoProc;
    }

    public PghFlujoEstEstadoProc(Long idFlujoEstEstadoProc, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idFlujoEstEstadoProc = idFlujoEstEstadoProc;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdFlujoEstEstadoProc() {
        return idFlujoEstEstadoProc;
    }

    public void setIdFlujoEstEstadoProc(Long idFlujoEstEstadoProc) {
        this.idFlujoEstEstadoProc = idFlujoEstEstadoProc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghFlujoEstado getIdFlujoEstado() {
        return idFlujoEstado;
    }

    public void setIdFlujoEstado(PghFlujoEstado idFlujoEstado) {
        this.idFlujoEstado = idFlujoEstado;
    }

    public PghEstadoProceso getIdEstadoProcesoSgt() {
        return idEstadoProcesoSgt;
    }

    public void setIdEstadoProcesoSgt(PghEstadoProceso idEstadoProcesoSgt) {
        this.idEstadoProcesoSgt = idEstadoProcesoSgt;
    }

    public PghEstadoProceso getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(PghEstadoProceso idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFlujoEstEstadoProc != null ? idFlujoEstEstadoProc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghFlujoEstEstadoProc)) {
            return false;
        }
        PghFlujoEstEstadoProc other = (PghFlujoEstEstadoProc) object;
        if ((this.idFlujoEstEstadoProc == null && other.idFlujoEstEstadoProc != null) || (this.idFlujoEstEstadoProc != null && !this.idFlujoEstEstadoProc.equals(other.idFlujoEstEstadoProc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghFlujoEstEstadoProc[ idFlujoEstEstadoProc=" + idFlujoEstEstadoProc + " ]";
    }
    
}
