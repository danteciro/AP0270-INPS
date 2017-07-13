/**
* Resumen		
* Objeto		: PghCnfActUniOrganica.java
*---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripcion
* ---------------------------------------------------------------------------------------------------
* OSINE_MANT_DSHL_003 27/06/2017    Claudio Chaucca Umana::Adapter	Objeto asociado a la tabla PGH_CNF_ACT_UNI_ORGANICA
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

@Entity
@Table(name = "PGH_CNF_ACT_UNI_ORGANICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghCnfActUniOrganica.findAll", query = "SELECT p FROM PghCnfActUniOrganica p")
})
public class PghCnfActUniOrganica extends Auditoria {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CNF_ACT_UNI_ORGANICA")
    private Long idCnfActUniOrganica;
    
    @Column(name = "ID_ACTIVIDAD")	
    private Long idActividad;
    
    @Column(name = "ID_UNIDAD_ORGANICA")	
    private Long idUnidadOrganica;

    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdCnfActUniOrganica() {
		return idCnfActUniOrganica;
	}

	public void setIdCnfActUniOrganica(Long idCnfActUniOrganica) {
		this.idCnfActUniOrganica = idCnfActUniOrganica;
	}
	

	public Long getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Long idActividad) {
		this.idActividad = idActividad;
	}

	public Long getIdUnidadOrganica() {
		return idUnidadOrganica;
	}

	public void setIdUnidadOrganica(Long idUnidadOrganica) {
		this.idUnidadOrganica = idUnidadOrganica;
	}
    
}
