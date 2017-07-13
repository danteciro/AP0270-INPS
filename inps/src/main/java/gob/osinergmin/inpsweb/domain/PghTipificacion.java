/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

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
@Table(name = "PGH_TIPIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghTipificacion.findAll", query = "SELECT p FROM PghTipificacion p"),
    @NamedQuery(name = "PghTipificacion.findByIdTipificacion", query = "SELECT p FROM PghTipificacion p WHERE p.idTipificacion = :idTipificacion")})
public class PghTipificacion extends Auditoria {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPIFICACION")
    private Long idTipificacion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ID_TIPO_MONEDA")
    private Long idTipoMoneda;
    @Column(name = "SANCION_MONETARIA")
    private String sancionMonetaria;
    @Column(name = "ID_TIPIFICACION_PADRE")
    private Long idTipificacionPadre;
    @Column(name = "BASES_LEGALES")
    private String basesLegales;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "COD_TIPIFICACION")
    private String codTipificacion;
    @Column(name = "CLASE_SANCION")
    private Long claseSancion;
    @OneToMany(mappedBy = "idTipificacion",fetch = FetchType.LAZY)
    private List<PghDetalleSupervision> pghDetalleSupervisionList;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pghTipificacion1")
//    private PghTipificacion pghTipificacion;
//    @JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION", insertable = false, updatable = false)
//    @OneToOne(optional = false)
//    private PghTipificacion pghTipificacion1;
    
    @OneToMany(mappedBy = "idTipificacion",fetch = FetchType.LAZY)
    private List<PghObligacionTipificacion> pghObligacionTipificacionList;
    @OneToMany(mappedBy = "idTipificacion",fetch = FetchType.LAZY)
    private List<PghTipificacionSancion> pghTipificacionSancionList;
    @OneToMany(mappedBy = "idTipificacion")
    private List<PghObliTipiCriterio> pghObliTipiCriterioList;

    public PghTipificacion() {
    }

    public PghTipificacion(Long idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public Long getIdTipificacion() {
        return idTipificacion;
    }

    public void setIdTipificacion(Long idTipificacion) {
        this.idTipificacion = idTipificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Long idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getSancionMonetaria() {
        return sancionMonetaria;
    }

    public void setSancionMonetaria(String sancionMonetaria) {
        this.sancionMonetaria = sancionMonetaria;
    }

    public Long getIdTipificacionPadre() {
        return idTipificacionPadre;
    }

    public void setIdTipificacionPadre(Long idTipificacionPadre) {
        this.idTipificacionPadre = idTipificacionPadre;
    }

    public String getBasesLegales() {
        return basesLegales;
    }

    public void setBasesLegales(String basesLegales) {
        this.basesLegales = basesLegales;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getCodTipificacion() {
        return codTipificacion;
    }

    public void setCodTipificacion(String codTipificacion) {
        this.codTipificacion = codTipificacion;
    }

    public Long getClaseSancion() {
        return claseSancion;
    }

    public void setClaseSancion(Long claseSancion) {
        this.claseSancion = claseSancion;
    }

    @XmlTransient
    public List<PghDetalleSupervision> getPghDetalleSupervisionList() {
        return pghDetalleSupervisionList;
    }

    public void setPghDetalleSupervisionList(List<PghDetalleSupervision> pghDetalleSupervisionList) {
        this.pghDetalleSupervisionList = pghDetalleSupervisionList;
    }

//    public PghTipificacion getPghTipificacion() {
//        return pghTipificacion;
//    }
//
//    public void setPghTipificacion(PghTipificacion pghTipificacion) {
//        this.pghTipificacion = pghTipificacion;
//    }
//
//    public PghTipificacion getPghTipificacion1() {
//        return pghTipificacion1;
//    }
//
//    public void setPghTipificacion1(PghTipificacion pghTipificacion1) {
//        this.pghTipificacion1 = pghTipificacion1;
//    }

    @XmlTransient
    public List<PghObligacionTipificacion> getPghObligacionTipificacionList() {
        return pghObligacionTipificacionList;
    }

    public void setPghObligacionTipificacionList(List<PghObligacionTipificacion> pghObligacionTipificacionList) {
        this.pghObligacionTipificacionList = pghObligacionTipificacionList;
    }

    @XmlTransient
    public List<PghTipificacionSancion> getPghTipificacionSancionList() {
        return pghTipificacionSancionList;
    }

    public void setPghTipificacionSancionList(List<PghTipificacionSancion> pghTipificacionSancionList) {
        this.pghTipificacionSancionList = pghTipificacionSancionList;
    }
    
    @XmlTransient
    public List<PghObliTipiCriterio> getPghObliTipiCriterioList() {
        return pghObliTipiCriterioList;
    }

    public void setPghObliTipiCriterioList(List<PghObliTipiCriterio> pghObliTipiCriterioList) {
        this.pghObliTipiCriterioList = pghObliTipiCriterioList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipificacion != null ? idTipificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghTipificacion)) {
            return false;
        }
        PghTipificacion other = (PghTipificacion) object;
        if ((this.idTipificacion == null && other.idTipificacion != null) || (this.idTipificacion != null && !this.idTipificacion.equals(other.idTipificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghTipificacion[ idTipificacion=" + idTipificacion + " ]";
    }

}
