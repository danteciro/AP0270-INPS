/**
* Resumen.
* Objeto            : MdiSupervisoraEmpresa.java.
* Descripción       : Domain con los atributos del SupervisoraEmpresa.
* Fecha de Creación : 21/05/2016     
* PR de Creación    : OSINE_SFS-480              
* Autor             : Julio Piro.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          			Descripción
* --------------------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/06/2016    Mario Dioses Fernandez    Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).                                                       
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
@Table(name = "MDI_SUPERVISORA_EMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiSupervisoraEmpresa.findAll", query = "SELECT m FROM MdiSupervisoraEmpresa m")
})
public class MdiSupervisoraEmpresa extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SUPERVISORA_EMPRESA")
    private Long idSupervisoraEmpresa;
    @Column(name = "TIPO_EMPRESA_CONSTITUCION")
    private Long tipoEmpresaConstitucion;
    @Size(max = 200)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Size(max = 500)
    @Column(name = "NOMBRE_CONSORCIO")
    private String nombreConsorcio;
    @Size(max = 15)
    @Column(name = "RUC")
    private String ruc;
    @Size(max = 50)
    @Column(name = "CIIU_PRINCIPAL")
    private String ciiuPrincipal;
    @Size(max = 100)
    @Column(name = "PAGINA_WEB")
    private String paginaWeb;
    @Column(name = "ESTADO")
    private Character estado;
    @Size(max = 2)
    @Column(name = "ORIGEN_INFORMACION")
    private String origenInformacion;
    @OneToMany(mappedBy = "idSupervisoraEmpresa", fetch = FetchType.LAZY)
    private List<PghPersonal> pghPersonalList;
    @OneToMany(mappedBy = "idSupervisoraEmpresa", fetch = FetchType.LAZY)
    private List<PghOrdenServicio> pghOrdenServicioList;
    @OneToMany(mappedBy = "idSupervisoraEmpresa", fetch = FetchType.LAZY)
    private List<PghEmprSupeActiObli> pghEmprSupeActiObliList;
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @OneToMany(mappedBy = "idSupervisoraEmpresa", fetch = FetchType.LAZY)
    private List<MdiContratoEmpresaLocador> mdiContratoEmpresaLocadorList;
    /* OSINE_SFS-480 - RSIS 11 - Fin */

    public MdiSupervisoraEmpresa() {
    }

    public MdiSupervisoraEmpresa(Long idSupervisoraEmpresa) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
    }
    public MdiSupervisoraEmpresa(Long idSupervisoraEmpresa,String razonSocial) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
        this.razonSocial = razonSocial;
    }
    
    public MdiSupervisoraEmpresa(Long idSupervisoraEmpresa,String razonSocial,String nombreConsorcio) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
        this.razonSocial=razonSocial;
        this.nombreConsorcio=nombreConsorcio;
    }
    
    public MdiSupervisoraEmpresa(Long idSupervisoraEmpresa, String razonSocial, String nombreConsorcio, String ruc, String paginaWeb,
    		Long tipoEmpresaConstitucion, String ciiuPrincipal, String origenInformacion) {
    	this.idSupervisoraEmpresa = idSupervisoraEmpresa;
        this.razonSocial=razonSocial;
        this.nombreConsorcio=nombreConsorcio;
        this.ruc=ruc;
        this.paginaWeb=paginaWeb;
        this.tipoEmpresaConstitucion=tipoEmpresaConstitucion;
        this.ciiuPrincipal=ciiuPrincipal;
        this.origenInformacion=origenInformacion;
    }

    public String getNombreConsorcio() {
        return nombreConsorcio;
    }

    public void setNombreConsorcio(String nombreConsorcio) {
        this.nombreConsorcio = nombreConsorcio;
    }

    public Long getIdSupervisoraEmpresa() {
        return idSupervisoraEmpresa;
    }

    public void setIdSupervisoraEmpresa(Long idSupervisoraEmpresa) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
    }

    public Long getTipoEmpresaConstitucion() {
        return tipoEmpresaConstitucion;
    }

    public void setTipoEmpresaConstitucion(Long tipoEmpresaConstitucion) {
        this.tipoEmpresaConstitucion = tipoEmpresaConstitucion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCiiuPrincipal() {
        return ciiuPrincipal;
    }

    public void setCiiuPrincipal(String ciiuPrincipal) {
        this.ciiuPrincipal = ciiuPrincipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getOrigenInformacion() {
        return origenInformacion;
    }

    public void setOrigenInformacion(String origenInformacion) {
        this.origenInformacion = origenInformacion;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghPersonal> getPghPersonalList() {
        return pghPersonalList;
    }

    public void setPghPersonalList(List<PghPersonal> pghPersonalList) {
        this.pghPersonalList = pghPersonalList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghOrdenServicio> getPghOrdenServicioList() {
        return pghOrdenServicioList;
    }

    public void setPghOrdenServicioList(List<PghOrdenServicio> pghOrdenServicioList) {
        this.pghOrdenServicioList = pghOrdenServicioList;
    }

    @XmlTransient
    public List<PghEmprSupeActiObli> getPghEmprSupeActiObliList() {
        return pghEmprSupeActiObliList;
    }

    public void setPghEmprSupeActiObliList(List<PghEmprSupeActiObli> pghEmprSupeActiObliList) {
        this.pghEmprSupeActiObliList = pghEmprSupeActiObliList;
    }
    
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @XmlTransient
    public List<MdiContratoEmpresaLocador> getMdiContratoEmpresaLocadorList() {
        return mdiContratoEmpresaLocadorList;
    }

    public void setMdiContratoEmpresaLocadorList(List<MdiContratoEmpresaLocador> mdiContratoEmpresaLocadorList) {
        this.mdiContratoEmpresaLocadorList = mdiContratoEmpresaLocadorList;
    }    
    /* OSINE_SFS-480 - RSIS 11 - Fin */
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSupervisoraEmpresa != null ? idSupervisoraEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiSupervisoraEmpresa)) {
            return false;
        }
        MdiSupervisoraEmpresa other = (MdiSupervisoraEmpresa) object;
        if ((this.idSupervisoraEmpresa == null && other.idSupervisoraEmpresa != null) || (this.idSupervisoraEmpresa != null && !this.idSupervisoraEmpresa.equals(other.idSupervisoraEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.MdiSupervisoraEmpresa[ idSupervisoraEmpresa=" + idSupervisoraEmpresa + " ]";
    }   
}
