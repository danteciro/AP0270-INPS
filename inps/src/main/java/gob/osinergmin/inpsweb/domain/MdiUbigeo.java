/**
* Resumen.
* Objeto            : MdiUbigeo.java.
* Descripción       : Domain con los atributos del Ubigeo.
* Fecha de Creación : 12/05/2016    
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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "MDI_UBIGEO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiUbigeo.findAll", query = "SELECT m FROM MdiUbigeo m")
})
public class MdiUbigeo {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
    protected MdiUbigeoPK mdiUbigeoPK;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mdiUbigeo", fetch = FetchType.LAZY)
    private List<MdiDirccionUnidadSuprvisada> mdiDirccionUnidadSuprvisadaList;
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @OneToMany(mappedBy = "mdiUbigeo", fetch = FetchType.LAZY)
    private List<PghUbigeoRegion> pghUbigeoRegionList;
    /* OSINE_SFS-480 - RSIS 11 - Fin */

    public MdiUbigeo() {
    }

    public MdiUbigeo(MdiUbigeoPK mdiUbigeoPK) {
        this.mdiUbigeoPK = mdiUbigeoPK;
    }

    public MdiUbigeo(String idDepartamento, String idProvincia, String idDistrito) {
        this.mdiUbigeoPK = new MdiUbigeoPK(idDepartamento, idProvincia, idDistrito);
    }
    public MdiUbigeo(String nombre,String idDepartamento, String idProvincia, String idDistrito) {
        this.nombre = nombre;
        this.mdiUbigeoPK = new MdiUbigeoPK(idDepartamento, idProvincia, idDistrito);
    }

    public MdiUbigeoPK getMdiUbigeoPK() {
        return mdiUbigeoPK;
    }

    public void setMdiUbigeoPK(MdiUbigeoPK mdiUbigeoPK) {
        this.mdiUbigeoPK = mdiUbigeoPK;
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

    @XmlTransient
    public List<MdiDirccionUnidadSuprvisada> getMdiDirccionUnidadSuprvisadaList() {
        return mdiDirccionUnidadSuprvisadaList;
    }

    public void setMdiDirccionUnidadSuprvisadaList(List<MdiDirccionUnidadSuprvisada> mdiDirccionUnidadSuprvisadaList) {
        this.mdiDirccionUnidadSuprvisadaList = mdiDirccionUnidadSuprvisadaList;
    }

    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @XmlTransient
    public List<PghUbigeoRegion> getPghUbigeoRegionList() {
        return pghUbigeoRegionList;
    }

    public void setPghUbigeoRegionList(List<PghUbigeoRegion> pghUbigeoRegionList) {
        this.pghUbigeoRegionList = pghUbigeoRegionList;
    }
    /* OSINE_SFS-480 - RSIS 11 - Fin */
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mdiUbigeoPK != null ? mdiUbigeoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiUbigeo)) {
            return false;
        }
        MdiUbigeo other = (MdiUbigeo) object;
        if ((this.mdiUbigeoPK == null && other.mdiUbigeoPK != null) || (this.mdiUbigeoPK != null && !this.mdiUbigeoPK.equals(other.mdiUbigeoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiUbigeo[ mdiUbigeoPK=" + mdiUbigeoPK + " ]";
    }
    
}
