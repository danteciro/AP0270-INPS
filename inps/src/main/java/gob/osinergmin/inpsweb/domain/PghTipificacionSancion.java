/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_TIPIFICACION_SANCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghTipificacionSancion.findAll", query = "SELECT p FROM PghTipificacionSancion p"),
    @NamedQuery(name = "PghTipificacionSancion.findByIdTipiSanc", query = "SELECT p FROM PghTipificacionSancion p WHERE p.idTipiSanc = :idTipiSanc")})
public class PghTipificacionSancion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPI_SANC")
    private BigDecimal idTipiSanc;
    @Column(name = "NIVEL")
    private Long nivel;
    @Column(name = "ESTADO")
    private Character estado;
    @JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghTipificacion idTipificacion;
    @JoinColumn(name = "ID_TIPO_SANCION", referencedColumnName = "ID_TIPO_SANCION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghTipoSancion idTipoSancion;
    @JoinColumn(name = "ID_CRITERIO", referencedColumnName = "ID_CRITERIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghCriterio idCriterio;
    @JoinColumn(name = "ID_DETALLE_CRITERIO", referencedColumnName = "ID_DETALLE_CRITERIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleCriterio idDetalleCriterio;

    public PghTipificacionSancion() {
    }

    public PghTipificacionSancion(BigDecimal idTipiSanc) {
        this.idTipiSanc = idTipiSanc;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public BigDecimal getIdTipiSanc() {
        return idTipiSanc;
    }

    public void setIdTipiSanc(BigDecimal idTipiSanc) {
        this.idTipiSanc = idTipiSanc;
    }

    public PghTipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(PghTipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public PghTipoSancion getIdTipoSancion() {
        return idTipoSancion;
    }

    public void setIdTipoSancion(PghTipoSancion idTipoSancion) {
        this.idTipoSancion = idTipoSancion;
    }
    
    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public PghCriterio getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(PghCriterio idCriterio) {
        this.idCriterio = idCriterio;
    }
    
    public PghDetalleCriterio getIdDetalleCriterio() {
        return idDetalleCriterio;
    }

    public void setIdDetalleCriterio(PghDetalleCriterio idDetalleCriterio) {
        this.idDetalleCriterio = idDetalleCriterio;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipiSanc != null ? idTipiSanc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghTipificacionSancion)) {
            return false;
        }
        PghTipificacionSancion other = (PghTipificacionSancion) object;
        if ((this.idTipiSanc == null && other.idTipiSanc != null) || (this.idTipiSanc != null && !this.idTipiSanc.equals(other.idTipiSanc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghTipificacionSancion[ idTipiSanc=" + idTipiSanc + " ]";
    }
    
}
