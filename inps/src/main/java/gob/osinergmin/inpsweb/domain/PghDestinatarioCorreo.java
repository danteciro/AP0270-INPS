package gob.osinergmin.inpsweb.domain;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_DESTINATARIO_CORREO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghDestinatarioCorreo.findAll", query = "SELECT p FROM PghDestinatarioCorreo p")
})
public class PghDestinatarioCorreo extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DESTINATARIO_CORREO")
    private Long idDestinatarioCorreo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_PERSONAL", referencedColumnName = "ID_PERSONAL")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PghPersonal idPersonal;
    @JoinColumn(name = "ID_CORREO", referencedColumnName = "ID_CORREO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghCorreo idCorreo;
    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiRegion idRegion;

    public PghDestinatarioCorreo() {
    }

    public PghDestinatarioCorreo(Long idDestinatarioCorreo) {
        this.idDestinatarioCorreo = idDestinatarioCorreo;
    }    
    
    public PghDestinatarioCorreo(Long idDestinaCorreo,Long idPersonal,String nombre,String apellido_paterno,String apellido_materno,String nombreComplet,String correo) {
        this.idDestinatarioCorreo = idDestinaCorreo;
        this.idPersonal = new PghPersonal(idPersonal, correo, apellido_paterno, apellido_materno, nombreComplet, correo);
    }

    public Long getIdDestinatarioCorreo() {
        return idDestinatarioCorreo;
    }

    public void setIdDestinatarioCorreo(Long idDestinatarioCorreo) {
        this.idDestinatarioCorreo = idDestinatarioCorreo;
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

    public PghCorreo getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(PghCorreo idCorreo) {
        this.idCorreo = idCorreo;
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
        hash += (idDestinatarioCorreo != null ? idDestinatarioCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghDestinatarioCorreo)) {
            return false;
        }
        PghDestinatarioCorreo other = (PghDestinatarioCorreo) object;
        if ((this.idDestinatarioCorreo == null && other.idDestinatarioCorreo != null) || (this.idDestinatarioCorreo != null && !this.idDestinatarioCorreo.equals(other.idDestinatarioCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.PghDestinatarioCorreo[ idDestinatarioCorreo=" + idDestinatarioCorreo + " ]";
    }
    
}
