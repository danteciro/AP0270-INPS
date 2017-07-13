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
@Table(name = "PGH_CRITERIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghCriterio.findAll", query = "SELECT p FROM PghCriterio p")})
public class PghCriterio extends Auditoria {
    @Column(name = "TIPO_CRITERIO")
    private String tipoCriterio;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CRITERIO")
    private Long idCriterio;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @Column(name = "SANCION_MONETARIA")
    private String sancionMonetaria;
//    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PghObligacion idObligacion;
//    @JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PghTipificacion idTipificacion;
    @OneToMany(mappedBy = "idCriterio",fetch = FetchType.LAZY)
    private List<PghDetalleCriterio> pghDetalleCriterioList;
    @OneToMany(mappedBy = "idCriterio",fetch = FetchType.LAZY)
    private List<PghDetalleSupervision> pghDetalleSupervisionList;
    @OneToMany(mappedBy = "idCriterio",fetch = FetchType.LAZY)
    private List<PghObliTipiCriterio> pghObliTipiCriterioList;
    @OneToMany(mappedBy = "idCriterio",fetch = FetchType.LAZY)
    private List<PghTipificacionSancion> pghTipificacionSancionList;

    public PghCriterio() {
    }

    public PghCriterio(Long idCriterio) {
        this.idCriterio = idCriterio;
    }

    public Long getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(Long idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

//    public PghObligacion getIdObligacion() {
//        return idObligacion;
//    }
//
//    public void setIdObligacion(PghObligacion idObligacion) {
//        this.idObligacion = idObligacion;
//    }

//    public PghTipificacion getIdTipificacion() {
//        return idTipificacion;
//    }
//
//    public void setIdTipificacion(PghTipificacion idTipificacion) {
//        this.idTipificacion = idTipificacion;
//    }

    public String getTipoCriterio() {
        return tipoCriterio;
    }

    public void setTipoCriterio(String tipoCriterio) {
        this.tipoCriterio = tipoCriterio;
    }
    
    @XmlTransient
    public List<PghDetalleCriterio> getPghDetalleCriterioList() {
        return pghDetalleCriterioList;
    }

    public void setPghDetalleCriterioList(List<PghDetalleCriterio> pghDetalleCriterioList) {
        this.pghDetalleCriterioList = pghDetalleCriterioList;
    }

    @XmlTransient
    public List<PghDetalleSupervision> getPghDetalleSupervisionList() {
        return pghDetalleSupervisionList;
    }

    public void setPghDetalleSupervisionList(List<PghDetalleSupervision> pghDetalleSupervisionList) {
        this.pghDetalleSupervisionList = pghDetalleSupervisionList;
    }

    @XmlTransient
    public List<PghObliTipiCriterio> getPghObliTipiCriterioList() {
        return pghObliTipiCriterioList;
    }

    public void setPghObliTipiCriterioList(List<PghObliTipiCriterio> pghObliTipiCriterioList) {
        this.pghObliTipiCriterioList = pghObliTipiCriterioList;
    }
    
    @XmlTransient
    public List<PghTipificacionSancion> getPghTipificacionSancionList() {
        return pghTipificacionSancionList;
    }

    public void setPghTipificacionSancionList(List<PghTipificacionSancion> pghTipificacionSancionList) {
        this.pghTipificacionSancionList = pghTipificacionSancionList;
    }
    
    
    public String getSancionMonetaria() {
		return sancionMonetaria;
	}

	public void setSancionMonetaria(String sancionMonetaria) {
		this.sancionMonetaria = sancionMonetaria;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCriterio != null ? idCriterio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghCriterio)) {
            return false;
        }
        PghCriterio other = (PghCriterio) object;
        if ((this.idCriterio == null && other.idCriterio != null) || (this.idCriterio != null && !this.idCriterio.equals(other.idCriterio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghCriterio[ idCriterio=" + idCriterio + " ]";
    }

}
