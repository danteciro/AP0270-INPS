package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author dmedrano
 */
@Entity
@Table(name = "PGH_PERSONAL_UNIDAD_ORGANICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghPersonalUnidadOrganica.findAll", query = "SELECT p FROM PghPersonalUnidadOrganica p")
})
public class PghPersonalUnidadOrganica extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERSONAL_UNIDAD_ORGANICA")
    private Long idPersonalUnidadOrganica;
    @Column(name = "FLAG_DEFAULT")
    private String flagDefault;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_PERSONAL", referencedColumnName = "ID_PERSONAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghPersonal idPersonal;
    @JoinColumn(name = "ID_UNIDAD_ORGANICA", referencedColumnName = "ID_UNIDAD_ORGANICA")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiUnidadOrganica idUnidadOrganica;

    public PghPersonalUnidadOrganica() {
    }

    public PghPersonalUnidadOrganica(Long idPersonalUnidadOrganica) {
        this.idPersonalUnidadOrganica = idPersonalUnidadOrganica;
    }

    public PghPersonalUnidadOrganica(Long idPersonalUnidadOrganica, String flagDefault, Long idUnidadOrganica) {
        this.idPersonalUnidadOrganica = idPersonalUnidadOrganica;
        this.flagDefault = flagDefault;
        this.idUnidadOrganica = new MdiUnidadOrganica(idUnidadOrganica);
    }

    public PghPersonalUnidadOrganica(Long idPersonalUnidadOrganica, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idPersonalUnidadOrganica = idPersonalUnidadOrganica;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdPersonalUnidadOrganica() {
        return idPersonalUnidadOrganica;
    }

    public void setIdPersonalUnidadOrganica(Long idPersonalUnidadOrganica) {
        this.idPersonalUnidadOrganica = idPersonalUnidadOrganica;
    }

    public String getFlagDefault() {
        return flagDefault;
    }

    public void setFlagDefault(String flagDefault) {
        this.flagDefault = flagDefault;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghPersonal getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(PghPersonal idPersonal) {
        this.idPersonal = idPersonal;
    }

    public MdiUnidadOrganica getIdUnidadOrganica() {
        return idUnidadOrganica;
    }

    public void setIdUnidadOrganica(MdiUnidadOrganica idUnidadOrganica) {
        this.idUnidadOrganica = idUnidadOrganica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonalUnidadOrganica != null ? idPersonalUnidadOrganica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghPersonalUnidadOrganica)) {
            return false;
        }
        PghPersonalUnidadOrganica other = (PghPersonalUnidadOrganica) object;
        if ((this.idPersonalUnidadOrganica == null && other.idPersonalUnidadOrganica != null) || (this.idPersonalUnidadOrganica != null && !this.idPersonalUnidadOrganica.equals(other.idPersonalUnidadOrganica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.PghPersonalUnidadOrganica[ idPersonalUnidadOrganica=" + idPersonalUnidadOrganica + " ]";
    }
    
}

