/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.osinergmin.inpsweb.domain;

import java.math.BigInteger;
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
@Table(name = "PGH_OBLIGACION_TIPIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghObligacionTipificacion.findAll", query = "SELECT p FROM PghObligacionTipificacion p")})
public class PghObligacionTipificacion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBLI_TIPI")
    private Long idObliTipi;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacion idObligacion;
    @JoinColumn(name = "ID_TIPIFICACION", referencedColumnName = "ID_TIPIFICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghTipificacion idTipificacion;

    public PghObligacionTipificacion() {
    }

    public PghObligacionTipificacion(Long idObliTipi) {
        this.idObliTipi = idObliTipi;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Long getIdObliTipi() {
        return idObliTipi;
    }

    public void setIdObliTipi(Long idObliTipi) {
        this.idObliTipi = idObliTipi;
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
        hash += (idObliTipi != null ? idObliTipi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghObligacionTipificacion)) {
            return false;
        }
        PghObligacionTipificacion other = (PghObligacionTipificacion) object;
        if ((this.idObliTipi == null && other.idObliTipi != null) || (this.idObliTipi != null && !this.idObliTipi.equals(other.idObliTipi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghObligacionTipificacion[ idObliTipi=" + idObliTipi + " ]";
    }
    
}
