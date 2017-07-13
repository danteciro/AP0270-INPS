/**
* Resumen		
* Objeto			: NpsConfiguracionReles.java
* Descripción		: Clase del modelo de dominio NpsConfiguracionReles.
* Fecha de Creación	: 05/10/2016.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "NPS_CONFIGURACION_RELES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsConfiguracionReles.findAll", query = "SELECT n FROM NpsConfiguracionReles n"),
    @NamedQuery(name = "NpsConfiguracionReles.findByIdConfiguracionReles", query = "SELECT n FROM NpsConfiguracionReles n WHERE n.idConfiguracionReles = :idConfiguracionReles"),
    @NamedQuery(name = "NpsConfiguracionReles.findByAnio", query = "SELECT n FROM NpsConfiguracionReles n WHERE n.anio = :anio"),
    @NamedQuery(name = "NpsConfiguracionReles.findByAnioEmpSubEst", query = "SELECT n FROM NpsConfiguracionReles n WHERE to_char(n.anio,'yyyy') = :anio and n.empCodemp = :empresa and n.idSubestacion = :subEstacion and n.estado = '1' "),
    @NamedQuery(name = "NpsConfiguracionReles.findByAnioEmp", query = "SELECT n FROM NpsConfiguracionReles n WHERE to_char(n.anio,'yyyy') = :anio and n.empCodemp = :empresa and n.estado = '1' "),
    @NamedQuery(name = "NpsConfiguracionReles.findByAnioEmpIdZona", query = "SELECT n FROM NpsConfiguracionReles n WHERE to_char(n.anio,'yyyy') = :anio and n.empCodemp = :empresa and n.idZona = :idZona and n.estado = '1' "),
    @NamedQuery(name = "NpsConfiguracionReles.findByEmpCodemp", query = "SELECT n FROM NpsConfiguracionReles n WHERE n.empCodemp = :empCodemp"),
    @NamedQuery(name = "NpsConfiguracionReles.findByIdSubestacion", query = "SELECT n FROM NpsConfiguracionReles n WHERE n.idSubestacion = :idSubestacion"),
    @NamedQuery(name = "NpsConfiguracionReles.findByEstado", query = "SELECT n FROM NpsConfiguracionReles n WHERE n.estado = :estado")})
public class NpsConfiguracionReles extends Auditoria {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONFIGURACION_RELES")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NPS_CONFIGURACION_RELES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idConfiguracionReles;
    @Column(name = "ANIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date anio;
    @Column(name = "EMP_CODEMP")
    private String empCodemp;
    @Column(name = "ID_SUBESTACION")
    private Long idSubestacion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ID_ZONA")
    private Long idZona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConfiguracionReles", fetch = FetchType.LAZY)
    private List<NpsDetaConfReles> npsDetaConfRelesList;

    public NpsConfiguracionReles() {
    }

    public NpsConfiguracionReles(Long idConfiguracionReles) {
        this.idConfiguracionReles = idConfiguracionReles;
    }

    public NpsConfiguracionReles(Long idConfiguracionReles, String estado) {
        this.idConfiguracionReles = idConfiguracionReles;
        this.estado = estado;
    }

    public Long getIdConfiguracionReles() {
        return idConfiguracionReles;
    }

    public void setIdConfiguracionReles(Long idConfiguracionReles) {
        this.idConfiguracionReles = idConfiguracionReles;
    }

    public Date getAnio() {
        return anio;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
    }

    public String getEmpCodemp() {
        return empCodemp;
    }

    public void setEmpCodemp(String empCodemp) {
        this.empCodemp = empCodemp;
    }

    public Long getIdSubestacion() {
        return idSubestacion;
    }

    public void setIdSubestacion(Long idSubestacion) {
        this.idSubestacion = idSubestacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }
    
    @XmlTransient
    public List<NpsDetaConfReles> getNpsDetaConfRelesList() {
        return npsDetaConfRelesList;
    }

    public void setNpsDetaConfRelesList(List<NpsDetaConfReles> npsDetaConfRelesList) {
        this.npsDetaConfRelesList = npsDetaConfRelesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracionReles != null ? idConfiguracionReles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NpsConfiguracionReles)) {
            return false;
        }
        NpsConfiguracionReles other = (NpsConfiguracionReles) object;
        if ((this.idConfiguracionReles == null && other.idConfiguracionReles != null) || (this.idConfiguracionReles != null && !this.idConfiguracionReles.equals(other.idConfiguracionReles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.NpsConfiguracionReles[ idConfiguracionReles=" + idConfiguracionReles + " ]";
    }
    
}
