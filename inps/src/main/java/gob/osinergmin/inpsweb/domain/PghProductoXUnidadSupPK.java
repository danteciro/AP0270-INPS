package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jpiro
 */
@Embeddable
public class PghProductoXUnidadSupPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UNIDAD_SUPERVISADA")
    private long idUnidadSupervisada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PRODUCTO")
    private long idProducto;
    
    @Transient
    private long idTipoProducto;
    
    
    public PghProductoXUnidadSupPK() {
    }

    public PghProductoXUnidadSupPK(long idUnidadSupervisada, long idProducto) {
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.idProducto = idProducto;
    }
    public PghProductoXUnidadSupPK(long idUnidadSupervisada, long idProducto,Long idTipoProducto) {
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.idProducto = idProducto;
        this.idTipoProducto = idTipoProducto;
    }

    public long getIdUnidadSupervisada() {
        return idUnidadSupervisada;
    }

    public void setIdUnidadSupervisada(long idUnidadSupervisada) {
        this.idUnidadSupervisada = idUnidadSupervisada;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUnidadSupervisada;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProductoXUnidadSupPK)) {
            return false;
        }
        PghProductoXUnidadSupPK other = (PghProductoXUnidadSupPK) object;
        if (this.idUnidadSupervisada != other.idUnidadSupervisada) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghProductoXUnidadSupPK[ idUnidadSupervisada=" + idUnidadSupervisada + ", idProducto=" + idProducto + " ]";
    }

    /**
     * @return the idTipoProducto
     */
    public long getIdTipoProducto() {
        return idTipoProducto;
    }

    /**
     * @param idTipoProducto the idTipoProducto to set
     */
    public void setIdTipoProducto(long idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }
    
}
