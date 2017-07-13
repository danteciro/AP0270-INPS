/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.util.Date;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_PROCESO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProceso.findAll", query = "SELECT p FROM PghProceso p where p.estado='1'"),
    @NamedQuery(name = "PghProceso.findByDifIdentificador", query = "SELECT p FROM PghProceso p where p.estado='1' and p.identificadorProceso != :identificadorProceso")
})
public class PghProceso extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROCESO")
    private Long idProceso;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "IDENTIFICADOR_PROCESO")
    private String identificadorProceso;    
    @OneToMany(mappedBy = "idProceso", fetch = FetchType.LAZY)
    private List<PghEtapa> pghEtapaList;
    @OneToMany(mappedBy = "idProceso", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;
    @OneToMany(mappedBy = "idProceso", fetch = FetchType.LAZY)
    private List<PghEmprSupeActiObli> pghEmprSupeActiObliList;

    public PghProceso() {
    }

    public PghProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    public Long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
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
    @JsonIgnore
    public List<PghEtapa> getPghEtapaList() {
        return pghEtapaList;
    }

    public void setPghEtapaList(List<PghEtapa> pghEtapaList) {
        this.pghEtapaList = pghEtapaList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }
    
    public String getIdentificadorProceso() {
		return identificadorProceso;
	}

	public void setIdentificadorProceso(String identificadorProceso) {
		this.identificadorProceso = identificadorProceso;
	}

	@XmlTransient
    public List<PghEmprSupeActiObli> getPghEmprSupeActiObliList() {
        return pghEmprSupeActiObliList;
    }

    public void setPghEmprSupeActiObliList(List<PghEmprSupeActiObli> pghEmprSupeActiObliList) {
        this.pghEmprSupeActiObliList = pghEmprSupeActiObliList;
    }
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idProceso != null ? idProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProceso)) {
            return false;
        }
        PghProceso other = (PghProceso) object;
        if ((this.idProceso == null && other.idProceso != null) || (this.idProceso != null && !this.idProceso.equals(other.idProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghProceso[ idProceso=" + idProceso + " ]";
    }
    
}
