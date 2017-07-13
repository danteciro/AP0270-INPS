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
import javax.persistence.JoinColumns;
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
@Table(name = "PGH_CONF_OBLIGACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghConfObligacion.findAll", query = "SELECT p FROM PghConfObligacion p")})
public class PghConfObligacion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONF_OBLIGACION")
    private Long idConfObligacion;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Column(name = "COD_TRAZABILIDAD")
    private String codTrazabilidad;
    @Column(name = "COD_ACCION")
    private String codAccion;
    @JoinColumn(name = "ID_OBLIGACION", referencedColumnName = "ID_OBLIGACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacion idObligacion;
    @JoinColumns({
        @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO"),
        @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD"),
        @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO"),
        @JoinColumn(name = "ID_PRO_OBL_TIP", referencedColumnName = "ID_PRO_OBL_TIP")})
    @ManyToOne(fetch = FetchType.LAZY)
    private PghProcesoObligacionTipo pghProcesoObligacionTipo;

    public PghConfObligacion() {
    }

    public PghConfObligacion(Long idConfObligacion,String codTrazabilidad,String codAccion,Long idObligacion) {
    	this.idConfObligacion=idConfObligacion;
    	this.codTrazabilidad=codTrazabilidad;
    	this.codAccion=codAccion;
    	this.idObligacion = new PghObligacion(idObligacion);
    }
    
    public PghConfObligacion(Long idConfObligacion) {
        this.idConfObligacion = idConfObligacion;
    }
    
    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Long getIdConfObligacion() {
        return idConfObligacion;
    }

    public void setIdConfObligacion(Long idConfObligacion) {
        this.idConfObligacion = idConfObligacion;
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

    public PghProcesoObligacionTipo getPghProcesoObligacionTipo() {
        return pghProcesoObligacionTipo;
    }

    public void setPghProcesoObligacionTipo(PghProcesoObligacionTipo pghProcesoObligacionTipo) {
        this.pghProcesoObligacionTipo = pghProcesoObligacionTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfObligacion != null ? idConfObligacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghConfObligacion)) {
            return false;
        }
        PghConfObligacion other = (PghConfObligacion) object;
        if ((this.idConfObligacion == null && other.idConfObligacion != null) || (this.idConfObligacion != null && !this.idConfObligacion.equals(other.idConfObligacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghConfObligacion[ idConfObligacion=" + idConfObligacion + " ]";
    }
    
}
