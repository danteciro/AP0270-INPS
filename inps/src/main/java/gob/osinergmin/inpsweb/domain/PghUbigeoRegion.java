/**
* Resumen.
* Objeto            : PghUbigeoRegion.java.
* Descripción       : Domain con los atributos del UbigeoRegion.
* Fecha de Creación : 09/06/2016       
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mdiosesf
 */
/* OSINE_SFS-480 - RSIS 11 - Inicio */
@Entity
@Table(name = "PGH_UBIGEO_REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghUbigeoRegion.findAll", query = "SELECT p FROM PghUbigeoRegion p")
})
public class PghUbigeoRegion extends Auditoria {   
    @Id
    @Basic(optional = false)
    @Column(name = "ID_UBIGEO_REGION")
    private Long idUbigeoRegion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID_DEPARTAMENTO"),
        @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA"),
        @JoinColumn(name = "ID_DISTRITO", referencedColumnName = "ID_DISTRITO")})
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiUbigeo mdiUbigeo;
    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiRegion idRegion;

    public PghUbigeoRegion() {
    }

    public PghUbigeoRegion(Long idUbigeoRegion) {
        this.idUbigeoRegion = idUbigeoRegion;
    }

    public PghUbigeoRegion(Long idUbigeoRegion, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idUbigeoRegion = idUbigeoRegion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdUbigeoRegion() {
        return idUbigeoRegion;
    }

    public void setIdUbigeoRegion(Long idUbigeoRegion) {
        this.idUbigeoRegion = idUbigeoRegion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public MdiUbigeo getMdiUbigeo() {
        return mdiUbigeo;
    }

    public void setMdiUbigeo(MdiUbigeo mdiUbigeo) {
        this.mdiUbigeo = mdiUbigeo;
    }

    public MdiRegion getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(MdiRegion idRegion) {
        this.idRegion = idRegion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUbigeoRegion != null ? idUbigeoRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghUbigeoRegion)) {
            return false;
        }
        PghUbigeoRegion other = (PghUbigeoRegion) object;
        if ((this.idUbigeoRegion == null && other.idUbigeoRegion != null) || (this.idUbigeoRegion != null && !this.idUbigeoRegion.equals(other.idUbigeoRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghUbigeoRegion[ idUbigeoRegion=" + idUbigeoRegion + " ]";
    }
}
/* OSINE_SFS-480 - RSIS 11 - Fin */

