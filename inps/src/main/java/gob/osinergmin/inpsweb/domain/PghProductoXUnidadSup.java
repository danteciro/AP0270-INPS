package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_PRODUCTO_X_UNIDAD_SUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProductoXUnidadSup.findAll", query = "SELECT p FROM PghProductoXUnidadSup p")
})
public class PghProductoXUnidadSup extends Auditoria {
    @EmbeddedId
    protected PghProductoXUnidadSupPK pghProductoXUnidadSupPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;

    public PghProductoXUnidadSup() {
    }
    public PghProductoXUnidadSup(Long idUnidadSupervisada,Long idProducto,Long tipoProducto) {
        this.pghProductoXUnidadSupPK = new  PghProductoXUnidadSupPK(idUnidadSupervisada, idProducto, tipoProducto);
    }

    public PghProductoXUnidadSup(PghProductoXUnidadSupPK pghProductoXUnidadSupPK) {
        this.pghProductoXUnidadSupPK = pghProductoXUnidadSupPK;
    }

    public PghProductoXUnidadSup(long idUnidadSupervisada, long idProducto) {
        this.pghProductoXUnidadSupPK = new PghProductoXUnidadSupPK(idUnidadSupervisada, idProducto);
    }

    public PghProductoXUnidadSupPK getPghProductoXUnidadSupPK() {
        return pghProductoXUnidadSupPK;
    }

    public void setPghProductoXUnidadSupPK(PghProductoXUnidadSupPK pghProductoXUnidadSupPK) {
        this.pghProductoXUnidadSupPK = pghProductoXUnidadSupPK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pghProductoXUnidadSupPK != null ? pghProductoXUnidadSupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProductoXUnidadSup)) {
            return false;
        }
        PghProductoXUnidadSup other = (PghProductoXUnidadSup) object;
        if ((this.pghProductoXUnidadSupPK == null && other.pghProductoXUnidadSupPK != null) || (this.pghProductoXUnidadSupPK != null && !this.pghProductoXUnidadSupPK.equals(other.pghProductoXUnidadSupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghProductoXUnidadSup[ pghProductoXUnidadSupPK=" + pghProductoXUnidadSupPK + " ]";
    }
    
}
