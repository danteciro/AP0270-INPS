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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_OBLI_TIPI_CRITERIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghObliTipiCriterio.findAll", query = "SELECT p FROM PghObliTipiCriterio p"),
    @NamedQuery(name = "PghObliTipiCriterio.findByIdObliTipiCriterio", query = "SELECT p FROM PghObliTipiCriterio p WHERE p.idObliTipiCriterio = :idObliTipiCriterio")})
public class PghObliTipiCriterio extends Auditoria{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBLI_TIPI_CRITERIO")
    private Long idObliTipiCriterio;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_CRITERIO", referencedColumnName = "ID_CRITERIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghCriterio idCriterio;
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacion idObligacion;
    @JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghTipificacion idTipificacion;

    public PghObliTipiCriterio() {
    }

    public PghObliTipiCriterio(Long idObliTipiCriterio) {
        this.idObliTipiCriterio = idObliTipiCriterio;
    }

    public PghObliTipiCriterio(Long idObliTipiCriterio, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idObliTipiCriterio = idObliTipiCriterio;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdObliTipiCriterio() {
        return idObliTipiCriterio;
    }

    public void setIdObliTipiCriterio(Long idObliTipiCriterio) {
        this.idObliTipiCriterio = idObliTipiCriterio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghCriterio getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(PghCriterio idCriterio) {
        this.idCriterio = idCriterio;
    }

    public PghObligacion getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(PghObligacion idObligacion) {
        this.idObligacion = idObligacion;
    }

    public PghTipificacion getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(PghTipificacion idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObliTipiCriterio != null ? idObliTipiCriterio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghObliTipiCriterio)) {
            return false;
        }
        PghObliTipiCriterio other = (PghObliTipiCriterio) object;
        if ((this.idObliTipiCriterio == null && other.idObliTipiCriterio != null) || (this.idObliTipiCriterio != null && !this.idObliTipiCriterio.equals(other.idObliTipiCriterio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghObliTipiCriterio[ idObliTipiCriterio=" + idObliTipiCriterio + " ]";
    }
    
}
