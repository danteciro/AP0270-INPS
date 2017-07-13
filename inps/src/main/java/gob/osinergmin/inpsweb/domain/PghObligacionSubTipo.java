/**
* Resumen		
* Objeto		: PghObligacionSubTipo.java
* Descripción		: Clase del modelo de dominio PghObligacionSubTipo
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/*OSINE_SFS-480 - RSIS 26 - Inicio */
@Entity
@Table(name = "PGH_OBLIGACION_SUB_TIPO")
@NamedQueries({
    @NamedQuery(name = "PghObligacionSubTipo.findAll", query = "SELECT p FROM PghObligacionSubTipo p"),
    @NamedQuery(name = "PghObligacionSubTipo.findTipoSupervisionSelMuestral", query = "SELECT DISTINCT pot FROM PghObligacionSubTipo posti "
			+"left join posti.idObligacionTipo pot  "
			+"where posti.identificadorSeleccion = :identiSeleMuestral "),
	@NamedQuery(name = "PghObligacionSubTipo.findByParameters", query = "SELECT p FROM PghObligacionSubTipo p " +
			"where p.estado= :estado " +
			"and p.idObligacionTipo.idObligacionTipo= :idObligacionTipo " +
			"and p.identificadorSeleccion= :identificadorMuestral ")
	})
public class PghObligacionSubTipo extends Auditoria {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name="ID_OBLIGACION_SUB_TIPO_SEQ",sequenceName="PGH_OBLIGACION_SUB_TIPO_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ID_OBLIGACION_SUB_TIPO_SEQ")
    @Column(name = "ID_OBLIGACION_SUB_TIPO")
    private Long idObligacionSubTipo;
    @Size(max = 400)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "IDENTIFICADOR_SELECCION")
    private String identificadorSeleccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idObligacionSubTipo", fetch = FetchType.LAZY)
    private List<PghUnidObliSubTipo> pghUnidObliSubTipoList;
    @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacionTipo idObligacionTipo;
    @OneToMany(mappedBy = "idObligacionSubTipo", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;

    public PghObligacionSubTipo() {
    }

    public PghObligacionSubTipo(Long idObligacionSubTipo) {
        this.idObligacionSubTipo = idObligacionSubTipo;
    }

    public PghObligacionSubTipo(Long idObligacionSubTipo, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idObligacionSubTipo = idObligacionSubTipo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdObligacionSubTipo() {
        return idObligacionSubTipo;
    }

    public void setIdObligacionSubTipo(Long idObligacionSubTipo) {
        this.idObligacionSubTipo = idObligacionSubTipo;
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

    

    public String getIdentificadorSeleccion() {
        return identificadorSeleccion;
    }

    public void setIdentificadorSeleccion(String identificadorSeleccion) {
        this.identificadorSeleccion = identificadorSeleccion;
    }

    public List<PghUnidObliSubTipo> getPghUnidObliSubTipoList() {
        return pghUnidObliSubTipoList;
    }

    public void setPghUnidObliSubTipoList(List<PghUnidObliSubTipo> pghUnidObliSubTipoList) {
        this.pghUnidObliSubTipoList = pghUnidObliSubTipoList;
    }

    public PghObligacionTipo getIdObligacionTipo() {
        return idObligacionTipo;
    }

    public void setIdObligacionTipo(PghObligacionTipo idObligacionTipo) {
        this.idObligacionTipo = idObligacionTipo;
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
        hash += (idObligacionSubTipo != null ? idObligacionSubTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghObligacionSubTipo)) {
            return false;
        }
        PghObligacionSubTipo other = (PghObligacionSubTipo) object;
        if ((this.idObligacionSubTipo == null && other.idObligacionSubTipo != null) || (this.idObligacionSubTipo != null && !this.idObligacionSubTipo.equals(other.idObligacionSubTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Z.PghObligacionSubTipo[ idObligacionSubTipo=" + idObligacionSubTipo + " ]";
    }
    
}
/*OSINE_SFS-480 - RSIS 26 - Fin */