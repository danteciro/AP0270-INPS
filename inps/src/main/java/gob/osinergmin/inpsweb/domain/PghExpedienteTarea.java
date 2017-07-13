/**
* Resumen		
* Objeto			: PghOrgaActiModuSecc.java
* Descripción		: Clase DTO PghOrgaActiModuSecc
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/ 
package gob.osinergmin.inpsweb.domain;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author mdiosesf
 */
@Entity
@Table(name = "PGH_EXPEDIENTE_TAREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghExpedienteTarea.findAll", query = "SELECT p FROM PghExpedienteTarea p"),
    @NamedQuery(name = "PghExpedienteTarea.findByIdExpediente", query = "SELECT p FROM PghExpedienteTarea p WHERE p.idExpediente = :idExpediente")})
public class PghExpedienteTarea extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EXPEDIENTE")
    private Long idExpediente;
    @Column(name = "FLAG_CORREO_OFICIO")
    private String flagCorreoOficio;
    @Column(name = "FLAG_ESTADO_REGH_MSFH")
    private String flagEstadoReghMsfh;
    @Column(name = "FLAG_ESTADO_REGH_INPS")
    private String flagEstadoReghInps;
    @Column(name = "FLAG_ENVIAR_CONSTANCIA_SIGED")
    private String flagEnviarConstanciaSiged;
    @Column(name = "FLAG_ESTADO_SIGED")
    private String flagEstadoSiged;
    @Column(name = "FLAG_CORREO_ESTADO_REGH")
    private String flagCorreoEstadoRegh;
    @Column(name = "FLAG_REGISTRA_DOCS_INPS")
    private String flagRegistraDocsInps;
    @Column(name = "FLAG_CORREO_SCOP")
    private String flagCorreoScop;
    @JoinColumn(name = "ID_EXPEDIENTE", referencedColumnName = "ID_EXPEDIENTE")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private PghExpediente pghExpediente;
    

    public PghExpedienteTarea() {
    }

    public PghExpedienteTarea(Long idExpediente) {
        this.idExpediente = idExpediente;
    }
    
    public PghExpedienteTarea(Long idExpediente, String flagCorreoOficio, String flagEstadoReghMsfh, String flagEstadoReghInps, String flagEnviarConstanciaSiged,
    		String flagEstadoSiged, String flagCorreoEstadoRegh, String flagRegistraDocsInps, String flagCorreoScop) {
        this.idExpediente = idExpediente;
        this.flagCorreoOficio = flagCorreoOficio;
        this.flagEstadoReghMsfh = flagEstadoReghMsfh;
        this.flagEstadoReghInps = flagEstadoReghInps;
        this.flagEnviarConstanciaSiged = flagEnviarConstanciaSiged;
        this.flagEstadoSiged = flagEstadoSiged;
        this.flagCorreoEstadoRegh = flagCorreoEstadoRegh;
        this.flagRegistraDocsInps = flagRegistraDocsInps;
        this.flagCorreoScop = flagCorreoScop;
    }

    public Long getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(Long idExpediente) {
        this.idExpediente = idExpediente;
    }

    public String getFlagCorreoOficio() {
        return flagCorreoOficio;
    }

    public void setFlagCorreoOficio(String flagCorreoOficio) {
        this.flagCorreoOficio = flagCorreoOficio;
    }

    public String getFlagEstadoReghMsfh() {
        return flagEstadoReghMsfh;
    }

    public void setFlagEstadoReghMsfh(String flagEstadoReghMsfh) {
        this.flagEstadoReghMsfh = flagEstadoReghMsfh;
    }

    public String getFlagEstadoReghInps() {
        return flagEstadoReghInps;
    }

    public void setFlagEstadoReghInps(String flagEstadoReghInps) {
        this.flagEstadoReghInps = flagEstadoReghInps;
    }

    public String getFlagEnviarConstanciaSiged() {
        return flagEnviarConstanciaSiged;
    }

    public void setFlagEnviarConstanciaSiged(String flagEnviarConstanciaSiged) {
        this.flagEnviarConstanciaSiged = flagEnviarConstanciaSiged;
    }

    public String getFlagEstadoSiged() {
        return flagEstadoSiged;
    }

    public void setFlagEstadoSiged(String flagEstadoSiged) {
        this.flagEstadoSiged = flagEstadoSiged;
    }

    public String getFlagRegistraDocsInps() {
        return flagRegistraDocsInps;
    }

    public void setFlagRegistraDocsInps(String flagRegistraDocsInps) {
        this.flagRegistraDocsInps = flagRegistraDocsInps;
    }

    public String getFlagCorreoScop() {
        return flagCorreoScop;
    }

    public void setFlagCorreoScop(String flagCorreoScop) {
        this.flagCorreoScop = flagCorreoScop;
    }   

    public PghExpediente getPghExpediente() {
		return pghExpediente;
	}

	public void setPghExpediente(PghExpediente pghExpediente) {
		this.pghExpediente = pghExpediente;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idExpediente != null ? idExpediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghExpedienteTarea)) {
            return false;
        }
        PghExpedienteTarea other = (PghExpedienteTarea) object;
        if ((this.idExpediente == null && other.idExpediente != null) || (this.idExpediente != null && !this.idExpediente.equals(other.idExpediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return "gob.osinergmin.inpsweb.domain.PghExpedienteTarea[ idExpediente=" + idExpediente + " ]";
    }

    /**
     * @return the flagCorreoEstadoRegh
     */
    public String getFlagCorreoEstadoRegh() {
        return flagCorreoEstadoRegh;
    }

    /**
     * @param flagCorreoEstadoRegh the flagCorreoEstadoRegh to set
     */
    public void setFlagCorreoEstadoRegh(String flagCorreoEstadoRegh) {
        this.flagCorreoEstadoRegh = flagCorreoEstadoRegh;
    }
    
}
