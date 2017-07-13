/**
* Resumen.
* Objeto            : MdiRegion.java.
* Descripción       : Domain con los atributos del Region.
* Fecha de Creación : 12/05/2016        
* PR de Creación    : OSINE_SFS-480           
* Autor             : Mario Dioses Fernandez.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          			Descripción
* --------------------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/06/2016    Mario Dioses Fernandez    Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).                                                       
*/

package gob.osinergmin.inpsweb.domain;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author mdiosesf
 */
/* OSINE_SFS-480 - RSIS 11 - Inicio */
@Entity
@Table(name = "MDI_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiRegion.findAll", query = "SELECT m FROM MdiRegion m")
})
public class MdiRegion extends Auditoria {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REGION")
    private Long idRegion;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "JEFE_REGION")
    private String jefeRegion;
    @Column(name = "CORREO_JEFE")
    private String correoJefe;
    @Column(name = "TELEFONO")
    private Character telefono;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idRegion", fetch = FetchType.LAZY)
    private List<PghUbigeoRegion> pghUbigeoRegionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mdiRegion", fetch = FetchType.LAZY)
    private List<MdiMacroRegionXRegion> mdiMacroRegionXRegionList;
    @OneToMany(mappedBy = "idRegion", fetch = FetchType.LAZY)
    private List<PghDestinatarioCorreo> pghDestinatarioCorreoList;

    public MdiRegion() {
    }

    public MdiRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    public MdiRegion(Long idRegion, String nombre, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String estado) {
        this.idRegion = idRegion;
        this.nombre = nombre;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.estado = estado;
    }

    public Long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getJefeRegion() {
        return jefeRegion;
    }

    public void setJefeRegion(String jefeRegion) {
        this.jefeRegion = jefeRegion;
    }

    public String getCorreoJefe() {
        return correoJefe;
    }

    public void setCorreoJefe(String correoJefe) {
        this.correoJefe = correoJefe;
    }

    public Character getTelefono() {
        return telefono;
    }

    public void setTelefono(Character telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PghUbigeoRegion> getPghUbigeoRegionList() {
        return pghUbigeoRegionList;
    }

    public void setPghUbigeoRegionList(List<PghUbigeoRegion> pghUbigeoRegionList) {
        this.pghUbigeoRegionList = pghUbigeoRegionList;
    }
    
    @XmlTransient
    @JsonIgnore
    public List<MdiMacroRegionXRegion> getMdiMacroRegionXRegionList() {
        return mdiMacroRegionXRegionList;
    }

    public void setMdiMacroRegionXRegionList(List<MdiMacroRegionXRegion> mdiMacroRegionXRegionList) {
        this.mdiMacroRegionXRegionList = mdiMacroRegionXRegionList;
    }

    @XmlTransient
    public List<PghDestinatarioCorreo> getPghDestinatarioCorreoList() {
        return pghDestinatarioCorreoList;
    }

    public void setPghDestinatarioCorreoList(List<PghDestinatarioCorreo> pghDestinatarioCorreoList) {
        this.pghDestinatarioCorreoList = pghDestinatarioCorreoList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegion != null ? idRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiRegion)) {
            return false;
        }
        MdiRegion other = (MdiRegion) object;
        if ((this.idRegion == null && other.idRegion != null) || (this.idRegion != null && !this.idRegion.equals(other.idRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiRegion[ idRegion=" + idRegion + " ]";
    }
}
/* OSINE_SFS-480 - RSIS 11 - Fin */
