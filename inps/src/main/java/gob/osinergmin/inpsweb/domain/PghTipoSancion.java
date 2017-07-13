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
@Table(name = "PGH_TIPO_SANCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghTipoSancion.findAll", query = "SELECT p FROM PghTipoSancion p"),
    @NamedQuery(name = "PghTipoSancion.findByIdTipoSancion", query = "SELECT p FROM PghTipoSancion p WHERE p.idTipoSancion = :idTipoSancion")})
public class PghTipoSancion extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_SANCION")
    private Long idTipoSancion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idTipoSancion",fetch = FetchType.LAZY)
    private List<PghTipificacionSancion> pghTipificacionSancionList;

    public PghTipoSancion() {
    }

    public PghTipoSancion(Long idTipoSancion) {
        this.idTipoSancion = idTipoSancion;
    }

    public Long getIdTipoSancion() {
        return idTipoSancion;
    }

    public void setIdTipoSancion(Long idTipoSancion) {
        this.idTipoSancion = idTipoSancion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PghTipificacionSancion> getPghTipificacionSancionList() {
        return pghTipificacionSancionList;
    }

    public void setPghTipificacionSancionList(List<PghTipificacionSancion> pghTipificacionSancionList) {
        this.pghTipificacionSancionList = pghTipificacionSancionList;
    }
    
    public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSancion != null ? idTipoSancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghTipoSancion)) {
            return false;
        }
        PghTipoSancion other = (PghTipoSancion) object;
        if ((this.idTipoSancion == null && other.idTipoSancion != null) || (this.idTipoSancion != null && !this.idTipoSancion.equals(other.idTipoSancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghTipoSancion[ idTipoSancion=" + idTipoSancion + " ]";
    }
    
}
