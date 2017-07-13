package gob.osinergmin.inpsweb.domain;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_PLAZO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghPlazo.findAll", query = "SELECT p FROM PghPlazo p")
})
public class PghPlazo extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAZO")
    private Long idPlazo;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "ID_UNIDAD_MEDIDA_MAESTRO")
    private Long idUnidadMedidaMaestro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "CODIGO_PLAZO")
    private String codigoPlazo;
    
    public PghPlazo() {
    }

    public PghPlazo(Long idPlazo) {
        this.idPlazo = idPlazo;
    }

    public PghPlazo(Long idPlazo, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idPlazo = idPlazo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdPlazo() {
        return idPlazo;
    }

    public void setIdPlazo(Long idPlazo) {
        this.idPlazo = idPlazo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdUnidadMedidaMaestro() {
        return idUnidadMedidaMaestro;
    }

    public void setIdUnidadMedidaMaestro(Long idUnidadMedidaMaestro) {
        this.idUnidadMedidaMaestro = idUnidadMedidaMaestro;
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
        hash += (idPlazo != null ? idPlazo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghPlazo)) {
            return false;
        }
        PghPlazo other = (PghPlazo) object;
        if ((this.idPlazo == null && other.idPlazo != null) || (this.idPlazo != null && !this.idPlazo.equals(other.idPlazo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghPlazo[ idPlazo=" + idPlazo + " ]";
    }

    /**
     * @return the codigoPlazo
     */
    public String getCodigoPlazo() {
        return codigoPlazo;
    }

    /**
     * @param codigoPlazo the codigoPlazo to set
     */
    public void setCodigoPlazo(String codigoPlazo) {
        this.codigoPlazo = codigoPlazo;
    }
    
}
