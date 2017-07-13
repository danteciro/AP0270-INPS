/**
* Resumen		
* Objeto			: NpsCnfUnidOrga.java
* Descripción		: Clase del modelo de dominio NpsCnfUnidOrga.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/ 

package gob.osinergmin.inpsweb.domain;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "NPS_CNF_UNID_ORGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsCnfUnidOrga.findAll", query = "SELECT n FROM NpsCnfUnidOrga n"),
    @NamedQuery(name = "NpsCnfUnidOrga.findByIdUnidadOrganica", query = "SELECT n FROM NpsCnfUnidOrga n WHERE n.idUnidadOrganica.idUnidadOrganica = :idUnidadOrganica and n.estado='1' ")
})
public class NpsCnfUnidOrga extends Auditoria {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CNF_UNID_ORGA")
    private Long idCnfUnidOrga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "BANDEJA_INPS")
    private Long bandejaInps;
    @JoinColumn(name = "ID_UNIDAD_ORGANICA", referencedColumnName = "ID_UNIDAD_ORGANICA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiUnidadOrganica idUnidadOrganica;

    public NpsCnfUnidOrga() {
    }

    public NpsCnfUnidOrga(Long idCnfUnidOrga) {
        this.idCnfUnidOrga = idCnfUnidOrga;
    }

    public Long getIdCnfUnidOrga() {
        return idCnfUnidOrga;
    }

    public void setIdCnfUnidOrga(Long idCnfUnidOrga) {
        this.idCnfUnidOrga = idCnfUnidOrga;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getBandejaInps() {
        return bandejaInps;
    }

    public void setBandejaInps(Long bandejaInps) {
        this.bandejaInps = bandejaInps;
    }

    public MdiUnidadOrganica getIdUnidadOrganica() {
        return idUnidadOrganica;
    }

    public void setIdUnidadOrganica(MdiUnidadOrganica idUnidadOrganica) {
        this.idUnidadOrganica = idUnidadOrganica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCnfUnidOrga != null ? idCnfUnidOrga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NpsCnfUnidOrga)) {
            return false;
        }
        NpsCnfUnidOrga other = (NpsCnfUnidOrga) object;
        if ((this.idCnfUnidOrga == null && other.idCnfUnidOrga != null) || (this.idCnfUnidOrga != null && !this.idCnfUnidOrga.equals(other.idCnfUnidOrga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.NpsCnfUnidOrga[ idCnfUnidOrga=" + idCnfUnidOrga + " ]";
    }
    
}
