/**
* Resumen		
* Objeto		: PghObligacionTipo.java
* Descripción		: Clase del modelo de dominio PghObligacionTipo
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* 
*/ 

package gob.osinergmin.inpsweb.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author gvillanueva
 */
@Entity
@Table(name = "PGH_OBLIGACION_TIPO")
@NamedQueries({
	@NamedQuery(name = "PghObligacionTipo.findAll", query = "SELECT p FROM PghObligacionTipo p where estado='1' "),
    @NamedQuery(name = "PghObligacionTipo.findValida", query = "SELECT p FROM PghObligacionTipo p where p.estado=:estado and upper(p.nombre)=:nombre "),
    @NamedQuery(name = "PghObligacionTipo.findSupervMuestralAll", query = "SELECT p.idObligacionTipo,p.nombre," +
    		"( SELECT count(ps) FROM PghObligacionSubTipo ps where ps.estado='1' and ps.identificadorSeleccion='S' and ps.idObligacionTipo.idObligacionTipo=p.idObligacionTipo ) as tieneMuestra " +
    		"FROM PghObligacionTipo p  " +
    		"WHERE p.estado='1' ")
    })
public class PghObligacionTipo extends Auditoria {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBLIGACION_TIPO")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_OBLIGACION_TIPO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idObligacionTipo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pghObligacionTipo", fetch = FetchType.LAZY)
    private List<PghProcesoObligacionTipo> pghProcesoObligacionTipoList;
    /*OSINE_SFS-480 - RSIS 26 - Inicio */
    @OneToMany(mappedBy = "idObligacionTipo", fetch = FetchType.LAZY)
    private List<PghObligacionSubTipo> pghObligacionSubTipoList;
    /*OSINE_SFS-480 - RSIS 26 - Fin */
    @Transient
    private Long tieneMuestra;
    @OneToMany(mappedBy = "idObligacionTipo", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;
    /*OSINE_SFS-1344 - Inicio */
    @OneToMany(mappedBy = "idObligacionTipo", fetch = FetchType.LAZY)
    private List<PghObliTipoUnidOrga> pghObliTipoUnidOrgaList;
    /*OSINE_SFS-1344 - Fin */
    
    public PghObligacionTipo() {
    }

    public PghObligacionTipo(Long idObligacionTipo) {
        this.idObligacionTipo = idObligacionTipo;
    }
    public PghObligacionTipo(Long idObligacionTipo,String nombre) {
        this.idObligacionTipo = idObligacionTipo;
        this.nombre=nombre;
    }


    public PghObligacionTipo(Long idObligacionTipo, String estado, Date fechaCreacion, String terminalCreacion, String usuarioCreacion) {
        this.idObligacionTipo = idObligacionTipo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.usuarioCreacion = usuarioCreacion;
    }

    public Long getIdObligacionTipo() {
        return idObligacionTipo;
    }

    public void setIdObligacionTipo(Long idObligacionTipo) {
        this.idObligacionTipo = idObligacionTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
	public List<PghProcesoObligacionTipo> getPghProcesoObligacionTipoList() {
        return pghProcesoObligacionTipoList;
    }

    public void setPghProcesoObligacionTipoList(List<PghProcesoObligacionTipo> pghProcesoObligacionTipoList) {
        this.pghProcesoObligacionTipoList = pghProcesoObligacionTipoList;
    }       
    
    public List<PghObligacionSubTipo> getPghObligacionSubTipoList() {
        return pghObligacionSubTipoList;
    }

    public void setPghObligacionSubTipoList(List<PghObligacionSubTipo> pghObligacionSubTipoList) {
        this.pghObligacionSubTipoList = pghObligacionSubTipoList;
    }    
    
	public Long getTieneMuestra() {
		return tieneMuestra;
	}

	public void setTieneMuestra(Long tieneMuestra) {
		this.tieneMuestra = tieneMuestra;
	}	
	
	@XmlTransient    
    public List<PghObliTipoUnidOrga> getPghObliTipoUnidOrgaList() {
		return pghObliTipoUnidOrgaList;
	}

	public void setPghObliTipoUnidOrgaList(List<PghObliTipoUnidOrga> pghObliTipoUnidOrgaList) {
		this.pghObliTipoUnidOrgaList = pghObliTipoUnidOrgaList;
	}

	@XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idObligacionTipo != null ? idObligacionTipo.hashCode() : 0);
        return hash;
    }
	

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghObligacionTipo)) {
            return false;
        }
        PghObligacionTipo other = (PghObligacionTipo) object;
        if ((this.idObligacionTipo == null && other.idObligacionTipo != null) || (this.idObligacionTipo != null && !this.idObligacionTipo.equals(other.idObligacionTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghObligacionTipo[ idObligacionTipo=" + idObligacionTipo + " ]";
    }
    
}
