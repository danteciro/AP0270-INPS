package gob.osinergmin.inpsweb.domain;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jcsar
 */
@Entity
@Table(name = "PGH_COMPLEMENTO_DETSUP_VALOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComplementoDetsupValor.findAll", query = "SELECT p FROM PghComplementoDetsupValor p")
})
public class PghComplementoDetsupValor extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPLEMENTO_DETSUP_VALOR")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_COMPL_DETSUP_VALOR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idComplementoDetsupValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "VALOR_ID")
    private Long valorId;
    @Size(max = 4000)
    @Column(name = "VALOR_DESC")
    private String valorDesc;
    @JoinColumn(name = "ID_COMPLEMENTO_DETSUPERVISION", referencedColumnName = "ID_COMPLEMENTO_DETSUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghComplementoDetsupervision idComplementoDetsupervision;

    public PghComplementoDetsupValor() {
    }

    public PghComplementoDetsupValor(Long idComplementoDetsupValor) {
        this.idComplementoDetsupValor = idComplementoDetsupValor;
    }

    public Long getIdComplementoDetsupValor() {
        return idComplementoDetsupValor;
    }

    public void setIdComplementoDetsupValor(Long idComplementoDetsupValor) {
        this.idComplementoDetsupValor = idComplementoDetsupValor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getValorId() {
        return valorId;
    }

    public void setValorId(Long valorId) {
        this.valorId = valorId;
    }

    public String getValorDesc() {
        return valorDesc;
    }

    public void setValorDesc(String valorDesc) {
        this.valorDesc = valorDesc;
    }

    public PghComplementoDetsupervision getIdComplementoDetsupervision() {
        return idComplementoDetsupervision;
    }

    public void setIdComplementoDetsupervision(PghComplementoDetsupervision idComplementoDetsupervision) {
        this.idComplementoDetsupervision = idComplementoDetsupervision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplementoDetsupValor != null ? idComplementoDetsupValor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghComplementoDetsupValor)) {
            return false;
        }
        PghComplementoDetsupValor other = (PghComplementoDetsupValor) object;
        if ((this.idComplementoDetsupValor == null && other.idComplementoDetsupValor != null) || (this.idComplementoDetsupValor != null && !this.idComplementoDetsupValor.equals(other.idComplementoDetsupValor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghComplementoDetsupValor[ idComplementoDetsupValor=" + idComplementoDetsupValor + " ]";
    }
    
}
