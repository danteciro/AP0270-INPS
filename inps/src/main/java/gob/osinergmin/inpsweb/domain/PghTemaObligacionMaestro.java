/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpaucar
 */
@Entity
@Table(name = "PGH_TEMA_OBLIGACION_MAESTRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghTemaObligacionMaestro.findAll", query = "SELECT p FROM PghTemaObligacionMaestro p"),
    @NamedQuery(name = "PghTemaObligacionMaestro.findByIdTemaObligacion", query = "SELECT p FROM PghTemaObligacionMaestro p WHERE p.idTemaObligacion = :idTemaObligacion")})
public class PghTemaObligacionMaestro extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Column(name = "ESTADO")
    private Character estado;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TEM_OBL_MA")
    private BigDecimal idTemOblMa;
    @Column(name = "ID_TEMA_OBLIGACION")
    private Long idTemaObligacion;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne
    private PghObligacion idObligacion;

    public PghTemaObligacionMaestro() {
    }

    public PghTemaObligacionMaestro(BigDecimal idTemOblMa) {
        this.idTemOblMa = idTemOblMa;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public BigDecimal getIdTemOblMa() {
        return idTemOblMa;
    }

    public void setIdTemOblMa(BigDecimal idTemOblMa) {
        this.idTemOblMa = idTemOblMa;
    }

    public Long getIdTemaObligacion() {
        return idTemaObligacion;
    }

    public void setIdTemaObligacion(Long idTemaObligacion) {
        this.idTemaObligacion = idTemaObligacion;
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

    public PghObligacion getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(PghObligacion idObligacion) {
        this.idObligacion = idObligacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemOblMa != null ? idTemOblMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghTemaObligacionMaestro)) {
            return false;
        }
        PghTemaObligacionMaestro other = (PghTemaObligacionMaestro) object;
        if ((this.idTemOblMa == null && other.idTemOblMa != null) || (this.idTemOblMa != null && !this.idTemOblMa.equals(other.idTemOblMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghTemaObligacionMaestro[ idTemOblMa=" + idTemOblMa + " ]";
    }
    
}
