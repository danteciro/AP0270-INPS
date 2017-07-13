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
@Table(name = "PGH_MOTIVO_NO_SUPERVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghMotivoNoSupervision.findAll", query = "SELECT p FROM PghMotivoNoSupervision p WHERE p.estado=1"),
    @NamedQuery(name = "PghMotivoNoSupervision.findByIdMotivoNoSupervision", query = "SELECT p FROM PghMotivoNoSupervision p WHERE p.idMotivoNoSupervision = :idMotivoNoSupervision")})
public class PghMotivoNoSupervision extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MOTIVO_NO_SUPERVISION")
    private Long idMotivoNoSupervision;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FLAG_SANCION")
    private String flagSancion;
    @Basic(optional = false)
    @Column(name = "FLAG_ESPECIFICAR")
    private String flagEspecificar;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idMotivoNoSupervision", fetch = FetchType.LAZY)
    private List<PghSupervision> pghSupervisionList;
    
    public PghMotivoNoSupervision() {
    }

    public PghMotivoNoSupervision(Long idMotivoNoSupervision) {
        this.idMotivoNoSupervision = idMotivoNoSupervision;
    }

    public Long getIdMotivoNoSupervision() {
        return idMotivoNoSupervision;
    }

    public void setIdMotivoNoSupervision(Long idMotivoNoSupervision) {
        this.idMotivoNoSupervision = idMotivoNoSupervision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFlagSancion() {
        return flagSancion;
    }

    public void setFlagSancion(String flagSancion) {
        this.flagSancion = flagSancion;
    }

    public String getFlagEspecificar() {
        return flagEspecificar;
    }

    public void setFlagEspecificar(String flagEspecificar) {
        this.flagEspecificar = flagEspecificar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PghSupervision> getPghSupervisionList() {
		return pghSupervisionList;
	}

	public void setPghSupervisionList(List<PghSupervision> pghSupervisionList) {
		this.pghSupervisionList = pghSupervisionList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idMotivoNoSupervision != null ? idMotivoNoSupervision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghMotivoNoSupervision)) {
            return false;
        }
        PghMotivoNoSupervision other = (PghMotivoNoSupervision) object;
        if ((this.idMotivoNoSupervision == null && other.idMotivoNoSupervision != null) || (this.idMotivoNoSupervision != null && !this.idMotivoNoSupervision.equals(other.idMotivoNoSupervision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghMotivoNoSupervision[ idMotivoNoSupervision=" + idMotivoNoSupervision + " ]";
    }
    
}
