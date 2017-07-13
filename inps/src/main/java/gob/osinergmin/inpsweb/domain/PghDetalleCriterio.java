/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rsantosv
 */
@Entity
@Table(name = "PGH_DETALLE_CRITERIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghDetalleCriterio.findAll", query = "SELECT p FROM PghDetalleCriterio p"),
    @NamedQuery(name = "PghDetalleCriterio.findByIdCriterio", query = "SELECT p FROM PghDetalleCriterio p WHERE p.estado='1' AND p.idCriterio.idCriterio = :idCriterio")})
public class PghDetalleCriterio extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_CRITERIO")
    private Long idDetalleCriterio;
    @Column(name = "SANCION_ESPECIFICA")
    private String sancionEspecifica;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @JoinColumn(name = "ID_CRITERIO", referencedColumnName = "ID_CRITERIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghCriterio idCriterio;
    @Column(name = "SANCION_MONETARIA")
    private String sancionMonetaria;
    @OneToMany(mappedBy = "idDetalleCriterio")
    private List<PghTipificacionSancion> pghTipificacionSancionList;

    public PghDetalleCriterio() {
    }

    public PghDetalleCriterio(Long idDetalleCriterio) {
        this.idDetalleCriterio = idDetalleCriterio;
    }

    public Long getIdDetalleCriterio() {
        return idDetalleCriterio;
    }

    public void setIdDetalleCriterio(Long idDetalleCriterio) {
        this.idDetalleCriterio = idDetalleCriterio;
    }

    public String getSancionEspecifica() {
        return sancionEspecifica;
    }

    public void setSancionEspecifica(String sancionEspecifica) {
        this.sancionEspecifica = sancionEspecifica;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public BigInteger getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(BigInteger idPadre) {
        this.idPadre = idPadre;
    }

    public String getCodTrazabilidad() {
        return codTrazabilidad;
    }

    public void setCodTrazabilidad(String codTrazabilidad) {
        this.codTrazabilidad = codTrazabilidad;
    }

    public String getCodAccion() {
        return codAccion;
    }

    public void setCodAccion(String codAccion) {
        this.codAccion = codAccion;
    }

    public PghCriterio getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(PghCriterio idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getSancionMonetaria() {
        return sancionMonetaria;
    }

    public void setSancionMonetaria(String sancionMonetaria) {
        this.sancionMonetaria = sancionMonetaria;
    }
    
    @XmlTransient
    public List<PghTipificacionSancion> getPghTipificacionSancionList() {
        return pghTipificacionSancionList;
    }

    public void setPghTipificacionSancionList(List<PghTipificacionSancion> pghTipificacionSancionList) {
        this.pghTipificacionSancionList = pghTipificacionSancionList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleCriterio != null ? idDetalleCriterio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghDetalleCriterio)) {
            return false;
        }
        PghDetalleCriterio other = (PghDetalleCriterio) object;
        if ((this.idDetalleCriterio == null && other.idDetalleCriterio != null) || (this.idDetalleCriterio != null && !this.idDetalleCriterio.equals(other.idDetalleCriterio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghDetalleCriterio[ idDetalleCriterio=" + idDetalleCriterio + " ]";
    }
    
}
