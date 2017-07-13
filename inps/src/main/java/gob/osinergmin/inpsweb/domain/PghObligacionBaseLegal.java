/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "PGH_OBLIGACION_BASE_LEGAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghObligacionBaseLegal.findAll", query = "SELECT p FROM PghObligacionBaseLegal p"),
    @NamedQuery(name = "PghObligacionBaseLegal.findByIdOblBase", query = "SELECT p FROM PghObligacionBaseLegal p WHERE p.idOblBase = :idOblBase")})
public class PghObligacionBaseLegal extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBL_BASE")
    private BigDecimal idOblBase;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @JoinColumn(name = "ID_BASE_LEGAL", referencedColumnName = "ID_BASE_LEGAL")
    @ManyToOne
    private PghBaseLegal idBaseLegal;
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne
    private PghObligacion idObligacion;

    public PghObligacionBaseLegal() {
    }
    public PghObligacionBaseLegal(Long idObligacion,Long idBaseLegal, String descripcionBase) {
        this.idObligacion = new PghObligacion(idObligacion);
        this.idBaseLegal = new PghBaseLegal(idBaseLegal,descripcionBase);
    }

    public PghObligacionBaseLegal(BigDecimal idOblBase) {
        this.idOblBase = idOblBase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getIdOblBase() {
        return idOblBase;
    }

    public void setIdOblBase(BigDecimal idOblBase) {
        this.idOblBase = idOblBase;
    }

    public String getCodTrazabilidad() {
        return codTrazabilidad;
    }

    public void setCodTrazabilidad(String codTrazabilidad) {
        this.codTrazabilidad = codTrazabilidad;
    }

    public BigInteger getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(BigInteger idPadre) {
        this.idPadre = idPadre;
    }

    public String getCodAccion() {
        return codAccion;
    }

    public void setCodAccion(String codAccion) {
        this.codAccion = codAccion;
    }

    public PghBaseLegal getIdBaseLegal() {
        return idBaseLegal;
    }

    public void setIdBaseLegal(PghBaseLegal idBaseLegal) {
        this.idBaseLegal = idBaseLegal;
    }

    public PghObligacion getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(PghObligacion idObligacion) {
        this.idObligacion = idObligacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOblBase != null ? idOblBase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghObligacionBaseLegal)) {
            return false;
        }
        PghObligacionBaseLegal other = (PghObligacionBaseLegal) object;
        if ((this.idOblBase == null && other.idOblBase != null) || (this.idOblBase != null && !this.idOblBase.equals(other.idOblBase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghObligacionBaseLegal[ idOblBase=" + idOblBase + " ]";
    }
    
}
