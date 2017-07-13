package gob.osinergmin.inpsweb.domain;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_COMPLEMENTO_DETSUPERVISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComplementoDetsupervision.findAll", query = "SELECT p FROM PghComplementoDetsupervision p")
})
public class PghComplementoDetsupervision extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPLEMENTO_DETSUPERVISION")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_COMPLEMENTO_DETSUP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idComplementoDetsupervision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_COMENTARIO_DETSUPERVISION", referencedColumnName = "ID_COMENTARIO_DETSUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghComentarioDetsupervision idComentarioDetsupervision;
    @JoinColumn(name = "ID_COMENTARIO_COMPLEMENTO", referencedColumnName = "ID_COMENTARIO_COMPLEMENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghComentarioComplemento idComentarioComplemento;
    @OneToMany(mappedBy = "idComplementoDetsupervision", fetch = FetchType.LAZY)
    private List<PghComplementoDetsupValor> pghComplementoDetsupValorList;

    public PghComplementoDetsupervision() {
    }

    public PghComplementoDetsupervision(Long idComplementoDetsupervision) {
        this.idComplementoDetsupervision = idComplementoDetsupervision;
    }
    
    public PghComplementoDetsupervision(Long idComplementoDetsupervision,Long idComentarioComplemento,Long idComplemento,String etiquetaComentario) {
        this.idComplementoDetsupervision = idComplementoDetsupervision;
        this.idComentarioComplemento = new PghComentarioComplemento(idComentarioComplemento,idComplemento, etiquetaComentario);
    }

    public Long getIdComplementoDetsupervision() {
        return idComplementoDetsupervision;
    }

    public void setIdComplementoDetsupervision(Long idComplementoDetsupervision) {
        this.idComplementoDetsupervision = idComplementoDetsupervision;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghComentarioDetsupervision getIdComentarioDetsupervision() {
        return idComentarioDetsupervision;
    }

    public void setIdComentarioDetsupervision(PghComentarioDetsupervision idComentarioDetsupervision) {
        this.idComentarioDetsupervision = idComentarioDetsupervision;
    }

    public PghComentarioComplemento getIdComentarioComplemento() {
        return idComentarioComplemento;
    }

    public void setIdComentarioComplemento(PghComentarioComplemento idComentarioComplemento) {
        this.idComentarioComplemento = idComentarioComplemento;
    }

    @XmlTransient
    public List<PghComplementoDetsupValor> getPghComplementoDetsupValorList() {
        return pghComplementoDetsupValorList;
    }

    public void setPghComplementoDetsupValorList(List<PghComplementoDetsupValor> pghComplementoDetsupValorList) {
        this.pghComplementoDetsupValorList = pghComplementoDetsupValorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplementoDetsupervision != null ? idComplementoDetsupervision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghComplementoDetsupervision)) {
            return false;
        }
        PghComplementoDetsupervision other = (PghComplementoDetsupervision) object;
        if ((this.idComplementoDetsupervision == null && other.idComplementoDetsupervision != null) || (this.idComplementoDetsupervision != null && !this.idComplementoDetsupervision.equals(other.idComplementoDetsupervision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghComplementoDetsupervision[ idComplementoDetsupervision=" + idComplementoDetsupervision + " ]";
    }
    
}
