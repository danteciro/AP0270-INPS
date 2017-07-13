/**
* Resumen		
* Objeto			: PghExpediente.java
* Descripción		: Clase del modelo de dominio DmTitularMinero.
* Fecha de Creación	: 27/10/2016.
* PR de Creación	: OSINE_SFS-1344,
* Autor				: Julio Piro Gonzales.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/ 

package gob.osinergmin.inpsweb.domain;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

@Entity
@Table(name = "DM_TITULAR_MINERO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DmTitularMinero.findAll", query = "SELECT d FROM DmTitularMinero d"),
    @NamedQuery(name = "DmTitularMinero.findCodigoTitularMinero", query = "SELECT d FROM DmTitularMinero d WHERE d.estado='1' and d.codigoTitularMinero =:codigoTitularMinero ")
})
public class DmTitularMinero extends Auditoria {
    
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TITULAR_MINERO")
    private Long idTitularMinero;
    @Size(max = 20)
    @Column(name = "CODIGO_TITULAR_MINERO")
    private String codigoTitularMinero;
    @Size(max = 200)
    @Column(name = "NOMBRE_TITULAR_MINERO")
    private String nombreTitularMinero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTitularMinero", fetch = FetchType.LAZY)
    private List<DmUnidadSupervisada> dmUnidadSupervisadaList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "idTitularMinero", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;   

    public DmTitularMinero() {
    }
    
    public DmTitularMinero(Long idTitularMinero,String nombreTitularMinero) {
        this.idTitularMinero = idTitularMinero;
        this.nombreTitularMinero = nombreTitularMinero;
    }
    
    public DmTitularMinero(Long idTitularMinero,String nombreTitularMinero,String codigoTitularMinero) {
        this.idTitularMinero = idTitularMinero;
        this.nombreTitularMinero = nombreTitularMinero;
        this.codigoTitularMinero = codigoTitularMinero;
    }
    
    public DmTitularMinero(Long idTitularMinero) {
        this.idTitularMinero = idTitularMinero;
    }

    public Long getIdTitularMinero() {
        return idTitularMinero;
    }

    public void setIdTitularMinero(Long idTitularMinero) {
        this.idTitularMinero = idTitularMinero;
    }
    
    public String getCodigoTitularMinero() {
        return codigoTitularMinero;
    }

    public void setCodigoTitularMinero(String codigoTitularMinero) {
        this.codigoTitularMinero = codigoTitularMinero;
    }
    
    public String getNombreTitularMinero() {
        return nombreTitularMinero;
    }

    public void setNombreTitularMinero(String nombreTitularMinero) {
        this.nombreTitularMinero = nombreTitularMinero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<DmUnidadSupervisada> getDmUnidadSupervisadaList() {
        return dmUnidadSupervisadaList;
    }

    public void setDmUnidadSupervisadaList(List<DmUnidadSupervisada> dmUnidadSupervisadaList) {
        this.dmUnidadSupervisadaList = dmUnidadSupervisadaList;
    }   
    
    public List<PghExpediente> getPghExpedienteList() {
		return pghExpedienteList;
	}

	public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
		this.pghExpedienteList = pghExpedienteList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idTitularMinero != null ? idTitularMinero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DmTitularMinero)) {
            return false;
        }
        DmTitularMinero other = (DmTitularMinero) object;
        if ((this.idTitularMinero == null && other.idTitularMinero != null) || (this.idTitularMinero != null && !this.idTitularMinero.equals(other.idTitularMinero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.DmTitularMinero[ idTitularMinero=" + idTitularMinero + " ]";
    }
    
}
